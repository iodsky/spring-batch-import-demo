package com.demo.springbatchdemo;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDto {

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Birthday is required")
    @Pattern(regexp = "^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d{2}$",
            message = "Birthday must be in MM/dd/yyyy format")
    private String birthday;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$",
            message = "Phone number must be in xxx-xxx-xxx format")
    private String phoneNumber;

    // Government Numbers
    @Pattern(regexp = "^\\d{2}-\\d{7}-\\d{1}$",
            message = "SSS number must be in xx-xxxxxxx-x format")
    private String sssNumber;

    @Pattern(regexp = "^\\d{12}$",
            message = "PhilHealth number must be 12 digits")
    private String philhealthNumber;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}-\\d{3}$",
            message = "TIN number must be in xxx-xxx-xxx-xxx format")
    private String tinNumber;

    @Pattern(regexp = "^\\d{12}$",
            message = "Pag-IBIG number must be 12 digits")
    private String pagIbigNumber;

    // Employment Details
    @NotBlank(message = "Employee status is required")
    @Pattern(regexp = "^(REGULAR|PROBATIONARY|CONTRACTUAL|INTERN|TERMINATED|PART_TIME)$",
            message = "Status must be REGULAR, PROBATIONARY, INTERN, TERMINATED, PART_TIME, or CONTRACTUAL")
    private String status;

    @NotBlank(message = "Position is required")
    @Size(max = 100, message = "Position must not exceed 100 characters")
    private String position;

    @Size(max = 100, message = "Supervisor name must not exceed 100 characters")
    private String supervisor;

    // Work Schedule
    @NotBlank(message = "Start shift is required")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "Start shift must be in HH:mm format")
    private String startShift;

    @NotBlank(message = "End shift is required")
    @Pattern(regexp = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$",
            message = "End shift must be in HH:mm format")
    private String endShift;

    // Compensation
    @NotBlank(message = "Basic salary is required")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$",
            message = "Basic salary must be a valid positive number")
    @DecimalMin(value = "1.00", message = "Basic salary must be at least 1.00")
    private String basicSalary;

    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$",
            message = "Meal allowance must be a valid number")
    @DecimalMin(value = "0.00", message = "Meal allowance must be non-negative")
    private String mealAllowance;

    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$",
            message = "Phone allowance must be a valid number")
    @DecimalMin(value = "0.00", message = "Phone allowance must be non-negative")
    private String phoneAllowance;

    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$",
            message = "Clothing allowance must be a valid number")
    @DecimalMin(value = "0.00", message = "Clothing allowance must be non-negative")
    private String clothingAllowance;

    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$",
            message = "Semi-monthly rate must be a valid number")
    @DecimalMin(value = "0.00", message = "Semi-monthly rate must be non-negative")
    private String semiMonthlyRate;

    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$",
            message = "Hourly rate must be a valid number")
    @DecimalMin(value = "0.00", message = "Hourly rate must be non-negative")
    private String hourlyRate;
}
