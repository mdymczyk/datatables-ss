package pl.pplcanfly.datatables.types;

public class ComparableType implements Type {

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public int compare(Object o1, Object o2) {
        return ((Comparable) o1).compareTo(o2);
    }

}
