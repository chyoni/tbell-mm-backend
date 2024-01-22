package kr.co.tbell.mm.service;

import kr.co.tbell.mm.dto.employee.ReqCreateEmployee;
import kr.co.tbell.mm.dto.employee.ResCreateEmployee;

import javax.management.InstanceAlreadyExistsException;

public interface EmployeeService {
    ResCreateEmployee createEmployee(ReqCreateEmployee createEmployee) throws InstanceAlreadyExistsException;
}
