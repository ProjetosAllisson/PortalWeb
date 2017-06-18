package com.allisson.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.allisson.portal.config.RastreamentoTenantContext;

public class RastreamentoMultiTenantInterceptor extends HandlerInterceptorAdapter{
	
	private static final String TENANT_HEADER_NAME = "X-TENANT-ID";
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String tenantId = request.getHeader(TENANT_HEADER_NAME);
		RastreamentoTenantContext.setTenantId(tenantId);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		RastreamentoTenantContext.setTenantId(null);
	}

}
