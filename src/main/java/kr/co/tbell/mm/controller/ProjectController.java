package kr.co.tbell.mm.controller;

import jakarta.validation.Valid;
import kr.co.tbell.mm.dto.common.Response;
import kr.co.tbell.mm.dto.project.ReqProject;
import kr.co.tbell.mm.dto.project.ResProject;
import kr.co.tbell.mm.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<Response<ResProject>> createProject(@RequestBody @Valid ReqProject reqProject) {
        log.info("[createProject]: Request payload = {}", reqProject);

        try {
            ResProject res = projectService.createProject(reqProject);
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
    public ResponseEntity<Response<Page<ResProject>>> getProjects(@PageableDefault Pageable pageable) {
        Page<ResProject> allProjects = projectService.findAllProjects(pageable);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new Response<>(true, null, allProjects));
    }
}
