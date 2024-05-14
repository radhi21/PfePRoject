package com.demo.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.springjwt.models.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
