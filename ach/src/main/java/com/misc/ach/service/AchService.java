package com.misc.ach.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misc.ach.model.DataFile;
import com.misc.ach.model.Line;
import com.misc.ach.repository.DataFileRepository;
import com.misc.ach.repository.LineRepository;

@Service
public class AchService {
	@Autowired
	private DataFileRepository dfRepo;
	
	@Autowired
	private LineRepository lRepo;
	
	public DataFile saveFile(DataFile file) {
		return this.dfRepo.save(file);
	}
	
	public Line saveLine(Line line) {
		return this.lRepo.save(line);
	}
	
	public Optional<DataFile> getFileById(Long id) {
		return this.dfRepo.findById(id);
	}
	
	public List<DataFile> getAllFiles() {
		return this.dfRepo.findAll();
	}
	
	public Optional<Line> getLineById(Long id) {
		return this.lRepo.findById(id);
	}
}
