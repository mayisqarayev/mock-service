package com.external.mock_service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "mock-data")
public record MockDataProperties(
		String usersLocation,
		String branchesLocation
) {
}
