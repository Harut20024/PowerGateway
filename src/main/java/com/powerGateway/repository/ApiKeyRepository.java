package com.powerGateway.repository;

import java.util.Optional;

public interface ApiKeyRepository {
    Optional<String> getRoleIfValid(String key);
}
