package kr.co.tbell.mm.service.project;

import kr.co.tbell.mm.dto.project.ProjectSearchCond;
import kr.co.tbell.mm.dto.project.ReqCreateProject;
import kr.co.tbell.mm.dto.project.ReqUpdateProject;
import kr.co.tbell.mm.dto.project.ResProject;
import kr.co.tbell.mm.entity.department.Department;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.Project;
import kr.co.tbell.mm.entity.project.UnitPrice;
import kr.co.tbell.mm.exception.InstanceCreationAlreadyExistsException;
import kr.co.tbell.mm.exception.InstanceDoesNotExistException;
import kr.co.tbell.mm.repository.department.DepartmentRepository;
import kr.co.tbell.mm.repository.project.ProjectRepository;
import kr.co.tbell.mm.repository.unitprice.UnitPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final DepartmentRepository departmentRepository;
    private final UnitPriceRepository unitPriceRepository;

    @Override
    public ResProject createProject(ReqCreateProject reqCreateProject) {

        Optional<Project> optionalProject =
                projectRepository.findByContractNumber(reqCreateProject.getContractNumber());

        if (optionalProject.isPresent()) {
            throw new InstanceCreationAlreadyExistsException(
                    "Project with this contract number : '" + reqCreateProject.getContractNumber() + "' already exist."
            );
        }

        Optional<Department> optionalDepartment =
                departmentRepository.findByName(reqCreateProject.getDepartmentName());

        if (optionalDepartment.isEmpty()) {
            throw new InstanceDoesNotExistException(
                    "Department with this name : '" + reqCreateProject.getDepartmentName() + "' does not exist."
            );
        }

        Project p = Project
                .builder()
                .contractNumber(reqCreateProject.getContractNumber())
                .teamName(reqCreateProject.getTeamName())
                .contractor(reqCreateProject.getContractor())
                .startDate(reqCreateProject.getStartDate())
                .endDate(reqCreateProject.getEndDate())
                .projectStatus(reqCreateProject.getProjectStatus())
                .operationRate(reqCreateProject.getOperationRate())
                .department(optionalDepartment.get())
                .build();

        projectRepository.save(p);

        // UnitPrice Map 뽑아내서 엔티티로 변환 후 DB에 저장
        makeUnitPrices(reqCreateProject.getUnitPrices(), p);

        return new ResProject(reqCreateProject);
    }

    @Override
    public Page<ResProject> findAllProjects(Pageable pageable, ProjectSearchCond projectSearchCond) {
        Page<Project> allProjects = projectRepository.getProjects(pageable, projectSearchCond);

        return allProjects.map(project -> {
            List<Map<Level, Integer>> dtoUnitPrices = getResProjectUnitPrices(project);

            return new ResProject(project, dtoUnitPrices);
        });
    }

    @Override
    public List<ResProject> findAllProjectsForOptions() {
        return projectRepository
                .findAll()
                .stream()
                .map(ResProject::new)
                .toList();
    }

    @Override
    public ResProject findProjectByContractNumber(String contractNumber) {
        Optional<Project> optionalProject = projectRepository.findByContractNumber(contractNumber);

        if (optionalProject.isEmpty()) {
            throw new InstanceDoesNotExistException(
                    "Project with this contract number : '" + contractNumber + "' does not exist."
            );
        }

        Project project = optionalProject.get();

        return new ResProject(project, getResProjectUnitPrices(project));
    }

    @Override
    public ResProject deleteProjectByContractNumber(String contractNumber) {
        Optional<Project> optionalProject = projectRepository.findByContractNumber(contractNumber);

        if (optionalProject.isEmpty()) {
            throw new InstanceDoesNotExistException(
                    "Project with this contract number '" + contractNumber + "' does not exist."
            );
        }

        Project project = optionalProject.get();

        List<Map<Level, Integer>> projectUnitPrices = getResProjectUnitPrices(project);

        unitPriceRepository.deleteAllByProject(project);

        projectRepository.delete(project);

        return new ResProject(project, projectUnitPrices);
    }

    @Override
    public ResProject editProjectByContractNumber(String contractNumber, ReqUpdateProject reqUpdateProject) {
        Optional<Project> optionalProject = projectRepository.findByContractNumber(contractNumber);

        if (optionalProject.isEmpty()) {
            throw new InstanceDoesNotExistException(
                    "Project with this contract number : '" + contractNumber + "' does not exist."
            );
        }

        Project project = optionalProject.get();

        project.updateProject(
                reqUpdateProject.getTeamName(),
                reqUpdateProject.getContractor(),
                reqUpdateProject.getStartDate(),
                reqUpdateProject.getEndDate(),
                reqUpdateProject.getProjectStatus(),
                reqUpdateProject.getOperationRate());

        if (reqUpdateProject.getDepartmentName() != null &&
            !project.getDepartment().getName().equals(reqUpdateProject.getDepartmentName())) {
            Optional<Department> optionalDepartment =
                    departmentRepository.findByName(reqUpdateProject.getDepartmentName());

            if (optionalDepartment.isEmpty())
                throw new InstanceDoesNotExistException("The department '" + reqUpdateProject.getDepartmentName() +
                        "' you want to modify does not exist.");

            project.changeDepartment(optionalDepartment.get());
        }

        if (!reqUpdateProject.getUnitPrices().isEmpty()) {
            unitPriceRepository.deleteAllByProject(project);

            makeUnitPrices(reqUpdateProject.getUnitPrices(), project);
        }

        return new ResProject(project, getResProjectUnitPrices(project));
    }

    private void makeUnitPrices(List<Map<Level, Integer>> unitPrices, Project project) {
        if (unitPrices == null) return;

        unitPrices.forEach(unitPrice ->
                unitPrice.forEach((key, value) -> {
                        UnitPrice newUnitPrice = UnitPrice
                                .builder()
                                .level(key)
                                .worth(value)
                                .project(project).build();

                        unitPriceRepository.save(newUnitPrice);
                    }
                )
        );
    }

    private List<Map<Level, Integer>> getResProjectUnitPrices(Project project) {
        List<Map<Level, Integer>> dtoUnitPrices = new ArrayList<>();

        List<UnitPrice> unitPrices = unitPriceRepository.findAllByProject(project);

        unitPrices.forEach(unitPrice ->
                dtoUnitPrices.add(Map.of(unitPrice.getLevel(), unitPrice.getWorth()))
        );

        return dtoUnitPrices;
    }
}
