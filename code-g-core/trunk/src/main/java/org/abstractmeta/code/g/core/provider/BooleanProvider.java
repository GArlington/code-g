package org.abstractmeta.code.g.core.provider;
/* 
 * Boolean Provider
 * @author Adrian Witas
 */

import javax.inject.Provider;
import java.util.Properties;

public class BooleanProvider extends AbstractProvider<Long> implements Provider<Boolean>  {

     public BooleanProvider(Properties properties, String[] pathFragments) {
        super(Long.class, properties, pathFragments);
    }

    @Override
    public Boolean get() {
        String stringValue = getValue();
        try {
            return Boolean.parseBoolean(stringValue);
        } catch(NumberFormatException ex) {
            throw new IllegalStateException("Could not cast "  + getPath() + " => " + stringValue + " into " + getType(), ex);
        }
    }
}
