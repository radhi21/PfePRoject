package com.demo.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	
}
