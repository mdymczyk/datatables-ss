package pl.pplcanfly.datatables.comparators;

import pl.pplcanfly.datatables.Type;



public class ComparableType implements Type {

    @Override
    public int compare(Object o1, Object o2) {
        return ((Comparable) o1).compareTo((Comparable) o2);
    }

}
