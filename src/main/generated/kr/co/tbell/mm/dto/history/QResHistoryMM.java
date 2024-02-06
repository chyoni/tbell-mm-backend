package kr.co.tbell.mm.dto.history;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.co.tbell.mm.dto.history.QResHistoryMM is a Querydsl Projection type for ResHistoryMM
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResHistoryMM extends ConstructorExpression<ResHistoryMM> {

    private static final long serialVersionUID = -170814951L;

    public QResHistoryMM(com.querydsl.core.types.Expression<? extends kr.co.tbell.mm.entity.EmployeeHistoryMM> mm) {
        super(ResHistoryMM.class, new Class<?>[]{kr.co.tbell.mm.entity.EmployeeHistoryMM.class}, mm);
    }

}

