package com.be.millipore.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.be.millipore.constant.DBConstant;

@Entity
public class Template {

	private Long id;
	private String content;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// @ApiModelProperty(hidden = true)
	@Column(name = DBConstant.CONTENT_ID, unique = true, nullable = false)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = DBConstant.CONTENT, unique = true, nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}