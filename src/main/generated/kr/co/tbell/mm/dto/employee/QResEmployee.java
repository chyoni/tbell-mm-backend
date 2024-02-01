package kr.co.tbell.mm.dto.employee;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.co.tbell.mm.dto.employee.QResEmployee is a Querydsl Projection type for ResEmployee
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResEmployee extends ConstructorExpression<ResEmployee> {

    private static final long serialVersionUID = 970638969L;

    public QResEmployee(com.querydsl.core.types.Expression<? extends kr.co.tbell.mm.entity.Employee> employee) {
        super(ResEmployee.class, new Class<?>[]{kr.co.tbell.mm.entity.Employee.class}, employee);
    }

}

