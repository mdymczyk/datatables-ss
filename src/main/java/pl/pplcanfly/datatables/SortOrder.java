package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.List;

enum SortOrder {
    ASC  { public int applyTo(int comparisonResult) { return comparisonResult; } },
    DESC { public int applyTo(int comparisonResult) { return comparisonResult * -1; } };

    public abstract int applyTo(int comparisonResult);

    public static List<SortOrder> toEnumList(List<String> sortOrders) {
        List<SortOrder> result = new ArrayList<SortOrder>();
        for (String order : sortOrders) {
            result.add(SortOrder.valueOf(order.toUpperCase()));
        }
        return result;
    }
}
