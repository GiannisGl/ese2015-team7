package org.sample.controller.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.SignupForm;
import org.sample.model.Grade;
import org.sample.model.TimeSlot;
import org.sample.model.User;
import org.sample.model.UserRole;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserService implements IUserDataService{
	
	@Autowired	UserDao userDao;

	public SignupForm saveFrom(SignupForm signupForm, User userToUpdate) {
		String name = signupForm.getName();

		if (!StringUtils.isEmpty(name) && "ESE".equalsIgnoreCase(name)) {
		    throw new InvalidUserException("Sorry, ESE is not a valid name"); // throw exception
		}

		BCryptPasswordEncoder password = new BCryptPasswordEncoder();

		User user = (userToUpdate == null) ? new User() : userToUpdate;
		user.setName(signupForm.getName());
		user.setEmail(signupForm.getEmail());
		
		user.setBiography(signupForm.getBiography());
		
		ArrayList<Grade> arrayList = new ArrayList<Grade>();
		ListIterator<Grade> itr = signupForm.getGrades().listIterator();
		while(itr.hasNext())
		{  
			arrayList.add(itr.next());
		}
		user.setGrades(arrayList);
		
		ArrayList<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
		ListIterator<TimeSlot> iter = signupForm.getTimeSlots().listIterator();
		while(iter.hasNext())
		{
			timeSlotList.add(iter.next());
		}
		user.setTimeSlots(timeSlotList);

		user.setPassword(password.encode(signupForm.getPassword()));

		user.setEnabled(true);

		Set<UserRole> userRole = new HashSet<UserRole>();
		UserRole role = new UserRole();
		role.setRole("ROLE_USER");
		role.setUser(user);
		userRole.add(role);

		user.setUserRole(userRole);

		user = userDao.save(user); // save object to DB

		signupForm.setId(user.getId());

		return signupForm;
	}
	
	public SignupForm saveFrom(SignupForm signupForm){
		return saveFrom(signupForm, null);
	}
	
	public User getUserById(Long userId) {
		return userDao.findOne(userId);
	    }

    public User getUserByEmail(String email) {
    	return userDao.findByEmail(email);
    }

	public boolean validatePassword(String password, String passwordVerify) {
		if (password.equals(passwordVerify))
			return true;
		else
		return false;
	}
    
}