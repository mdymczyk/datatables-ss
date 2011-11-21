package pl.pplcanfly.datatables;

import pl.pplcanfly.datatables.comparators.ComparableType;

public abstract class Types {
    public static Type numeric() {
        return new ComparableType();
    }
    
    public static Type text() {
        return new ComparableType();
    }

}