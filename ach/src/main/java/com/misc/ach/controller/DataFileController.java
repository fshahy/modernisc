package com.misc.ach.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.misc.ach.model.DataFile;
import com.misc.ach.model.Line;
import com.misc.ach.service.AchService;

@RestController
@RequestMapping("/file")
public class DataFileController {	
	
	@Autowired
	private AchService service;
	
	@GetMapping
	public ResponseEntity<List<DataFile>> getAll() {
		List<DataFile> files = this.service.getAllFiles();
		return ResponseEntity.ok().body(files);
	}
	
	@GetMapping("/{fileId}")
	public ResponseEntity<DataFile> getDataFileById(@PathVariable Long fileId) {
		return this.service.getFileById(fileId).map(file -> {
			return ResponseEntity.ok(file);
		})
		.orElse(ResponseEntity.notFound().build());
	}
	
	@GetMapping("/{fileId}/lines")
	public ResponseEntity<List<Line>> getLines(@PathVariable Long fileId) {
		return this.service.getFileById(fileId).map(file -> {
			List<Line> lines = file.getLines();
			return ResponseEntity.ok().body(lines);
		})
		.orElse(ResponseEntity.notFound().build());	
	}
	
	@GetMapping("/{fileId}/lines/{lineId}")
	public ResponseEntity<Line> getLineById(@PathVariable Long fileId, @PathVariable Long lineId) {
		return this.service.getFileById(fileId).map(file -> {
			return this.service.getLineById(lineId).map(line -> {
				return ResponseEntity.ok().body(line);
			})
			.orElse(ResponseEntity.notFound().build());
		})
		.orElse(ResponseEntity.notFound().build());	
	}
}
