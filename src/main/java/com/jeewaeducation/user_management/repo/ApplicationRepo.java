package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface ApplicationRepo extends JpaRepository<Application, Integer> {


    boolean existsByReception_ReceptionId(int receptionReceptionId);
}
