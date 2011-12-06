package pl.pplcanfly.datatables;

import java.util.List;

public interface Filter {
    List<?> filter(List<?> rows);
}
