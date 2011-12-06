package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import pl.pplcanfly.datatables.DefaultSorter;
import pl.pplcanfly.datatables.ServerSideDataTable;
import pl.pplcanfly.datatables.params.RequestParams;
import pl.pplcanfly.datatables.types.Types;
import pl.pplcanfly.datatables.utils.TestUtils;

public class DefaultSorterTest {
    private ServerSideDataTable dataTable;
    private RequestParams params;
    private DefaultSorter sorter;

    @Before
    public void setUp() {
        dataTable = ServerSideDataTable.build()
                .column(Types.text(), "foo")
                .column(Types.numeric(), "bar")
                .done();

        params = mock(RequestParams.class);
        stub(params.getDisplayStart()).toReturn(0);
        stub(params.getDisplayLength()).toReturn(20);

        sorter = new DefaultSorter(dataTable, params);
    }

    @Test
    public void should_not_change_order_of_input_rows() {
        // given
        setSortCols(params, "foo");
        setSortDirs(params, "asc");

        List<Something> rows = TestUtils.load("1");

        // when
        sorter.sort(rows);

        // then
        assertThat(rows).isEqualTo(TestUtils.load("1"));
    }

    @Test
    public void should_sort_by_one_column_asc() {
        // given
        setSortCols(params, "foo");
        setSortDirs(params, "asc");

        List<Something> rows = TestUtils.load("1");

        // when
        List<?> processedRows = sorter.sort(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("1_foo_asc"));
    }

    @Test
    public void should_sort_by_one_column_desc() {
        // given
        setSortCols(params, "foo");
        setSortDirs(params, "desc");

        List<Something> rows = TestUtils.load("1");

        // when
        List<?> processedRows = sorter.sort(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("1_foo_desc"));
    }

    @Test
    public void should_preserve_order_of_elements_having_same_value_in_column() {
        // given
        setSortCols(params, "foo");
        setSortDirs(params, "asc");

        List<Something> rows = TestUtils.load("2");

        // when
        List<?> processedRows = sorter.sort(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("2_foo_asc"));
    }

    @Test
    public void should_sort_by_two_columns_both_asc() {
        // given
        setSortCols(params, "foo", "bar");
        setSortDirs(params, "asc", "asc");

        List<Something> rows = TestUtils.load("2");

        // when
        List<?> processedRows = sorter.sort(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("2_foo_asc_bar_asc"));
    }

    @Test
    public void should_sort_by_two_columns_asc_desc() {
        // given
        setSortCols(params, "foo", "bar");
        setSortDirs(params, "asc", "desc");

        List<Something> rows = TestUtils.load("2");

        // when
        List<?> processedRows = sorter.sort(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("2_foo_asc_bar_desc"));
    }

    @Test
    public void should_accept_custom_value_accessor() {
        // given
        ServerSideDataTable dataTable = ServerSideDataTable.build()
                .column(Types.text(), "foo", new ReversingValueAccessor())
                .column(Types.numeric(), "bar")
                .done();

        setSortCols(params, "foo");
        setSortDirs(params, "asc");

        DefaultSorter sorter = new DefaultSorter(dataTable, params);

        List<Something> rows = TestUtils.load("3");

        // when
        List<?> processedRows = sorter.sort(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("3_foo_asc_revacc"));
    }

    private void setSortCols(RequestParams params, String... cols) {
        stub(params.getSortCols()).toReturn(Arrays.asList(cols));
        stub(params.getSortingColsCount()).toReturn(cols.length);
    }

    private void setSortDirs(RequestParams params, String... dirs) {
        stub(params.getSortDirs()).toReturn(Arrays.asList(dirs));
    }


}
