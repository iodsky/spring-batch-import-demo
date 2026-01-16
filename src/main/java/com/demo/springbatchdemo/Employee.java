package com.demo.springbatchdemo;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "employees")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @SequenceGenerator(name="employee_id_seq", initialValue = 10001, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
    private Long  id;

    // Personal Information
    private String lastName;
    private String firstName;
    private LocalDate birthday;
    private String address;
    private String phoneNumber;

    // Government Numbers
    @Column(unique = true)
    private String sssNumber;
    @Column(unique = true)
    private String philhealthNumber;
    @Column(unique = true)
    private String tinNumber;
    @Column(unique = true)
    private String pagIbigNumber;

    // Employment Details
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
    private String position;

    private String supervisor;

    // Work Schedule
    private LocalTime startShift;
    private LocalTime endShift;

    // Compensation
    private double basicSalary;
    private double mealAllowance;
    private double phoneAllowance;
    private double clothingAllowance;
    private double semiMonthlyRate;
    private double hourlyRate;

}
