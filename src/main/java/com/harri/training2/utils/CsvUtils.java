package com.harri.training2.utils;


import com.harri.training2.annotations.CSVMapping;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class CsvUtils {

    public static List<?> toCsv(List<?> results) {
        Class<?> classMapping = results.get(0).getClass();
        Field[] fields = classMapping.getDeclaredFields();

        boolean hasCsvMappingField = Arrays.stream(fields)
                .anyMatch(field -> field.isAnnotationPresent(CSVMapping.class));

        if (hasCsvMappingField) {
            return convertToCsv(results, classMapping);
        } else {
            return results;
        }
    }

    private static List<String> convertToCsv(List<?> results, Class<?> clazz) {
        try {
            List<String> csvRows = new ArrayList<>();
            csvRows.add(getCsvHeaders(clazz));

            results.forEach(result -> {
                String[] data = getFieldValues(result, clazz);
                csvRows.add(String.join(",", data));
            });

            return csvRows;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String[] getFieldValues(Object object, Class<?> clazz) {
        return Stream.of(clazz.getDeclaredFields())
                .peek(field -> field.setAccessible(true))
                .map(field -> {
                    try {
                        Object fieldValue = field.get(object);
                        return fieldValue != null ? fieldValue.toString() : "";
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return "";
                    }
                })
                .toArray(String[]::new);
    }

    private static String getCsvHeaders(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] headers = Stream.of(fields)
                .filter(field -> field.isAnnotationPresent(CSVMapping.class))
                .map(field -> field.getAnnotation(CSVMapping.class).value())
                .toArray(String[]::new);

        return String.join(",", headers);
    }
}
