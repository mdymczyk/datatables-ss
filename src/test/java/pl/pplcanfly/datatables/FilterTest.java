package pl.pplcanfly.datatables;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.types.Types;
import pl.pplcanfly.datatables.utils.TestUtils;

public class FilterTest {
    private Column fooColumn = new Column(Types.text(), "foo");
    private Column barColumn = new Column(Types.numeric(), "bar");

    @Test
    @SuppressWarnings("unchecked")
    public void should_do_nothing_if_there_are_no_searchable_columns() {
        // given
        Filter filter = new Filter(Collections.EMPTY_LIST, "a");

        List<Something> rows = TestUtils.load("4");

        // when
        List<?> processed = filter.process(rows);

        // then
        assertThat(processed).isSameAs(rows);
    }

    @Test
    public void should_not_alter_input_list() {
        // given
        List<Something> rows = TestUtils.load("4");

        Filter filter = new Filter(Arrays.asList(fooColumn), "aa");

        // when
        filter.process(rows);

        // then
        assertThat(rows).hasSize(4);
    }

    @Test
    public void should_filter_records_by_single_column() {
        // given
        List<Something> rows = TestUtils.load("4");

        Filter filter = new Filter(Arrays.asList(fooColumn), "aa");

        // when
        List<?> processedRows = filter.process(rows);

        // then
        assertThat(processedRows).hasSize(2).onProperty("foo").containsExactly("aax", "aay");
    }

    @Test
    public void should_match_numeric_columns_as_if_it_was_text() {
        // given
        List<Something> rows = TestUtils.load("5");

        Filter filter = new Filter(Arrays.asList(fooColumn, barColumn), "22");

        // when
        List<?> processedRows = filter.process(rows);

        // then
        assertThat(processedRows).hasSize(2).onProperty("foo").containsExactly("11", "22");
    }

    @Test
    public void should_match_multiple_words_in_multiple_columns() {
        // given
        List<Something> rows = TestUtils.load("5");

        Filter filter = new Filter(Arrays.asList(fooColumn, barColumn), "55 44"); // even in reverse order

        // when
        List<?> processedRows = filter.process(rows);

        // then
        assertThat(processedRows).hasSize(1).onProperty("foo").containsExactly("44");
    }

    @Test
    public void should_match_nothing_if_row_does_not_conain_both_search_words() {
        // given
        List<Something> rows = TestUtils.load("6");

        Filter filter = new Filter(Arrays.asList(fooColumn, barColumn), "55 XX"); // 55 matches but XX does not

        // when
        List<?> processedRows = filter.process(rows);

        // then
        assertThat(processedRows).hasSize(0);
    }

    @Test
    public void should_match_mixed_case_search_string() {
        // given
        List<Something> rows = TestUtils.load("6");

        Filter filter = new Filter(Arrays.asList(fooColumn, barColumn), "aBcD");

        // when
        List<?> processedRows = filter.process(rows);

        // then
        assertThat(processedRows).hasSize(3).onProperty("foo").containsExactly("AbcD", "aBCD", "ABcd");
    }

    @Test
    public void should_not_panic_if_there_is_null_in_data() {
        // given
        List<Something> rows = TestUtils.load("6");
        rows.get(0).setFoo(null);

        Filter filter = new Filter(Arrays.asList(fooColumn, barColumn), "aBcD");

        // when
        filter.process(rows);

        // then should not throw NullPointer
    }

    @Test
    public void should_escape_special_characters_when_filtering() {
        // given
        List<Something> rows = TestUtils.load("7");

        Filter filter = new Filter(Arrays.asList(fooColumn), ".*?/");

        // when
        List<?> processedRows = filter.process(rows);

        // then
        assertThat(processedRows).hasSize(1).onProperty("foo").containsExactly("-.*?/#"); // only one should be filtered
    }

}
