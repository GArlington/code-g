package org.abstractmeta.code.g.core.config.properties;

import org.abstractmeta.code.g.config.Descriptor;
import org.abstractmeta.code.g.core.config.builder.DescriptorBuilder;

import java.util.Map;

/**
 * Represents DescriptorDecoder
 * <p>
 * </p>
 *
 * @author Adrian Witas
 */
public class DescriptorDecoder {


    public Descriptor decode(Map<String, String> properties) {
        DescriptorBuilder resultBuilder = new DescriptorBuilder();
        resultBuilder.setPlugin(properties.get("plugin"));
        resultBuilder.setCompilationSources(DecoderUtil.readAsStringList(properties, "compilationSources"));
        resultBuilder.setExclusions(DecoderUtil.readAsStringSet(properties, "exclusion"));
        resultBuilder.setInclusion(DecoderUtil.readAsStringSet(properties, "inclusions"));
        resultBuilder.setInterfaces(properties.get("interfaces"));
        resultBuilder.setSourceClass(properties.get("sourceClass"));
        resultBuilder.setSourcePackage(properties.get("sourcePackage"));
        resultBuilder.setSuperType(properties.get("superType"));
        resultBuilder.setTargetPackage(properties.get("targetPackage"));
        resultBuilder.setTargetPrefix(properties.get("targetPrefix"));
        resultBuilder.setTargetPostfix(properties.get("targetPostfix"));
        Map<String, String> options = DecoderUtil.matchWithPrefix(properties, "options");
        resultBuilder.setOptions(options);
        Map<String, String> immutableImplementation = DecoderUtil.matchWithPrefix(properties, "immutableImplementation");
        resultBuilder.setImmutableImplementation(immutableImplementation);
        return resultBuilder.build();
    }
}