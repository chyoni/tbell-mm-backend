package kr.co.tbell.mm.service.history;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.ChronoPeriod;
import java.time.temporal.TemporalAdjusters;

@Slf4j
class EmployeeHistoryServiceImplTest {

    @Test
    void period() {
        LocalDate givenDate = LocalDate.parse("2023-11-03");

        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(givenDate, currentDate);

        log.info("totalMonths: {}", period.toTotalMonths());

        for (int i = 0; i <= period.toTotalMonths() + 1; i++) {
            LocalDate mmStart, mmEnd;
            if (i == 0) {
                 mmStart = givenDate.plusMonths(i);
            } else {
                LocalDate mmDate = givenDate.plusMonths(i);
                mmStart = LocalDate.of(mmDate.getYear(), mmDate.getMonth(), 1);
            }
            mmEnd = mmStart.with(TemporalAdjusters.lastDayOfMonth());

            if (mmStart.isAfter(LocalDate.now())) break;

            log.info("mmStart: {} | mmEnd: {}", mmStart, mmEnd);
            log.info("mmStartYear: {} | mmStartMonth: {}", mmStart.getYear(), mmStart.getMonthValue());

            log.info("days: {}", mmStart.until(mmEnd).getDays() + 1);
            int i1 = mmStart.until(mmEnd).getDays() + 1;
            int i2 = mmEnd.getDayOfMonth();
            double result = (double) i1 / i2;
            String format = String.format("%.2f", result);
            log.info("dayOfMonth: {} | mm: {}", mmEnd.getDayOfMonth(), format);
        }
    }

    @Test
    void intervalHistoryScheduler() {
        log.info("Year: {}", LocalDate.now().getYear());
        log.info("Month: {}", LocalDate.now().getMonthValue());

        log.info("Now: {}", LocalDate.now());

        LocalDate manMonthEnd = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());

        if (true) manMonthEnd = LocalDate.parse("2024-02-14");

        log.info("is after ? {}", LocalDate.now().isAfter(manMonthEnd));
        log.info("is equals ? {}", LocalDate.now().isEqual(manMonthEnd));

        log.info("LastDayOfMonth: {}", manMonthEnd);

        int durationDay = LocalDate.now().until(manMonthEnd).getDays() + 1;
        int dayOfMonth = manMonthEnd.getDayOfMonth();

        double inputManMonth = (double) durationDay / dayOfMonth;

        String inputManMonthToString = String.format("%.2f", inputManMonth);
        log.info("manMonth: {}", inputManMonthToString);
    }
}