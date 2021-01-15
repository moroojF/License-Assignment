package com.example.demo.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.models.*;
import com.example.demo.services.*;

@Controller
public class RelationController {
private final RelationService relationService;
    
    public RelationController(RelationService relationService) {
        this.relationService = relationService;
    }
 
    @RequestMapping("/")
    public String index() {
		return "redirect:/persons/new";
    }
    
    @RequestMapping("/persons/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "/pages/person.jsp";
    }
    
    @RequestMapping("/license/new")
    public String newLicense(@ModelAttribute("license") License license, Model model) {
    	List<Person> unlicensed = relationService.getUnlicensedPeople();
		model.addAttribute("persons", unlicensed);
        return "/pages/license.jsp";
    }
    
    @RequestMapping("/persons/{id}")
    public String show(@ModelAttribute("id") Long id, Model model) {
    	Person person = relationService.getPerson(id);
        model.addAttribute("person", person);

        return "/pages/show.jsp";
    }
    
    @RequestMapping(value="/persons", method=RequestMethod.POST)
    public String createPerson(@Valid @ModelAttribute("person") Person person, BindingResult result) {
		if(result.hasErrors() ) {
			return "/pages/person.jsp";
		}
		relationService.createPerson(person);
		return "redirect:/persons/new";
	}
    
    @RequestMapping(value="/license", method=RequestMethod.POST)
    public String createLicense(@Valid @ModelAttribute("license") License license, BindingResult result) {
        if (result.hasErrors()) {
            return "/pages/license.jsp";
        } else {
        	relationService.createLicense(license);
            return "redirect:/license/new";
        }
    }

}
