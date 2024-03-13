package kr.co.tbell.mm.dto.history;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.co.tbell.mm.dto.history.QResContractHistoryStatistics is a Querydsl Projection type for ResContractHistoryStatistics
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResContractHistoryStatistics extends ConstructorExpression<ResContractHistoryStatistics> {

    private static final long serialVersionUID = 797576394L;

    public QResContractHistoryStatistics(com.querydsl.core.types.Expression<Integer> month, com.querydsl.core.types.Expression<Double> totalInputManMonth, com.querydsl.core.types.Expression<Integer> totalInputPrice, com.querydsl.core.types.Expression<Double> totalCalculateManMonth, com.querydsl.core.types.Expression<Integer> totalCalculatePrice, com.querydsl.core.types.Expression<String> contractNumber, com.querydsl.core.types.Expression<String> teamName) {
        super(ResContractHistoryStatistics.class, new Class<?>[]{int.class, double.class, int.class, double.class, int.class, String.class, String.class}, month, totalInputManMonth, totalInputPrice, totalCalculateManMonth, totalCalculatePrice, contractNumber, teamName);
    }

    public QResContractHistoryStatistics(com.querydsl.core.types.Expression<Double> totalInputManMonth, com.querydsl.core.types.Expression<Double> totalCalculateManMonth, com.querydsl.core.types.Expression<String> contractNumber, com.querydsl.core.types.Expression<String> teamName) {
        super(ResContractHistoryStatistics.class, new Class<?>[]{double.class, double.class, String.class, String.class}, totalInputManMonth, totalCalculateManMonth, contractNumber, teamName);
    }

}

