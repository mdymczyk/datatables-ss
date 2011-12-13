package pl.pplcanfly.datatables;

import java.util.List;

import pl.pplcanfly.datatables.params.RequestParams;
import pl.pplcanfly.datatables.params.ResponseParams;

public interface Formatter {
    String format(RequestParams requestParams, ResponseParams params, ServerSideDataTable dataTable, List<?> rows);
}
