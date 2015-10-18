package org.sample.controller;

import javax.validation.Valid;

import org.sample.controller.exceptions.InvalidUserException;
import org.sample.controller.pojos.LoginForm;
import org.sample.controller.pojos.SignupForm;
import org.sample.controller.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {

    @Autowired
    SampleService sampleService;


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
    	ModelAndView model = new ModelAndView("index");
    	model.addObject("loginForm", new LoginForm());
    	
        return model;
    }
    
    @RequestMapping(value = "/signUp", method = RequestMethod.GET)
    public ModelAndView signUp() {
    	ModelAndView model = new ModelAndView("signUp");
    	model.addObject("signupForm", new SignupForm());
    	
        return model;
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;
    	if (!result.hasErrors()) {
            try {
            	// sampleService.saveFrom(signupForm);
            	model = new ModelAndView(new RedirectView("profile"));
            } catch (InvalidUserException e) {
            	model = new ModelAndView("signUp");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("signUp");
        }   	
    	return model;
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public ModelAndView validate(@Valid LoginForm loginForm, BindingResult result, RedirectAttributes redirectAttributes) {
    	ModelAndView model;
    	if (!result.hasErrors()) {
            try {
            	// sampleService.saveFrom(loginForm);
            	model = new ModelAndView(new RedirectView("profile"));
            } catch (InvalidUserException e) {
            	model = new ModelAndView("index");
            	model.addObject("page_error", e.getMessage());
            }
        } else {
        	model = new ModelAndView("index");
        }   	
    	return model;
    }
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
    	ModelAndView model = new ModelAndView("profile");
    	
        return model;
    }
    
    @RequestMapping(value = "/search")
    public ModelAndView search() {
    	ModelAndView model = new ModelAndView("search");
    	
        return model;
    }
    
    @RequestMapping(value = "/security-error", method = RequestMethod.GET)
    public String securityError(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("page_error", "You do have have permission to do that!");
        return "redirect:/";
    }

}