package kr.co.tbell.mm.dto.department;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.co.tbell.mm.dto.department.QResDepartment is a Querydsl Projection type for ResDepartment
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResDepartment extends ConstructorExpression<ResDepartment> {

    private static final long serialVersionUID = -1454011583L;

    public QResDepartment(com.querydsl.core.types.Expression<? extends kr.co.tbell.mm.entity.department.Department> department) {
        super(ResDepartment.class, new Class<?>[]{kr.co.tbell.mm.entity.department.Department.class}, department);
    }

}

