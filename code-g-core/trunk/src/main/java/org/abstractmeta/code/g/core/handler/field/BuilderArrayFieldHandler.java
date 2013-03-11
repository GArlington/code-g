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
package org.abstractmeta.code.g.core.handler.field;

/**
 * This handle creates 'add', 'clear' methods for any arrays type field.
 * <p>For instance for the given source type
 * <code><pre>
 * class Bar {
 *      ...
 *      private int [] bars;
 *      ...
 *  }
 * </pre></code> the following code is generated
 * <code><pre>
 * public FooBuilder addBars(int ... bars) {
 *     int [] temp = new int[this.bars.length + bars.length];
 *     System.arraycopy(this.bars, 0, temp, 0, this.bars.length);
 *     System.arraycopy(bars, 0, temp, this.bars.length, bars.length);
 *     this.bars = temp;
 *     this._bars = true;
 *     return this;
 * }
 *
 * public FooBuilder addBars(Collection&lt;Integer&gt; bars) {
 *     int [] temp = new int[this.bars.length + bars.size()];
 *     System.arraycopy(this.bars, 0, temp, 0, this.bars.length);
 *     int i = this.bars.length;
 *     for(int item: bars) temp[i++] = item;
 *     this.bars = temp;
 *     this._bars = true;
 *     return this;
 * }
 *
 * public FooBuilder clearBars() {
 *     this.bars = new int[]{};
 *     return this;
 * }
 * <p/>
 * <p/>
 * </pre></code>
 * </p>
 *
 * @author Adrian Witas
 */
public class BuilderArrayFieldHandler  {

//    private final JavaTypeBuilderImpl ownerTypeBuilder;
//    private final Descriptor descriptor;
//
//    public BuilderArrayFieldHandler(JavaTypeBuilderImpl ownerTypeBuilder, Descriptor descriptor) {
//        this.ownerTypeBuilder = ownerTypeBuilder;
//        this.descriptor =descriptor;
//    }
//
//    //TODO add generic array type support
//    @Override
//    public void handle(JavaType sourceType, JavaField javaField) {
//        Type fieldType = javaField.getType();
//        Class rawFieldType = ReflectUtil.getRawClass(fieldType);
//        String fieldName = javaField.getName();
//        if (rawFieldType.isArray()) {
//            String methodName = StringUtil.format(CaseFormat.LOWER_CAMEL, "add", fieldName, CaseFormat.LOWER_CAMEL);
//            if (!ownerTypeBuilder.containsMethod(methodName)) {
//                addArrayAddItemMethod(ownerTypeBuilder, javaField.getName(), javaField.getType());
//                addArrayAddItemsMethod(ownerTypeBuilder, javaField.getName(), javaField.getType());
//                addCollectionClearMethod(ownerTypeBuilder, javaField.getName(), javaField.getType());
//            }
//        }
//    }
//
//    protected void addArrayAddItemsMethod(JavaTypeBuilderImpl typeBuilder, String fieldName, Type fieldType) {
//        String methodName = StringUtil.format(CaseFormat.LOWER_CAMEL, "add", fieldName, CaseFormat.LOWER_CAMEL);
//        Class componentType = ReflectUtil.getRawClass(fieldType).getComponentType();
//        JavaMethodBuilder methodBuilder =  new  JavaMethodBuilder();
//        methodBuilder.addModifier("public");
//        methodBuilder.setResultType(new TypeNameWrapper(typeBuilder.getName()));
//        methodBuilder.setName(methodName);
//        methodBuilder.addParameter(fieldName, new ParameterizedTypeImpl(null, Collection.class, ReflectUtil.getObjectType(componentType)));
//        methodBuilder.addBodyLines(String.format("%s [] temp = new %s[this.%s.length + %s.size()];", componentType.getSimpleName(), componentType.getSimpleName(), fieldName, fieldName));
//
//        methodBuilder.addBodyLines(String.format("System.arraycopy(this.%s, 0, temp, 0, this.%s.length);", fieldName, fieldName));
//        methodBuilder.addBodyLines(String.format("int i = this.%s.length;", fieldName));
//        methodBuilder.addBodyLines(String.format("for(%s item: %s) temp[i++] = item;", componentType.getSimpleName(), fieldName));
//        methodBuilder.addBodyLines(String.format("this.%s = temp;", fieldName));
//        if(DescriptorUtil.is(descriptor, BuilderGeneratorPlugin.ADD_PRESENT_METHOD)) {
//            methodBuilder.addBodyLines(String.format("this.%s = true;", StringUtil.isPresentFieldName(fieldName)));
//        }
//        methodBuilder.addBodyLines("return this;");
//        typeBuilder.addMethod(methodBuilder.build());
//    }
//
//    protected void addArrayAddItemMethod(JavaTypeBuilderImpl typeBuilder, String fieldName, Type fieldType) {
//        String methodName = StringUtil.format(CaseFormat.LOWER_CAMEL, "add", fieldName, CaseFormat.LOWER_CAMEL);
//        Class componentType = ReflectUtil.getRawClass(fieldType).getComponentType();
//        JavaMethodBuilder methodBuilder =  new  JavaMethodBuilder();
//        methodBuilder.addModifier("public");
//        methodBuilder.setResultType(new TypeNameWrapper(typeBuilder.getName()));
//        methodBuilder.setName(methodName);
//        methodBuilder.addParameter("... " + fieldName, componentType);
//        methodBuilder.addBodyLines(String.format("%s [] temp = new %s[this.%s.length + %s.length];", componentType.getSimpleName(), componentType.getSimpleName(), fieldName, fieldName));
//        methodBuilder.addBodyLines(String.format("System.arraycopy(this.%s, 0, temp, 0, this.%s.length);", fieldName, fieldName));
//        methodBuilder.addBodyLines(String.format("System.arraycopy(%s, 0, temp, this.%s.length, %s.length);", fieldName, fieldName, fieldName));
//        methodBuilder.addBodyLines(String.format("this.%s = temp;", fieldName));
//        if(DescriptorUtil.is(descriptor, BuilderGeneratorPlugin.ADD_PRESENT_METHOD)) {
//            methodBuilder.addBodyLines(String.format("this.%s = true;", StringUtil.isPresentFieldName(fieldName)));
//        }
//        methodBuilder.addBodyLines("return this;");
//        typeBuilder.addMethod(methodBuilder.build());
//    }
//
//    protected void addCollectionClearMethod(JavaTypeBuilderImpl typeBuilder, String fieldName, Type fieldType) {
//        String methodName = StringUtil.format(CaseFormat.LOWER_CAMEL, "clear", fieldName, CaseFormat.LOWER_CAMEL);
//        if (!typeBuilder.containsMethod(methodName)) {
//            JavaMethodBuilder methodBuilder = new JavaMethodBuilder();
//            methodBuilder.addModifier("public");
//            methodBuilder.setResultType(new TypeNameWrapper(typeBuilder.getName()));
//            methodBuilder.setName(methodName);
//            Class rawClass = ReflectUtil.getRawClass(fieldType).getComponentType();
//            methodBuilder.addBodyLines(String.format("this.%s = new %s[]{};", fieldName, rawClass.getSimpleName()));
//            methodBuilder.addBodyLines("return this;");
//            typeBuilder.addMethod(methodBuilder.build());
//        }
//    }

}
