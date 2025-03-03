package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.entity.Reception;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface ApplicationRepo extends JpaRepository<Application, Integer> {


    boolean existsByReception_ReceptionId(int receptionReceptionId);

    List<Application> findByReception(Reception reception);

    List<Application> findByReception_ReceptionId(int receptionReceptionId);
}
