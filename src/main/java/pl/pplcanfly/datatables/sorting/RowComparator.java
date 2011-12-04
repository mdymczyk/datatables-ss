package pl.pplcanfly.datatables.sorting;

import java.util.Comparator;

import pl.pplcanfly.datatables.accessors.ReflectionValueAccessor;
import pl.pplcanfly.datatables.accessors.ValueAccessor;
import pl.pplcanfly.datatables.types.Type;

public class RowComparator implements Comparator<Object> {

    private ValueAccessor valueAccessor;
    private Comparator<Object> valueComparator;
    private SortOrder sortOrder;
    private RowComparator next;

    public RowComparator(Type type, SortOrder sortOrder, String fieldName) {
        this(type, sortOrder, new ReflectionValueAccessor(fieldName));
    }

    public RowComparator(Type type, SortOrder sortOrder, ValueAccessor valueAccessor) {
        this.valueAccessor = valueAccessor;
        this.valueComparator = type;
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
        int comparisonResult = valueComparator.compare(valueAccessor.getValueFrom(o1),
                valueAccessor.getValueFrom(o2));

        if (comparisonResult == 0) {
            return hasNextAppended() ? next.compare(o1, o2) : comparisonResult;
        }

        return sortOrder.applyTo(comparisonResult);
    }

    private boolean hasNextAppended() {
        return next != null;
    }

}
