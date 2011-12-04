package pl.pplcanfly.datatables.types;

import java.util.Date;

public class DateType implements Type {

    @Override
    public int compare(Object o1, Object o2) {
        return ((Date) o1).compareTo((Date) o2);
    }

}
