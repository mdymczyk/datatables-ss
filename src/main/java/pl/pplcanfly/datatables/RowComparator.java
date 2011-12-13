package pl.pplcanfly.datatables;

import java.util.Comparator;

class RowComparator implements Comparator<Object> {

    private Column column;
    private SortOrder sortOrder;

    private RowComparator next;

    public RowComparator(Column column, SortOrder sortOrder) {
        this.column = column;
        this.sortOrder = sortOrder;
    }

    public void append(RowComparator rowComparator) {
        if (hasNextAppended()) {
            next.append(rowComparator);
        } else {
            next = rowComparator;
        }
    }

    public RowComparator getNext() {
        return next;
    }

    @Override
    public int compare(Object o1, Object o2) {
        int comparisonResult = column.getType().compare(column.getValueFrom(o1), column.getValueFrom(o2));

        if (comparisonResult == 0) {
            return hasNextAppended() ? next.compare(o1, o2) : comparisonResult;
        }

        return sortOrder.applyTo(comparisonResult);
    }

    private boolean hasNextAppended() {
        return next != null;
    }

}
