package org.abstractmeta.code.g.core.plugin;

import org.abstractmeta.code.g.code.JavaType;
import org.abstractmeta.code.g.code.JavaTypeRegistry;
import org.abstractmeta.code.g.core.builder.SimpleClassBuilder;
import org.abstractmeta.code.g.core.code.builder.JavaTypeBuilder;
import org.abstractmeta.code.g.core.extractor.AccessorFieldExtractor;
import org.abstractmeta.code.g.core.extractor.RegistryFieldExtractor;
import org.abstractmeta.code.g.core.internal.TypeNameWrapper;
import org.abstractmeta.code.g.config.Descriptor;
import org.abstractmeta.code.g.extractor.MethodExtractor;
import org.abstractmeta.code.g.plugin.CodeGeneratorPlugin;

import java.util.Arrays;
import java.util.Collections;


/**
 * <p><b>Builder Generator Plugin</b></p>
 * <h2>Overview</h2>
 * <p>This plugin generates code implementation for a given interface, superclass. The generation process can be break down to</p>
 * <ul>
 * <li>Fields generation, all fields are extracted from source class' based on matching method names.
 * <ul>
 * <li>It extracts setter/getter fields. If source class has ony get method it assumes the field is immutable.</li>
 * <li>It extracts registry fields for registry like methods (register, isRegister, get, etc ...)</li>
 * </ul>
 * </li>
 * <li>Method generation - once all fields are defined and type is being build the following
 * field handlers are fired with ability to build relevant method
 * <ul>
 * <li>{@link org.abstractmeta.code.g.core.handler.RegistryFieldHandler}</li>
 * <li>{@link org.abstractmeta.code.g.core.handler.CollectionFieldHandler}</li>
 * <li>{@link org.abstractmeta.code.g.core.handler.GetterFieldHandler}</li>
 * <li>{@link org.abstractmeta.code.g.core.handler.SimpleTypeHandler}</li>
 * </ul>
 * </li>
 * <p/>
 * </ul>
 *
 * @author Adrian Witas
 */
public class ClassGenerator extends AbstractGeneratorPlugin implements CodeGeneratorPlugin {


    public ClassGenerator() {
        super(Arrays.asList(new AccessorFieldExtractor(),
                new RegistryFieldExtractor()), Collections.<MethodExtractor>emptyList());
    }

    @Override
    protected boolean isApplicable(JavaType sourceType) {
        return isExtractable(sourceType);
    }

    @Override
    protected JavaTypeBuilder generateType(JavaType sourceType, JavaTypeRegistry registry, String targetTypeName, Descriptor descriptor) {
        SimpleClassBuilder classBuilder = new SimpleClassBuilder(sourceType);
        classBuilder.setSourceType(sourceType);
        classBuilder.addModifier("public").setTypeName(targetTypeName);
        if (!sourceType.getGenericTypeArguments().isEmpty()) {
            classBuilder.addGenericTypeArguments(sourceType.getGenericTypeArguments());
        }
        if ("class".equals(sourceType.getKind())) {
            classBuilder.setSuperType(new TypeNameWrapper(sourceType.getName(), sourceType.getGenericTypeArguments()));
        } else {
            classBuilder.addSuperInterface(new TypeNameWrapper(sourceType.getName(), sourceType.getGenericTypeArguments()));
        }

        addExtractableFields(classBuilder, sourceType);
        return classBuilder;
    }


}
