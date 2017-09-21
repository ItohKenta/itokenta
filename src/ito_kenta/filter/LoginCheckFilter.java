package ito_kenta.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ito_kenta.beans.User;

@WebFilter("/*")
public class LoginCheckFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {


		String path =((HttpServletRequest) request).getServletPath();


        HttpSession session = ((HttpServletRequest)request).getSession();
        User user = (User) session.getAttribute("loginUser");


		if(!(path.equals("/login"))){
	        if(user != null){
	            chain.doFilter(request, response);

	        }else{
	        	session.setAttribute("errorMessages",  "ログインしてください");
	        	((HttpServletResponse) response).sendRedirect("./login");
	        }

		}else{
			chain.doFilter(request, response);
		}



	}


	public void init(FilterConfig config) throws ServletException{}
    public void destroy(){}
}
