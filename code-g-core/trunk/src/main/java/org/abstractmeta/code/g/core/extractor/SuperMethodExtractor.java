package org.abstractmeta.code.g.core.extractor;

import org.abstractmeta.code.g.code.JavaKind;
import org.abstractmeta.code.g.code.JavaMethod;
import org.abstractmeta.code.g.code.JavaType;
import org.abstractmeta.code.g.core.code.builder.JavaMethodBuilder;
import org.abstractmeta.code.g.extractor.MethodExtractor;
import org.abstractmeta.code.g.generator.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents SuperMethodExtractor
 * <p>
 * </p>
 *
 * @author Adrian Witas
 */
public class SuperMethodExtractor implements MethodExtractor {

    @Override
    public List<JavaMethod> extract(JavaType sourceType, Context context) {
        List<JavaMethod> result = new ArrayList<JavaMethod>();
        if (JavaKind.INTERFACE.equals(sourceType.getKind())) {
            return result;
        }
        for (JavaMethod methodCandidate : sourceType.getMethods()) {
            String methodName = methodCandidate.getName();
            if (methodName.startsWith("get") || methodName.equals("hashCode")
                    || (methodCandidate.getParameters().size() == 1
                    && (methodName.startsWith("set")
                    || methodName.startsWith("is")
                    || methodName.equals("equals"))))
                continue;
            result.add(new JavaMethodBuilder()
                    .setName(methodCandidate.getName())
                    .setResultType(methodCandidate.getResultType())
                    .addParameters(methodCandidate.getParameters())
                    .addModifiers(methodCandidate.getModifiers())
                    .addExceptionTypes(methodCandidate.getExceptionTypes())
                    .build()
            );
        }

        return result;
    }
}
