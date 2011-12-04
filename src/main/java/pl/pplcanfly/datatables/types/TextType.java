package pl.pplcanfly.datatables.types;

public class TextType implements Type {

    @Override
    public int compare(Object o1, Object o2) {
        return ((String) o1).compareToIgnoreCase((String) o2);
    }

}
