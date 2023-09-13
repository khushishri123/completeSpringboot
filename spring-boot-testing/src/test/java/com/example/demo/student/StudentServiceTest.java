package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@ExtendWith ( MockitoExtension.class )
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    private StudentService underTest;

    @BeforeEach
    void setUp() {
       underTest=new StudentService ( studentRepository );

    }



    @Test
    void testGetAllStudents() {
        //when
        List<Student> ls=new ArrayList<> ();
        lenient ().when ( studentRepository.findAll () ).thenReturn ( ls );
        underTest.getAllStudents ();
        //then
       verify(studentRepository).findAll ();
    }

    @Test
    void addStudent() {
        //given
        Student student=new Student ("Tillu","tillu@gmail.com",Gender.MALE);

        //when
        underTest.addStudent ( student );
        // we have 2 different options to test: see notion to understand both snippets
        //when ( studentRepository.save ( any() ) ).thenReturn ( Student.class )
        //then
        ArgumentCaptor<Student> argumentCaptor=ArgumentCaptor.forClass ( Student.class );
        verify ( studentRepository ).save ( argumentCaptor.capture () );
        Student capturedStudent=argumentCaptor.getValue ();
        assertThat(capturedStudent).isEqualTo ( student );

    }

    @Test
    public void testWillThrowIfEmailExists()
    {
        Student student=new Student ("pista","pista@gmail.com",Gender.MALE);
        //then
        given(studentRepository.selectExistsEmail ( student.getEmail ())).willReturn(true);
        assertThatThrownBy ( ()->{underTest.addStudent (student);} ).isInstanceOf ( BadRequestException.class ).hasMessageContaining ( "Email " + student.getEmail() + " taken" );
        verify ( studentRepository,never () ).save ( any () );
    }

    @Test
    void testDeleteStudent() {
        //given

        Long studentId=Long.valueOf ( 1 );
        //when
        given(studentRepository.existsById ( studentId )).willReturn ( true);
        underTest.deleteStudent (studentId);
        //then

        ArgumentCaptor<Long> argumentCaptor=ArgumentCaptor.forClass ( Long.class);
        verify ( studentRepository ).deleteById (argumentCaptor.capture ());
        Long capturedValue=argumentCaptor.getValue ();
        assertThat ( capturedValue ).isEqualTo ( studentId );

    }
    @Test
    public void testWillThrowIfIdDoesNotExists()
    {
        given(studentRepository.existsById ( any () )).willReturn ( false );
        assertThatThrownBy ( ()->{underTest.deleteStudent ( Long.valueOf ( 1 ) );} ).isInstanceOf ( StudentNotFoundException.class ).hasMessageContaining ( "Student with id 1"  + " does not exists"  );
        verify ( studentRepository,never () ).deleteById ( any() );

    }

    @Test
    public void testUpdateStudent()
    {
        Long studentId=1L;
        //given, we will check that whether it is updating or not
        Student existingStudent=new Student (Long.valueOf ( 1 ),"salad","salad@gmail.com",Gender.FEMALE);
        Student updatedStudent=new Student (Long.valueOf ( 1 ),"saladWithGreenVegetables","saladWithGreenVegetables@gmail.com",Gender.FEMALE);

        //when
        given ( studentRepository.findById ( Long.valueOf ( 1 ) ) ).willReturn ( Optional.of ( existingStudent ) );
        given ( studentRepository.save ( any() ) ).willReturn ( updatedStudent );
        Student result=underTest.updateStudent (updatedStudent );
        //then
        assertThat ( result ).isEqualTo ( updatedStudent );
        verify ( studentRepository ).findById ( studentId );
        verify ( studentRepository ).save ( updatedStudent );


    }
}