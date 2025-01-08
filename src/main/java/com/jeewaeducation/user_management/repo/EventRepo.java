package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepo extends JpaRepository<Event,Integer> {
}
