package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.verify;
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
    private RowsProcessor sorter;
    private RowsProcessor filter;
    private RowsProcessor limiter;
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
        limiter = mock(Limiter.class);
        formatter = mock(Formatter.class);

        request = new DataTablesRequest(params, dataTable);
        request.setSorter(sorter);
        request.setFilter(filter);
        request.setLimiter(limiter);

        request.setFormatter(formatter);
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_filter_first_and_then_sort_and_limit() {
        // given
        List<Something> rows = new ArrayList<Something>();
        List filtered = new ArrayList<Something>();
        List sorted = new ArrayList<Something>();

        when(filter.process(rows)).thenReturn(filtered);
        when(sorter.process(filtered)).thenReturn(sorted);

        // when
        request.process(rows);

        // then
        InOrder inOrder = inOrder(filter, sorter, limiter);
        inOrder.verify(filter).process(rows);
        inOrder.verify(sorter).process(filtered);
        inOrder.verify(limiter).process(anyList());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void should_return_response_after_processing() {
        // given
        stub(params.getEcho()).toReturn(3);

        List rows = TestUtils.load("1");

        List processed = Arrays.asList(new Something("foo", 1), new Something("foo", 2));

        when(filter.process(rows)).thenReturn(rows);
        when(sorter.process(rows)).thenReturn(rows);
        when(limiter.process(rows)).thenReturn(processed);

        when(formatter.format(anyList(), anyInt(), anyInt())).thenReturn("json");

        // when
        DataTablesResponse response = request.process(rows);

        // then
        assertThat(response.getProcessedRows()).isSameAs(processed);
        assertThat(response.toJson()).isEqualTo("json");

        verify(formatter).format(eq(processed), eq(rows.size()), eq(2));
    }

}
