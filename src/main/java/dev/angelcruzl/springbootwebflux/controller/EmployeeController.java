package dev.angelcruzl.springbootwebflux.controller;

import dev.angelcruzl.springbootwebflux.dto.EmployeeDto;
import dev.angelcruzl.springbootwebflux.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {
  private EmployeeService employeeService;

  @PostMapping
  @ResponseStatus(value = HttpStatus.CREATED)
  public Mono<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto) {
    return employeeService.saveEmployee(employeeDto);
  }

  @GetMapping("{id}")
  public Mono<EmployeeDto> getEmployee(@PathVariable("id") String employeeId) {
    return employeeService.getEmployee(employeeId);
  }

  @GetMapping
  public Flux<EmployeeDto> getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @PutMapping("{id}")
  public Mono<EmployeeDto> updateEmployee(
      @PathVariable("id") String employeeId,
      @RequestBody EmployeeDto employeeDto
  ) {
    return employeeService.updateEmployee(employeeId, employeeDto);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> deleteEmployee(@PathVariable("id") String employeeId) {
    return employeeService.deleteEmployee(employeeId);
  }
}
