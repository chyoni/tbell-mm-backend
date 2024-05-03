package kr.co.tbell.mm.service.employee;

import kr.co.tbell.mm.dto.employee.ReqCreateEmployee;
import kr.co.tbell.mm.dto.employee.ReqUpdateEmployee;
import kr.co.tbell.mm.dto.employee.ResCreateEmployee;
import kr.co.tbell.mm.dto.employee.ResEmployee;
import kr.co.tbell.mm.entity.employee.Employee;
import kr.co.tbell.mm.repository.employee.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class EmployeeServiceImplTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @Test
    void createEmployee() throws InstanceAlreadyExistsException {

        ReqCreateEmployee employee = ReqCreateEmployee
                .builder()
                .employeeNumber("20200102")
                .name("최치원")
                .startDate(LocalDate.parse("2020-01-02"))
                .build();

        ResCreateEmployee createdEmployee = employeeService.createEmployee(employee);

        Employee findEmployee = employeeRepository.getEmployeeByEmployeeNumber("20200102").get();

        assertThat(findEmployee.getName()).isEqualTo(createdEmployee.getName());

        assertThatThrownBy(() -> employeeService.createEmployee(employee))
                .isInstanceOf(InstanceAlreadyExistsException.class);
    }

    @Test
    void findAllEmployees() throws InstanceAlreadyExistsException {

        for (int i = 0; i < 5; i++) {
            ReqCreateEmployee employee = ReqCreateEmployee
                    .builder()
                    .employeeNumber("20200102" + i)
                    .name("최치원" + i)
                    .startDate(LocalDate.parse("2020-01-02"))
                    .build();

            employeeService.createEmployee(employee);
        }

        Page<ResEmployee> allEmployees =
                employeeService.findAllEmployees(PageRequest.of(0, 5), null);

        assertThat(allEmployees.getTotalElements()).isEqualTo(5L);
        assertThat(allEmployees.getSize()).isEqualTo(5);

        assertThat(allEmployees.getContent().getFirst().getName()).isEqualTo("최치원0");
        assertThat(allEmployees.getContent().getLast().getName()).isEqualTo("최치원4");
    }

    @Test
    void findEmployee() throws InstanceAlreadyExistsException {
        ReqCreateEmployee employee = ReqCreateEmployee
                .builder()
                .employeeNumber("20200102")
                .name("최치원")
                .startDate(LocalDate.parse("2020-01-02"))
                .build();

        employeeService.createEmployee(employee);

        Employee findEmployee = employeeRepository.getEmployeeByEmployeeNumber("20200102").get();

        assertThat(findEmployee.getName()).isEqualTo("최치원");
        assertThat(findEmployee.getEmployeeNumber()).isEqualTo("20200102");
    }

    @Test
    void deleteEmployee() throws InstanceAlreadyExistsException {
        ReqCreateEmployee employee = ReqCreateEmployee
                .builder()
                .employeeNumber("20200102")
                .name("최치원")
                .startDate(LocalDate.parse("2020-01-02"))
                .build();

        employeeService.createEmployee(employee);

        Employee findEmployee = employeeRepository.getEmployeeByEmployeeNumber("20200102").get();

        assertThat(findEmployee.getName()).isEqualTo("최치원");
        assertThat(findEmployee.getEmployeeNumber()).isEqualTo("20200102");

        employeeService.deleteEmployee(findEmployee.getEmployeeNumber());

        assertThatThrownBy(() -> employeeRepository.getEmployeeByEmployeeNumber("20200102").get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void updateEmployee() throws InstanceAlreadyExistsException {
        ReqCreateEmployee employee = ReqCreateEmployee
                .builder()
                .employeeNumber("20200102")
                .name("최치원")
                .startDate(LocalDate.parse("2020-01-02"))
                .build();

        employeeService.createEmployee(employee);

        ReqUpdateEmployee reqUpdateEmployee =
                new ReqUpdateEmployee(null, "Choi Chiwon", null, null);

        employeeService.editEmployee("20200102", reqUpdateEmployee);

        Employee findEmployee = employeeRepository.getEmployeeByEmployeeNumber("20200102").orElseThrow();

        assertThat(findEmployee.getName()).isEqualTo("Choi Chiwon");
    }
}