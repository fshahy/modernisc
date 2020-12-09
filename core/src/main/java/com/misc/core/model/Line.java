package com.misc.core.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Line {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotEmpty
	private String data;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Builder.Default
	private Date processed_at = Calendar.getInstance().getTime();
}
