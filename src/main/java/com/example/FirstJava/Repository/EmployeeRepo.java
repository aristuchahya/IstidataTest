package com.example.FirstJava.Repository;

import com.example.FirstJava.Entity.Employee;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByNik(Long nik);
    List<Employee> findByFullNameContainingIgnoreCase(String fullName);
//    List<Employee> findByNikAndFullNameContainingIgnoreCase(Long nik, String fullName);
}
