package pl.pplcanfly.datatables;

import java.util.List;

public interface Formatter {
    String format(List<?> rows, int totalRows, int displayRows);
}
