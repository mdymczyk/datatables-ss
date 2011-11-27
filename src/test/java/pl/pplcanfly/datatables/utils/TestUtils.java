package pl.pplcanfly.datatables.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pl.pplcanfly.datatables.Something;

public class TestUtils {
    public static List<Something> load(String file) {
        InputStream is = TestUtils.class.getResourceAsStream("/fixtures/" + file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        List<Something> list = new ArrayList<Something>();
        String line = null;
        while ((line = readLine(reader)) != null) {
            String[] splitted = line.split(" ");
            list.add(new Something(splitted[0], Integer.parseInt(splitted[1])));
        }

        return list;
    }

    private static String readLine(BufferedReader reader) {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
