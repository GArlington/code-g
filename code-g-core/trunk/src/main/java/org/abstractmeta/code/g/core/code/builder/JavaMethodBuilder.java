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
import org.abstractmeta.code.g.code.JavaModifier;
import org.abstractmeta.code.g.code.JavaParameter;
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


    private List<JavaParameter> parameters = new ArrayList<JavaParameter>();

    private List<Type> exceptionTypes = new ArrayList<Type>();
            
    private List<String> bodyLines = new ArrayList<String>();

    private Type resultType;

    private List<JavaType> javaTypes = new ArrayList<JavaType>();

    private List<JavaTypeBuilderImpl> nestedTypeBuilders = new ArrayList<JavaTypeBuilderImpl>();

    private List<JavaModifier> modifiers = new ArrayList<JavaModifier>();

    private String name;

    private List<Annotation> annotations = new ArrayList<Annotation>();

    private List<String> documentation = new ArrayList<String>();

    public List<JavaParameter> getParameters() {
        return parameters;
    }

    @Override
    public List<Type> getExceptionTypes() {
        return exceptionTypes;
    }

     public JavaMethodBuilder addParameter(String name, Type type) {
           return addParameter(null, name, type);
     }

    public JavaMethodBuilder addParameter(JavaModifier modifier, String name, Type type) {
        JavaParameterBuilder parameterBuilder = new JavaParameterBuilder();
        parameterBuilder.setName(name);
        parameterBuilder.setType(type);
        if(modifier != null) {
            parameterBuilder.addModifiers(modifier);
        }
        this.parameters.add(parameterBuilder.build());
        return this;
    }

    public  JavaMethodBuilder addParameters(JavaParameter ... parameters) {
        Collections.addAll(this.parameters, parameters);
        return this;
    }


    public  JavaMethodBuilder addParameters(Collection<JavaParameter> parameters) {
        this.parameters.addAll(parameters);
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

    public List<String> getBodyLines() {
        return this.bodyLines;
    }

    public JavaMethodBuilder setBodyLines(List<String> bodyLines) {
        this.bodyLines = bodyLines;
        return this;
    }

    public JavaMethodBuilder addBodyLines(String ...bodyLines) {
        Collections.addAll(this.bodyLines, bodyLines);
        return this;
    }

    public JavaMethodBuilder addBody(Collection<String> body) {
        this.bodyLines.addAll(body);
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

    public JavaMethodBuilder addNestedJavaType(JavaType javaType) {
        this.javaTypes.add(javaType);
        return this;
    }

    public JavaMethodBuilder addNestedJavaTypes(Collection<JavaType> javaTypes) {
        this.javaTypes.addAll(javaTypes);
        return this;
    }

    public List<JavaModifier> getModifiers() {
        return this.modifiers;
    }

    public JavaMethodBuilder setModifiers(List<JavaModifier> modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public JavaMethodBuilder addModifier(JavaModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public JavaMethodBuilder addModifiers(Collection<JavaModifier> modifiers) {
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
        for (JavaTypeBuilderImpl builder : nestedTypeBuilders) {
            javaTypes.add(builder.build());
        }
        nestedTypeBuilders.clear();
        JavaMethod result = new JavaMethodImpl(parameters, exceptionTypes, bodyLines, resultType, javaTypes, modifiers, name, annotations, documentation);
        return result;
    }

    public void merge(JavaMethod instance) {

        if(instance.getParameters() != null){
            addParameters(instance.getParameters());
        }
        if(instance.getExceptionTypes() != null){
            addExceptionTypes(instance.getExceptionTypes());
        }
        if (instance.getBodyLines() != null) {
            addBody(instance.getBodyLines());
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