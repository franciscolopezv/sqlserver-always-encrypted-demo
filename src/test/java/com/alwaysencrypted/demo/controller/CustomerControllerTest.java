package com.alwaysencrypted.demo.controller;

import com.alwaysencrypted.demo.dto.CreateCustomerRequest;
import com.alwaysencrypted.demo.model.CustomerNVarchar;
import com.alwaysencrypted.demo.model.CustomerVarchar;
import com.alwaysencrypted.demo.repository.CustomerNVarcharRepository;
import com.alwaysencrypted.demo.repository.CustomerVarcharRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

@WebMvcTest(CustomerController.class)
@ActiveProfiles("test")
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerNVarcharRepository nVarcharRepository;

    @MockitoBean
    private CustomerVarcharRepository varcharRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateBothCustomersSuccessfully() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.setName("John Doe");
        request.setSsn("123-45-6789");

        Mockito.when(varcharRepository.save(ArgumentMatchers.any(CustomerVarchar.class)))
                .thenReturn(new CustomerVarchar());
        Mockito.when(nVarcharRepository.save(ArgumentMatchers.any(CustomerNVarchar.class)))
                .thenReturn(new CustomerNVarchar());

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("✅ VARCHAR and NVARCHAR customers saved."));
    }

    @Test
    void shouldReturnBadRequestWhenMissingFields() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest(); // No name or SSN

        mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Name and SSN are required."));
    }

    @Test
    void shouldReturnAllUnifiedCustomers() throws Exception {
        CustomerVarchar varchar = new CustomerVarchar();
        varchar.setId(1L);
        varchar.setName("Varchar Name");
        varchar.setSsn("111");

        CustomerNVarchar nvarchar = new CustomerNVarchar();
        nvarchar.setId(2L);
        nvarchar.setName("NVarchar Name");
        nvarchar.setSsn("222");

        Mockito.when(varcharRepository.findAll()).thenReturn(List.of(varchar));
        Mockito.when(nVarcharRepository.findAll()).thenReturn(List.of(nvarchar));

        mockMvc.perform(MockMvcRequestBuilders.get("/customers/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }
}
