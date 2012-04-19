package org.abstractmeta.code.g.core.util;

import org.abstractmeta.code.g.core.internal.TypeNameWrapper;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * @author Adrian Witas
 */
public class ReflectUtil {


    public static Class getRawClass(Type type) {
        if (type instanceof Class) {
            return Class.class.cast(type);
        } else if (type instanceof ParameterizedType) {
            return getRawClass(ParameterizedType.class.cast(type).getRawType());
        } else if (type instanceof GenericArrayType) {
            Type componentType = GenericArrayType.class.cast(type).getGenericComponentType();
            if (componentType instanceof Class) {
                return Array.newInstance((Class) componentType, 0).getClass();
            }
        }
        return Object.class;
    }

    public static Type getObjectType(Type type) {
        Class rawResultType = ReflectUtil.getRawClass(type);
        if (rawResultType.isPrimitive()) {
            return ReflectUtil.getPrimitiveCounterType(rawResultType);
        }
        return type;
    }


    public static Type[] getGenericActualTypeArguments(Type type) {
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = ParameterizedType.class.cast(type);
            return parameterizedType.getActualTypeArguments();
        } else if (type instanceof GenericArrayType) {
            return new Type[]{GenericArrayType.class.cast(type).getGenericComponentType()};
        } else if (type instanceof TypeNameWrapper) {
            return new Type[]{};
        } else if (type instanceof Class) {
            Class clazz = ((Class) type);
            if (clazz.isPrimitive()) {
                clazz = getPrimitiveCounterType(clazz);
            }
            TypeVariable[] typeVariables = clazz.getTypeParameters();
            if (typeVariables != null && typeVariables.length > 0) {
                Type[] result = new Type[typeVariables.length];
                for (int i = 0; i < result.length; i++) {
                    result[i] = Object.class;
                }
                return result;
            }
            return new Type[]{};
        }
        throw new IllegalStateException(String.format("Unsupported type %s", type));
    }


    public static String extractFieldNameFromMethodName(String methodName) {
        int methodNameLength = methodName.length();
        if ((methodName.startsWith("set") || methodName.startsWith("get")) && methodNameLength > 3) {
            String upperCaseMethodName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, methodName);
            String fieldName = upperCaseMethodName.substring(4, upperCaseMethodName.length());
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldName);
        } else if (methodName.startsWith("is") && methodNameLength > 2) {
            String upperCaseMethodName = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, methodName);
            String fieldName = upperCaseMethodName.substring(3, upperCaseMethodName.length());
            return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldName);
        }
        return null;
    }

    public static List<Method> getMethods(Class type) {
        List<Method> result = new ArrayList<Method>();
        Set<String> uniqueMethodSignatures = new HashSet<String>();
        updateMethods(type, result, uniqueMethodSignatures);
        return result;
    }

    protected static boolean isMethodUnique(Method method, Set<String> uniqueMethodSignatures) {
        String methodSignature = method.getName() + Joiner.on(",").join(getClassNames(method.getParameterTypes()));
        if (uniqueMethodSignatures.contains(methodSignature)) {
            return false;
        }
        uniqueMethodSignatures.add(methodSignature);
        return true;
    }

    protected static void updateMethods(Class type, List<Method> result, Set<String> uniqueMethodSignatures) {
        Method[] methods = type.getDeclaredMethods();
        for (Method method : methods) {
            if (isMethodUnique(method, uniqueMethodSignatures)) {
                result.add(method);
            }
        }
        if (type.getSuperclass() != null && type.getSuperclass() != Class.class && type.getSuperclass() != Object.class) {
            updateMethods(type.getSuperclass(), result, uniqueMethodSignatures);
        }
        if (type.getInterfaces() != null) {
            for (Class iFace : type.getInterfaces()) {
                updateMethods(iFace, result, uniqueMethodSignatures);
            }
        }
    }

    public static List<Field> getFields(Class type) {
        List<Field> result = new ArrayList<Field>();
        updateFields(type, result);
        return result;
    }

    private static void updateFields(Class type, List<Field> result) {
        Field[] fields = type.getDeclaredFields();
        for (Field field : fields) {
            result.add(field);
        }
        if (type.getSuperclass() != null && type.getSuperclass() != Class.class && type.getSuperclass() != Object.class) {
            updateFields(type.getSuperclass(), result);
        }
    }

    public static Map<String, Object> readAnnotation(Annotation annotation) {
        Class type = annotation.annotationType();
        Map<String, Object> result = new HashMap<String, Object>();
        for (Method method : type.getMethods()) {
            String methodName = method.getName();
            if (method.getGenericParameterTypes().length > 0 || "toString".equals(methodName) || "hashCode".equals(methodName) || "annotationType".equals(methodName)) {
                continue;
            }
            try {
                method.setAccessible(true);
                Object instance = method.invoke(annotation);
                result.put(method.getName(), instance);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to read annotation " + annotation, e);
            }
        }
        return result;
    }

    public static Class getPrimitiveCounterType(Class primitiveType) {
        char c = primitiveType.getSimpleName().charAt(0);
        switch (c) {
            case 'i':
                return Integer.class;
            case 'l':
                return Long.class;
            case 'c':
                return Character.class;
            case 'f':
                return Float.class;
            case 'd':
                return Double.class;
            case 'v':
                return Void.class;
            default:
                if (primitiveType == boolean.class) {
                    return Boolean.class;
                } else if (primitiveType == byte.class) {
                    return Byte.class;
                } else {
                    throw new IllegalStateException("Invalid primitive type " + primitiveType);
                }
        }
    }

    public static Class getGenericArgument(Type type, int argumentIndex, Class defaultType) {
        if (type instanceof ParameterizedType) {
            Type[] types = ParameterizedType.class.cast(type).getActualTypeArguments();
            if (argumentIndex < types.length) {
                return ReflectUtil.getRawClass(types[argumentIndex]);
            }
        }
        return defaultType;
    }


    public static Class getGenericArgument(Type[] types, int argumentIndex, Class defaultType) {
        if (argumentIndex < types.length) {
            return ReflectUtil.getRawClass(types[argumentIndex]);
        }
        return defaultType;
    }

    public static Collection<String> getClassNames(Class... classes) {
        List<String> result = new ArrayList<String>();
        for (Class clazz : classes) {
            result.add(clazz.getName());
        }
        return result;
    }


}
