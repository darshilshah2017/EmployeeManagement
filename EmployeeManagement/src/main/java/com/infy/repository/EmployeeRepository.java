package com.infy.repository;

import com.infy.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    public Page<Employee> findByDepartment(String department, Pageable pageable);
    public List<Employee> findByAddress_City(String city);

    @Transactional
    @Modifying
    @Query(value = "update Employee emp set emp.empName=:empName where emp.empId=:empId")
    public int updateNameById(@Param("empName") String empName,@Param("empId") int empId);
}
