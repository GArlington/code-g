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


import org.abstractmeta.code.g.code.JavaField;
import org.abstractmeta.code.g.core.code.JavaFieldImpl;


import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 * Provide generateBuilder implementation of org.abstractmeta.toolbox.code.JavaField
 * This class has been auto-generated by code-g.
 */
public class JavaFieldBuilder implements JavaField {


    private Type type;

    private String initBody;

    private List<String> modifiers = new ArrayList<String>();

    private String name;

    private List<Annotation> annotations = new ArrayList<Annotation>();

    private List<String> documentation = new ArrayList<String>();

    private boolean immutable;

    public Type getType() {
        return this.type;
    }

    @Override
    public boolean isImmutable() {
        return immutable;
    }

    public JavaFieldBuilder setType(Type type) {
        this.type = type;
        return this;
    }

    public String getInitBody() {
        return this.initBody;
    }

    public JavaFieldBuilder setInitBody(String initBody) {
        this.initBody = initBody;
        return this;
    }

    public List<String> getModifiers() {
        return this.modifiers;
    }

    public JavaFieldBuilder setModifiers(List<String> modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public JavaFieldBuilder addModifier(String modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public JavaFieldBuilder addModifiers(Collection<String> modifiers) {
        this.modifiers.addAll(modifiers);
        return this;
    }

    public JavaFieldBuilder addModifiers(String ... modifiers) {
        Collections.addAll(this.modifiers, modifiers);
        return this;
    }

    public String getName() {
        return this.name;
    }

    public JavaFieldBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public List<Annotation> getAnnotations() {
        return this.annotations;
    }

    public JavaFieldBuilder setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
        return this;
    }

    public JavaFieldBuilder addAnnotation(Annotation annotation) {
        this.annotations.add(annotation);
        return this;
    }

    public JavaFieldBuilder addAnnotations(Collection<Annotation> annotations) {
        this.annotations.addAll(annotations);
        return this;
    }

    public List<String> getDocumentation() {
        return this.documentation;
    }

    public JavaFieldBuilder setDocumentation(List<String> documentation) {
        this.documentation = documentation;
        return this;
    }

    public JavaFieldBuilder addDocumentation(String documentation) {
        this.documentation.add(documentation);
        return this;
    }

    public JavaFieldBuilder addDocumentation(Collection<String> documentation) {
        this.documentation.addAll(documentation);
        return this;
    }


    public JavaFieldBuilder setImmutable(boolean immutable) {
        this.immutable = immutable;
        return this;
    }

    public JavaField build() {
        JavaFieldImpl result = new JavaFieldImpl(type, initBody, modifiers, name, annotations, documentation, immutable);
        return result;
    }

    public void merge(JavaField instance) {
        if (instance.getType() != null) {
            setType(instance.getType());
        }
        if (instance.getInitBody() != null) {
            setInitBody(instance.getInitBody());
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