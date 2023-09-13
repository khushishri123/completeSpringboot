package com.example.demo.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentApiIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;
    public  String createURL()
    {
        return "http://localhost:"+port+"/api";
    }

    private static HttpHeaders httpHeaders;
    private final ObjectMapper objectMapper=new ObjectMapper ();

    @BeforeAll
    static void beforeAll() {
        httpHeaders=new HttpHeaders ();
        httpHeaders.setContentType ( MediaType.APPLICATION_JSON );
    }

    @Test
    @Sql(statements = "insert into student(id,name,email,gender) values(1L,'badam','badam@gmail.com','MALE')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements ="delete from student where id=1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testGetAllStudents()
    {
        HttpEntity<String> httpEntity=new HttpEntity<> ( null,httpHeaders);
        ResponseEntity<List<Student>> response=testRestTemplate.exchange ( createURL () + "/getAllStudents" ,
                HttpMethod.GET , httpEntity , new ParameterizedTypeReference<List<Student>> () {} );
        List<Student> students=response.getBody ();
        assert students!=null;
        assertEquals(response.getStatusCodeValue (),200);
        assertEquals ( students,studentRepository.findAll () );
        assertEquals ( students,studentService.getAllStudents () );
        assertEquals ( students.size(),studentService.getAllStudents ().size () );
    }

    @Test
    @Sql(statements = "delete from student where id=1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testAddStudent() throws JsonProcessingException {
        Student student=new Student (1L,"badam","badam@gmail.com",Gender.MALE);
        HttpEntity<String> httpEntity=new HttpEntity<> ( objectMapper.writeValueAsString ( student ),httpHeaders );
        ResponseEntity<Student> response=testRestTemplate.exchange ( createURL ()+"/addStudent",HttpMethod.POST,httpEntity,Student.class );
        assertEquals ( response.getStatusCodeValue (),200 );
        Student savedStudent= Objects.requireNonNull ( response.getBody () );
        assertEquals ( savedStudent.getName (),"badam" );
        assertEquals ( savedStudent.getEmail (),studentRepository.save ( student ).getEmail () );
    }

    @Test
    @Sql(statements = "insert into student(id,name,email,gender) values(1L,'badam','badam@gmail.com','MALE')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements ="delete from student where id=1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testDeleteById()
    {
        HttpEntity<String> httpEntity=new HttpEntity<> ( null, httpHeaders );
        ResponseEntity<Void> responseEntity=testRestTemplate.exchange ( createURL ()+"/deleteById/1",HttpMethod.DELETE,httpEntity, Void.class );
        assertEquals ( responseEntity.getStatusCodeValue (),200 );
    }


    @Test
    @Sql(statements = "insert into student(id,name,email,gender) values(1L,'badam','badam@gmail.com','MALE')",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements ="delete from student where id=1",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testUpdateStudent() throws JsonProcessingException {
        Student student=new Student (1L,"kaju","kaju@gmail.com",Gender.MALE);
        HttpEntity<String> httpEntity=new HttpEntity<> ( objectMapper.writeValueAsString ( student ) ,httpHeaders);
        ResponseEntity<Student> responseEntity=testRestTemplate.exchange ( createURL ()+"/updateStudent",HttpMethod.PUT,httpEntity, Student.class );
        Student updatedStudent=responseEntity.getBody ();
        assertEquals ( responseEntity.getStatusCodeValue (),200 );
        assertEquals ( updatedStudent.getName (),student.getName () );
        assertEquals ( studentService.updateStudent (student ).getEmail (),updatedStudent.getEmail () );
    }
}
