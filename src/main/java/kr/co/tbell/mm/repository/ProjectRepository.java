package kr.co.tbell.mm.repository;

import kr.co.tbell.mm.entity.project.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value = "SELECT p FROM Project p JOIN FETCH p.department WHERE p.contractNumber = :contractNumber")
    Optional<Project> findByContractNumber(@Param("contractNumber") String contractNumber);

    @Override
    @Query(value = "SELECT p FROM Project p JOIN FETCH p.department")
    Page<Project> findAll(Pageable pageable);
}
