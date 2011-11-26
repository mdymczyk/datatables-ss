package pl.pplcanfly.datatables.accessors;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import pl.pplcanfly.datatables.Something;
import pl.pplcanfly.datatables.accessors.ReflectionValueAccessor;
import pl.pplcanfly.datatables.accessors.ValueNotAccessibleException;

public class ReflectionValueAccessorTest {

    @Test
    public void should_get_value_from_object() {
        // given
        Something s = new Something("name", 22);

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("foo");

        // when
        Object value = accessor.getValueFrom(s);

        // then
        assertThat(value).isEqualTo("name");
    }

    @Test(expected=ValueNotAccessibleException.class)
    public void should_throw_exception_if_value_not_accessible() {
        // given
        Something s = new Something("name", 22);

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("idontexist");

        // when
        accessor.getValueFrom(s);

        // then throw exception
    }

}
