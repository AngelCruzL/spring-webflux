package dev.angelcruzl.springbootwebflux.service;

import dev.angelcruzl.springbootwebflux.dto.EmployeeDto;
import reactor.core.publisher.Mono;

public interface EmployeeService {
  Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto);
}
