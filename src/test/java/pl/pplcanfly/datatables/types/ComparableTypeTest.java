package pl.pplcanfly.datatables.types;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class ComparableTypeTest {

    private ComparableType type = new ComparableType();

    @Test
    public void should_compare_ints() {
        assertThat(type.compare(1, 2)).isNegative();
        assertThat(type.compare(1, 1)).isZero();
        assertThat(type.compare(2, 1)).isPositive();
    }

    @Test
    public void should_compare_doubles() {
        assertThat(type.compare(1.1, 2.3)).isNegative();
        assertThat(type.compare(1.2, 1.2)).isZero();
        assertThat(type.compare(2.4, 1.5)).isPositive();
    }

    @Test
    public void should_compare_longs() {
        assertThat(type.compare(1L, 2L)).isNegative();
        assertThat(type.compare(1L, 1L)).isZero();
        assertThat(type.compare(2L, 1L)).isPositive();
    }

}
