package kr.co.tbell.mm.dto.history;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.co.tbell.mm.dto.history.QResHistory is a Querydsl Projection type for ResHistory
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResHistory extends ConstructorExpression<ResHistory> {

    private static final long serialVersionUID = 1599820473L;

    public QResHistory(com.querydsl.core.types.Expression<? extends kr.co.tbell.mm.entity.project.Project> project, com.querydsl.core.types.Expression<? extends kr.co.tbell.mm.entity.employee.Employee> employee, com.querydsl.core.types.Expression<? extends kr.co.tbell.mm.entity.employeehistory.EmployeeHistory> employeeHistory) {
        super(ResHistory.class, new Class<?>[]{kr.co.tbell.mm.entity.project.Project.class, kr.co.tbell.mm.entity.employee.Employee.class, kr.co.tbell.mm.entity.employeehistory.EmployeeHistory.class}, project, employee, employeeHistory);
    }

}

