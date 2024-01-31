package kr.co.tbell.mm.repository.project;

import kr.co.tbell.mm.dto.project.ProjectSearchCond;
import kr.co.tbell.mm.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectRepositoryQueryDsl {
    Page<Project> getProjects(Pageable pageable, ProjectSearchCond projectSearchCond);
}
