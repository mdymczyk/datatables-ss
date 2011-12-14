package pl.pplcanfly.datatables;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

class JsonFormatter implements Formatter {

    private List<Column> columns;
    private RequestParams params;

    public JsonFormatter(List<Column> columns, RequestParams params) {
        this.columns = columns;
        this.params = params;
    }

    @Override
    public String format(List<?> rows, int totalRows, int displayRows) {
        JSONObject json = new JSONObject();
        json.accumulate("sEcho", params.getEcho());
        json.accumulate("iTotalRecords", totalRows);
        json.accumulate("iTotalDisplayRecords", displayRows);
        JSONArray jsonArray = new JSONArray();
        for (Object row : rows) {
            List<Object> values = new ArrayList<Object>();
            for(Column column : columns) {
                values.add(column.getValueFrom(row));
            }
            jsonArray.element(values);
        }
        json.accumulate("aaData", jsonArray.toString());
        return json.toString();
    }

}
