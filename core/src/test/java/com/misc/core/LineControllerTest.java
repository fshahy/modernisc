package com.misc.core;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;
import java.util.Date;
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

import com.misc.core.model.Line;
import com.misc.core.service.CoreService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LineControllerTest {
	@MockBean
	private CoreService coreService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Get Line By Id - Success")
	void testGetLineById() throws Exception {
		Date processedAt = Calendar.getInstance().getTime();
		Line line = Line.builder().id(1L).data("AAA-AAA").processed_at(processedAt).build();
		
		doReturn(Optional.of(line)).when(coreService).getLineById(1L);
		
		mockMvc.perform(get("/line/{id}", 1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.data", is("AAA-AAA")));
	}
}
