package pl.pplcanfly.datatables.formatters;

import java.util.List;

import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.http.ResponseParams;

public interface Formatter {
    String format(ResponseParams params, ServerSideDataTable dataTable, List<?> rows);
}
