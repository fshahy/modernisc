package com.misc.ach.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"file"})
public class Line {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(nullable = false)
	private String data;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private DataFile file;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Builder.Default
	private Date created_at = Calendar.getInstance().getTime();
	
	private Long coreId;
}