package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.types.Types;
import pl.pplcanfly.datatables.utils.TestUtils;

public class SorterTest {

    private Column fooColumn = new Column(Types.text(), "foo");
    private Column barColumn = new Column(Types.numeric(), "bar");

    @Test
    @SuppressWarnings("unchecked")
    public void should_do_nothing_if_there_are_no_sortable_columns() {
        // given
        Sorter sorter = new Sorter(Collections.EMPTY_LIST, Collections.EMPTY_LIST);

        List<Something> rows = TestUtils.load("4");

        // when
        List<?> processed = sorter.process(rows);

        // then
        assertThat(processed).isSameAs(rows);
    }

    @Test
    public void should_not_change_order_of_input_rows() {
        // given
        List<Something> rows = TestUtils.load("1");

        Sorter sorter = new Sorter(Arrays.asList(fooColumn), Arrays.asList(SortOrder.ASC));

        // when
        sorter.process(rows);

        // then
        assertThat(rows).isEqualTo(TestUtils.load("1"));
    }

    @Test
    public void should_sort_by_one_column_asc() {
        // given
        List<Something> rows = TestUtils.load("1");

        Sorter sorter = new Sorter(Arrays.asList(fooColumn), Arrays.asList(SortOrder.ASC));

        // when
        List<?> processedRows = sorter.process(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("1_foo_asc"));
    }

    @Test
    public void should_sort_by_one_column_desc() {
        // given
        List<Something> rows = TestUtils.load("1");

        Sorter sorter = new Sorter(Arrays.asList(fooColumn), Arrays.asList(SortOrder.DESC));

        // when
        List<?> processedRows = sorter.process(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("1_foo_desc"));
    }

    @Test
    public void should_preserve_order_of_elements_having_same_value_in_column() {
        // given
        List<Something> rows = TestUtils.load("2");

        Sorter sorter = new Sorter(Arrays.asList(fooColumn), Arrays.asList(SortOrder.ASC));

        // when
        List<?> processedRows = sorter.process(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("2_foo_asc"));
    }

    @Test
    public void should_sort_by_two_columns_both_asc() {
        // given
        List<Something> rows = TestUtils.load("2");

        Sorter sorter = new Sorter(Arrays.asList(fooColumn, barColumn),
                Arrays.asList(SortOrder.ASC, SortOrder.ASC));

        // when
        List<?> processedRows = sorter.process(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("2_foo_asc_bar_asc"));
    }

    @Test
    public void should_sort_by_two_columns_asc_desc() {
        // given
        List<Something> rows = TestUtils.load("2");

        Sorter sorter = new Sorter(Arrays.asList(fooColumn, barColumn),
                Arrays.asList(SortOrder.ASC, SortOrder.DESC));

        // when
        List<?> processedRows = sorter.process(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("2_foo_asc_bar_desc"));
    }

    @Test
    public void should_accept_custom_value_accessor() {
        // given
        Sorter sorter = new Sorter(Arrays.asList(new Column(Types.text(), "foo",
                new ReversingValueAccessor())), Arrays.asList(SortOrder.ASC));

        List<Something> rows = TestUtils.load("3");

        // when
        List<?> processedRows = sorter.process(rows);

        // then
        assertThat(processedRows).isEqualTo(TestUtils.load("3_foo_asc_revacc"));
    }

}
