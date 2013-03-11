package org.abstractmeta.code.g.core.collection.function;

import com.google.common.base.Function;
import org.abstractmeta.code.g.code.JavaParameter;

import javax.annotation.Nullable;
import java.lang.reflect.Type;

/**
 *
 * @author Adrian Witas
 */
public class JavaParameterName implements Function<JavaParameter, String> {

    @Override
    public String apply(@Nullable JavaParameter parameter) {
        return parameter.getName();
    }
}
