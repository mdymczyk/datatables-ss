package pl.pplcanfly.datatables.http;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RequestParamsTest {

    @Test
    public void should_parse_params() {
        // given
        Map<String, String> params = new HashMap<String, String>();
        
        params.put("iDisplayStart", "20");
        params.put("iDisplayLength", "10");
        
        params.put("sColumns", "col1,col2,col3,col4,col5");
        params.put("iSortingCols", "3");
        params.put("iSortCol_0", "2");
        params.put("iSortCol_1", "3");
        params.put("iSortCol_2", "0");
        params.put("iSortDir_0", "asc");
        params.put("iSortDir_1", "desc");
        params.put("iSortDir_2", "asc");
        
        // when
        RequestParams request = new RequestParams(params);
        
        // then
        assertThat(request.getDisplayStart()).isEqualTo(20);
        assertThat(request.getDisplayLength()).isEqualTo(10);
        
        assertThat(request.getColumns()).containsExactly("col1", "col2", "col3", "col4", "col5");
        assertThat(request.getSortingColsCount()).isEqualTo(3);
        assertThat(request.getSortCols()).containsExactly("col3", "col4", "col1");
        assertThat(request.getSortDirs()).containsExactly("asc", "desc", "asc");
    }
}
