package pl.pplcanfly.datatables.http;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RequestParamsTest {

    @Test
    public void should_parse_params() {
        // given
        Map<String, String[]> params = new HashMap<String, String[]>();
        
        params.put("iDisplayStart", new String[]{"20"});
        params.put("iDisplayLength", new String[]{"10"});
        
        params.put("sColumns", new String[]{"col1,col2,col3,col4,col5"});
        params.put("iSortingCols", new String[]{"3"});
        params.put("iSortCol_0", new String[]{"2"});
        params.put("iSortCol_1", new String[]{"3"});
        params.put("iSortCol_2", new String[]{"0"});
        params.put("iSortDir_0", new String[]{"asc"});
        params.put("iSortDir_1", new String[]{"desc"});
        params.put("iSortDir_2", new String[]{"asc"});
        
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
