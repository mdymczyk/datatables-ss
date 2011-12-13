package pl.pplcanfly.datatables;

import java.util.List;

public class DataTablesResponse {

    private List<?> processedRows;
    private String json;

    public DataTablesResponse(List<?> processedRows, String json) {
        this.processedRows = processedRows;
        this.json = json;
    }

    public List<?> getProcessedRows() {
        return processedRows;
    }

    public String toJson() {
        return json;
    }

}
