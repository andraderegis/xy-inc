package com.zup.xyapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
public class InterestPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotNull(message = "InterestPoint.name null")
	@NotBlank(message = "InterestPoint.name empty")
	@Column(name = "name", length = 50)
	private String name;

	@NotNull
	@Range(min = 1, max = 10000)
	private Integer coordinateX;

	@NotNull
	@Range(min = 1, max = 10000)
	private Integer coordinateY;

	public InterestPoint() {
	}

	public InterestPoint(String name, int coordinateX, int coordinateY) {
		setName(name);
		setCoordinateX(coordinateX);
		setCoordinateY(coordinateY);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCoordinateX() {
		return coordinateX;
	}

	public void setCoordinateX(Integer coordinateX) {
		this.coordinateX = coordinateX;
	}

	public Integer getCoordinateY() {
		return coordinateY;
	}

	public void setCoordinateY(Integer coordinateY) {
		this.coordinateY = coordinateY;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
