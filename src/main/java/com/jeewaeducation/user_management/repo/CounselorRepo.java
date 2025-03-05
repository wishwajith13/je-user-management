package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Counselor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CounselorRepo  extends JpaRepository<Counselor, Integer>
{
    boolean existsByBranch(Branch branch);

    List<Counselor> findByBranch(Branch branch);
}
