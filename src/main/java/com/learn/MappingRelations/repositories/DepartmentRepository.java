package com.learn.MappingRelations.repositories;


import com.learn.MappingRelations.entities.DepartmentEntity;
import com.learn.MappingRelations.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
    DepartmentEntity findByManager(EmployeeEntity employeeEntity);
}
