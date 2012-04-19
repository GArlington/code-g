package org.abstractmeta.code.g.core.handler;

import org.abstractmeta.code.g.code.JavaField;
import org.abstractmeta.code.g.code.JavaType;
import org.abstractmeta.code.g.core.code.builder.JavaMethodBuilder;
import org.abstractmeta.code.g.core.code.builder.JavaTypeBuilder;
import org.abstractmeta.code.g.core.internal.TypeNameWrapper;
import org.abstractmeta.code.g.core.util.ReflectUtil;
import org.abstractmeta.code.g.core.util.StringUtil;
import org.abstractmeta.code.g.handler.JavaFieldHandler;
import com.google.common.base.CaseFormat;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * This handler creates 'add', 'clear' methods for any Map type field.
 * For instance for the given source type
 * <code><pre>
 * class Foo {
 *      ...
 *      private Map<&lt;String, String> bars;
 *      ...
 * }
 * </code></pre>
 * The following code is generated
 * <code><pre>
 * public FooBuilder addBars(String key, String value) {
 *     this.bars.put(key, value);
 *     this._bars = true;
 *     return this;
 * }
 * <p/>
 * public FooBuilder addBars(Map&lt;String, String> bars) {
 *     this.bars.putAll(bars);
 *     this._bars = true;
 *     return this;
 * }
 * <p/>
 * public FooBuilder clearBars() {
 *    this.bars.clear();
 *    return this;
 * }
 * </pre></code>
 * </p>
 *
 * @author Adrian Witas
 */
public class BuilderMapFieldHandler implements JavaFieldHandler {

    private final JavaTypeBuilder ownerTypeBuilder;

    public BuilderMapFieldHandler(JavaTypeBuilder ownerTypeBuilder) {
        this.ownerTypeBuilder = ownerTypeBuilder;
    }


    @Override
    public void handle(JavaType sourceType, JavaField javaField) {
        Type fieldType = javaField.getType();
        Class rawFieldType = ReflectUtil.getRawClass(fieldType);
        if (Map.class.isAssignableFrom(rawFieldType)) {
            addCollectionAddItemMethod(ownerTypeBuilder, javaField.getName(), javaField.getType());
            addCollectionAddItemsMethod(ownerTypeBuilder, javaField.getName(), javaField.getType());
            addCollectionClearMethod(ownerTypeBuilder, javaField.getName());
        }
    }

    protected void addCollectionAddItemsMethod(JavaTypeBuilder typeBuilder, String fieldName, Type fieldType) {
        String methodName = StringUtil.format(CaseFormat.LOWER_CAMEL, "add", fieldName, CaseFormat.LOWER_CAMEL);
        if (!typeBuilder.containsMethod(methodName)) {
            JavaMethodBuilder methodBuilder = new JavaMethodBuilder();
            methodBuilder.addModifier("public");
            methodBuilder.setResultType(new TypeNameWrapper(typeBuilder.getName()));
            methodBuilder.setName(methodName);
            methodBuilder.addParameter(fieldName, fieldType);
            methodBuilder.addBody(String.format("this.%s.putAll(%s);", fieldName, fieldName));
            methodBuilder.addBody(String.format("this._%s = true;", fieldName));
            methodBuilder.addBody("return this;");
            typeBuilder.addMethod(methodBuilder.build());
        }
    }


    protected void addCollectionAddItemMethod(JavaTypeBuilder typeBuilder, String fieldName, Type fieldType) {
        String singularName = StringUtil.getSingular(fieldName);
        String methodName = StringUtil.format(CaseFormat.LOWER_CAMEL, "add", singularName, CaseFormat.LOWER_CAMEL);
        if (!typeBuilder.containsMethod(methodName)) {
            JavaMethodBuilder methodBuilder = new JavaMethodBuilder();
            methodBuilder.addModifier("public");
            methodBuilder.setResultType(new TypeNameWrapper(typeBuilder.getName()));
            methodBuilder.setName(methodName);
            Type keyType = ReflectUtil.getGenericArgument(fieldType, 0, Object.class);
            Type valueType = ReflectUtil.getGenericArgument(fieldType, 1, Object.class);
            methodBuilder.addParameter("key", keyType);
            methodBuilder.addParameter("value", valueType);
            methodBuilder.addBody(String.format("this.%s.put(key, value);", fieldName));
            methodBuilder.addBody(String.format("this._%s = true;", fieldName));
            methodBuilder.addBody("return this;");
            typeBuilder.addMethod(methodBuilder.build());
        }
    }


    protected void addCollectionClearMethod(JavaTypeBuilder typeBuilder, String fieldName) {
        String methodName = StringUtil.format(CaseFormat.LOWER_CAMEL, "clear", fieldName, CaseFormat.LOWER_CAMEL);
        if (!typeBuilder.containsMethod(methodName)) {
            JavaMethodBuilder methodBuilder = new JavaMethodBuilder();
            methodBuilder.addModifier("public");
            methodBuilder.setResultType(new TypeNameWrapper(typeBuilder.getName()));
            methodBuilder.setName(methodName);
            methodBuilder.addBody(String.format("this.%s.clear();", fieldName));
            methodBuilder.addBody("return this;");
            typeBuilder.addMethod(methodBuilder.build());
        }
    }
}

