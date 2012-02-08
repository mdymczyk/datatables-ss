package pl.pplcanfly.datatables.types;


public final class Types {
    private Types() {}

    public static Type numeric() {
        return new ComparableType();
    }

    public static Type text() {
        return new TextType();
    }

    public static Type date() {
        return new DateType();
    }

    public static Type bool() {
    	return new BooleanType();
    }

}
