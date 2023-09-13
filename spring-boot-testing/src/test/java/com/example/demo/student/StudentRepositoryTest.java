package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    // after each test the below method will get executed
    @AfterEach
    void tearDown()
    {
        studentRepository.deleteAll ();
    }
    @Test
    void checkIfStudentEmailExists() {
        //given
        String email="badam@gmail.com";
        Student student=new Student (
                "badam",
                email,
                Gender.MALE
        );
        studentRepository.save ( student );

        //when
        boolean expected=studentRepository.selectExistsEmail ( email );

        //then
        assertThat(expected).isTrue ();

    }
    @Test
    void checkIfStudentEmailDoesNotExists()
    {
        //given
       String email="kaju@gmail.com";
//        Student student=new Student (
//          "kaju",
//          email,
//          Gender.MALE
//        );
//        studentRepository.save ( student );
        //when
        boolean expected=studentRepository.selectExistsEmail ( email );

        //then
        assertThat ( expected ).isFalse ();

    }
}
