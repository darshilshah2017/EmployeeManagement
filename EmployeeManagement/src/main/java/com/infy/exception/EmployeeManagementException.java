package com.infy.exception;

public class EmployeeManagementException extends RuntimeException{

    public EmployeeManagementException(String errorMsg){
        super(errorMsg);
    }
}
