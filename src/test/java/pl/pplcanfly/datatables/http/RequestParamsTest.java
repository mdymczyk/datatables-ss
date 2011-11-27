package pl.pplcanfly.datatables.http;

import static org.fest.assertions.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class RequestParamsTest {

    @Test
    public void should_parse_common_params() {
        // given
        Map<String, String[]> params = new HashMap<String, String[]>();

        params.put("sEcho", new String[]{"3"});
        params.put("iDisplayStart", new String[]{"20"});
        params.put("iDisplayLength", new String[]{"10"});

        // when
        RequestParams request = new RequestParams(params);

        // then
        assertThat(request.getEcho()).isEqualTo(3);
        assertThat(request.getDisplayStart()).isEqualTo(20);
        assertThat(request.getDisplayLength()).isEqualTo(10);
    }

    @Test
    public void should_parse_sort_params() {
        // given
        Map<String, String[]> params = new HashMap<String, String[]>();

        params.put("sColumns", new String[]{"col1,col2,col3,col4,col5"});
        params.put("iSortingCols", new String[]{"3"});
        params.put("iSortCol_0", new String[]{"2"});
        params.put("iSortCol_1", new String[]{"3"});
        params.put("iSortCol_2", new String[]{"0"});
        params.put("sSortDir_0", new String[]{"asc"});
        params.put("sSortDir_1", new String[]{"desc"});
        params.put("sSortDir_2", new String[]{"asc"});

        // when
        RequestParams request = new RequestParams(params);

        // then
        assertThat(request.getColumns()).containsExactly("col1", "col2", "col3", "col4", "col5");
        assertThat(request.getSortingColsCount()).isEqualTo(3);
        assertThat(request.getSortCols()).containsExactly("col3", "col4", "col1");
        assertThat(request.getSortDirs()).containsExactly("asc", "desc", "asc");
    }

    @Test
    public void should_parse_search_params() {
        // given
        Map<String, String[]> params = new HashMap<String, String[]>();

        params.put("sColumns", new String[]{"col1,col2,col3,col4,col5"});
        params.put("sSearch", new String[]{"searching for the meaining"});
        params.put("bSearchable_0", new String[]{"true"});
        params.put("bSearchable_1", new String[]{"false"});
        params.put("bSearchable_2", new String[]{"true"});
        params.put("bSearchable_3", new String[]{"false"});
        params.put("bSearchable_4", new String[]{"true"});

        // when
        RequestParams request = new RequestParams(params);

        // then
        assertThat(request.getSearch()).isEqualTo("searching for the meaining");
        assertThat(request.getSearchableCols()).containsExactly("col1", "col3", "col5");
    }
}
