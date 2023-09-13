package com.example.demo.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith ( SpringExtension.class )
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private StudentService studentService;
    private ObjectMapper objectMapper=new ObjectMapper ();
    @Test
     void getAllStudents() throws Exception {
        List<Student> ls=new ArrayList<> ();
        ls.add(new Student ("badam","badam@gmail.com",Gender.MALE));
        when(studentService.getAllStudents ()).thenReturn ( ls );
        mockMvc.perform( MockMvcRequestBuilders.get ("/api/getAllStudents"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType( MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void addStudent() throws Exception {
        Student student=new Student ("badam","badam@gmail.com",Gender.MALE);
        when(studentService.addStudent ( any() )).thenReturn ( student );
        mockMvc.perform ( MockMvcRequestBuilders.post ( "/api/addStudent" ).
                content ( objectMapper.writeValueAsString ( student ) ).
                contentType ( MediaType.APPLICATION_JSON ) ).andDo ( print () ).andExpect ( status ().isOk () ).
                andExpect ( content ().contentType (MediaType.APPLICATION_JSON) ).
                andExpect ( jsonPath ( "$.name",is ( "badam" ) ) ).
                andExpect ( jsonPath ( "$.email",is("badam@gmail.com" ) )).
                andExpect ( jsonPath ("$" ).isNotEmpty () );
    }

    @Test
    void updateStudent() throws Exception {
        Student updatedStudent=new Student (1L,"badam","badam@gmail.com",Gender.MALE);
when ( studentService.updateStudent ( any () ) ).thenReturn ( updatedStudent );
mockMvc.perform ( MockMvcRequestBuilders.put ( "/api/updateStudent" ).
        content ( objectMapper.writeValueAsString ( updatedStudent ) ).
        contentType ( MediaType.APPLICATION_JSON ) ).andDo ( print() ).
        andExpect ( status ().isOk () ).
        andExpect ( jsonPath ( "$.name",is("badam") ) ).
        andExpect ( jsonPath ( "$" ).isNotEmpty () );

    }

    @Test
    void deleteStudent() throws Exception {
       mockMvc.perform ( MockMvcRequestBuilders.delete ( "/api/deleteById/1" ) ).
               andDo ( print () ).andExpect ( status ().isOk () );
// Verify that the deleteStudent method was called with parameter 1L
        //verify is used to check that whether method was called with the expected parameter
       verify ( studentService).deleteStudent ( 1L );

    }
}