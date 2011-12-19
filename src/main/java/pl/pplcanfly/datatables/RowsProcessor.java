package pl.pplcanfly.datatables;

import java.util.List;

public interface RowsProcessor {
    List<?> process(List<?> rows);
}
