package pl.pplcanfly.datatables.http;

import java.util.List;

public class DataTablesResponse {

    private List<?> processedRows;

    public DataTablesResponse(List<?> processedRows) {
        this.processedRows = processedRows;
    }

    public List<?> getProcessedRows() {
        return processedRows;
    }

}
