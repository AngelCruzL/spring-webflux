package dev.angelcruzl.springbootwebflux.service.impl;

import dev.angelcruzl.springbootwebflux.dto.EmployeeDto;
import dev.angelcruzl.springbootwebflux.entity.Employee;
import dev.angelcruzl.springbootwebflux.mapper.EmployeeMapper;
import dev.angelcruzl.springbootwebflux.repository.EmployeeRepository;
import dev.angelcruzl.springbootwebflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
  private EmployeeRepository employeeRepository;

  @Override
  public Mono<EmployeeDto> saveEmployee(EmployeeDto employeeDto) {
    Employee employee= EmployeeMapper.mapToEmployee(employeeDto);
    Mono<Employee> savedEmployee = employeeRepository.save(employee);

    return savedEmployee.map(EmployeeMapper::mapToEmployeeDto);
  }
}