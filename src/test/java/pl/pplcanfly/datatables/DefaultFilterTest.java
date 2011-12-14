package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.types.Types;
import pl.pplcanfly.datatables.utils.TestUtils;

public class DefaultFilterTest {
    private Column fooColumn = new Column(Types.text(), "foo");
    private Column barColumn = new Column(Types.numeric(), "bar");

    @Test
    public void should_not_alter_input_list() {
        // given
        List<Something> rows = TestUtils.load("4");

        DefaultFilter filter = new DefaultFilter(Arrays.asList(fooColumn), "aa");

        // when
        filter.filter(rows);

        // then
        assertThat(rows).hasSize(4);
    }

    @Test
    public void should_filter_records_by_single_column() {
        // given
        List<Something> rows = TestUtils.load("4");

        DefaultFilter filter = new DefaultFilter(Arrays.asList(fooColumn), "aa");

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(2).onProperty("foo").containsExactly("aax", "aay");
    }

    @Test
    public void should_match_numeric_columns_as_if_it_was_text() {
        // given
        List<Something> rows = TestUtils.load("5");

        DefaultFilter filter = new DefaultFilter(Arrays.asList(fooColumn, barColumn), "22");

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(2).onProperty("foo").containsExactly("11", "22");
    }

    @Test
    public void should_match_multiple_words_in_multiple_columns() {
        // given
        List<Something> rows = TestUtils.load("5");

        DefaultFilter filter = new DefaultFilter(Arrays.asList(fooColumn, barColumn), "55 44"); // even in reverse order

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(1).onProperty("foo").containsExactly("44");
    }

    @Test
    public void should_match_nothing_if_row_does_not_conain_both_search_words() {
        // given
        List<Something> rows = TestUtils.load("6");

        DefaultFilter filter = new DefaultFilter(Arrays.asList(fooColumn, barColumn), "55 XX"); // 55 matches but XX does not

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(0);
    }

    @Test
    public void should_match_mixed_case_search_string() {
        // given
        List<Something> rows = TestUtils.load("6");

        DefaultFilter filter = new DefaultFilter(Arrays.asList(fooColumn, barColumn), "aBcD");

        // when
        List<?> processedRows = filter.filter(rows);

        // then
        assertThat(processedRows).hasSize(3).onProperty("foo").containsExactly("AbcD", "aBCD", "ABcd");
    }

    @Test
    public void should_not_panic_if_there_is_null_in_data() {
        // given
        List<Something> rows = TestUtils.load("6");
        rows.get(0).setFoo(null);

        DefaultFilter filter = new DefaultFilter(Arrays.asList(fooColumn, barColumn), "aBcD");

        // when
        filter.filter(rows);

        // then should not throw NullPointer
    }

}
