package com.infy.controller;

import com.infy.dto.EmployeeDTO;
import com.infy.dto.PageDataDTO;
import com.infy.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@RequestMapping("/employee")
@Validated
public class EmployeeController {

    private Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService service;

    @PostMapping
    public ResponseEntity<Integer> addEmployee(@Valid @RequestBody EmployeeDTO employeeDTO){
        int empId = service.addEmployee(employeeDTO);
        return new ResponseEntity<>(empId, HttpStatus.OK);
    }

    @GetMapping("/{empId}")
    public EmployeeDTO findEmployee(@Digits(integer = 5, fraction = 0, message = "{employee.id.invalid}") @PathVariable(name = "empId") int empId){
        return service.findEmployeeById(empId);
    }

    @Secured("ROLE_USER")
    @GetMapping("/department/{department}")
    public List<EmployeeDTO> findEmployeesByDepartment(@Pattern(regexp = "^[A-Z]{4}$", message = "{employee.department.invalid}")
                                                       @PathVariable(name = "department") String department,
                                                       @Min (value = 0,message = "{page.value.invalid}") @RequestParam(name = "page") int page,
                                                       @Min(value = 0,message = "{size.value.invalid}") @RequestParam(name = "size") int size){
        return service.findEmployeeByDepartment(department,page,size);
    }

    @GetMapping("/city/{city}")
    public List<EmployeeDTO> findEmployeesByCity(@PathVariable(name = "city") String city){
        return service.findEmployeeByCity(city);
    }

    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_PUBLIC')")
    @GetMapping
    public List<EmployeeDTO> findAll(@Valid @RequestBody PageDataDTO pageData){
        logger.info("findAll request received: {}",pageData);
        return service.findAll(pageData.getPage(), pageData.getSize(), Sort.by(Sort.Direction.ASC,"empId"));
    }

    @PutMapping("/name")
    public String updateEmpName(@Pattern(regexp = "^[a-zA-Z ]{1,10}$", message = "{employee.name.invalid}")
            @RequestParam(name = "name") String empName,
                                     @Digits(integer = 5, fraction = 0, message = "{employee.id.invalid}")
                                     @RequestParam(name = "id") int empId){
        return service.updateEmployeeNameById(empName,empId);
    }
}
