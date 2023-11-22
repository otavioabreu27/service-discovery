package com.inpe.focosservice.database;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostgresDriver {

    private JdbcTemplate jdbcTemplate;

    public PostgresDriver(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public <T> List<T> executeQuery(String sql, Class<T> elementType) {
        try {
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(elementType));
        } catch (CannotGetJdbcConnectionException ex) {
            // Connection may be expired. Try creating a new JdbcTemplate or reinitialize the existing one.
            handleConnectionException(ex);
            // Retry the query
            return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(elementType));
        } catch (Exception e) {
            // Handle other exceptions appropriately
            e.printStackTrace();
            throw new RuntimeException("Error executing query: " + e.getMessage(), e);
        }
    }

    private void handleConnectionException(CannotGetJdbcConnectionException ex) {
        this.jdbcTemplate = createNewJdbcTemplate();
    }

    private JdbcTemplate createNewJdbcTemplate() {
        return new JdbcTemplate(jdbcTemplate.getDataSource());
    }
}