package pl.pplcanfly.datatables.filtering;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.Something;
import pl.pplcanfly.datatables.http.RequestParams;
import pl.pplcanfly.datatables.types.Types;
import pl.pplcanfly.datatables.utils.TestUtils;

public class DefaultFilterTest {
    private ServerSideDataTable dataTable;
    private RequestParams params;
    private DefaultFilter filter;

    @Before
    public void setUp() {
        dataTable = new ServerSideDataTable();
        dataTable.addColumn(Types.text(), "foo");
        dataTable.addColumn(Types.numeric(), "bar");

        params = mock(RequestParams.class);
        stub(params.getDisplayStart()).toReturn(0);
        stub(params.getDisplayLength()).toReturn(20);

        filter = new DefaultFilter(dataTable, params);
    }

    @Test
    public void should_filter_records_by_single_column() {
        // given
        stub(params.getSearchableCols()).toReturn(Arrays.asList("foo"));
        stub(params.getSearch()).toReturn("aa");

        List<Something> rows = TestUtils.load("4");

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(2).onProperty("foo").containsExactly("aax", "aay");
    }

    @Test
    public void should_match_numeric_columns_as_if_it_was_text() {
        // given
        stub(params.getSearchableCols()).toReturn(Arrays.asList("foo", "bar"));
        stub(params.getSearch()).toReturn("22");

        List<Something> rows = TestUtils.load("5");

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(2).onProperty("foo").containsExactly("11", "22");
    }

    @Test
    public void should_match_multiple_words_in_multiple_columns() {
        // given
        stub(params.getSearchableCols()).toReturn(Arrays.asList("foo", "bar"));
        stub(params.getSearch()).toReturn("55 44"); // even in reverse order

        List<Something> rows = TestUtils.load("5");

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(1).onProperty("foo").containsExactly("44");
    }

    @Test
    public void should_match_mixed_case_search_string() {
        // given
        stub(params.getSearchableCols()).toReturn(Arrays.asList("foo", "bar"));
        stub(params.getSearch()).toReturn("aBcD");

        List<Something> rows = TestUtils.load("6");

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(3).onProperty("foo").containsExactly("AbcD", "aBCD", "ABcd");
    }

    @Test
    public void should_not_panic_if_there_is_null_in_data() {
        // given
        stub(params.getSearchableCols()).toReturn(Arrays.asList("foo", "bar"));
        stub(params.getSearch()).toReturn("aBcD");

        List<Something> rows = TestUtils.load("6");
        rows.get(0).setFoo(null);

        // when
        filter.filter(rows);

        // then should not throw NullPointer
    }

}
