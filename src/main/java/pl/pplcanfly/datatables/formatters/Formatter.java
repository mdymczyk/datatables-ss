package pl.pplcanfly.datatables.formatters;

import java.util.List;

import pl.pplcanfly.datatables.http.ResponseParams;
import pl.pplcanfly.datatables.http.ServerSideDataTable;

public interface Formatter {
    String format(ResponseParams params, ServerSideDataTable dataTable, List<?> rows);
}
