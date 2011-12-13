package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import pl.pplcanfly.datatables.types.Types;
import pl.pplcanfly.datatables.utils.TestUtils;

public class DataTablesRequestTest {

    private ServerSideDataTable dataTable;
    private RequestParams params;
    private DataTablesRequest request;
    private Sorter sorter;
    private Filter filter;
    private Formatter formatter;

    @Before
    public void setUp() {
        dataTable = ServerSideDataTable.build()
                .column(Types.text(), "foo")
                .column(Types.numeric(), "bar")
                .done();

        params = mock(RequestParams.class);
        stub(params.getDisplayStart()).toReturn(0);
        stub(params.getDisplayLength()).toReturn(20);

        sorter = mock(Sorter.class);
        filter = mock(Filter.class);
        formatter = mock(Formatter.class);

        request = new DataTablesRequest(params, dataTable);
        request.setSorter(sorter);
        request.setFilter(filter);
        request.setFormatter(formatter);
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_filter_first_and_then_sort() {
        // given
        stub(params.getEcho()).toReturn(3);
        stub(params.hasSearchParams()).toReturn(true);
        stub(params.hasSortingParams()).toReturn(true);

        List<Something> rows = new ArrayList<Something>();
        List filtered = new ArrayList<Something>();
        List sorted = new ArrayList<Something>();

        when(filter.filter(rows)).thenReturn(filtered);
        when(sorter.sort(filtered)).thenReturn(sorted);

        // when
        request.process(rows);

        // then
        InOrder inOrder = inOrder(filter, sorter);
        inOrder.verify(filter).filter(rows);
        inOrder.verify(sorter).sort(filtered);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @SuppressWarnings({ "rawtypes" })
    public void should_skip_filtering_if_theres_no_search_string() {
        // given
        stub(params.hasSearchParams()).toReturn(false);
        stub(params.hasSortingParams()).toReturn(true);

        List<Something> rows = new ArrayList<Something>();
        List filtered = new ArrayList<Something>();

        // when
        request.process(rows);

        // then
        verifyZeroInteractions(filter);
        verify(sorter).sort(filtered);
    }

    @Test
    public void should_skip_sorting_if_theres_are_no_sort_criteria() {
        // given
        stub(params.hasSearchParams()).toReturn(true);
        stub(params.hasSortingParams()).toReturn(false);

        List<Something> rows = new ArrayList<Something>();

        // when
        request.process(rows);

        // then
        verify(filter).filter(rows);
        verifyZeroInteractions(sorter);
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_return_response_after_processing() {
        // given
        stub(params.getEcho()).toReturn(3);
        stub(params.hasSearchParams()).toReturn(true);

        stub(params.getDisplayStart()).toReturn(0);
        stub(params.getDisplayLength()).toReturn(1);

        List<Something> rows = TestUtils.load("1");

        List processed = Arrays.asList(new Something("foo", 1), new Something("foo", 2));

        when(filter.filter(rows)).thenReturn(processed);
        when(sorter.sort(processed)).thenReturn(processed);
        when(formatter.format(anyList(), anyInt(), anyInt())).thenReturn("json");

        // when
        DataTablesResponse response = request.process(rows);

        // then
        assertThat(response.getProcessedRows()).hasSize(1); // limited to display length
        assertThat(response.toJson()).isEqualTo("json");

        verify(formatter).format(eq(Arrays.asList(processed.get(0))), eq(rows.size()), eq(2)); // limited to display length
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_offset_and_limit_processed_rows() {
        // given
        stub(params.getDisplayStart()).toReturn(1);
        stub(params.getDisplayLength()).toReturn(2);

        stub(params.hasSearchParams()).toReturn(true);

        List<Something> rows = TestUtils.load("1");
        List processed = TestUtils.load("1_foo_asc");
        when(filter.filter(rows)).thenReturn(processed);
        when(sorter.sort(processed)).thenReturn(processed);

        // when
        DataTablesResponse response = request.process(rows);

        // then
        assertThat(response.getProcessedRows()).hasSize(2).onProperty("foo").containsExactly("b", "c");
    }

}
