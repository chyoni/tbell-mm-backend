package kr.co.tbell.mm.service.project;

import kr.co.tbell.mm.dto.project.ProjectSearchCond;
import kr.co.tbell.mm.dto.project.ReqCreateProject;
import kr.co.tbell.mm.dto.project.ReqUpdateProject;
import kr.co.tbell.mm.dto.project.ResProject;
import kr.co.tbell.mm.entity.Department;
import kr.co.tbell.mm.entity.project.*;
import kr.co.tbell.mm.repository.department.DepartmentRepository;
import kr.co.tbell.mm.repository.project.ProjectRepository;
import kr.co.tbell.mm.repository.unitprice.UnitPriceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

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

    @Autowired
    UnitPriceRepository unitPriceRepository;

    @Test
    void sort() {
        int[] arr1 = {1, 3, 5, 9};
        int[] arr2 = {2, 6, 7, 11};

        int[] mergedArray = new int[arr1.length + arr2.length];

        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                mergedArray[k++] = arr1[i++];
            } else {
                mergedArray[k++] = arr2[j++];
            }
        }

        while (i < arr1.length) {
            mergedArray[k++] = arr1[i++];
        }

        while (j < arr2.length) {
            mergedArray[k++] = arr2[j++];
        }

        System.out.println("mergedArray = " + Arrays.toString(mergedArray));
    }

    @Test
    void createProject() throws InstanceAlreadyExistsException {

        ReqCreateProject reqCreateProject = ReqCreateProject
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
        assertThatThrownBy(() -> projectService.createProject(reqCreateProject))
                .isInstanceOf(NoSuchElementException.class);

        departmentRepository.save(Department.builder().name("STE 1실").build());

        ResProject project = projectService.createProject(reqCreateProject);

        Project findProject = projectRepository
                .findByContractNumber(reqCreateProject.getContractNumber())
                .orElseThrow();

        assertThat(findProject.getContractNumber()).isEqualTo(project.getContractNumber());

        // 같은 프로젝트 번호가 존재하는 경우
        assertThatThrownBy(() -> projectService.createProject(reqCreateProject))
                .isInstanceOf(InstanceAlreadyExistsException.class);
    }

    @Test
    void findAllProjects() throws InstanceAlreadyExistsException {

        departmentRepository.save(Department.builder().name("STE 1실").build());

        for (int i = 0; i < 5; i++) {
            ReqCreateProject reqCreateProject = ReqCreateProject
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

            projectService.createProject(reqCreateProject);
        }

        Page<ResProject> allProjects =
                projectService.findAllProjects(PageRequest.of(0, 5), new ProjectSearchCond());

        assertThat(allProjects.getTotalElements()).isEqualTo(5L);
        assertThat(allProjects.getContent().getFirst().getContractNumber()).isEqualTo("P11110");
        assertThat(allProjects.getContent().getLast().getContractNumber()).isEqualTo("P11114");
    }

    @Test
    void findProjectByContractNumber() throws InstanceAlreadyExistsException {
        departmentRepository.save(Department.builder().name("STE 1실").build());

        ReqCreateProject reqCreateProject = ReqCreateProject
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

        projectService.createProject(reqCreateProject);

        ResProject project = projectService.findProjectByContractNumber(reqCreateProject.getContractNumber());
        assertThat(project.getContractor()).isEqualTo("SK텔레콤");
        assertThat(project.getTeamName()).isEqualTo("SK텔레콤 100팀");
    }

    @Test
    void deleteProjectByContractNumber() throws InstanceAlreadyExistsException {
        departmentRepository.save(Department.builder().name("STE 1실").build());

        ReqCreateProject reqCreateProject = ReqCreateProject
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

        projectService.createProject(reqCreateProject);

        ResProject created = projectService.findProjectByContractNumber(reqCreateProject.getContractNumber());

        assertThat(created.getTeamName()).isEqualTo("SK텔레콤 100팀");

        projectService.deleteProjectByContractNumber(created.getContractNumber());

        ResProject notExist = projectService.findProjectByContractNumber(reqCreateProject.getContractNumber());

        assertThat(notExist).isNull();
    }

    @Test
    void editProjectByContractNumber() throws InstanceAlreadyExistsException {
        departmentRepository.save(Department.builder().name("STE 1실").build());

        List<Map<Level, Integer>> unitPrices = new ArrayList<>();

        unitPrices.add(Map.of(Level.BEGINNER, 100));
        unitPrices.add(Map.of(Level.INTERMEDIATE, 200));
        unitPrices.add(Map.of(Level.ADVANCED, 300));

        ReqCreateProject reqCreateProject = ReqCreateProject
                .builder()
                .contractNumber("P1111")
                .teamName("SK텔레콤 100팀")
                .contractor("SK텔레콤")
                .startDate(LocalDate.parse("2023-01-01"))
                .endDate(LocalDate.parse("2026-12-31"))
                .projectStatus(ProjectStatus.SINGLE)
                .operationRate(OperationRate.EXCEPT)
                .departmentName("STE 1실")
                .unitPrices(unitPrices)
                .build();


        ResProject created = projectService.createProject(reqCreateProject);

        assertThat(created.getTeamName()).isEqualTo("SK텔레콤 100팀");
        assertThat(created.getUnitPrices().getFirst().get(Level.BEGINNER)).isEqualTo(100);
        assertThat(created.getUnitPrices().getLast().get(Level.ADVANCED)).isEqualTo(300);

        List<Map<Level, Integer>> updateUnitPrices = new ArrayList<>();

        updateUnitPrices.add(Map.of(Level.BEGINNER, 400));
        updateUnitPrices.add(Map.of(Level.INTERMEDIATE, 500));
        updateUnitPrices.add(Map.of(Level.ADVANCED, 600));

        ReqUpdateProject req = ReqUpdateProject
                .builder()
                .teamName("SK텔레콤 1팀")
                .unitPrices(updateUnitPrices)
                .build();

        projectService.editProjectByContractNumber("P1111", req);

        Project p1111 = projectRepository.findByContractNumber("P1111").orElseThrow();
        List<UnitPrice> p1111UnitPrices = unitPriceRepository.findAllByProject(p1111);

        assertThat(p1111.getTeamName()).isEqualTo("SK텔레콤 1팀");

        for (UnitPrice p1111UnitPrice : p1111UnitPrices) {
            if (p1111UnitPrice.getLevel().equals(Level.BEGINNER)) {
                if (!p1111UnitPrice.getWorth().equals(400)) throw new RuntimeException("Test Failed");
            }
            if (p1111UnitPrice.getLevel().equals(Level.INTERMEDIATE)) {
                if (!p1111UnitPrice.getWorth().equals(500)) throw new RuntimeException("Test Failed");
            }
            if (p1111UnitPrice.getLevel().equals(Level.ADVANCED)) {
                if (!p1111UnitPrice.getWorth().equals(600)) throw new RuntimeException("Test Failed");
            }
        }
    }
}