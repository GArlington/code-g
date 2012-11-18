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
package org.abstractmeta.code.g.core.code.builder;

import org.abstractmeta.code.g.code.JavaMethod;
import org.abstractmeta.code.g.code.JavaType;
import org.abstractmeta.code.g.core.code.JavaMethodImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Provide generateBuilder implementation of org.abstractmeta.toolbox.code.JavaMethod
 * This class has been auto-generated by code-g.
 */
public class JavaMethodBuilder implements JavaMethod {


    private List<String> parameterModifiers = new ArrayList<String>();

    private List<Type> parameterTypes = new ArrayList<Type>();

    private List<String> parameterNames = new ArrayList<String>();

    private List<Type> exceptionTypes = new ArrayList<Type>();
            
    private List<String> body = new ArrayList<String>();

    private Type resultType;

    private List<JavaType> javaTypes = new ArrayList<JavaType>();

    private List<JavaTypeBuilder> nestedTypeBuilders = new ArrayList<JavaTypeBuilder>();

    private List<String> modifiers = new ArrayList<String>();

    private String name;

    private List<Annotation> annotations = new ArrayList<Annotation>();

    private List<String> documentation = new ArrayList<String>();

    public List<String> getParameterModifiers() {
        return parameterModifiers;
    }

    public List<Type> getParameterTypes() {
        return this.parameterTypes;
    }

    @Override
    public List<String> getParameterNames() {
        return parameterNames;
    }

    @Override
    public List<Type> getExceptionTypes() {
        return exceptionTypes;
    }

    public JavaMethodBuilder setParameterTypes(List<Type> parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public JavaMethodBuilder addParameterModifiers(Collection<String> parameterModifiers) {
        this.parameterModifiers.addAll(parameterModifiers);
        return this;
    }
    

    public JavaMethodBuilder addParameterTypes(Collection<Type> argumentTypes) {
        this.parameterTypes.addAll(argumentTypes);
        return this;
    }


    public JavaMethodBuilder addParameter(boolean finalModifier, String name, Type type) {
        return addParameter(finalModifier ? "final" : "", name, type);
    }

     public JavaMethodBuilder addParameter(String name, Type type) {
           return addParameter("", name, type);
     }

    public JavaMethodBuilder addParameter(String modifier, String name, Type type) {
        this.parameterModifiers.add(modifier);
        this.parameterNames.add(name);
        this.parameterTypes.add(type);
        return this;
    }

    
    public JavaMethodBuilder setParameterNames(List<String> parameterNames) {
        this.parameterNames = parameterNames;
        return this;
    }

    public JavaMethodBuilder addParameterNames(Collection<String> parameterNames) {
        this.parameterNames.addAll(parameterNames);
        return this;
    }

    public JavaMethodBuilder setExceptionTypes(List<Type> exceptionTypes) {
        this.exceptionTypes = exceptionTypes;
        return this;
    }

    public JavaMethodBuilder addExceptionTypes(Collection<Type> exceptionTypes) {
        this.exceptionTypes.addAll(exceptionTypes);
        return this;
    }

    public JavaMethodBuilder addExceptionTypes(Type ... exceptionTypes) {
        Collections.addAll(this.exceptionTypes, exceptionTypes);
        return this;
    }

    public List<String> getBody() {
        return this.body;
    }

    public JavaMethodBuilder setBody(List<String> body) {
        this.body = body;
        return this;
    }

    public JavaMethodBuilder addBody(String body) {
        this.body.add(body);
        return this;
    }

    public JavaMethodBuilder addBody(Collection<String> body) {
        this.body.addAll(body);
        return this;
    }

    public Type getResultType() {
        return this.resultType;
    }

    public JavaMethodBuilder setResultType(Type resultType) {
        this.resultType = resultType;
        return this;
    }

    public List<JavaType> getNestedJavaTypes() {
        return this.javaTypes;
    }

    public JavaMethodBuilder setNestedJavaTypes(List<JavaType> javaTypes) {
        this.javaTypes = javaTypes;
        return this;
    }

    public JavaTypeBuilder addNestedJavaType() {
        JavaTypeBuilder builder = new JavaTypeBuilder();
        nestedTypeBuilders.add(builder);
        builder.setNested(true);
        return builder;
    }

    public JavaMethodBuilder addNestedJavaType(JavaType javaType) {
        this.javaTypes.add(javaType);
        return this;
    }

    public JavaMethodBuilder addNestedJavaTypes(Collection<JavaType> javaTypes) {
        this.javaTypes.addAll(javaTypes);
        return this;
    }

    public List<String> getModifiers() {
        return this.modifiers;
    }

    public JavaMethodBuilder setModifiers(List<String> modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public JavaMethodBuilder addModifier(String modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public JavaMethodBuilder addModifiers(Collection<String> modifiers) {
        this.modifiers.addAll(modifiers);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public JavaMethodBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public JavaMethodBuilder setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
        return this;
    }

    public JavaMethodBuilder addAnnotation(Annotation annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public JavaMethodBuilder addAnnotations(Collection<Annotation> annotations) {
        this.annotations.addAll(annotations);
        return this;
    }

    public List<String> getDocumentation() {
        return this.documentation;
    }

    public JavaMethodBuilder setDocumentation(List<String> documentation) {
        this.documentation = documentation;
        return this;
    }

    public JavaMethodBuilder addDocumentation(String documentation) {
        this.documentation.add(documentation);
        return this;
    }

    public JavaMethodBuilder addDocumentation(Collection<String> documentation) {
        this.documentation.addAll(documentation);
        return this;
    }

    public JavaMethod build() {
        for (JavaTypeBuilder builder : nestedTypeBuilders) {
            javaTypes.add(builder.build());
        }
        nestedTypeBuilders.clear();
        JavaMethod result = new JavaMethodImpl(parameterModifiers, parameterTypes, parameterNames, exceptionTypes, body, resultType, javaTypes, modifiers, name, annotations, documentation);
        return result;
    }

    public void merge(JavaMethod instance) {
        if (instance.getParameterModifiers() != null) {
            addParameterModifiers(instance.getParameterModifiers());
        }
        if (instance.getParameterTypes() != null) {
            addParameterTypes(instance.getParameterTypes());
        }
        if(instance.getParameterNames() != null){
            addParameterNames(instance.getParameterNames());
        }
        if(instance.getExceptionTypes() != null){
            addExceptionTypes(instance.getExceptionTypes());
        }
        if (instance.getBody() != null) {
            addBody(instance.getBody());
        }
        if (instance.getResultType() != null) {
            setResultType(instance.getResultType());
        }
        if (instance.getNestedJavaTypes() != null) {
            addNestedJavaTypes(instance.getNestedJavaTypes());
        }
        if (instance.getModifiers() != null) {
            addModifiers(instance.getModifiers());
        }
        if (instance.getName() != null) {
            setName(instance.getName());
        }
        if (instance.getAnnotations() != null) {
            addAnnotations(instance.getAnnotations());
        }
        if (instance.getDocumentation() != null) {
            addDocumentation(instance.getDocumentation());
        }

    }

  
}