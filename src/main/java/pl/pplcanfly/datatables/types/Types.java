package pl.pplcanfly.datatables.types;


public abstract class Types {
    public static Type numeric() {
        return new ComparableType();
    }
    
    public static Type text() {
        return new ComparableType();
    }

}