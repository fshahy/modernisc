package com.misc.ach;

import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.misc.ach.model.DataFile;
import com.misc.ach.model.Line;
import com.misc.ach.repository.DataFileRepository;
import com.misc.ach.repository.LineRepository;
import com.misc.ach.service.AchService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AchServiceTest {
	@MockBean
	private DataFileRepository dfRepo;
	
	@MockBean
	private LineRepository lRepo;
	
	@Autowired
	private AchService achService;
	
	@Test
	@DisplayName("Get File By Id - ACH Service")
	void testGetFileById() {
		Date createdAt = Calendar.getInstance().getTime();
		DataFile file = DataFile.builder().id(1L).name("some-data.txt").created_at(createdAt).build();
		
		doReturn(Optional.of(file)).when(dfRepo).findById(1L);
		
		Optional<DataFile> optfile = this.achService.getFileById(1L);
		
		Assertions.assertTrue(optfile.isPresent(), "DataFile not found.");
		Assertions.assertEquals(optfile.get().getId(), 1L);
		Assertions.assertEquals(optfile.get().getCreated_at(), createdAt);
		Assertions.assertEquals(optfile.get().getName(), "some-data.txt");
	}
	
	@Test
	@DisplayName("Get all files- ACH Service")
	void testGetAllFiles() {
		List<DataFile> files = new ArrayList<>();
		
		Date createdAt = Calendar.getInstance().getTime();
		
		DataFile file1 = DataFile.builder().id(1L).name("File1.txt").created_at(createdAt).build();
		DataFile file2 = DataFile.builder().id(2L).name("File2.txt").created_at(createdAt).build();
		
		files.add(file1);
		files.add(file2);
		
		doReturn(files).when(dfRepo).findAll();
		
		List<DataFile> result = this.achService.getAllFiles();
		
		Assertions.assertEquals(result.size(), 2);
		Assertions.assertEquals(result.get(0).getId(), 1L);
		Assertions.assertEquals(result.get(1).getId(), 2L);
	}

	@Test
	@DisplayName("Get Line By Id - ACH Service")
	void testGetLineById() {
		Date createdAt = Calendar.getInstance().getTime();
		Line line = Line.builder().id(1L).data("bbb").created_at(createdAt).coreId(3L).build();
		
		doReturn(Optional.of(line)).when(lRepo).findById(1L);
		
		Optional<Line> optLine = this.achService.getLineById(1L);
		
		Assertions.assertTrue(optLine.isPresent(), "Line not Found.");
		Assertions.assertEquals(optLine.get().getId(), 1L);
		Assertions.assertEquals(optLine.get().getCoreId(), 3L);
		Assertions.assertEquals(optLine.get().getData(), "bbb");
		Assertions.assertEquals(optLine.get().getCreated_at(), createdAt);
	}
}
