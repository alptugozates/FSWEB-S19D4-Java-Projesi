package com.workintech;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.controller.EmployeeController;
import com.workintech.entity.Employee;
import com.workintech.exceptions.EmployeeNotFoundException;
import com.workintech.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private List<Employee> employeeList;

    public EmployeeControllerTest(MockMvc mockMvc, EmployeeService employeeService, EmployeeController employeeController, List<Employee> employeeList) {
        this.mockMvc = mockMvc;
        this.employeeService = employeeService;
        this.employeeController = employeeController;
        this.employeeList = employeeList;
    }

    @BeforeEach
    void setUp() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee(1L, "John", "Doe", "johndoe@gmail.com", 50.000 ));
        employeeList.add(new Employee(2L,"Jane", "Doe", "janedoe@gmail.com", 50.000 ));
    }

    @Test
    void getAllEmployees() throws Exception {
        when(employeeService.findAll()).thenReturn(employeeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }

    @Test
    void getEmployeeById() throws Exception {
        when(employeeService.findById(1L)).thenReturn(employeeList.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(50000.0));
    }

    @Test
    void getEmployeeByEmail() throws Exception {
        when(employeeService.findByEmail("john.doe@example.com")).thenReturn(Optional.of(employeeList.get(0)));

        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees/byEmail/john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(50000.0));
    }

    @Test
    void getEmployeeByEmail_NotFound() throws Exception {
        when(employeeService.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees/byEmail/nonexistent@example.com"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void getEmployeesOrderByLastName() throws Exception {
        when(employeeService.findByOrderByLastName()).thenReturn(employeeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/workintech/employees/byOrder/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2));
    }

    @Test
    void saveOrUpdateEmployee() throws Exception {
        Employee newEmployee = new Employee(null, "New", "Employee", "new.employee@example.com", 70000.0);
        when(employeeService.saveOrUpdate(any(Employee.class))).thenReturn(newEmployee);

        mockMvc.perform(MockMvcRequestBuilders.post("/workintech/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("New Employee"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("new.employee@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.salary").value(70000.0));
    }

    @Test
    void getEmployeesBySalaryGreaterThan() throws Exception {
        double salary = 55000.0;
        when(employeeService.findBySalaryGreaterThanOrderBySalaryDesc(salary)).thenReturn(employeeList);

        mockMvc.perform(MockMvcRequestBuilders.post("/workintech/employees/bySalary/{salary}", salary))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(1));
    }

    @Test
    void deleteEmployeeById() throws Exception {
        Long employeeId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/workintech/employees/{id}", employeeId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
