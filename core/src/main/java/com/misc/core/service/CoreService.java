package com.misc.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.misc.core.model.Line;
import com.misc.core.repository.LineRepository;

@Service
public class CoreService {
	@Autowired
	private LineRepository lRepo;
	
	public Optional<Line> getLineById(Long id) {
		return this.lRepo.findById(id);
	}
	
	public List<Line> getAllLines() {
		return this.lRepo.findAll();
	}
	
	public Line saveLine(Line line) {
		return this.lRepo.save(line);
	}
	
	public Optional<Line> deleteLine(Long id) {
		Optional<Line> optLine = this.lRepo.findById(id);
		if(optLine.isPresent()) {
			Line line = optLine.get();
			this.lRepo.deleteById(id);
			
			return Optional.of(line);
		}
		
		return Optional.empty();
	}
	
	public Optional<Line> updateLineData(Long id, String data) {
		Optional<Line> optLine = this.lRepo.findById(id);
		if(optLine.isPresent()) {
			Line line = optLine.get();
			line.setData(data);
			saveLine(line);
			
			return Optional.of(line);
		}
		
		return Optional.empty();
	}
	
	public Optional<Line> replaceLine(Line newLine) {
		Optional<Line> optLine = this.lRepo.findById(newLine.getId());
		if(optLine.isPresent()) {
			Line savedLine = this.lRepo.save(newLine);
			return Optional.of(savedLine);
		}
		
		return Optional.empty();
	}
}
