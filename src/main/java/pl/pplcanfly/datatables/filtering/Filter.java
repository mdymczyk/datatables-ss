package pl.pplcanfly.datatables.filtering;

import java.util.List;

public interface Filter {
    List<?> filter(List<?> rows);
}
