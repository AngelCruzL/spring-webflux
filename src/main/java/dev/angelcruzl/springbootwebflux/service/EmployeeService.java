package dev.angelcruzl.springbootwebflux.service;

import dev.angelcruzl.springbootwebflux.dto.EmployeeDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService {
  Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);

  Mono<EmployeeDto> getEmployee(String employeeId);

  Flux<EmployeeDto> getAllEmployees();

  Mono<EmployeeDto> updateEmployee(String employeeId, EmployeeDto employeeDto);

  Mono<Void> deleteEmployee(String employeeId);
}
