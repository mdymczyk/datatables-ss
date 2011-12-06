package pl.pplcanfly.datatables;

import java.util.List;

import pl.pplcanfly.datatables.params.ResponseParams;

public interface Formatter {
    String format(ResponseParams params, ServerSideDataTable dataTable, List<?> rows);
}
