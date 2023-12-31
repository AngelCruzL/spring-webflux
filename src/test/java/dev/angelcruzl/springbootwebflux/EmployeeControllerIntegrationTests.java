package dev.angelcruzl.springbootwebflux;

import dev.angelcruzl.springbootwebflux.dto.EmployeeDto;
import dev.angelcruzl.springbootwebflux.repository.EmployeeRepository;
import dev.angelcruzl.springbootwebflux.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {
  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private EmployeeRepository employeeRepository;

  @Autowired
  private WebTestClient webTestClient;

  @BeforeEach
  public void before() {
    System.out.println("Before each test, we delete all the employees");
    employeeRepository.deleteAll().subscribe();
  }

  @Test
  public void testSaveEmployee() {
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("Angel");
    employeeDto.setLastName("Cruz");
    employeeDto.setEmail("me@angelcruzl.dev");

    webTestClient.post().uri("/api/employees")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(employeeDto), EmployeeDto.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.id").isNotEmpty()
        .jsonPath("$.firstName").isEqualTo(employeeDto.getFirstName())
        .jsonPath("$.lastName").isEqualTo(employeeDto.getLastName())
        .jsonPath("$.email").isEqualTo(employeeDto.getEmail());
  }

  @Test
  public void testGetSingleEmployee() {
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("Angel");
    employeeDto.setLastName("Cruz");
    employeeDto.setEmail("me@angelcruzl.dev");

    EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

    webTestClient.get().uri("/api/employees/{id}", savedEmployee.getId())
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.id").isEqualTo(savedEmployee.getId())
        .jsonPath("$.firstName").isEqualTo(savedEmployee.getFirstName())
        .jsonPath("$.lastName").isEqualTo(savedEmployee.getLastName())
        .jsonPath("$.email").isEqualTo(savedEmployee.getEmail());
  }

  @Test
  public void testGetAllEmployees() {
    EmployeeDto employeeAngelCruz = new EmployeeDto();
    employeeAngelCruz.setFirstName("Angel");
    employeeAngelCruz.setLastName("Cruz");
    employeeAngelCruz.setEmail("me@angelcruzl.dev");

    employeeService.saveEmployee(employeeAngelCruz).block();

    EmployeeDto employeeJohnDoe = new EmployeeDto();
    employeeJohnDoe.setFirstName("John");
    employeeJohnDoe.setLastName("Doe");
    employeeJohnDoe.setEmail("john@doe.dev");

    employeeService.saveEmployee(employeeJohnDoe).block();

    webTestClient.get().uri("/api/employees")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(EmployeeDto.class)
        .consumeWith(System.out::println);
  }

  @Test
  public void testUpdateEmployee() {
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("Angel");
    employeeDto.setLastName("Cruz");
    employeeDto.setEmail("me@angelcruzl.dev");

    EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

    EmployeeDto updatedEmployee = new EmployeeDto();
    updatedEmployee.setFirstName("Luis");
    updatedEmployee.setLastName("Lara");
    updatedEmployee.setEmail("luis@lara.dev");

    webTestClient.put().uri("/api/employees/{id}", savedEmployee.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(updatedEmployee), EmployeeDto.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.id").isEqualTo(savedEmployee.getId())
        .jsonPath("$.firstName").isEqualTo(updatedEmployee.getFirstName())
        .jsonPath("$.lastName").isEqualTo(updatedEmployee.getLastName())
        .jsonPath("$.email").isEqualTo(updatedEmployee.getEmail());
  }

  @Test
  public void testDeleteEmployee() {
    EmployeeDto employeeDto = new EmployeeDto();
    employeeDto.setFirstName("Angel");
    employeeDto.setLastName("Cruz");
    employeeDto.setEmail("me@angelcruzl.dev");

    EmployeeDto savedEmployee = employeeService.saveEmployee(employeeDto).block();

    webTestClient.delete().uri("/api/employees/{id}", savedEmployee.getId())
        .exchange()
        .expectStatus().isNoContent()
        .expectBody()
        .consumeWith(System.out::println);
  }
}
