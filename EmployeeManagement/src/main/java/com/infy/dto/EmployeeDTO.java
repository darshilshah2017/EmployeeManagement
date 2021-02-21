package com.infy.dto;

import com.infy.entity.Employee;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class EmployeeDTO {

    @Digits(integer = 5, fraction = 0, message = "{employee.id.invalid}")
    private int empId;
    @NotNull(message = "{employee.name.null}")
    @Pattern(regexp = "^[a-zA-Z ]{1,10}$", message = "{employee.name.invalid}")
    private String empName;
    @NotNull(message = "{employee.department.null}")
    @Pattern(regexp = "^[A-Z]{4}$", message = "{employee.department.invalid}")
    private String department;
    @NotBlank(message = "{employee.baseLocation.blank}")
    private String baseLocation;
    @NotNull(message = "{employee.address.null}")
    private AddressDTO address;

    public EmployeeDTO(){}

    public EmployeeDTO(int empId, String empName, String department, String baseLocation, AddressDTO address) {
        this.empId = empId;
        this.empName = empName;
        this.department = department;
        this.baseLocation = baseLocation;
        this.address = address;
    }

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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public static EmployeeDTO createEmployeeDTO(Employee employee){
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId(employee.getEmpId());
        employeeDTO.setEmpName(employee.getEmpName());
        employeeDTO.setBaseLocation(employee.getBaseLocation());
        employeeDTO.setDepartment(employee.getDepartment());

        if(employee.getAddress()!=null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setAddressLine(employee.getAddress().getAddressLine());
            addressDTO.setCity(employee.getAddress().getCity());
            addressDTO.setState(employee.getAddress().getState());
            addressDTO.setPincode(employee.getAddress().getPincode());
            employeeDTO.setAddress(addressDTO);
        }
        return employeeDTO;
    }
}
