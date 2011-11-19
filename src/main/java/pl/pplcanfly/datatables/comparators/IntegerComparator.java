package pl.pplcanfly.datatables.comparators;

import java.util.Comparator;

public class IntegerComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        return (Integer) o1 - (Integer) o2;
    }

}
