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
package com.jfinaljext.core.model;
/**
 * 
 * ModelFilter
 *
 */
public abstract class ModelFilter {
	/**
     * Tests whether or not the specified abstract paraName should be
     * included in a paraName list.
     *
     * @param  paraName  The abstract pathname to be tested
     * @return  <code>true</code> if and only if <code>paraName</code>
     *          should be included
     */
	public abstract boolean accept(String paraName);
	/**
     * Tests whether or not the specified abstract paraName should be
     * included in a paraName list.
     *
     * @param  paraName  The abstract pathname to be tested
     * @return  <code>true</code> if and only if <code>paraName</code>
     *          should be included
     */
	public abstract boolean refuse(String paraName);
}
