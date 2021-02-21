package com.infy.entity;

import com.infy.dto.EmployeeDTO;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {

    @Id
    private int empId;
    private String empName;
    private String department;
    private String baseLocation;
    @Embedded
    private Address address;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getBaseLocation() {
        return baseLocation;
    }

    public void setBaseLocation(String baseLocation) {
        this.baseLocation = baseLocation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public static Employee createEmployeeEntity(EmployeeDTO employeeDTO){
        Employee employee = new Employee();
        employee.setEmpId(employeeDTO.getEmpId());
        employee.setEmpName(employeeDTO.getEmpName());
        employee.setDepartment(employeeDTO.getDepartment());
        employee.setBaseLocation(employeeDTO.getBaseLocation());

        Address address = new Address();
        address.setAddressLine(employeeDTO.getAddress().getAddressLine());
        address.setCity(employeeDTO.getAddress().getCity());
        address.setState(employeeDTO.getAddress().getState());
        address.setPincode(employeeDTO.getAddress().getPincode());
        employee.setAddress(address);

        return employee;
    }
}
