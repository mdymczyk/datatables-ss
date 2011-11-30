package pl.pplcanfly.datatables.accessors;

import java.lang.reflect.Field;


public class ReflectionValueAccessor implements ValueAccessor {

    private String fieldName;
    private Field field;

    public ReflectionValueAccessor(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public Object getValueFrom(Object obj) {
        try {
            if (field == null) {
                field = obj.getClass().getDeclaredField(fieldName);
                field.setAccessible(true);
            }
            return field.get(obj);
        } catch (Exception e) {
            throw new ValueNotAccessibleException(e);
        }
    }

}
