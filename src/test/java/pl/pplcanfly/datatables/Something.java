package pl.pplcanfly.datatables;

public class Something {
    private String foo;
    private int bar;

    public Something(String foo, int bar) {
        this.foo = foo;
        this.bar = bar;
    }
    
    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public int getBar() {
        return bar;
    }

    public void setBar(int bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "User [foo=" + foo + ", bar=" + bar + "]";
    }

}
