package kr.co.tbell.mm.service.project;

import kr.co.tbell.mm.dto.project.ProjectSearchCond;
import kr.co.tbell.mm.dto.project.ReqCreateProject;
import kr.co.tbell.mm.dto.project.ReqUpdateProject;
import kr.co.tbell.mm.dto.project.ResProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface ProjectService {
    ResProject createProject(ReqCreateProject reqCreateProject) throws InstanceAlreadyExistsException;

    Page<ResProject> findAllProjects(Pageable pageable, ProjectSearchCond projectSearchCond);

    List<ResProject> findAllProjectsForOptions();

    ResProject findProjectByContractNumber(String contractNumber);

    ResProject deleteProjectByContractNumber(String contractNumber);

    ResProject editProjectByContractNumber(String contractNumber, ReqUpdateProject reqUpdateProject);
}
