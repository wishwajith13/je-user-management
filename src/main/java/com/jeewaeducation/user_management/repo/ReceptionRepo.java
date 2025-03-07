package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Reception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ReceptionRepo extends JpaRepository<Reception, Integer> {
    boolean existsByBranch(Branch branch);

    List<Reception> findAllByBranch(Branch branch);
}
