package com.harri.training2.mappers;

import com.harri.training2.annotations.FieldMapping;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class GenericMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    @Override
    public T mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            Field[] fields = clazz.getDeclaredFields();
            Map<String, String> columnFieldMappings = getColumnFieldMappings(fields);

            for (Map.Entry<String, String> entry : columnFieldMappings.entrySet()) {
                String columnName = entry.getKey();
                String fieldName = entry.getValue();

                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(instance, rs.getObject(columnName));
            }

            return instance;
        } catch (Exception e) {
            throw new SQLException("Error mapping result set to object: ", e);
        }
    }

    private Map<String, String> getColumnFieldMappings(Field[] fields) {
        Map<String, String> mappings = new HashMap<>();
        for (Field field : fields) {
            FieldMapping fieldMapping = field.getAnnotation(FieldMapping.class);
            if (fieldMapping != null) {
                String columnName = fieldMapping.value();
                mappings.put(columnName, field.getName());
            }
        }
        return mappings;
    }
}