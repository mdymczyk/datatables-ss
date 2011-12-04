package pl.pplcanfly.datatables.accessors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


public class ReflectionValueAccessor implements ValueAccessor {

    private String fieldName;
    private Field field;
    private Method method;

    public ReflectionValueAccessor(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public Object getValueFrom(Object obj) {
        if (field == null && method == null) {
            initialize(obj);
        }

        try {
            return field != null ? field.get(obj) : method.invoke(obj, (Object[])null);
        } catch (Exception e) {
            // initialization takes care of checking fields/methods
            throw new RuntimeException(e);
        }
    }

    private void initialize(Object obj) {
        Class<? extends Object> klass = obj.getClass();

        if (!tryToInitializeField(klass) && !tryToInitializeMethod(klass)) {
            throw new ValueNotAccessibleException(fieldName);
        }
    }

    private boolean tryToInitializeField(Class<? extends Object> klass) {
        field = findField(klass.getDeclaredFields());
        if (field == null) {
            field = findField(klass.getFields());
        }
        if (field != null) {
            field.setAccessible(true);
        }
        return field != null;
    }

    private boolean tryToInitializeMethod(Class<? extends Object> klass) {
        method = findMethod(klass.getDeclaredMethods());
        if (method == null) {
            method = findMethod(klass.getMethods());
        }
        return method != null;
    }


    private Field findField(Field[] fields) {
        for (Field f : fields) {
            if (f.getName().equals(fieldName)) {
                return f;
            }
        }
        return null;
    }

    private Method findMethod(Method[] methods) {
        List<String> getters = Arrays.asList(getter(), boolGetter());
        for (Method m : methods) {
            if (getters.contains(m.getName())) {
                return m;
            }
        }
        return null;
    }

    private String getter() {
        return "get" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

    private String boolGetter() {
        return "is" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
    }

}
