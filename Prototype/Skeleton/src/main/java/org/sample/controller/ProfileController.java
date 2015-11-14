package org.sample.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.ListIterator;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.service.IUserDataService;
import org.sample.controller.service.SearchService;
import org.sample.model.Course;
import org.sample.model.Grade;
import org.sample.model.UserCourseFormAttributeFactory;
import org.sample.model.dao.UserCourseDao;
import org.sample.model.Subject;
import org.sample.model.TimeSlot;
import org.sample.model.TimeSlotFactory;
import org.sample.model.University;
import org.sample.model.User;
import org.sample.model.UserCourse;
import org.sample.model.UserCourseFormAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;
import org.springframework.util.AutoPopulatingList;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * handles all requests concerning the editing and displaying of the profileData.
 * 
 * @author simon
 *
 */
/**
 * @author mirko
 *
 */
@SessionAttributes("username")
@Controller
public class ProfileController {
	
	@Autowired
    IUserDataService userService;
	
	@Autowired
    SearchService searchService;
	
	@Autowired
	UserCourseDao userCourseDao;
	
	/**
	 * displays the profile data of the Principal if no User is entered
	 * @param theUser
	 * @return
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile(@RequestParam(required=false,name="user") User theUser){
    	ModelAndView model = new ModelAndView("profile");
    	User principal =userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
    	
    	User user = principal;
    		if (theUser!=null)
    			user = theUser;
    	model.addObject(user);
    	
    	String principalName=SecurityContextHolder.getContext().getAuthentication().getName();
    	model.addObject("principalName", principalName);
    	
    	ArrayList<UserCourse> userCourses = new ArrayList<UserCourse>(); 
        userCourses =  (ArrayList<UserCourse>) userCourseDao.findByUser(user);
        model.addObject("userCourses", userCourses);

        return model;
    }
	
	/**
	 * shows profile of entered tutorEmailaddress
	 * @param tutorEmail of Tutor to be displayed 
	 * @return ModelView profile of the entered Tutor.
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ModelAndView postProfile( @RequestParam("itemUser") String tutorEmail){
    	ModelAndView model = new ModelAndView("profile");
    	User user = userService.getUserByEmail(tutorEmail);
    	model.addObject(user);
    	
    	String username = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).getName(); //gets principal and loads user from Database and gets his name
    	model.addObject("username", username);
    	
    	String principalName=SecurityContextHolder.getContext().getAuthentication().getName();
    	model.addObject("principalName", principalName);
    	
    	ArrayList<UserCourse> userCourses = new ArrayList<UserCourse>(); 
        userCourses =  (ArrayList<UserCourse>) userCourseDao.findByUser(user);
        model.addObject("userCourses", userCourses);
    	
        return model;
    }
	
	/**
	 * Serve model with signupForm.
	 * @param principal the logged in principal.
	 * @return the a signupForm with filled name and biography fields.
	 */
	@ModelAttribute("signupForm")
	public SignupForm getSignupForm(Principal principal)
	{
		User user = userService.getUserByEmail(principal.getName());
    	
    	SignupForm form = new SignupForm();
    	form.setName(user.getName());
    	form.setBiography(user.getBiography());
    	
    	
    	try{
    	AutoPopulatingList<UserCourseFormAttribute> userCourseList = new AutoPopulatingList<UserCourseFormAttribute>(new UserCourseFormAttributeFactory());
    	ListIterator<UserCourse> itr = userCourseDao.findByUser(user).listIterator();
    	while(itr.hasNext())
    	{
    		UserCourse tmpUserCourse = itr.next();
    		UserCourseFormAttribute formAttr = new UserCourseFormAttribute();
    		formAttr.setUniversity(tmpUserCourse.getCourse().getSubject().getUniversity().toString());
    		formAttr.setSubject(tmpUserCourse.getCourse().getSubject().toString());
    		formAttr.setCourse(tmpUserCourse.getCourse().toString());
    		formAttr.setGrade( String.valueOf(tmpUserCourse.getGrade()) );
    		formAttr.setTeaching( tmpUserCourse.isTeaching() );
    		userCourseList.add(formAttr);
    	}
		form.setUserCourseList(userCourseList);} catch (Exception e){}
		
    	try{
		AutoPopulatingList<TimeSlot> timeSlotList = new AutoPopulatingList<TimeSlot>(new TimeSlotFactory());
    	ListIterator<TimeSlot> iter = user.getTimeSlots().listIterator();
    	while(iter.hasNext())
    	{
    		timeSlotList.add(iter.next());
    	}
		form.setTimeSlots(timeSlotList);} catch (Exception e){}
		
		return form;
	}
	
