package com.log.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class CorsFilter implements Filter {
 
    /**
     * Default constructor.
     */
    public CorsFilter() {}
 
    /**
     * @see Filter#destroy()
     */
    public void destroy() {}
 
    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
 
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String dominio = request.getHeader("Origin");
//      System.out.println("CORSFilter Request: " + request.getMethod()+ " - "+ dominio);

        resp.addHeader("Access-Control-Allow-Origin", dominio);
        resp.addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PATCH, PUT, POST, DELETE");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        resp.addHeader("Access-Control-Max-Age", "3600");
        
 
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
 
        chain.doFilter(request, resp);
    }
 
    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException { }
 
}
