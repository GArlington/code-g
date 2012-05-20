/**
 * Copyright 2011 Adrian Witas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.abstractmeta.code.g.core.builder;

import org.abstractmeta.code.g.code.JavaType;
import org.abstractmeta.code.g.core.code.builder.JavaTypeBuilder;
import org.abstractmeta.code.g.core.handler.*;

/**
 * SimpleClassBuilder
 *
 * @author Adrian Witas
 */
public class SimpleClassBuilder extends JavaTypeBuilder {

    public SimpleClassBuilder(JavaType sourceType) {
        super();
        setSourceType(sourceType);
        addFieldHandler(new RegistryFieldHandler(this));
        addFieldHandler(new CollectionFieldHandler(this));
        addFieldHandler(new SetterFieldHandler(this));
        addFieldHandler(new GetterFieldHandler(this));
        addTypeHandler(new SimpleTypeHandler(this));
    }

}