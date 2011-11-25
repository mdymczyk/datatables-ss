package pl.pplcanfly.datatables;

import pl.pplcanfly.datatables.accessors.ReflectionValueAccessor;
import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Type;

public class Column {
    private Type type;
    private String name;
    private ValueAccessor valueAccessor;

    public Column(Type type, String name) {
        this.type = type;
        this.name = name;
        this.valueAccessor = new ReflectionValueAccessor(name);
    }

    public Column(Type type, String name, ValueAccessor valueAccessor) {
        this(type, name);
        this.valueAccessor = valueAccessor;
    }
    
    public boolean hasCustomValueAccesor() {
        return valueAccessor != null;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ValueAccessor getValueAccessor() {
        return valueAccessor;
    }

}
