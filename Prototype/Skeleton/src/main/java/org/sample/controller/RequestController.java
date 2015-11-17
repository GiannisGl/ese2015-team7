package org.sample.controller;



import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.SearchForm;
import org.sample.controller.service.IRequestService;
import org.sample.controller.service.UserService;
import org.sample.model.Course;
import org.sample.model.Request;
import org.sample.model.User;
import org.sample.model.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * handels all requests which belong to request of a tutor action.
 * @author mirko
 *
 */
@SessionAttributes({"username"})
@Controller
public class RequestController {
	
	
	@Autowired 
	UserService userService;
	
	@Autowired
	IRequestService iRequestService;
	
	@Autowired
	UserDao userDao;

	
	/**
	 * gets searchedCourse from Session and saves the Request to the Database and deletes searchedCourse
	 * 
	 * 
	 * @param tutorEmail Email of Tutor which was requested
	 * @param session holds the current searchedCourse
	 * @return ModelView to new myRequestPage
	 */
	@RequestMapping(value = "/myRequests", method = RequestMethod.POST)
    public ModelAndView myRequestPost( @RequestParam("requestedUser") String tutorEmail, HttpSession session){
    	ModelAndView model = new ModelAndView("myRequests");
    	String principalEmail =SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	Course course = (Course) session.getAttribute("searchedCourse");
    	iRequestService.saveRequest(tutorEmail, SecurityContextHolder.getContext().getAuthentication().getName(), course);
    	session.removeAttribute("searchedCourse");
    	
    	
    	
    	User principal = userDao.findByEmail(principalEmail);
    	ArrayList<Request> myRequests = iRequestService.getAllMyRequests(principal);
    	model.addObject("myRequests",myRequests);
 
    	String username = principal.getFirstName()+" "+principal.getLastName();
    	model.addObject("username", username); 
        return model;
    }
	
	
	/**
	 * gets all myRequests (outgoing Requests) 
	 * Deletes searched Course if available
	 * @param session  --if available
	 * @return ModelView with all outgoing requests
	 */
	@RequestMapping(value = "/myRequests", method = RequestMethod.GET)
    public ModelAndView myRequestGet( HttpSession session){
    	ModelAndView model = new ModelAndView("myRequests");
    	String principalEmail =SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	User principal = userDao.findByEmail(principalEmail);
    	
    	ArrayList<Request> myRequests = iRequestService.getAllMyRequests(principal);
    	model.addObject("myRequests", myRequests);
    	
    	model.addObject("searchForm", new SearchForm());
    	session.removeAttribute("searchedCourse");
    	String username = principal.getFirstName()+" "+principal.getLastName();
    	session.setAttribute("username", username);
    	
        return model;
    }
	
	/**
	 * gets all requests ( Incoming Requests)
	 * deletes searchedCourse if available
	 * @param session --if available searchedCourse 
	 * @return MOdelView with all incoming Requests
	 */
	@RequestMapping(value = "/requests", method = RequestMethod.GET)
    public ModelAndView requestGet(HttpSession session){
    	ModelAndView model = new ModelAndView("requests");
    	String principalEmail =SecurityContextHolder.getContext().getAuthentication().getName();
    	
    	User principal = userDao.findByEmail(principalEmail);
    	
    	ArrayList<Request> requests = iRequestService.getAllRequests(principal);
    	model.addObject("requests", requests);
    	session.removeAttribute("searchedCourse");
    	String username = principal.getFirstName()+" "+principal.getLastName();
    	session.setAttribute("username", username);
    	
        return model;
    }
	
	/**
	 * handles action on outgoing requests
	 * @param tutorEmail the Email of the Tutor which should be deleted
	 * @return ModelView with updated list of outgoing requests
	 */
	@RequestMapping(value = "/myRequests/action", method = RequestMethod.POST)
    public ModelAndView myRequestAction(@RequestParam(name="courseId")String courseId, @RequestParam("deleteRequest") String tutorEmail,RedirectAttributes redirect){
    	ModelAndView model = new ModelAndView("redirect:/myRequests");
    	
    	String principalEmail =SecurityContextHolder.getContext().getAuthentication().getName();
    	User principal = userDao.findByEmail(principalEmail);
	
    	iRequestService.deleteRequest(userDao.findByEmail(tutorEmail), principal,courseId);
  
    	ArrayList<Request> requests = iRequestService.getAllRequests(principal);
    	redirect.addFlashAttribute("requests", requests);
    	
    	String username = principal.getFirstName()+" "+principal.getLastName();
    	redirect.addFlashAttribute("username", username);
    	
        return model;
    }
	
	/**
	 * handles action of requestsPage depended of the user action:
	 * @param studentEmail if not empty visit Profile of Email
	 * @param acceptStudentEmail if not empty accept Student and store to Database
	 * @param declineStudentEmail if not empty decline Student and store to Database
	 * @param redirectAttrs 
	 * @return ModelView with updated requestPage
	 */
	@RequestMapping(value = "/requests/action", method = RequestMethod.POST)
    public ModelAndView requestAction(@RequestParam(name="courseId")String courseId, @RequestParam(required=false,name="acceptRequest") String acceptStudentEmail,@RequestParam(required=false,name="declineRequest") String declineStudentEmail,RedirectAttributes redirect){
    	ModelAndView model = new ModelAndView("redirect:/requests");
    	String principalEmail =SecurityContextHolder.getContext().getAuthentication().getName();
    	User principal = userDao.findByEmail(principalEmail);
    	
    try{  	
   
    	if (acceptStudentEmail != null){
    		iRequestService.acceptRequest(principal,userDao.findByEmail(acceptStudentEmail),courseId);
    		
    	}
    	else if (declineStudentEmail != null){
    		iRequestService.declineRequest(principal, userDao.findByEmail(declineStudentEmail),courseId);
    	}
    	

    	ArrayList<Request> requests = iRequestService.getAllRequests(principal);
    	redirect.addFlashAttribute("requests", requests);
	} catch (InvalidUserException e) {
    	e.printStackTrace();
    }
   
		String username = principal.getFirstName()+" "+principal.getLastName();
    	redirect.addFlashAttribute("username", username);
    	
        return model;
    }
}
