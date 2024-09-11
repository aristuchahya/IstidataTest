package com.example.FirstJava.Service;

import com.example.FirstJava.Entity.Employee;
import com.example.FirstJava.Repository.EmployeeRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepo employeeRepo;



    public Employee saveEmployee (@Valid Employee employee) {
        if (employeeRepo.existsById(employee.getNik())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NIK already exist");
        }
        return employeeRepo.save(employee);
    }

    public List<Employee> getAllData () {
        return employeeRepo.findAll();
    }

    public Employee getById (Long nik) {
        return employeeRepo.findById(nik).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
    }

    public Employee updateEmployee (Long nik, @Valid Employee updatedEmployee) {
        Employee existingEmployee = employeeRepo.findById(nik).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));

        existingEmployee.setFullName(updatedEmployee.getFullName());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setAddress(updatedEmployee.getAddress());
        existingEmployee.setBirth(updatedEmployee.getBirth());
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setCountry(updatedEmployee.getCountry());

        return employeeRepo.save(existingEmployee);
    }

    public List<Employee> searchByNik (Long nik) {
        List<Employee> employees = employeeRepo.findByNik(nik);
        if (employees == null || employees.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with nik" + nik);
        }
        return employees;
    }

    public List<Employee> searchByFullname (String fullName) {
        List<Employee> employees = employeeRepo.findByFullNameContainingIgnoreCase(fullName);
        if (employees == null || employees.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee with name" + fullName);
        }
        return employees;
    }


    public void deleteByNik (Long nik) {
       if (!employeeRepo.existsById(nik)) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
       }
        employeeRepo.deleteById(nik);
    }
}
