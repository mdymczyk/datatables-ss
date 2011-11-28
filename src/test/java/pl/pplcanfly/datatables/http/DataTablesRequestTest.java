package pl.pplcanfly.datatables.http;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.Something;
import pl.pplcanfly.datatables.filtering.Filter;
import pl.pplcanfly.datatables.sorting.Sorter;
import pl.pplcanfly.datatables.types.Types;
import pl.pplcanfly.datatables.utils.TestUtils;

public class DataTablesRequestTest {

    private ServerSideDataTable dataTable;
    private RequestParams params;
    private DataTablesRequest request;
    private Sorter sorter;
    private Filter filter;

    @Before
    public void setUp() {
        dataTable = new ServerSideDataTable();
        dataTable.addColumn(Types.text(), "foo");
        dataTable.addColumn(Types.numeric(), "bar");

        params = mock(RequestParams.class);
        stub(params.getDisplayStart()).toReturn(0);
        stub(params.getDisplayLength()).toReturn(20);

        sorter = mock(Sorter.class);
        filter = mock(Filter.class);

        request = new DataTablesRequest(params, dataTable);
        request.setSorter(sorter);
        request.setFilter(filter);
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_filter_first_and_then_sort() {
        // given
        stub(params.getEcho()).toReturn(3);

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
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_set_response_params_after_sorting() {
        // given
        stub(params.getEcho()).toReturn(3);

        List<Something> rows = TestUtils.load("1");
        List processed = new ArrayList<Something>();
        processed.add(new Something());
        processed.add(new Something());
        when(filter.filter(rows)).thenReturn(processed);
        when(sorter.sort(processed)).thenReturn(processed);

        // when
        DataTablesResponse response = request.process(rows);

        // then
        assertThat(response.getParams().getEcho()).isEqualTo(3);
        assertThat(response.getParams().getTotalRecords()).isEqualTo(4);
        assertThat(response.getParams().getTotalDisplayRecords()).isEqualTo(2);
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_offset_and_limit_processed_rows() {
        // given
        stub(params.getDisplayStart()).toReturn(1);
        stub(params.getDisplayLength()).toReturn(2);

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
