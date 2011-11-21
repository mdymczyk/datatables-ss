package pl.pplcanfly.datatables.types;




public class ComparableType implements Type {

    @Override
    public int compare(Object o1, Object o2) {
        return ((Comparable) o1).compareTo((Comparable) o2);
    }

}
