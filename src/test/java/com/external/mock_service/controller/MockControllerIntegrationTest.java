package com.external.mock_service.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class MockControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturnUserById() throws Exception {
		mockMvc.perform(get("/mock/users/13724"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.userId", is("13724")))
				.andExpect(jsonPath("$.departmentName", is("Operator departamenti")))
				.andExpect(jsonPath("$.section", is("Fraud hallarının idarə edilməsi şöbəsi")));
	}

	@Test
	void shouldReturnDepartmentByUserId() throws Exception {
		mockMvc.perform(get("/mock/users/13724/department"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.departmentId", is("OPS-001")))
				.andExpect(jsonPath("$.departmentName", is("Operator departamenti")));
	}

	@Test
	void shouldFilterActiveUsers() throws Exception {
		mockMvc.perform(get("/mock/users").param("active", "false"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].userId", is("12659")));
	}

	@Test
	void shouldReturnBranchById() throws Exception {
		mockMvc.perform(get("/mock/branches/BR-001"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.branchId", is("BR-001")))
				.andExpect(jsonPath("$.managerUserId", is("11637")));
	}

	@Test
	void shouldReturnBranchManager() throws Exception {
		mockMvc.perform(get("/mock/branches/BR-001/manager"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.branchId", is("BR-001")))
				.andExpect(jsonPath("$.manager.userId", is("11637")))
				.andExpect(jsonPath("$.manager.section", is("Təhlükəsizlik sistemləri şöbəsi")));
	}

	@Test
	void shouldReturnBranchesByManager() throws Exception {
		mockMvc.perform(get("/mock/branches/by-manager/11637"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].branchId", is("BR-001")));
	}
}
