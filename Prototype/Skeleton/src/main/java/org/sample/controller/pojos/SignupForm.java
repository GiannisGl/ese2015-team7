package org.sample.controller.pojos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

import org.sample.model.Grade;
import org.sample.model.GradeFactory;
import org.sample.model.TimeSlot;
import org.sample.model.TimeSlotFactory;
import org.springframework.util.AutoPopulatingList;


public class SignupForm {

	public interface SignupValidatorGroup {}

    private Long id;
    private String name;
    
    private String biography;
    // @Valid
    private AutoPopulatingList<Grade> grades = new AutoPopulatingList<Grade>(new GradeFactory());
    // @Valid
    private AutoPopulatingList<TimeSlot> timeSlots = new AutoPopulatingList<TimeSlot>(new TimeSlotFactory());
    
    @Size(groups= {SignupValidatorGroup.class, Default.class}, min = 6, max = 20, message = "Bitte wähle ein passwort mit mindestens 6 Zeichen.")
    private String password;
    
    private String passwordVerify;


    @NotNull
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", 
    message = "Must be valid email address")
    private String email;

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AutoPopulatingList<Grade> getGrades() {
		return grades;
	}

	public void setGrades(AutoPopulatingList<Grade> grades) {
		this.grades = grades;
	}

	public AutoPopulatingList<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(AutoPopulatingList<TimeSlot> timeSlots) {
		this.timeSlots = timeSlots;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getPasswordVerify() {
		return passwordVerify;
	}

	public void setPasswordVerify(String passwordVerify) {
		this.passwordVerify = passwordVerify;
	}
}
