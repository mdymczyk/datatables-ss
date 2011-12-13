package pl.pplcanfly.datatables;

import java.util.List;

import pl.pplcanfly.datatables.params.RequestParams;
import pl.pplcanfly.datatables.params.ResponseParams;

public class DataTablesResponse {

    private List<?> processedRows;
    private ServerSideDataTable dataTable;
    private ResponseParams params;
    private RequestParams requestParams;

    public DataTablesResponse(RequestParams requestParams, ResponseParams params, ServerSideDataTable dataTable, List<?> processedRows) {
        this.requestParams = requestParams;
        this.params = params;
        this.processedRows = processedRows;
        this.dataTable = dataTable;
    }

    public List<?> getProcessedRows() {
        return processedRows;
    }

    public String toJson() {
        return new JsonFormatter().format(requestParams, params, dataTable, processedRows);
    }

    ResponseParams getParams() {
        return params;
    }

}
