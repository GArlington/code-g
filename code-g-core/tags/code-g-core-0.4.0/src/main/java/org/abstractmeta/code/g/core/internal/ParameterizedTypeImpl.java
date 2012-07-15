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
package org.abstractmeta.code.g.core.internal;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

public class ParameterizedTypeImpl implements ParameterizedType {
    
    private final Type ownerType;
    private final Type rawType;
    private final Type [] actualTypeArguments;

    public ParameterizedTypeImpl(Type ownerType, Type rawType, Type ... actualTypeArguments) {
        this.ownerType = ownerType;
        this.rawType = rawType;
        this.actualTypeArguments = actualTypeArguments;
    }

    public Type getOwnerType() {
        return ownerType;
    }

    public Type getRawType() {
        return rawType;
    }

    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Type argument : actualTypeArguments) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(",");
            }
            String rawArgumentType = argument instanceof Class ? Class.class.cast(argument).getName() : String.valueOf(argument);
            stringBuilder.append(rawArgumentType);
        }
        String rawTypeName = rawType instanceof Class ? Class.class.cast(rawType).getName() : String.valueOf(rawType);
        return String.format("%s<%s>", rawTypeName, stringBuilder.toString());
    }
}
