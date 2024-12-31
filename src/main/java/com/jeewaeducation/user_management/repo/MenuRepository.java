package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.NavMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<NavMenu,Integer> {

}
