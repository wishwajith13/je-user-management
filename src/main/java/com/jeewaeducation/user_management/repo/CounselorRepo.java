package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.Counselor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CounselorRepo  extends JpaRepository<Counselor, Integer>
{
}
