package ru.javawebinar.topjava.web;

import javax.servlet.*;
import java.io.IOException;

//https://www.baeldung.com/tomcat-utf-8
public class CharacterSetFilter implements Filter {
    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }
}
