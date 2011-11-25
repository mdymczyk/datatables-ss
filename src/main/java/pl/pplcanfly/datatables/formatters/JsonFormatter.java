package pl.pplcanfly.datatables.formatters;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import pl.pplcanfly.datatables.Column;
import pl.pplcanfly.datatables.http.ResponseParams;
import pl.pplcanfly.datatables.http.ServerSideDataTable;

public class JsonFormatter implements Formatter {

    @Override
    public String format(ResponseParams params, ServerSideDataTable dataTable, List<?> rows) {
        JSONObject json = new JSONObject();
        json.accumulate("sEcho", params.getEcho());
        json.accumulate("iTotalRecords", params.getTotalRecords());
        json.accumulate("iTotalDisplayRecords", params.getTotalDisplayRecords());
        JSONArray jsonArray = new JSONArray();
        for (Object row : rows) {
            List<Object> values = new ArrayList<Object>();
            for(Column column : dataTable.getColumns()) {
                values.add(column.getValueAccessor().getValueFrom(row));
            }
            jsonArray.element(values);
        }
        json.accumulate("aaData", jsonArray.toString());
        return json.toString();
    }

}
