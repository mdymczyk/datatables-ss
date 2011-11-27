package pl.pplcanfly.datatables;

public class Something {
    private String foo;
    private int bar;

    public Something() { }

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + bar;
        result = prime * result + ((foo == null) ? 0 : foo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Something other = (Something) obj;
        if (bar != other.bar)
            return false;
        if (foo == null) {
            if (other.foo != null)
                return false;
        } else if (!foo.equals(other.foo))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [foo=" + foo + ", bar=" + bar + "]";
    }

}
