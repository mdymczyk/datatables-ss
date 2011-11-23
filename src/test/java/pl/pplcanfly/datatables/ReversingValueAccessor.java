package pl.pplcanfly.datatables;

import pl.pplcanfly.datatables.accessors.ValueAccessor;

public class ReversingValueAccessor implements ValueAccessor {
    @Override
    public Object getValueFrom(Object obj) {
        return reverse(((Something) obj).getFoo());
    }

    private String reverse(String string) {
        return new StringBuilder(string).reverse().toString();
    }
};
