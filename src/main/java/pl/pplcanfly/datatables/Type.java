package pl.pplcanfly.datatables;

import java.util.Comparator;

import pl.pplcanfly.datatables.comparators.IntegerComparator;
import pl.pplcanfly.datatables.comparators.StringComparator;

public enum Type {
    INTEGER(new IntegerComparator()), 
    STRING(new StringComparator());

    private Comparator<Object> comparator;

    private Type(Comparator<Object> comparator) {
        this.comparator = comparator;
    }

    public Comparator<Object> getComparator() {
        return comparator;
    }

}
