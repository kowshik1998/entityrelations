package com.learn.MappingRelations.services;



import com.learn.MappingRelations.entities.DepartmentEntity;
import com.learn.MappingRelations.entities.EmployeeEntity;
import com.learn.MappingRelations.repositories.DepartmentRepository;
import com.learn.MappingRelations.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    public DepartmentEntity createNewDepartment(DepartmentEntity departmentEntity) {
        return departmentRepository.save(departmentEntity);
    }

    public DepartmentEntity getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }


    public DepartmentEntity assignManagerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity=departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity=employeeRepository.findById(employeeId);
        return departmentEntity.flatMap(department->
                employeeEntity.map(employee->{department.setManager(employee);
                    return departmentRepository.save(department);
                }
                )).orElse(null);
    }

    public DepartmentEntity getAssignedDepartmentOfManager(Long employeeId) {
//        Optional<EmployeeEntity> employeeEntity=employeeRepository.findById(employeeId);
//         return employeeEntity.map(
//                  EmployeeEntity::getManagedDepartment).orElse(null);
        EmployeeEntity employeeEntity=EmployeeEntity.builder().id(employeeId).build();
        return departmentRepository.findByManager(employeeEntity);
    }

    public DepartmentEntity assignWorkerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        return departmentEntity.flatMap(department->
                employeeEntity.map(employee-> {
                    employee.setWorkerDepartment(department);
                    employeeRepository.save(employee);
                    department.getWorkers().add(employee);
                    return department;
                })).orElse(null);
    }


    public DepartmentEntity assignFreelancerToDepartment(Long departmentId, Long employeeId) {
        Optional<DepartmentEntity> departmentEntity = departmentRepository.findById(departmentId);
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findById(employeeId);
        return departmentEntity.flatMap(department->
                employeeEntity.map(employee-> {
               employee.getFreelanceDepartments().add(department);
                employeeRepository.save(employee);
                 department.getFreelancers().add(employee);
                 return department;
                })).orElse(null);
    }
}








