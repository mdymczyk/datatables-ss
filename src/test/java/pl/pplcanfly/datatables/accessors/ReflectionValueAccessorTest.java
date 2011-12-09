package pl.pplcanfly.datatables.accessors;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pl.pplcanfly.datatables.Something;

public class ReflectionValueAccessorTest {
    @Test
    public void should_get_value_from_object_by_declared_field() {
        // given
        RefSomething s = new RefSomething();

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("field");

        // when
        Object value = accessor.getValueFrom(s);

        // then
        assertThat(value).isEqualTo("fieldVal");
    }

    @Test
    public void should_get_value_from_object_by_declared_getter_method() {
        // given
        RefSomething s = new RefSomething();

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("foo");

        // when
        Object value = accessor.getValueFrom(s);

        // then
        assertThat(value).isEqualTo("getFoo");
    }

    @Test
    public void should_get_value_from_object_by_inherited_method() {
        // given
        RefSomethingSub s = new RefSomethingSub();

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("foo");

        // when
        Object value = accessor.getValueFrom(s);

        // then
        assertThat(value).isEqualTo("getFoo");
    }

    @Test
    public void should_get_value_from_object_by_inherited_field() {
        // given
        RefSomethingSub s = new RefSomethingSub();

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("publicField");

        // when
        Object value = accessor.getValueFrom(s);

        // then
        assertThat(value).isEqualTo("publicFieldVal");
    }

    @Test
    public void should_get_value_from_object_by_inherited_overriden_method() {
        // given
        RefSomethingSub s = new RefSomethingSub();

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("bar");

        // when
        Object value = accessor.getValueFrom(s);

        // then
        assertThat(value).isEqualTo("getBarSub");
    }

    @Test
    public void should_get_value_from_object_by_getter_bool_method() {
        // given
        RefSomethingSub s = new RefSomethingSub();

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("baz");

        // when
        Object value = accessor.getValueFrom(s);

        // then
        assertThat(value).isEqualTo("isBaz");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void should_throw_exception_if_value_not_accessible() {
        // given
        Something s = new Something("name", 22);
        String fieldName = "idontexist";

        thrown.expect(ValueNotAccessibleException.class);
        thrown.expectMessage(fieldName);

        ReflectionValueAccessor accessor = new ReflectionValueAccessor(fieldName);

        // when
        accessor.getValueFrom(s);
    }

    @Test
    public void should_throw_exception_if_getter_is_not_valid() {
        // given
        InvalidGetter obj = new InvalidGetter();

        thrown.expect(ValueNotAccessibleException.class);

        ReflectionValueAccessor accessor = new ReflectionValueAccessor("foo");

        // when
        accessor.getValueFrom(obj);
    }

}

class RefSomething {
    @SuppressWarnings("unused")
    private String field = "fieldVal";
    public String publicField = "publicFieldVal";

    public String getFoo() {
        return "getFoo";
    }

    public String getBar() {
        return "getBar";
    }
}

class RefSomethingSub extends RefSomething {

    // private foo field is not accessible here directly, only via getter

    @Override
    public String getBar() {
        return "getBarSub";
    }

    public String isBaz() {
        return "isBaz";
    }
}

class InvalidGetter {
    public String getFoo(int boo) {
        return "";
    }
}
