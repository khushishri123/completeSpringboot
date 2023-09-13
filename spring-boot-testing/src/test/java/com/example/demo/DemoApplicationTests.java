package com.example.demo;

import com.example.demo.student.Gender;
import com.example.demo.student.Student;
import com.example.demo.student.TestH2Repository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	@LocalServerPort
	private int port;

	private static RestTemplate restTemplate;

	@Autowired
	private TestH2Repository testH2Repository;

	private String baseUrl="http://localhost";

	@BeforeAll
	static void beforeAll() {
		restTemplate=new RestTemplate ();
	}

	@BeforeEach
	void setUp() {
		baseUrl=baseUrl.concat ( ":" ).concat ( port+"/api" );
	}

	@Test
	public void testAddStudent()
	{
		Student student=new Student ("Geeta","geeta@gmail.com",Gender.FEMALE);
		baseUrl=baseUrl.concat ( "/addStudent" );
		System.out.println (baseUrl);
		Student createdObject=restTemplate.postForObject ( baseUrl,student, Student.class );
		assertEquals("Geeta",createdObject.getName ());
		assertEquals ( 1,testH2Repository.findAll ().size () );

	}

	@Test
	@Sql(statements = "insert into student(name,email,gender) values(\"kaju\",\"kaju@gmail.com\",Gender.MALE)",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
	public void testGetAllStudents()
	{

	}




	Calculator calculator=new Calculator ();
	@Test
	void isShouldAddTwoNumber() {
		//given
		int num1=20;
		int num2=30;
		//when
		int result=calculator.add ( num1,num2 );
		//then
		int expected=50;
		assertThat(result).isEqualTo ( expected );
	}
	class Calculator
	{
		public int add(int a,int b){
			return a+b;
		}
	}
}
