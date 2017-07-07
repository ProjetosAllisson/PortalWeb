package com.allisson.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.allisson.portal.config.RastreamentoTenantContext;
import com.allisson.portal.model.Tenant;
import com.allisson.portal.model.Usuario;
import com.allisson.portal.repository.Usuarios;

@Controller
@RequestMapping("/{tenantid}/usuarios")
public class UsuariosController {
	
	@Autowired
	private Usuarios usuarios;

	@RequestMapping("/novo")
	public String novo() {
		return "usuario/cadastro-usuario";
	}
	
	@GetMapping
	public ModelAndView pesquisar(){
		ModelAndView mv = new ModelAndView("usuario/pesquisa-usuarios");
		
		for (Usuario usuario : usuarios.findAll()) {
			
			System.out.println(usuario.getEmail());
		}
		String tenant = RastreamentoTenantContext.getTenantId();
		mv.addObject("tenant", tenant);
		mv.addObject("usuarios",usuarios.findAll());
		
		
		return mv;
	}
}
