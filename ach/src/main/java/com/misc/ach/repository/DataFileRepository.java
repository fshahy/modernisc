package com.misc.ach.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.misc.ach.model.DataFile;

public interface DataFileRepository extends JpaRepository<DataFile, Long> {
}