	/**
	 * Serve model with universities.
	 * @return the universities from the database.
	 */
	@ModelAttribute("universities")
	public ArrayList<University> getUniversities()
	{
    	
		ArrayList<University> universities = new ArrayList<University>(); 
        universities =  searchService.getUniversities();
		return universities;
	}
	
	/**
	 * Serve model with subjects.
	 * @return the subjects from the database.
	 */
	@ModelAttribute("subjects")
	public ArrayList<Subject> getSubjects()
	{
    	
		ArrayList<Subject> subjects = new ArrayList<Subject>();
    	subjects = searchService.getSubjects();
		return subjects;
	}
	
	/**
	 * Serve model with courses.
	 * @return the courses from the database.
	 */
	@ModelAttribute("courses")
	public ArrayList<Course> getCourses()
	{
    	
		ArrayList<Course> courses = new ArrayList<Course>();
    	courses = searchService.getCourses();
		return courses;
	}
	
	/**
	 * <p>Called when the form is initially displayed</p>
	 * @param principal the logged in principal.
	 * @return the editProfile page.
	 */
	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
    public ModelAndView editProfile(Principal principal){
    	ModelAndView model = new ModelAndView("editProfile");
    	
    	User user = userService.getUserByEmail(principal.getName());
    	model.addObject(user);
    	
        return model;
    }
	
	@RequestMapping(value="/editProfileAppend", method = RequestMethod.GET)
	protected String appendGradeField(@RequestParam Integer fieldId, ModelMap model)
	{
		model.addAttribute("gradeNumber", fieldId);
		return "template/gradeRow";
	}
	
	@RequestMapping(value="/editProfileAttach", method = RequestMethod.GET)
	protected String attachTimeSlotField(@RequestParam Integer fieldId, ModelMap model)
	{	
		model.addAttribute("timeSlotNumber", fieldId);
		return "template/timeSlotRow";
	}
	
	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
    public ModelAndView postProfile(Principal principal, @Validated(SignupForm.SignupValidatorGroup.class) SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
		
		User user = userService.getUserByEmail(principal.getName());
		signupForm.setEmail(user.getEmail());
		
		ModelAndView model;
	
		try {
		    if (!userService.validatePassword(signupForm.getPassword(), signupForm.getPasswordVerify())) {
			    redirectAttributes.addFlashAttribute("infoMessage", "Deine Passwörter stimmen nicht überein");
			    return new ModelAndView("redirect:/editProfile");
			}
		} catch (Exception d) {}
			
	
		if (!result.hasErrors()) {
		    try {
		
			userService.saveFrom(signupForm, user);
			userService.createAndSaveUserCourseFromForm(signupForm, user);
			redirectAttributes.addFlashAttribute("infoMessage", "Du hast erfolgreich dein Profil bearbeitet!");
	
			model = new ModelAndView("redirect:/profile");
		    } catch (InvalidUserException e) {
			model = new ModelAndView("editProfile");
			model.addObject("page_error", e.getMessage());
		    }
		} else {
		    model = new ModelAndView("editProfile");
		    System.out.println("Form hat fehler:\n" + result.toString());
		}
		return model;
	}
	
}
