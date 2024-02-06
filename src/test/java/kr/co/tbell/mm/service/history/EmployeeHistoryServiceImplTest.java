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
        LocalDate givenDate = LocalDate.parse("2023-02-07");

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
}