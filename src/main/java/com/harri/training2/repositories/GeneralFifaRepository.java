package com.harri.training2.repositories;

import com.harri.training2.mappers.GenericMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public abstract class GeneralFifaRepository<T> {
    private final JdbcTemplate jdbcTemplate;
    private final GenericMapper<T> genericMapper;

    public GeneralFifaRepository(JdbcTemplate jdbcTemplate, Class<T> clazz) {
        this.jdbcTemplate = jdbcTemplate;
        this.genericMapper = new GenericMapper<>(clazz);
    }

    protected abstract String select();

    protected String where() { return ""; }

    protected String join() { return ""; }

    protected String groupBy(){ return ""; }

    protected String orderBy() { return ""; }

    protected String limit() { return ""; }

    public List<T> executeQuery(){
        String sql = select() + where() + join() + groupBy() + orderBy() + limit();
        return jdbcTemplate.query(sql, genericMapper);
    }
}
