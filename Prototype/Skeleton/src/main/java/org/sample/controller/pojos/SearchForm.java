package org.sample.controller.pojos;

import javax.validation.constraints.NotNull;

/**
 * This is the form used for searching.
 * 
 * @author Team7
 *
 */
public class SearchForm 
{	
    private Long id;
    @NotNull
    private String university;
    
    private String subject;
    
    private String course;
    
    private float grade;

	public Long getId() {
        return id;
    }

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public void setId(Long id) {
        this.id = id;
    }
	
    public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
}
