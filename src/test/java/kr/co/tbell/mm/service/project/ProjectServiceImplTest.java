package kr.co.tbell.mm.service.project;

import kr.co.tbell.mm.dto.project.ReqProject;
import kr.co.tbell.mm.dto.project.ResProject;
import kr.co.tbell.mm.entity.Department;
import kr.co.tbell.mm.entity.project.OperationRate;
import kr.co.tbell.mm.entity.project.Project;
import kr.co.tbell.mm.entity.project.ProjectStatus;
import kr.co.tbell.mm.repository.DepartmentRepository;
import kr.co.tbell.mm.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Slf4j
class ProjectServiceImplTest {

    @Autowired
    ProjectService projectService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Test
    void createProject() throws InstanceAlreadyExistsException {

        ReqProject reqProject = ReqProject
                .builder()
                .contractNumber("P1111")
                .teamName("SK텔레콤 1팀")
                .contractor("SK텔레콤")
                .startDate(LocalDate.parse("2023-01-01"))
                .endDate(LocalDate.parse("2026-12-31"))
                .projectStatus(ProjectStatus.SINGLE)
                .operationRate(OperationRate.EXCEPT)
                .departmentName("STE 1실")
                .build();

        // Department가 없는 경우
        assertThatThrownBy(() -> projectService.createProject(reqProject))
                .isInstanceOf(NoSuchElementException.class);

        departmentRepository.save(Department.builder().name("STE 1실").build());

        ResProject project = projectService.createProject(reqProject);

        Project findProject = projectRepository
                .findByContractNumber(reqProject.getContractNumber())
                .orElseThrow();

        assertThat(findProject.getContractNumber()).isEqualTo(project.getContractNumber());

        // 같은 프로젝트 번호가 존재하는 경우
        assertThatThrownBy(() -> projectService.createProject(reqProject))
                .isInstanceOf(InstanceAlreadyExistsException.class);
    }

    @Test
    void findAllProjects() throws InstanceAlreadyExistsException {

        departmentRepository.save(Department.builder().name("STE 1실").build());

        for (int i = 0; i < 5; i++) {
            ReqProject reqProject = ReqProject
                    .builder()
                    .contractNumber("P1111" + i)
                    .teamName("SK텔레콤 " +i+ "팀")
                    .contractor("SK텔레콤")
                    .startDate(LocalDate.parse("2023-01-01"))
                    .endDate(LocalDate.parse("2026-12-31"))
                    .projectStatus(ProjectStatus.SINGLE)
                    .operationRate(OperationRate.EXCEPT)
                    .departmentName("STE 1실")
                    .build();

            projectService.createProject(reqProject);
        }

        Page<ResProject> allProjects = projectService.findAllProjects(PageRequest.of(0, 5));

        assertThat(allProjects.getTotalElements()).isEqualTo(5L);
        assertThat(allProjects.getContent().getFirst().getContractNumber()).isEqualTo("P11110");
        assertThat(allProjects.getContent().getLast().getContractNumber()).isEqualTo("P11114");
    }

    @Test
    void findProjectByContractNumber() throws InstanceAlreadyExistsException {
        departmentRepository.save(Department.builder().name("STE 1실").build());

        ReqProject reqProject = ReqProject
                .builder()
                .contractNumber("P1111")
                .teamName("SK텔레콤 100팀")
                .contractor("SK텔레콤")
                .startDate(LocalDate.parse("2023-01-01"))
                .endDate(LocalDate.parse("2026-12-31"))
                .projectStatus(ProjectStatus.SINGLE)
                .operationRate(OperationRate.EXCEPT)
                .departmentName("STE 1실")
                .build();

        projectService.createProject(reqProject);

        ResProject project = projectService.findProjectByContractNumber(reqProject.getContractNumber());
        assertThat(project.getContractor()).isEqualTo("SK텔레콤");
        assertThat(project.getTeamName()).isEqualTo("SK텔레콤 100팀");
    }

    @Test
    void deleteProjectByContractNumber() throws InstanceAlreadyExistsException {
        departmentRepository.save(Department.builder().name("STE 1실").build());

        ReqProject reqProject = ReqProject
                .builder()
                .contractNumber("P1111")
                .teamName("SK텔레콤 100팀")
                .contractor("SK텔레콤")
                .startDate(LocalDate.parse("2023-01-01"))
                .endDate(LocalDate.parse("2026-12-31"))
                .projectStatus(ProjectStatus.SINGLE)
                .operationRate(OperationRate.EXCEPT)
                .departmentName("STE 1실")
                .build();

        projectService.createProject(reqProject);

        ResProject created = projectService.findProjectByContractNumber(reqProject.getContractNumber());

        assertThat(created.getTeamName()).isEqualTo("SK텔레콤 100팀");

        projectService.deleteProjectByContractNumber(created.getContractNumber());

        ResProject notExist = projectService.findProjectByContractNumber(reqProject.getContractNumber());

        assertThat(notExist).isNull();
    }
}