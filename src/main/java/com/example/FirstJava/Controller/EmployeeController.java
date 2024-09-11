package com.example.FirstJava.Controller;

import com.example.FirstJava.ApiResponse.ApiResponse;
import com.example.FirstJava.Entity.Employee;
import com.example.FirstJava.Service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/employee")
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @PostMapping("/create")
    public ResponseEntity<?> saveEmployee (@Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return new ResponseEntity<>(new ApiResponse<>("Create Employee successfully", savedEmployee), HttpStatus.CREATED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllEmployee () {
        List<Employee> getAllEmployee = employeeService.getAllData();
        return new ResponseEntity<>(new ApiResponse<>("Employees retrieved successfully", getAllEmployee), HttpStatus.OK);
    }

    @GetMapping("/{nik}")
    public ResponseEntity<?> getByNik (@PathVariable Long nik ) {
       Employee employee = employeeService.getById(nik);
       return new ResponseEntity<>(new ApiResponse<>("Employee retrieved successfully", employee), HttpStatus.OK);
    }

    @PatchMapping("/update/{nik}")
    public ResponseEntity<?> updateEmployee (@PathVariable Long nik, @Valid @RequestBody Employee employee) {
        Employee updatedEmployee = employeeService.updateEmployee(nik, employee);
        return new ResponseEntity<>(new ApiResponse<>("Updated employee successfully", updatedEmployee), HttpStatus.CREATED);
    }

    @DeleteMapping("/{nik}")
    public ResponseEntity<?> deleteByNik (@PathVariable Long nik) {
        employeeService.deleteByNik(nik);
        return new ResponseEntity<>(new ApiResponse<>("Delete employee successfully", null), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchByNikOrFullname (@RequestParam(required = false) Long nik, @RequestParam(required = false) String fullName) {
        if (nik == null && (fullName == null || fullName.isEmpty())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "NIK or Fullname as required");
        }

        List<Employee> employees;

        if (nik != null) {
            employees = employeeService.searchByNik(nik);
        }

        else {
            employees = employeeService.searchByFullname(fullName);
        }

        if (employees.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Data not found");
        }

        ApiResponse<List<Employee>> response = new ApiResponse<>("Data Found", employees);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    }


