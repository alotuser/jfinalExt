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

package com.jfinaljext.config;

import java.util.List;

import com.jfinal.config.Routes;
import com.jfinal.config.Routes.Route;
import com.jfinal.core.Controller;
import com.jfinaljext.aop.JClasser;
import com.jfinaljext.aop.JController;
import com.jfinaljext.aop.JType;
import com.jfinaljext.kit.StrKit;

public class JRoutes{

	private Routes me;
	public JRoutes(Routes me){
		this.me=me;
	} 
	
	/**
	 * Add routes alotuser
	 * @param packagePaths can find controller by key
	 * @return
	 */
	public JRoutes adds(String...packagePaths){
		return scan("",JType.FIRST,packagePaths);
	}

	/**
	 * Add routes alotuser
	 * @param prekey
	 * @param packagePaths
	 * @return
	 */
	public JRoutes scan(String prekey,String...packagePaths){
		return scan(prekey,JType.FIRST,packagePaths);
	}

	/**
	 * Add routes alotuser
	 * @param prekey
	 * @param jtype
	 * @param packagePaths
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JRoutes scan(String prekey,JType jtype,String...packagePaths){
		JType type;
		JController jc;
		String[] values;
		String viewPath;
		String controllerKey;
		Class<? extends Controller> controllerClass;
		for(String packagePath:packagePaths){
			List<Class<?>> clazzs=JClasser.getClassList(packagePath,true,JController.class);
			for(Class<?> clazz:clazzs){
				jc=clazz.getAnnotation(JController.class);
				values=jc.value();
				controllerKey=jc.key();
				viewPath=jc.viewPath();
				type=jc.type();
				if(!JType.accept(type,jtype)||!JClasser.findSuperclass(clazz,Controller.class)){
					continue;
				}
				controllerClass=(Class<? extends Controller>)clazz;
				if(null!=values&&values.length>0){
					controllerKey=values[0];
				}
				viewPath=StrKit.isBlank(viewPath)?controllerKey:viewPath;
				controllerKey=StrKit.joins("/",prekey,controllerKey);
				me.getRouteItemList().add(new Route(controllerKey,controllerClass,viewPath));
			}
		}
		return this;
	}

}
