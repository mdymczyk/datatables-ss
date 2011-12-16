package pl.pplcanfly.datatables;

import pl.pplcanfly.datatables.accessors.ReflectionValueAccessor;
import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Type;

class Column {
    private Type type;
    private String name;
    private ValueAccessor valueAccessor;

    public Column(String name) {
        this(null, name, null);
    }

    public Column(Type type, String name) {
        this(type, name, new ReflectionValueAccessor(name));
    }

    public Column(Type type, String name, ValueAccessor valueAccessor) {
        this.type = type;
        this.name = name;
        this.valueAccessor = valueAccessor;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Object getValueFrom(Object row) {
        return valueAccessor.getValueFrom(row);
    }

    ValueAccessor getValueAccessor() {
        return valueAccessor;
    }

}
