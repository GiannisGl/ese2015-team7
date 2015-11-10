package org.sample.controller.service;

import java.util.ArrayList;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.SearchForm;
import org.sample.model.Course;
import org.sample.model.Subject;
import org.sample.model.Tutor;
import org.sample.model.University;

public interface SearchService {
	
	 public ArrayList<University> getUniversities();
	 
	 public ArrayList<Subject> getSubjectsFromUniversity(String university);
	 
	 public ArrayList<Course> getCourseFromSubject(String subject);
	 
	 public ArrayList<Subject> getSubjects();
	 
	 public ArrayList<Course> getCourses();
	 
	 public ArrayList<Tutor> getTutorsFromSearchForm(SearchForm searchForm) throws InvalidUserException;
	 
	 public University getUniversityByName(String universityName);
	 
	 public Subject getSubjectByName(String subjectName);
	 
	 public Course getCourseByName(String courseName);
<<<<<<< HEAD

	public Course getCourse(SearchForm searchForm);
=======
>>>>>>> a261c271cf38bba659f0eafc30d92dcb3853f899

}