package kr.co.tbell.mm.service.project;

import kr.co.tbell.mm.dto.project.ReqProject;
import kr.co.tbell.mm.dto.project.ResProject;
import kr.co.tbell.mm.entity.Department;
import kr.co.tbell.mm.entity.project.Level;
import kr.co.tbell.mm.entity.project.Project;
import kr.co.tbell.mm.entity.project.UnitPrice;
import kr.co.tbell.mm.repository.DepartmentRepository;
import kr.co.tbell.mm.repository.ProjectRepository;
import kr.co.tbell.mm.repository.UnitPriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.InstanceAlreadyExistsException;
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
    public ResProject createProject(ReqProject reqProject) throws
            InstanceAlreadyExistsException, NoSuchElementException {

        Optional<Project> optionalProject =
                projectRepository.findByContractNumber(reqProject.getContractNumber());

        if (optionalProject.isPresent())
            throw new InstanceAlreadyExistsException("Project with this contract number : '" +
                    reqProject.getContractNumber() + "' already exist.");

        Optional<Department> optionalDepartment =
                departmentRepository.findByName(reqProject.getDepartmentName());

        if (optionalDepartment.isEmpty())
            throw new NoSuchElementException("Department with this name : '" +
                    reqProject.getDepartmentName() + "' does not exist.");

        Project p = Project
                .builder()
                .contractNumber(reqProject.getContractNumber())
                .teamName(reqProject.getTeamName())
                .contractor(reqProject.getContractor())
                .startDate(reqProject.getStartDate())
                .endDate(reqProject.getEndDate())
                .projectStatus(reqProject.getProjectStatus())
                .operationRate(reqProject.getOperationRate())
                .department(optionalDepartment.get())
                .build();

        projectRepository.save(p);

        // UnitPrice Map 뽑아내서 엔티티로 변환
        for (Map<Level, Integer> unitPrice : reqProject.getUnitPrices()) {
            for (Map.Entry<Level, Integer> lw : unitPrice.entrySet()) {
                UnitPrice up = UnitPrice
                        .builder()
                        .level(lw.getKey())
                        .worth(lw.getValue())
                        .project(p)
                        .build();

                unitPriceRepository.save(up);
            }
        }

        return new ResProject(reqProject);
    }

    @Override
    public Page<ResProject> findAllProjects(Pageable pageable) {
        Page<Project> allProjects = projectRepository.findAll(pageable);

        return allProjects.map(project -> {
            List<Map<Level, Integer>> ups = new ArrayList<>();

            List<UnitPrice> unitPrices = unitPriceRepository.findAllByProject(project);

            for (UnitPrice unitPrice : unitPrices) {
                ups.add(Map.of(unitPrice.getLevel(), unitPrice.getWorth()));
            }

            return new ResProject(project, ups);
        });
    }
}
