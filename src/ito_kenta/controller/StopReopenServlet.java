package ito_kenta.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ito_kenta.beans.User;
import ito_kenta.service.UserService;

@WebServlet(urlPatterns = { "/stopReopen" })
public class  StopReopenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		User isStoppedUser = new User();

		isStoppedUser.setId(Integer.parseInt(request.getParameter("id")));
		isStoppedUser.setIsStopped(Integer.parseInt(request.getParameter("isStopped")));

		new UserService().updateIsStopped(isStoppedUser);
		//homeへsendRedirect
		response.sendRedirect("management");
	}
}