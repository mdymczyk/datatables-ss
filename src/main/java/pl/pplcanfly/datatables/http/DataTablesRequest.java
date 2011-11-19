package pl.pplcanfly.datatables.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataTablesRequest {

    private Map<String, String> params;

    public DataTablesRequest(Map<String, String> params) {
        this.params = params;
    }

    public int getDisplayStart() {
        return Integer.parseInt(params.get("iDisplayStart")); 
    }

    public List<String> getColumns() {
        return Arrays.asList(params.get("sColumns").split(","));
    }

    public int getSortingColsCount() {
        return Integer.parseInt(params.get("iSortingCols"));
    }
    
    public List<String> getSortCols() {
        List<String> columns = getColumns();
        List<String> sortingColumns = new ArrayList<String>();
        for (int i = 0; i < getSortingColsCount(); i++) {
            sortingColumns.add(columns.get(Integer.parseInt(params.get("iSortCol_" + i))));
        }
        return sortingColumns;
    }

    public List<String> getSortDirs() {
        List<String> sortDirs = new ArrayList<String>();
        for (int i = 0; i < getSortingColsCount(); i++) {
            sortDirs.add(params.get("iSortDir_" + i));
        }
        return sortDirs;
    }

    public int getDisplayLength() {
        return Integer.parseInt(params.get("iDisplayLength"));
    }

}
