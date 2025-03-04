package com.jeewaeducation.user_management.repo;

import com.jeewaeducation.user_management.entity.Application;
import com.jeewaeducation.user_management.entity.Branch;
import com.jeewaeducation.user_management.entity.Counselor;
import com.jeewaeducation.user_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface StudentRepo extends JpaRepository<Student, Integer> {
    boolean existsByBranchId(Branch branchId);

    boolean existsByCounselorId(Counselor counselorId);

    boolean existsByApplication(Application application);

    Student findByApplication(Application application);

    List<Student> findByCounselorId_CounselorId(int counselorIdCounselorId);
}
