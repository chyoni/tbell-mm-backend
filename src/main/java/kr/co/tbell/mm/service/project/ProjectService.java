package kr.co.tbell.mm.service.project;

import kr.co.tbell.mm.dto.project.ReqProject;
import kr.co.tbell.mm.dto.project.ResProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.InstanceAlreadyExistsException;

public interface ProjectService {
    ResProject createProject(ReqProject reqProject) throws InstanceAlreadyExistsException;

    Page<ResProject> findAllProjects(Pageable pageable);

    ResProject findProjectByContractNumber(String contractNumber);

    ResProject deleteProjectByContractNumber(String contractNumber);
}
