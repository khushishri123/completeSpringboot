package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService {

    @Autowired
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student addStudent(Student student) {
        Boolean existsEmail = studentRepository
                .selectExistsEmail(student.getEmail());
        if (existsEmail) {
            throw new BadRequestException(
                    "Email " + student.getEmail() + " taken");
        }

       return studentRepository.save(student);
    }


    public void deleteStudent(Long studentId) {
        if(!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(
                    "Student with id " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    public Student updateStudent( Student student) {
        Long studentId= student.getId ();;
        //Optional<Student> s =studentRepository.findById ( id );
        Student s = studentRepository.findById(studentId)
                .orElseThrow(()-> new StudentNotFoundException ( "Student with id: "+studentId+ " does not exists"  )); // Provide a default value or action

        s.setEmail ( student.getEmail () );
        s.setName ( student.getName () );
        s.setGender ( student.getGender () );

       return studentRepository.save ( s );
    }
}
