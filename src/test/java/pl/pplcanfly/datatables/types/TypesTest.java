package pl.pplcanfly.datatables.types;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

public class TypesTest {

    @Test
    public void should_return_instances_of_type() {
        assertThat(Types.text()).isInstanceOf(TextType.class);
        assertThat(Types.numeric()).isInstanceOf(ComparableType.class);
        assertThat(Types.date()).isInstanceOf(DateType.class);
        assertThat(Types.bool()).isInstanceOf(BooleanType.class);
    }

}
