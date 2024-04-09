package com.demo.springjwt.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "offres") 
public class Offre {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	 @Column(name = "title")
	  private String title;

	  @Column(name = "description")
	  private String description;

	  @Column(name = "published")
	  private boolean published;

	public Offre() {
	}

	public Long getId() {
		return id; 
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
