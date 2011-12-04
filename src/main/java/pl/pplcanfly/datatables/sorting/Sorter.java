package pl.pplcanfly.datatables.sorting;

import java.util.List;

public interface Sorter {

    List<?> sort(List<?> rows);

}