package pl.pplcanfly.datatables.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

class RequestParams {
    private Map<String, String[]> params;

    public RequestParams(Map<String, String[]> params) {
        this.params = params;
    }

    public int getDisplayStart() {
        return Integer.parseInt(getParam("iDisplayStart")); 
    }

    public List<String> getColumns() {
        return Arrays.asList(getParam("sColumns").split(","));
    }

    public int getSortingColsCount() {
        return Integer.parseInt(getParam("iSortingCols"));
    }
    
    public List<String> getSortCols() {
        List<String> columns = getColumns();
        List<String> sortingColumns = new ArrayList<String>();
        for (int i = 0; i < getSortingColsCount(); i++) {
            sortingColumns.add(columns.get(Integer.parseInt(getParam("iSortCol_" + i))));
        }
        return sortingColumns;
    }

    public List<String> getSortDirs() {
        List<String> sortDirs = new ArrayList<String>();
        for (int i = 0; i < getSortingColsCount(); i++) {
            sortDirs.add(getParam("iSortDir_" + i));
        }
        return sortDirs;
    }

    public int getDisplayLength() {
        return Integer.parseInt(getParam("iDisplayLength"));
    }
    
    private String getParam(String key) {
        return params.get(key)[0];
    }
    
}
