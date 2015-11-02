package org.sample.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.NaturalId;
import org.springframework.util.AutoPopulatingList;


@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    
    private String biography;
    
    @Column(columnDefinition = "LONGBLOB") // to store large data
    private ArrayList<Grade> grades = new ArrayList<Grade>();
    @Column(columnDefinition = "LONGBLOB") // to store large data
    private ArrayList<TimeSlot> timeSlots = new ArrayList<TimeSlot>();

    @NaturalId(mutable=false)
    @Column(name="EMAIL", unique = true, nullable = false, length = 111)
    private String email;
    private String password;
    private boolean enabled;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> userRole;
    
    public boolean isEnabled() {
		return enabled;
	}

	public Set<UserRole> getUserRole() {
		return userRole;
	}

	public void setUserRole(Set<UserRole> userRole) {
		this.userRole = userRole;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public ArrayList<Grade> getGrades() {
		return grades;
	}

	public void setGrades(ArrayList<Grade> grades) {
		this.grades = grades;
	}

    public ArrayList<TimeSlot> getTimeSlots() {
		return timeSlots;
	}

	public void setTimeSlots(ArrayList<TimeSlot> timeSlots) {
		this.timeSlots = timeSlots;
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

}
