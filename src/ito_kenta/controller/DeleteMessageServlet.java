package ito_kenta.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ito_kenta.service.MessageService;


@WebServlet(urlPatterns = { "/deleteMessage" })
public class  DeleteMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println(request.getParameter("id"));
		int id =(Integer.parseInt(request.getParameter("id")));

		new MessageService().deleteMessage(id);
		//homeへsendRedirect
		response.sendRedirect("./");
	}
}