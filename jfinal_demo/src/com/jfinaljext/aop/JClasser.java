/**
 * Copyright (c) 2011-2017, alotuser (alotuser@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinaljext.aop;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public class JClasser {
	
	/**
	 * 是否存在该父类
	 * @param clazz
	 * @param type
	 * @return
	 */
	public static boolean findSuperclass(Class<?> clazz, Class<?> type) {
		boolean isok = false;
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			searchType = searchType.getSuperclass();
			if (searchType.equals(type)) {
				isok = true;
				break;
			}
		}
		return isok;
	}
	/**
	 * 是否存在该字段
	 * @param clazz
	 * @param name
	 * @param type
	 * @return
	 */
	public static Field findField(Class<?> clazz, String name, Class<?> type) {
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if (name == null || name.equals(field.getName()) && (type == null || type.equals(field.getType()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 获取指定包下的Class
	 * @param pkgName
	 * @param isRecursive
	 * @param annotation
	 * @return
	 */
	public static List<Class<?>> getClassList(String pkgName, boolean isRecursive, Class<? extends Annotation> annotation) {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try {
			// 按文件的形式去查找
			String strFile = pkgName.replaceAll("\\.", "/");
			Enumeration<URL> urls = loader.getResources(strFile);// 路径重复?
			List<String> urlKeys = new ArrayList<String>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				if (url != null) {
					String protocol = url.getProtocol();
					String pkgPath = url.getPath();
					if (urlKeys.contains(pkgPath)) {
						continue;
					} else {
						urlKeys.add(pkgPath);
					}
					pkgPath = URLDecoder.decode(pkgPath, "utf-8");
					if ("file".equals(protocol)) {
						// 本地自己可见的代码
						findClassName(classList, pkgName, pkgPath, isRecursive, annotation);
					} else if ("jar".equals(protocol)) {
						// 引用第三方jar的代码
						findClassName(classList, pkgName, url, isRecursive, annotation);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return classList;
	}
	/**
	 * 新增本地可见的类
	 * @param clazzList
	 * @param pkgName
	 * @param pkgPath
	 * @param isRecursive
	 * @param annotation
	 */
	public static void findClassName(List<Class<?>> clazzList, String pkgName, String pkgPath, boolean isRecursive, Class<? extends Annotation> annotation) {
		if (clazzList == null) {
			return;
		}
		File[] files = filterClassFiles(pkgPath);// 过滤出.class文件及文件夹
		if (files != null) {
			for (File f : files) {
				String fileName = f.getName();
				if (f.isFile()) {
					// .class 文件的情况
					String clazzName = getClassName(pkgName, fileName);
					addClassName(clazzList, clazzName, annotation);
				} else {
					// 文件夹的情况
					if (isRecursive) {
						// 需要继续查找该文件夹/包名下的类
						String subPkgName = pkgName + "." + fileName;
						String subPkgPath = pkgPath + "/" + fileName;
						findClassName(clazzList, subPkgName, subPkgPath, true, annotation);
					}
				}
			}
		}
	}
	

	/**
	 * 第三方Jar类库的引用
	 * 
	 * @throws IOException
	 */
	private static void findClassName(List<Class<?>> clazzList, String pkgName, URL url, boolean isRecursive, Class<? extends Annotation> annotation) throws IOException {
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			String jarEntryName = jarEntry.getName(); // 类似：sun/security/internal/interfaces/TlsMasterSecret.class
			String clazzName = jarEntryName.replace("/", ".");
			int endIndex = clazzName.lastIndexOf(".");
			String prefix = null;
			if (endIndex > 0) {
				String prefix_name = clazzName.substring(0, endIndex);
				endIndex = prefix_name.lastIndexOf(".");
				if (endIndex > 0) {
					prefix = prefix_name.substring(0, endIndex);
				}
			}
			if (prefix != null && jarEntryName.endsWith(".class")) {
				if (prefix.equals(pkgName)) {
					addClassName(clazzList, clazzName, annotation);
				} else if (isRecursive && prefix.startsWith(pkgName)) {
					// 遍历子包名：子类
					addClassName(clazzList, clazzName, annotation);
				}
			}
		}
	}

	private static File[] filterClassFiles(String pkgPath) {
		if (pkgPath == null) {
			return null;
		}
		FileFilter ff = new FileFilter() {
			@Override
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
			}
		};
		// 接收 .class 文件 或 类文件夹
		return new File(pkgPath).listFiles(ff);
	}

	private static String getClassName(String pkgName, String fileName) {
		int endIndex = fileName.lastIndexOf(".");
		String clazz = null;
		if (endIndex >= 0) {
			clazz = fileName.substring(0, endIndex);
		}
		String clazzName = null;
		if (clazz != null) {
			clazzName = pkgName + "." + clazz;
		}
		return clazzName;
	}

	private static void addClassName(List<Class<?>> clazzList, String clazzName, Class<? extends Annotation> annotation) {
		if (clazzList != null && clazzName != null) {
			Class<?> clazz = null;
			try {
				clazz = Class.forName(clazzName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (clazz != null) {
				if (annotation == null) {
					clazzList.add(clazz);
				} else if (clazz.isAnnotationPresent(annotation)) {
					clazzList.add(clazz);
				}
			}
		}
	}

	public static void main(String[] args) {
		// 标识是否要遍历该包路径下子包的类名
		// boolean recursive = false;
		boolean recursive = true;
		// 指定的包名
		String pkg = "com.demo.blog";
		List<Class<?>> list = null;
		// list = getClassList(pkg, recursive, null);
		list = getClassList(pkg, recursive, JController.class);

		for (int i = 0; i < list.size(); i++) {
			JController jc = list.get(i).getAnnotation(JController.class);
			String[] vs = jc.value();
			String key = jc.key(), viewPath = jc.viewPath();
			JType type = jc.type();
			if (type != JType.FIRST) {
				continue;
			}
			if (vs.length > 0) {

			} else {
				if (StrKit.isBlank(viewPath)) {

				}
			}
			System.out.println(key+"\t"+findSuperclass(list.get(i), Controller.class));
		}

	}

}
