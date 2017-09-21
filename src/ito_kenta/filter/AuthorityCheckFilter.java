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

@WebFilter({"/setting", "/signup", "/stopReopen", "/management"} )
public class AuthorityCheckFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();
		User user = (User) session.getAttribute("loginUser");
		if(user != null && user.getPositionId() != 1){

			session.setAttribute("errorMessages", "このページには入れません");
			((HttpServletResponse) response).sendRedirect("./");
		}else{
			chain.doFilter(request, response);
		}
	}


    public void init(FilterConfig config) throws ServletException{}
    public void destroy(){}
}

