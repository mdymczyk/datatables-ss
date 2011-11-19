package pl.pplcanfly.datatables.comparators;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import pl.pplcanfly.datatables.Something;
import pl.pplcanfly.datatables.Type;
import pl.pplcanfly.datatables.accessors.ValueAccessor;

public class RowComparatorTest {

    @Test
    public void should_compare_objects_by_single_field() {
        // given
        Something s1 = new Something("u1", 5);
        Something s2 = new Something("u2", 10);
        Something s3 = new Something("u3", 5);

        RowComparator comparator = RowComparator.ascending(Type.INTEGER, "bar");

        // when then
        assertThat(comparator.compare(s1, s2)).isLessThan(0);
        assertThat(comparator.compare(s2, s1)).isGreaterThan(0);
        assertThat(comparator.compare(s1, s3)).isZero();
    }
    
    @Test
    public void should_compare_objects_by_single_field_descending() {
        // given
        Something s1 = new Something("u1", 5);
        Something s2 = new Something("u2", 10);
        Something s3 = new Something("u3", 5);

        RowComparator comparator = RowComparator.descending(Type.INTEGER, "bar");

        // when then
        assertThat(comparator.compare(s1, s2)).isGreaterThan(0);
        assertThat(comparator.compare(s2, s1)).isLessThan(0);
        assertThat(comparator.compare(s1, s3)).isZero();
    }
    
    @Test
    public void should_compare_objects_by_multiple_fields_asc_asc() {
        // given
        Something s1 = new Something("aaa", 5);
        Something s2 = new Something("aaa", 10);
        Something s3 = new Something("bbb", 2);

        RowComparator comparator = RowComparator.ascending(Type.STRING, "foo");
        comparator.add(RowComparator.ascending(Type.INTEGER, "bar"));

        // when then
        assertThat(comparator.compare(s1, s1)).isZero();
        assertThat(comparator.compare(s1, s2)).isLessThan(0);
        assertThat(comparator.compare(s1, s3)).isLessThan(0);
        
        assertThat(comparator.compare(s2, s1)).isGreaterThan(0);
        assertThat(comparator.compare(s2, s2)).isZero();
        assertThat(comparator.compare(s2, s3)).isLessThan(0);
        
        assertThat(comparator.compare(s3, s1)).isGreaterThan(0);
        assertThat(comparator.compare(s3, s2)).isGreaterThan(0);
        assertThat(comparator.compare(s3, s3)).isZero();
    }
    
    @Test
    public void should_compare_objects_by_multiple_fields_desc_desc() {
        // given
        Something s1 = new Something("aaa", 5);
        Something s2 = new Something("aaa", 10);
        Something s3 = new Something("bbb", 2);

        RowComparator comparator = RowComparator.descending(Type.STRING, "foo");
        comparator.add(RowComparator.descending(Type.INTEGER, "bar"));

        // when then
        assertThat(comparator.compare(s1, s1)).isZero();
        assertThat(comparator.compare(s1, s2)).isGreaterThan(0);
        assertThat(comparator.compare(s1, s3)).isGreaterThan(0);
        
        assertThat(comparator.compare(s2, s1)).isLessThan(0);
        assertThat(comparator.compare(s2, s2)).isZero();
        assertThat(comparator.compare(s2, s3)).isGreaterThan(0);
        
        assertThat(comparator.compare(s3, s1)).isLessThan(0);
        assertThat(comparator.compare(s3, s2)).isLessThan(0);
        assertThat(comparator.compare(s3, s3)).isZero();
    }
    
    @Test
    public void should_compare_objects_using_custom_value_accessor_asc() {
        // given
        Something s1 = new Something("abc3", 1);
        Something s2 = new Something("abc2", 1);
        Something s3 = new Something("abc1", 1);
        
        RowComparator comparator = RowComparator.ascending(Type.STRING, new ReversingValueAccessor());
        
        // when then
        assertThat(comparator.compare(s1, s2)).isGreaterThan(0);
        assertThat(comparator.compare(s1, s3)).isGreaterThan(0);
        
        assertThat(comparator.compare(s2, s1)).isLessThan(0);
        assertThat(comparator.compare(s2, s3)).isGreaterThan(0);
        
        assertThat(comparator.compare(s3, s1)).isLessThan(0);
        assertThat(comparator.compare(s3, s2)).isLessThan(0);
    }
    
    
    @Test
    public void should_compare_objects_using_custom_value_accessor_desc() {
        // given
        Something s1 = new Something("abc1", 1);
        Something s2 = new Something("abc2", 1);
        Something s3 = new Something("abc3", 1);
        
        RowComparator comparator = RowComparator.descending(Type.STRING, new ReversingValueAccessor());
        
        // when then
        assertThat(comparator.compare(s1, s2)).isGreaterThan(0);
        assertThat(comparator.compare(s1, s3)).isGreaterThan(0);
        
        assertThat(comparator.compare(s2, s1)).isLessThan(0);
        assertThat(comparator.compare(s2, s3)).isGreaterThan(0);
        
        assertThat(comparator.compare(s3, s1)).isLessThan(0);
        assertThat(comparator.compare(s3, s2)).isLessThan(0);
    }

}

class ReversingValueAccessor implements ValueAccessor {
    @Override
    public Object getValueFrom(Object obj) {
        return reverse(((Something) obj).getFoo());
    }
    
    private String reverse(String string) {
        return new StringBuilder(string).reverse().toString();
    }
};
        