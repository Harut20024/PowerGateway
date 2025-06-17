package com.powerGateway.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ApiKeyRepositoryImpl implements ApiKeyRepository {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Optional<String> getRoleIfValid(String key) {
        String sql = "SELECT role FROM api_keys WHERE key = :key";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("key", key);

        try {
            String role = jdbc.queryForObject(sql, params, String.class);
            return Optional.ofNullable(role);
        } catch (Exception e) {
            return Optional.empty();
        }
    }}
