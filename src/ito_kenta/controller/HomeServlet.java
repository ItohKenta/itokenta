package ito_kenta.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import ito_kenta.beans.Comment;
import ito_kenta.beans.User;
import ito_kenta.beans.UserComment;
import ito_kenta.beans.UserMessage;
import ito_kenta.service.CommentService;
import ito_kenta.service.MessageService;

@WebServlet(urlPatterns = { "/index.jsp" })
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// メッセージを取得し、リクエストにメッセージをセットするコード
		String fromdate;
		fromdate = request.getParameter("fromdate");
		String todate;
		todate = request.getParameter("todate");
		request.setAttribute("fromdate", fromdate);
		request.setAttribute("todate", todate);

		System.out.println(todate);
		String categorySelect;
		categorySelect = request.getParameter("categorySelect");
		List<UserMessage> messages = new MessageService().getMessage(fromdate, todate, categorySelect);
		request.setAttribute("messages", messages);

		List<String>messageCategories = new MessageService().getMessageCategories();
		List<UserComment> comments = new CommentService().getComment();
		request.setAttribute("messageCategories", messageCategories);
		request.setAttribute("comments", comments);
		request.setAttribute("categorySelect", categorySelect);
		request.getRequestDispatcher("/home.jsp").forward(request, response);
	}


	//コメントをdoPostするメソッド
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		HttpSession session = request.getSession();
		List<String> messages = new ArrayList<String>();

		User user = (User) session.getAttribute("loginUser");
		Comment comment = new Comment();
		comment.setMessageId(Integer.parseInt(request.getParameter("messageId")));
		comment.setText(request.getParameter("text"));
		comment.setBranchId(user.getBranchId());
		comment.setPositionId(user.getPositionId());
		comment.setUserId(user.getId());

		if (isValid(request, messages) == true){
			new CommentService().register(comment);
			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);
			session.setAttribute("comment", comment);
			response.sendRedirect("./");
		}
	}

	private boolean isValid(HttpServletRequest request, List<String>messages) {
		String text = request.getParameter("text");

		if(500 < text.length()) {
			messages.add("500文字以下で入力してください");
		}
		if(StringUtils.isEmpty(text) == true || 0 == text.length()){
			messages.add("コメントは未入力のまま登録できません");
		}
		if(messages.size() == 0){
			return true;
		} else {
			return false;
		}
	}
}