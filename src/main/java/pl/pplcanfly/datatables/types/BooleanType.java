package pl.pplcanfly.datatables.types;

public class BooleanType implements Type {

    @Override
    public int compare(Object o1, Object o2) {
        return ((Boolean) o1).compareTo((Boolean) o2);
    }

}
