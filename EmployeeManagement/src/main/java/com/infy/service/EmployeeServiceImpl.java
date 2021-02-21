package com.infy.service;

import com.infy.dto.EmployeeDTO;
import com.infy.entity.Employee;
import com.infy.exception.EmployeeManagementException;
import com.infy.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

    Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    @Autowired
    EmployeeRepository employeeRepository;

    public int addEmployee(EmployeeDTO employeeDTO){
        Employee employee = Employee.createEmployeeEntity(employeeDTO);
        employee = employeeRepository.saveAndFlush(employee);
        return employee.getEmpId();
    }

    public EmployeeDTO findEmployeeById(int empId){
        Optional<Employee> employee = employeeRepository.findById(empId);
        if(!employee.isPresent())
            throw new EmployeeManagementException("No employee found");
        return EmployeeDTO.createEmployeeDTO(employee.get());
    }

    //Filter the records based on department and responding with the result using pagination
    @Override
    public List<EmployeeDTO> findEmployeeByDepartment(String department,int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC,"empId"));
        Page<Employee> employeeList = employeeRepository.findByDepartment(department,pageable);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for(Employee employee: employeeList){
            EmployeeDTO employeeDTO = EmployeeDTO.createEmployeeDTO(employee);
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    @Override
    public List<EmployeeDTO> findEmployeeByCity(String city) {
        List<Employee> employeeList = employeeRepository.findByAddress_City(city);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for(Employee employee: employeeList){
            EmployeeDTO employeeDTO = EmployeeDTO.createEmployeeDTO(employee);
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    public List<EmployeeDTO> findAll(int page, int size, Sort sort){
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Employee> page1 = employeeRepository.findAll(pageable);
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        logger.info("Total elements/rows in the table: {}",page1.getTotalElements());
        logger.info("Total number of pages required to display all the elements/rows: {}",page1.getTotalPages());
        logger.info("Total elements/rows in the current page: {}",page1.getNumberOfElements());
        for(Employee employee: page1.getContent()){
            EmployeeDTO employeeDTO = EmployeeDTO.createEmployeeDTO(employee);
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    @Override
    public String updateEmployeeNameById(String empName, int empId){
        int noOfRowsUpdated = employeeRepository.updateNameById(empName, empId);

        try {
            Thread.currentThread().sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int noOfRowsUpdated2 = employeeRepository.updateNameById("qwerty", empId);
        return (noOfRowsUpdated == 0)?"No employee exists with id:"+empId:"Update name successful";
    }
}
