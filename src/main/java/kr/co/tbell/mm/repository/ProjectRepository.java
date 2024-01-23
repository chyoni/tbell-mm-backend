package kr.co.tbell.mm.repository;

import kr.co.tbell.mm.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Optional<Project> findByContractNumber(String contractNumber);

    @Override
    @Query(value = "SELECT p FROM Project p JOIN FETCH p.department")
    Page<Project> findAll(Pageable pageable);
}
