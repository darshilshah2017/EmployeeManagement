package com.infy.service;

import com.infy.dto.EmployeeDTO;
import com.infy.entity.Employee;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface EmployeeService {

    public int addEmployee(EmployeeDTO employeeDTO);
    public EmployeeDTO findEmployeeById(int empId);
    public List<EmployeeDTO> findEmployeeByDepartment(String department,int page, int size);
    public List<EmployeeDTO> findEmployeeByCity(String city);
    public List<EmployeeDTO> findAll(int page, int size, Sort sort);

    public String updateEmployeeNameById(String empName, int empId);
}
