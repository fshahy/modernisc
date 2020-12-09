package com.misc.ach;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.misc.ach.model.DataFile;
import com.misc.ach.model.Line;
import com.misc.ach.service.AchService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DataFileControllerTest {
	
	@MockBean
	private AchService achService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("GET /file - All Files")
	void getAllFiles() throws Exception {
		DataFile file1 = DataFile.builder().id(1L).name("data1.txt").build();
		DataFile file2 = DataFile.builder().id(2L).name("data2.txt").build();
		List<DataFile> files = new ArrayList<>();
		files.add(file1);
		files.add(file2);
		
		doReturn(files).when(achService).getAllFiles();
		
		mockMvc.perform(get("/file"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].name", is("data1.txt")))
			
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].name", is("data2.txt")));
	}

	@Test
	@DisplayName("GET /file/1 - Found ")
	void testGetDataFileById() throws Exception {
		Date createdAt = Calendar.getInstance().getTime();
		
		DataFile file = DataFile.builder().id(1L).name("data.txt").created_at(createdAt).build(); 
		doReturn(Optional.of(file)).when(achService).getFileById(1L);

		mockMvc.perform(get("/file/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("data.txt")));
	}
	
	@Test
	@DisplayName("GET /file/2 - Not Found")
	void testGetDataFileById_404() throws Exception {
		doReturn(Optional.empty()).when(achService).getFileById(2L);
		
		mockMvc.perform(get("/file/{id}", 2))
			.andExpect(status().isNotFound());
	}
	
	@Test
	@DisplayName("GET /file/1/lines - Found")
	void testGetLines() throws Exception {
		DataFile file = DataFile.builder().id(1L).name("sample.txt").build();
		
		Line line1 = Line.builder().id(1L).data("AAA").coreId(10L).build();
		Line line2 = Line.builder().id(2L).data("BBB").coreId(11L).build();
		
		List<Line> lines = new ArrayList<>();
		lines.add(line1);
		lines.add(line2);
		
		file.setLines(lines);
		
		doReturn(Optional.of(file)).when(achService).getFileById(1L);
			
		mockMvc.perform(get("/file/1/lines"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].data", is("AAA")))
			.andExpect(jsonPath("$[0].coreId", is(10)))
		
			.andExpect(jsonPath("$[1].id", is(2)))
			.andExpect(jsonPath("$[1].data", is("BBB")))
			.andExpect(jsonPath("$[1].coreId", is(11)));
	}
	
	@Test
	@DisplayName("GET /file/1/lines/2 - Found")
	void testGetLineById() throws Exception {
		Date createdAt = Calendar.getInstance().getTime();
		DataFile file = DataFile.builder().id(1L).name("test.txt").created_at(createdAt).build();
		
		Line line1 = Line.builder().id(1L).data("aaaa").coreId(10L).created_at(createdAt).build();
		Line line2 = Line.builder().id(2L).data("bbbb").coreId(11L).created_at(createdAt).build();
		
		List<Line> lines = new ArrayList<>();
		lines.add(line1);
		lines.add(line2);
		
		file.setLines(lines);
		
		doReturn(Optional.of(file)).when(achService).getFileById(1L);
		doReturn(Optional.of(line2)).when(achService).getLineById(2L);
			
		mockMvc.perform(get("/file/1/lines/2"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$.id", is(2)))
			.andExpect(jsonPath("$.data", is("bbbb")))
			.andExpect(jsonPath("$.coreId", is(11)));
	}
}
