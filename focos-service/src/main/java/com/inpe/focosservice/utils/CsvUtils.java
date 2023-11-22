package com.inpe.focosservice.utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class CsvUtils {
    public static <T> String convertListToCSV(List<T> objectList) {
        if (objectList == null || objectList.isEmpty()) {
            return "";
        }

        Class<?> clazz = objectList.get(0).getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);

        StringBuilder csvData = new StringBuilder();

        // Append headers
        fieldList.forEach(field -> csvData.append(field.getName()).append(","));

        csvData.deleteCharAt(csvData.length() - 1);
        csvData.append("\n");

        // Append values for each object in the list
        for (T object : objectList) {
            fieldList.forEach(field -> {
                field.setAccessible(true);
                try {
                    csvData.append(field.get(object)).append(",");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            csvData.deleteCharAt(csvData.length() - 1);
            csvData.append("\n");
        }

        return csvData.toString();
    }
}
