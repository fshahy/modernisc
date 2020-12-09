package com.misc.ach.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.misc.ach.model.Line;

public interface LineRepository extends JpaRepository<Line, Long> {
}