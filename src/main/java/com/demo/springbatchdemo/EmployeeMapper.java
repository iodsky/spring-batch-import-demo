package com.demo.springbatchdemo;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EmployeeMapper {

    public static Employee toEntity(EmployeeDto dto) {
        return Employee.builder()
                .lastName(dto.getLastName())
                .firstName(dto.getFirstName())
                .birthday(DateTimeUtil.parseDate(dto.getBirthday()))
                .address(dto.getAddress())
                .phoneNumber(dto.getPhoneNumber())
                .sssNumber(dto.getSssNumber())
                .philhealthNumber(dto.getPhilhealthNumber())
                .tinNumber(dto.getTinNumber())
                .pagIbigNumber(dto.getPagIbigNumber())
                .status(EmployeeStatus.valueOf(dto.getStatus().toUpperCase()))
                .position(dto.getPosition())
                .supervisor(dto.getSupervisor())
                .startShift(DateTimeUtil.parseTime(dto.getStartShift()))
                .endShift(DateTimeUtil.parseTime(dto.getEndShift()))
                .basicSalary(Double.parseDouble(dto.getBasicSalary()))
                .mealAllowance(Double.parseDouble(dto.getMealAllowance()))
                .phoneAllowance(Double.parseDouble(dto.getPhoneAllowance()))
                .clothingAllowance(Double.parseDouble(dto.getClothingAllowance()))
                .semiMonthlyRate(Double.parseDouble(dto.getSemiMonthlyRate()))
                .hourlyRate(Double.parseDouble(dto.getHourlyRate()))
                .build();
    }

}
