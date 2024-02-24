package com.workintech;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.entity.Employee;
import com.workintech.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetAllEmployees() throws Exception {
        Mockito.when(employeeService.findAll()).thenReturn(Arrays.asList(new Employee()));
        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testGetEmployeeById() throws Exception {
        Mockito.when(employeeService.findById(1L)).thenReturn(new Employee());
        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testGetEmployeeByEmail() throws Exception {
        Mockito.when(employeeService.findByEmail("test@example.com")).thenReturn(Arrays.asList(new Employee()));
        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees/byEmail/test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testGetEmployeesByOrder() throws Exception {
        Mockito.when(employeeService.findByOrderByLastName()).thenReturn(Arrays.asList(new Employee()));
        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees/byOrder")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testSaveOrUpdateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setLastName("Doe");

        Mockito.when(employeeService.saveOrUpdate(Mockito.any(Employee.class))).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/workintech/employees")
                        .content(objectMapper.writeValueAsString(employee))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testGetEmployeesBySalary() throws Exception {
        Mockito.when(employeeService.findBySalaryGreaterThanOrderBySalaryDesc(50000.0)).thenReturn(Arrays.asList(new Employee()));
        mockMvc.perform(MockMvcRequestBuilders.post("/workintech/employees/bySalary/50000.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void testDeleteEmployeeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/workintech/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Employee with id 1 has been deleted."));
    }
}
