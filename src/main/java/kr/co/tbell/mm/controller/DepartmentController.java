package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.dto.department.DepartmentSearchCond;
import kr.co.tbell.mm.dto.department.ReqCreateDepartment;
import kr.co.tbell.mm.dto.department.ResDepartment;
import kr.co.tbell.mm.entity.Department;
import kr.co.tbell.mm.repository.department.DepartmentRepository;
import kr.co.tbell.mm.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    @GetMapping("")
    public ResponseEntity<Response<Page<ResDepartment>>> getDepartments(DepartmentSearchCond departmentSearchCond,
                                                                        Pageable pageable) {
        log.info("[getDepartments]: DepartmentSearchCond: {}", departmentSearchCond);
        Page<ResDepartment> departments = departmentRepository.getDepartments(pageable, departmentSearchCond);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true,
                        null,
                        departments));
    }

    @PostMapping("")
    public ResponseEntity<Response<ResDepartment>> createDepartment(@RequestBody @Valid
                                                                    ReqCreateDepartment reqCreateDepartment) {
        log.info("[createDepartment]: ReqCreateDepartment = {}", reqCreateDepartment);

        Optional<Department> optionalDepartment =
                departmentRepository.findByName(reqCreateDepartment.getName());

        if (optionalDepartment.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(
                            false,
                            "Department with this name : " + reqCreateDepartment.getName() +
                                    " already exist",
                            null));
        }

        Department department = Department
                .builder()
                .name(reqCreateDepartment.getName())
                .build();

        departmentRepository.save(department);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new Response<>(true, null, new ResDepartment(department)));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Response<ResDepartment>> getDepartmentByName(
            @PathVariable String name,
            @SessionAttribute(name = Constants.SESSION_ADMIN_ID, required = false) Long adminId) {
        log.info("[getDepartmentByName]: adminId: {}", adminId);

        log.info("[getDepartmentByName]: Department Name: {}", name);

        Optional<Department> optionalDepartment = departmentRepository.findByName(name);

        if (optionalDepartment.isEmpty()) return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true,
                        null,
                        new ResDepartment(optionalDepartment.get())));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Response<ResDepartment>> deleteDepartmentByName(@PathVariable String name) {
        log.info("[deleteDepartmentByName]: Department Name: {}", name);

        Optional<Department> optionalDepartment = departmentRepository.findByName(name);

        if (optionalDepartment.isEmpty())
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false,
                            "Department with this name :" + name + " does not exist",
                            null));

        departmentRepository.delete(optionalDepartment.get());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true,
                        null,
                        new ResDepartment(optionalDepartment.get())));
    }

    @Transactional
    @PutMapping("/{name}")
    public ResponseEntity<Response<ResDepartment>> editDepartmentByName(
            @PathVariable String name,
            @RequestBody @Valid ReqCreateDepartment reqCreateDepartment) {
        log.info("[editDepartmentByName]: Department Name: {}", name);

        Optional<Department> optionalDepartment = departmentRepository.findByName(name);

        if (optionalDepartment.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, "Department with this name '" +
                            name + "' does not exist.", null));
        }

        Department department = optionalDepartment.get();

        department.updateDepartment(reqCreateDepartment.getName());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, new ResDepartment(department)));
    }
}
