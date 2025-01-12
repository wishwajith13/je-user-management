package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.AdmissionManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface AdmissionManagerRepo extends JpaRepository<AdmissionManager,Integer> {
}
