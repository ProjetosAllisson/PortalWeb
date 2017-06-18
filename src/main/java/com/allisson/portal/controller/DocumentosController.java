package com.allisson.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/rastreamento")
public class DocumentosController {

	@GetMapping("/documentos")
	public ModelAndView pesquisar(){
		ModelAndView mv = new ModelAndView("/documento/pesquisa-documentos");
		return mv;
	}
	
	
	@GetMapping("/transito")
	public ModelAndView notasEmTransito(){
		ModelAndView mv = new ModelAndView("/documento/pesquisa-rastreamento");
		return mv;
	}
	
	
}
