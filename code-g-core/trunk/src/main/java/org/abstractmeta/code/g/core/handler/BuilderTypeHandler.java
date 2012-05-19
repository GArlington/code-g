package org.abstractmeta.code.g.core.handler;

import org.abstractmeta.code.g.code.JavaField;
import org.abstractmeta.code.g.code.JavaType;
import org.abstractmeta.code.g.core.code.builder.JavaFieldBuilder;
import org.abstractmeta.code.g.core.internal.TypeNameWrapper;
import org.abstractmeta.code.g.core.code.builder.JavaTypeBuilder;
import org.abstractmeta.code.g.core.util.ReflectUtil;
import org.abstractmeta.code.g.core.code.builder.JavaMethodBuilder;
import org.abstractmeta.code.g.core.util.JavaTypeUtil;
import org.abstractmeta.code.g.core.util.StringUtil;
import org.abstractmeta.code.g.handler.JavaTypeHandler;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;

import java.lang.reflect.Type;
import java.util.*;

/**
 * This handler creates constructor for all fields defined on the builder owner type.
 * In case where all fields are mutable the empty constructor is added.
 * <p>For instance for the given source type
 * <code><pre>
 * public interface Bar {
 *     int getId();
 *     Bar getBar();
 *     String getDummy();
 *     void setDummy(String dummy);
 *     Map<String, Bar> getBarMap();
 *     List<Bar> getBarList();
 *     Bar[] getBars();
 * }
 *  </code></pre>
 * The following code is generated
 * <code><pre>
 * public Bar generate() {
 *     Bar result = new FooImpl(id, bar, barMap, barList, bars);
 *     result.setDummy(dummy);
 *     return result;
 * }
 * </pre></code>
 * </p>
 *
 * @author Adrian Witas
 */
public class BuilderTypeHandler implements JavaTypeHandler {


    private final JavaTypeBuilder ownerTypeBuilder;
    private final Map<String, String> immutableImplementations;

    public BuilderTypeHandler(JavaTypeBuilder ownerTypeBuilder, Map<String, String> immutableImplementations) {
        this.ownerTypeBuilder = ownerTypeBuilder;
        this.immutableImplementations = immutableImplementations;
    }


    @Override
    public void handle(JavaType sourceType) {
        if (ownerTypeBuilder.containsMethod("build")) {
            return;
        }

        generateBuildMethod(ownerTypeBuilder, sourceType);
        setFieldDefaults(ownerTypeBuilder);
    }

    protected void setFieldDefaults(JavaTypeBuilder ownerTypeBuilder) {
        List<JavaField> javaFields = ownerTypeBuilder.getFields();
        ownerTypeBuilder.setFields(new ArrayList<JavaField>());
        for (JavaField field : javaFields) {
            JavaFieldBuilder fieldBuilder = new JavaFieldBuilder();
            fieldBuilder.merge(field);
            setDefaultValue(ownerTypeBuilder, fieldBuilder);
            ownerTypeBuilder.addField(fieldBuilder.build());
        }
    }

    protected void generateBuildMethod(JavaTypeBuilder typeBuilder, JavaType sourceType) {
        JavaMethodBuilder methodBuilder = new JavaMethodBuilder();
        methodBuilder.setName("build");
        methodBuilder.addModifier("public");
        Type buildResultType = JavaTypeUtil.getSuperType(sourceType);
        String buildResultSimpleClassName = JavaTypeUtil.getSimpleClassName(JavaTypeUtil.getSuperTypeName(sourceType), true);
        methodBuilder.setResultType(buildResultType);
        List<String> buildTypeConstructorCallArguments = new ArrayList<String>();
        List<String> buildTypeSettingCode = new ArrayList<String>();

        for (JavaField field : sourceType.getFields()) {
            String fieldName = field.getName();
            Class fieldRawType = ReflectUtil.getRawClass(field.getType());
            if (field.isImmutable()) {
                if (immutableImplementations.containsKey(fieldRawType.getName())) {
                    String immutableImplementation = immutableImplementations.get(fieldRawType.getName());
                    String implementationMethod = StringUtil.substringAfterLastIndexOf(immutableImplementation, ".");
                    String importType = StringUtil.substringBeforeLastIndexOf(immutableImplementation, ".");
                    String importSimpleTypeName = JavaTypeUtil.getSimpleClassName(importType);
                    buildTypeConstructorCallArguments.add(String.format("%s.%s(%s)", importSimpleTypeName, implementationMethod, fieldName));
                    typeBuilder.addImportType(new TypeNameWrapper(importType));

                } else {
                    buildTypeConstructorCallArguments.add(fieldName);
                }
            } else {
                String setterMethodName = StringUtil.format(CaseFormat.LOWER_CAMEL, "set", fieldName, CaseFormat.LOWER_CAMEL);
                buildTypeSettingCode.add(String.format("result.%s(%s);", setterMethodName, fieldName));
            }
        }
        typeBuilder.addImportType(methodBuilder.getResultType());
        String builtImplementationSimpleTypeName = JavaTypeUtil.getSimpleClassName(sourceType.getName());
        ownerTypeBuilder.addImportType(new TypeNameWrapper(sourceType.getName()));
        methodBuilder.addBody(String.format("%s result = new %s(%s);",
                buildResultSimpleClassName,
                builtImplementationSimpleTypeName,
                Joiner.on(", ").join(buildTypeConstructorCallArguments)
        ));
        methodBuilder.addBody(buildTypeSettingCode);
        methodBuilder.addBody("return result;");
        typeBuilder.addMethod(methodBuilder.build());
    }

    protected void setDefaultValue(JavaTypeBuilder typeBuilder, JavaFieldBuilder field) {
        Class rawType = ReflectUtil.getRawClass(field.getType());
        Type[] genericTypeArguments = ReflectUtil.getGenericActualTypeArguments(field.getType());
        Class componentType = ReflectUtil.getGenericArgument(genericTypeArguments, 0, Object.class);

        if (Set.class.isAssignableFrom(rawType)) {
            typeBuilder.addImportType(HashSet.class);
            typeBuilder.addImportType(componentType);
            field.setInitBody(String.format(" = new %s<%s>()", HashSet.class.getSimpleName(), componentType.getSimpleName()));
        } else if (Collection.class.isAssignableFrom(rawType)) {
            typeBuilder.addImportType(ArrayList.class);
            typeBuilder.addImportType(componentType);
            field.setInitBody(String.format(" = new %s<%s>()", ArrayList.class.getSimpleName(), componentType.getSimpleName()));

        } else if (Properties.class.isAssignableFrom(rawType)) {
            typeBuilder.addImportType(HashSet.class);
            typeBuilder.addImportType(componentType);
            field.setInitBody(" = new Properties()");
        } else if (Map.class.isAssignableFrom(rawType)) {
            typeBuilder.addImportType(HashSet.class);
            typeBuilder.addImportType(componentType);
            Class valueType = ReflectUtil.getGenericArgument(genericTypeArguments, 1, Object.class);
            ownerTypeBuilder.addImportType(HashMap.class);
            field.setInitBody(String.format(" = new %s<%s, %s>()", HashMap.class.getSimpleName(), componentType.getSimpleName(), valueType.getSimpleName()));

        } else if (rawType.isArray()) {
            componentType = rawType.getComponentType();
            typeBuilder.addImportType(componentType);
            field.setInitBody(String.format(" = new %s[]{}", componentType.getSimpleName()));

        }
    }


}
