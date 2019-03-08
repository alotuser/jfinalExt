/**
 * Copyright (c) 2011-2019, alotuser (alotuser@126.com).
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

package com.jfinaljext.core;

import com.jfinaljext.core.model.ModelFilter;
import com.jfinaljext.core.model.ModelFilterType;

/**
 * Controller
 * 	FINESQI	<BR>
 *	JAYQI	<BR>
 *	CHILEQI	<BR>
 *	7GBOY	<BR>
 */
public abstract class Controller extends com.jfinal.core.Controller{

	// TODO public <T> List<T> getModels(Class<T> modelClass, String modelName) {}
	// TODO -----START----- public <T> List<T> getModels(Class<T> modelClass, String modelName) {}

	public <T> T getModel(Class<T> modelClass,ModelFilter modelFilter){
		return (T)Injector.injectModel(modelClass,super.getRequest(),false,modelFilter);
	}

	public <T> T getModel(Class<T> modelClass,String...paraNames){
		return (T)Injector.injectModel(modelClass,super.getRequest(),false,ModelFilterType.ACCEPT,paraNames);
	}

	public <T> T getModel(Class<T> modelClass,ModelFilterType modelFilterType,String...paraNames){
		return (T)Injector.injectModel(modelClass,super.getRequest(),false,modelFilterType,paraNames);
	}

	public <T> T getModel(Class<T> modelClass,boolean skipConvertError,ModelFilter modelFilter){
		return (T)Injector.injectModel(modelClass,super.getRequest(),skipConvertError,modelFilter);
	}

	public <T> T getModel(Class<T> modelClass,boolean skipConvertError,String...paraNames){
		return (T)Injector.injectModel(modelClass,super.getRequest(),skipConvertError,ModelFilterType.ACCEPT,paraNames);
	}

	public <T> T getModel(Class<T> modelClass,boolean skipConvertError,ModelFilterType modelFilterType,String...paraNames){
		return (T)Injector.injectModel(modelClass,super.getRequest(),skipConvertError,modelFilterType,paraNames);
	}

	public <T> T getModel(Class<T> modelClass,String modelName,ModelFilter modelFilter){
		return (T)Injector.injectModel(modelClass,modelName,super.getRequest(),false,modelFilter,ModelFilterType.NONE,modelName);
	}

	public <T> T getModel(Class<T> modelClass,String modelName,String...paraNames){
		return (T)Injector.injectModel(modelClass,modelName,super.getRequest(),false,null,ModelFilterType.ACCEPT,paraNames);
	}

	public <T> T getModel(Class<T> modelClass,String modelName,ModelFilterType modelFilterType,String...paraNames){
		return (T)Injector.injectModel(modelClass,modelName,super.getRequest(),false,null,modelFilterType,paraNames);
	}

	public <T> T getModel(Class<T> modelClass,String modelName,boolean skipConvertError,ModelFilter modelFilter){
		return (T)Injector.injectModel(modelClass,modelName,super.getRequest(),skipConvertError,modelFilter,ModelFilterType.NONE,modelName);
	}

	public <T> T getModel(Class<T> modelClass,String modelName,boolean skipConvertError,String...paraNames){
		return (T)Injector.injectModel(modelClass,modelName,super.getRequest(),skipConvertError,null,ModelFilterType.ACCEPT,paraNames);
	}

	public <T> T getModel(Class<T> modelClass,String modelName,boolean skipConvertError,ModelFilterType modelFilterType,String...paraNames){
		return (T)Injector.injectModel(modelClass,modelName,super.getRequest(),skipConvertError,null,modelFilterType,paraNames);
	}

	// --------END--------

	public <T> T getBean(Class<T> beanClass,ModelFilter modelFilter){
		return (T)Injector.injectBean(beanClass,super.getRequest(),false,modelFilter);
	}

	public <T> T getBean(Class<T> beanClass,String...paraNames){
		return (T)Injector.injectBean(beanClass,super.getRequest(),false,ModelFilterType.ACCEPT,paraNames);
	}

	public <T> T getBean(Class<T> beanClass,ModelFilterType modelFilterType,String...paraNames){
		return (T)Injector.injectBean(beanClass,super.getRequest(),false,modelFilterType,paraNames);
	}

	public <T> T getBean(Class<T> beanClass,boolean skipConvertError,ModelFilter modelFilter){
		return (T)Injector.injectBean(beanClass,super.getRequest(),skipConvertError,modelFilter);
	}

	public <T> T getBean(Class<T> beanClass,boolean skipConvertError,String...paraNames){
		return (T)Injector.injectBean(beanClass,super.getRequest(),skipConvertError,ModelFilterType.ACCEPT,paraNames);
	}

	public <T> T getBean(Class<T> beanClass,boolean skipConvertError,ModelFilterType modelFilterType,String...paraNames){
		return (T)Injector.injectBean(beanClass,super.getRequest(),skipConvertError,modelFilterType,paraNames);
	}

	public <T> T getBean(Class<T> beanClass,String beanName,ModelFilter modelFilter){
		return (T)Injector.injectBean(beanClass,beanName,super.getRequest(),false,modelFilter,ModelFilterType.NONE,beanName);
	}

	public <T> T getBean(Class<T> beanClass,String beanName,String...paraNames){
		return (T)Injector.injectBean(beanClass,beanName,super.getRequest(),false,null,ModelFilterType.ACCEPT,paraNames);
	}

	public <T> T getBean(Class<T> beanClass,String beanName,ModelFilterType modelFilterType,String...paraNames){
		return (T)Injector.injectBean(beanClass,beanName,super.getRequest(),false,null,modelFilterType,paraNames);
	}

	public <T> T getBean(Class<T> beanClass,String beanName,boolean skipConvertError,ModelFilter modelFilter){
		return (T)Injector.injectBean(beanClass,beanName,super.getRequest(),skipConvertError,modelFilter,ModelFilterType.NONE,beanName);
	}

	public <T> T getBean(Class<T> beanClass,String beanName,boolean skipConvertError,String...paraNames){
		return (T)Injector.injectBean(beanClass,beanName,super.getRequest(),skipConvertError,null,ModelFilterType.ACCEPT,paraNames);
	}

	public <T> T getBean(Class<T> beanClass,String beanName,boolean skipConvertError,ModelFilterType modelFilterType,String...paraNames){
		return (T)Injector.injectBean(beanClass,beanName,super.getRequest(),skipConvertError,null,modelFilterType,paraNames);
	}

	// --------

}
