package pl.pplcanfly.datatables.comparators;

import java.util.Comparator;

public class StringComparator implements Comparator<Object> {

    @Override
    public int compare(Object o1, Object o2) {
        return ((String) o1).compareTo((String) o2);
    }

}
