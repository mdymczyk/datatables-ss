package pl.pplcanfly.datatables.accessors;

import java.lang.reflect.Field;


public class ReflectionValueAccessor implements ValueAccessor {

    private String fieldName;

    public ReflectionValueAccessor(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public Object getValueFrom(Object obj) {
        Field field;
        try {
            field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            throw new ValueNotAccessibleException(e);
        }
    }

}
