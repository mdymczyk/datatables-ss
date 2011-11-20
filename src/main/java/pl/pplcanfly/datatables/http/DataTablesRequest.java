package pl.pplcanfly.datatables.http;

import java.util.Map;

public class DataTablesRequest {

    private RequestParams request;

    public DataTablesRequest(Map<String, String> params) {
        this.request = new RequestParams(params);
    }

}
