package kr.co.tbell.mm.dto.history;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * kr.co.tbell.mm.dto.history.QResHistoryStatistics is a Querydsl Projection type for ResHistoryStatistics
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QResHistoryStatistics extends ConstructorExpression<ResHistoryStatistics> {

    private static final long serialVersionUID = 1968213180L;

    public QResHistoryStatistics(com.querydsl.core.types.Expression<Integer> month, com.querydsl.core.types.Expression<Double> totalInputManMonth, com.querydsl.core.types.Expression<Integer> totalInputPrice, com.querydsl.core.types.Expression<Double> totalCalculateManMonth, com.querydsl.core.types.Expression<Integer> totalCalculatePrice) {
        super(ResHistoryStatistics.class, new Class<?>[]{int.class, double.class, int.class, double.class, int.class}, month, totalInputManMonth, totalInputPrice, totalCalculateManMonth, totalCalculatePrice);
    }

}

