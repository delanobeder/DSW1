package br.ufscar.dc.dsw.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = { "*.jsp" })
public class JSPFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		
		String path = req.getRequestURI();
		String contextPath = req.getServletContext().getContextPath() + "/";
		
		if (path.equals(contextPath)) {
			// index.jsp is "automatically" called (because is the welcome page)
			// In this case, this filter just continue chain
		    chain.doFilter(request, response); // Just continue chain.
		} else {
			// otherwise deny acess to any jsp (typing in browser)
			req.getRequestDispatcher("/denied.jsp").forward(request, response);
		}
	}
}