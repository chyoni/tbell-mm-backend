package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.dto.project.ProjectSearchCond;
import kr.co.tbell.mm.dto.project.ReqCreateProject;
import kr.co.tbell.mm.dto.project.ReqUpdateProject;
import kr.co.tbell.mm.dto.project.ResProject;
import kr.co.tbell.mm.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("")
    public ResponseEntity<Response<ResProject>> createProject(@RequestBody @Valid ReqCreateProject reqCreateProject) {
        log.info("[createProject]: Request payload = {}", reqCreateProject);

        try {
            ResProject res = projectService.createProject(reqCreateProject);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new Response<>(true, null, res));
        } catch (InstanceAlreadyExistsException | NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, e.getMessage(), null));
        }
    }

    @GetMapping("")
    public ResponseEntity<Response<Page<ResProject>>> getProjects(ProjectSearchCond projectSearchCond,
                                                                  Pageable pageable) {
        Page<ResProject> allProjects = projectService.findAllProjects(pageable, projectSearchCond);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, allProjects));
    }

    @GetMapping("/options")
    public ResponseEntity<Response<List<ResProject>>> getProjectsForOptions() {
        List<ResProject> resProjects = projectService.findAllProjectsForOptions();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, resProjects));
    }

    @GetMapping("/{contractNumber}")
    public ResponseEntity<Response<ResProject>> getProjectByContractNumber(@PathVariable String contractNumber) {
        log.info("[getProjectByContractNumber]: Contract Number = {}", contractNumber);

        ResProject project = projectService.findProjectByContractNumber(contractNumber);

        if (project == null)
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Response<>(false, "Project with this contract number : '" +
                            contractNumber + "' does not exist.", null));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, project));
    }

    @DeleteMapping("/{contractNumber}")
    public ResponseEntity<Response<ResProject>> deleteProjectByContractNumber(@PathVariable String contractNumber) {
        log.info("[deleteProjectByContractNumber]: Contract Number = {}", contractNumber);

        try {
            ResProject deleted = projectService.deleteProjectByContractNumber(contractNumber);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new Response<>(true, null, deleted));
        } catch (NoSuchElementException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, e.getMessage(), null));
        }
    }

    @PutMapping("/{contractNumber}")
    public ResponseEntity<Response<ResProject>> editProjectByContractNumber(
            @PathVariable String contractNumber,
            @RequestBody @Valid ReqUpdateProject reqUpdateProject) {
        log.info("[editProjectByContractNumber]: Contract Number = {}", contractNumber);

        log.info("[editProjectByContractNumber]: Request payload = {}", reqUpdateProject);

        try {
            ResProject response = projectService.editProjectByContractNumber(contractNumber, reqUpdateProject);
            if (response == null) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new Response<>(false, "Project with this contract number : '" +
                                contractNumber + "' does not exist.", null));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new Response<>(true, null, response));
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Response<>(false, e.getMessage(), null));
        }
    }
}
