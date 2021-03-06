package org.sample.model.dao;

import java.util.ArrayList;

import org.sample.model.Course;
import org.sample.model.Subject;
import org.springframework.data.repository.CrudRepository;

/**
 * <p>This is the interface between the database and the services concerning Courses.</p>
 * It provides basic methods as storing, deleting, updating and getting entries in the database.
 * @author Team7
 *
 */
public interface CourseDao extends CrudRepository<Course,Long> {
	
	Course findByCourseNameAndSubject(String course, Subject subject);
	Course findByCourseName(String courseName);
	ArrayList<Course> findBySubject(Subject subject);
	Course findById(long id);

}