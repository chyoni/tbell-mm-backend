package kr.co.tbell.mm.repository;

import kr.co.tbell.mm.entity.project.Project;
import kr.co.tbell.mm.entity.project.UnitPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitPriceRepository extends JpaRepository<UnitPrice, Long> {
    List<UnitPrice> findAllByProject(Project project);
}
