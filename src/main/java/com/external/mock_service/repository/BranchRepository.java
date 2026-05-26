package com.external.mock_service.repository;

import com.external.mock_service.config.MockDataProperties;
import com.external.mock_service.model.BranchRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class BranchRepository {

	private final JsonDataLoader jsonDataLoader;
	private final MockDataProperties mockDataProperties;

	private List<BranchRecord> branches;
	private Map<String, BranchRecord> branchesById;

	public BranchRepository(JsonDataLoader jsonDataLoader, MockDataProperties mockDataProperties) {
		this.jsonDataLoader = jsonDataLoader;
		this.mockDataProperties = mockDataProperties;
	}

	@PostConstruct
	void init() {
		branches = List.copyOf(jsonDataLoader.loadList(
				mockDataProperties.branchesLocation(),
				new TypeReference<>() {
				}
		));
		branchesById = branches.stream().collect(Collectors.toUnmodifiableMap(BranchRecord::branchId, Function.identity()));
	}

	public List<BranchRecord> findAll() {
		return branches;
	}

	public Optional<BranchRecord> findById(String branchId) {
		return Optional.ofNullable(branchesById.get(branchId));
	}
}
