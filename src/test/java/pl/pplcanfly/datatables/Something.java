package pl.pplcanfly.datatables;

public class Something {
    private String foo;
    private int bar;

    public Something(String name, int age) {
        this.foo = name;
        this.bar = age;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String name) {
        this.foo = name;
    }

    public int getBar() {
        return bar;
    }

    public void setBar(int age) {
        this.bar = age;
    }

    @Override
    public String toString() {
        return "User [foo=" + foo + ", bar=" + bar + "]";
    }

}
