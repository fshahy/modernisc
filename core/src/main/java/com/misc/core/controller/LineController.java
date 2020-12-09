package com.misc.core.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misc.core.model.Line;
import com.misc.core.service.CoreService;

@RestController
@RequestMapping("/line")
public class LineController {
	
	@Autowired
	private CoreService coreService;
	
	@GetMapping("/{lineId}")
	public ResponseEntity<?> getLineById(@PathVariable Long lineId) {
		return this.coreService.getLineById(lineId).map(line -> {
			return ResponseEntity.ok().body(line);
		})
		.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public ResponseEntity<List<Line>> getAll() {
		List<Line> allLines = this.coreService.getAllLines();
		return ResponseEntity.ok().body(allLines);
	}
	
	@PostMapping
	public ResponseEntity<Line> createLine(@Valid @RequestBody Line line, Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest().build();
		}
		
		Line savedLine = this.coreService.saveLine(line);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedLine);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteLine(@PathVariable Long id) {
		Optional<Line> result = this.coreService.deleteLine(id);
		if(result.isPresent()) {
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Line> updateLineData(@PathVariable Long id, @RequestBody String data) {
		Optional<Line> optLine = this.coreService.updateLineData(id, data);
		
		if(optLine.isPresent()) {
			Line line = optLine.get();
			return ResponseEntity.ok(line);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Line> replaceLine(@RequestBody Line line) {
		Optional<Line> optLine = this.coreService.replaceLine(line); 
		if(optLine.isPresent()) {
			return ResponseEntity.ok(optLine.get());
		}
		
		return ResponseEntity.notFound().build();
	}
}
