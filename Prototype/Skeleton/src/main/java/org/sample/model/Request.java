package org.sample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;


/**
 * <p>This entity represents a request.</p>
 * 
 * @author Team7
 *
 */
@Entity
public class Request {
	
	
	public Request() {
		super();
		this.isActiv = false;
		this.isAccepted = false;
		this.isDeclined = false;
		this.isDeleted = false;
		this.newRequest = false;
	}



	@Id
    @GeneratedValue
    private Long id;
	
	@NotNull
	@ManyToOne(targetEntity=User.class)
	private User student;
	
	
	@NotNull
	@ManyToOne(targetEntity=UserCourse.class)
	private UserCourse userCourse;
	
	private long userCourseId;

	
	@NotNull
	Boolean isActiv;
	@NotNull
	Boolean isAccepted;
	@NotNull
	Boolean isDeclined;
	@NotNull
	Boolean isDeleted;
	@NotNull
	Boolean newRequest;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public UserCourse getUserCourse() {
		return userCourse;
	}

	public void setUserCourse(UserCourse userCourse) {
		this.userCourseId = userCourse.getUserCourseId();
		this.userCourse = userCourse;
	}
	
	public long getUserCourseId() {
		return userCourseId;
	}

	public void setUserCourseId(long userCourseId) {
		this.userCourseId = userCourseId;
	}
	
	public Course getCourse(){
		return userCourse.getCourse();
	}
	
	public Subject getSubject(){
		return getCourse().getSubject();
	}
	
	public University getUniversity(){
		return getSubject().getUniversity();
	}
	
	public User getTutor(){
		return userCourse.getUser();
	}
	
	public String getTutorsEmail(){
		return getTutor().getEmail();
	}
	
	public String getStudentsEmail(){
		return getStudent().getEmail();
	}
	
	

	public Boolean getIsActiv() {
		return isActiv;
	}

	public void setIsActiv(Boolean isActiv) {
		this.isActiv = isActiv;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Boolean getIsDeclined() {
		return isDeclined;
	}

	public void setIsDeclined(Boolean isDeclined) {
		this.isDeclined = isDeclined;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Boolean getNewRequest() {
		return newRequest;
	}

	public void setNewRequest(Boolean newRequest) {
		this.newRequest = newRequest;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isAccepted == null) ? 0 : isAccepted.hashCode());
		result = prime * result + ((isActiv == null) ? 0 : isActiv.hashCode());
		result = prime * result + ((isDeclined == null) ? 0 : isDeclined.hashCode());
		result = prime * result + ((isDeleted == null) ? 0 : isDeleted.hashCode());
		result = prime * result + ((newRequest == null) ? 0 : newRequest.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		result = prime * result + ((userCourse == null) ? 0 : userCourse.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isAccepted == null) {
			if (other.isAccepted != null)
				return false;
		} else if (!isAccepted.equals(other.isAccepted))
			return false;
		if (isActiv == null) {
			if (other.isActiv != null)
				return false;
		} else if (!isActiv.equals(other.isActiv))
			return false;
		if (isDeclined == null) {
			if (other.isDeclined != null)
				return false;
		} else if (!isDeclined.equals(other.isDeclined))
			return false;
		if (isDeleted == null) {
			if (other.isDeleted != null)
				return false;
		} else if (!isDeleted.equals(other.isDeleted))
			return false;
		if (newRequest == null) {
			if (other.newRequest != null)
				return false;
		} else if (!newRequest.equals(other.newRequest))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		if (userCourse == null) {
			if (other.userCourse != null)
				return false;
		} else if (!userCourse.equals(other.userCourse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Request [id=" + id + ", student=" + student + ", tutor=" + userCourse.getUser() + ", course=" + userCourse.getCourse() + "]";
	}
}
