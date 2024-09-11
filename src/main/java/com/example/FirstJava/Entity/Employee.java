package com.example.FirstJava.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.util.Date;


@Entity
@Data
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "nik", nullable = false, unique = true)
    @NotNull(message = "NIK is required")
    @Digits(integer = 16, fraction = 0, message = "NIK must be number and not exceed 16 digits")
    private Long nik;

    @Column(name = "fullName", nullable = false)
    @NotBlank(message = "Fullname is required")
    private String fullName;

    @Column(name = "age", nullable = false)
    @NotNull(message = "Age is required")
    private Integer age;

    @Column(name = "gender")
    @NotBlank(message = "Gender is required")
    private String gender;

    @Column(name = "birth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    @NotNull(message = "Birth is required")
    @Temporal(TemporalType.DATE)
    private Date birth;

    @Column(name = "address")
    @NotBlank(message = "Address is required")
    private String address;

    @Column(name = "country")
    @NotBlank(message = "Country is required")
    private String country;
}
