package com.allisson.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class DashBoardController {
	
	@GetMapping
	public ModelAndView dashboard() {
		ModelAndView mv = new ModelAndView("/dashboard/dashboard");
		return mv;
	}

}
