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

import javax.sql.DataSource;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Config;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Table;
import com.jfinaljext.aop.JClasser;
import com.jfinaljext.aop.JModel;
import com.jfinaljext.aop.JType;
import com.jfinaljext.kit.StrKit;

public class JActiveRecordPlugin extends ActiveRecordPlugin{

	public JActiveRecordPlugin(Config config){
		super(config);
	}

	public JActiveRecordPlugin(DataSource dataSource,int transactionLevel){
		super(dataSource,transactionLevel);
	}

	public JActiveRecordPlugin(DataSource dataSource){
		super(dataSource);
	}

	public JActiveRecordPlugin(IDataSourceProvider dataSourceProvider,int transactionLevel){
		super(dataSourceProvider,transactionLevel);
	}

	public JActiveRecordPlugin(IDataSourceProvider dataSourceProvider){
		super(dataSourceProvider);
	}

	public JActiveRecordPlugin(String configName,DataSource dataSource,int transactionLevel){
		super(configName,dataSource,transactionLevel);
	}

	public JActiveRecordPlugin(String configName,DataSource dataSource){
		super(configName,dataSource);
	}

	public JActiveRecordPlugin(String configName,IDataSourceProvider dataSourceProvider,int transactionLevel){
		super(configName,dataSourceProvider,transactionLevel);
	}

	public JActiveRecordPlugin(String configName,IDataSourceProvider dataSourceProvider){
		super(configName,dataSourceProvider);
	}

	/**
	 * Add Model   alotuser
	 * @param packagePaths
	 * @return
	 */
	public JActiveRecordPlugin addMapping(String...packagePaths){
		return addMapping(JType.FIRST,packagePaths);
	}

	/**
	 * Add Model   alotuser
	 * @param jtype
	 * @param packagePaths
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public JActiveRecordPlugin addMapping(JType jtype,String...packagePaths){

		JType type;
		JModel jm;
		String[] values;
		String primaryKey;
		String tableName;
		Class<? extends Model<?>> modelClass;
		for(String packagePath:packagePaths){
			List<Class<?>> clazzs=JClasser.getClassList(packagePath,true,JModel.class);
			for(Class<?> clazz:clazzs){
				jm=clazz.getAnnotation(JModel.class);
				values=jm.value();
				tableName=jm.tableName();
				primaryKey=jm.primaryKey();
				type=jm.type();

				if(!JType.accept(type,jtype)||!JClasser.findSuperclass(clazz,Model.class)){
					continue;
				}
				modelClass=(Class<? extends Model<?>>)clazz;
				if(null!=values&&values.length>0){
					tableName=values[0];
				}
				if(!StrKit.isBlank(tableName)&&!StrKit.isBlank(primaryKey)){
					tableList.add(new Table(tableName,primaryKey,modelClass));
				}else if(!StrKit.isBlank(tableName)&&StrKit.isBlank(primaryKey)){
					tableList.add(new Table(tableName,modelClass));
				}
			}
		}
		return this;
	}

}
