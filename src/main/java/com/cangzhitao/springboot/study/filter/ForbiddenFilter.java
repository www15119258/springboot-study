package com.cangzhitao.springboot.study.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class ForbiddenFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setStatus(403);
		httpResponse.setContentType("application/json;charset=UTF-8");
		httpResponse.setCharacterEncoding("UTF-8");
		PrintWriter pw = new PrintWriter(httpResponse.getOutputStream());
		pw.write("禁止访问");
		pw.flush();
		pw.close();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// default implements
	}

	@Override
	public void destroy() {
		// default implements
	}

}
