package pl.pplcanfly.datatables;

import java.util.Comparator;
import java.util.List;

class RowComparator implements Comparator<Object> {

    private Column column;
    private SortOrder sortOrder;

    private RowComparator next;

    public RowComparator(Column column, SortOrder sortOrder) {
        this.column = column;
        this.sortOrder = sortOrder;
    }

    public static RowComparator multiColumn(List<Column> columns, List<SortOrder> sortOrders) {
        RowComparator comparator = null;
        for (int i = 0; i < columns.size(); i++) {
            Column column = columns.get(i);

            RowComparator newComparator = new RowComparator(column, sortOrders.get(i));

            if (comparator == null) {
                comparator = newComparator;
            } else {
                comparator.append(newComparator);
            }
        }
        return comparator;
    }

    @Override
    public int compare(Object o1, Object o2) {
        int comparisonResult = column.getType().compare(column.getValueFrom(o1), column.getValueFrom(o2));

        if (comparisonResult == 0) {
            return hasNextAppended() ? next.compare(o1, o2) : comparisonResult;
        }

        return sortOrder.applyTo(comparisonResult);
    }

    void append(RowComparator rowComparator) {
        if (hasNextAppended()) {
            next.append(rowComparator);
        } else {
            next = rowComparator;
        }
    }

    RowComparator getNext() {
        return next;
    }

    private boolean hasNextAppended() {
        return next != null;
    }

}
