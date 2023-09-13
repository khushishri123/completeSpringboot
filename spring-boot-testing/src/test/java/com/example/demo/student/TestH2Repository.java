package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repository extends JpaRepository<Student,Long> {
}
