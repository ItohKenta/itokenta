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

import ito_kenta.beans.Message;
import ito_kenta.beans.User;
import ito_kenta.service.MessageService;

@WebServlet(urlPatterns = { "/newmessage"})

//新規投稿のクラス
public class NewMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.getRequestDispatcher("newMessage.jsp").forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		//request領域からgetしたsessionを、HttpSessionインタフェースのsessionに代入する
		HttpSession session = request.getSession();
		//List<String>型変数messagesに、ArrayList<String>インスタンスを生成する。
		List<String> messages = new ArrayList<String>();


			//Userビーンズのuserに、session領域からloginUserを格納する
			User user = (User) session.getAttribute("loginUser");
			//Messageビーンズ型の変数messageを作り、Messegeインスタンスを生成
			Message message = new Message();
			//JSPで入力したtextをrequest領域からgetParameterする。そして、ビーンズのmessegeにセット。
			message.setText(request.getParameter("text"));
			message.setSubject(request.getParameter("subject"));
			message.setCategory(request.getParameter("category"));
			//userビーンズから取得したIdを、messageにセット。
			message.setBranchId(user.getBranchId());
			message.setPositionId(user.getPositionId());
			message.setUserId(user.getId());

		//メッセージの入力がされていれば、登録ボタンを押すと、messageServiceへ送り、ホームへ戻るメソッド
		if (isValid(request, messages) == true){
			//生成したMessageServiceに、request領域のmessageを登録
			new MessageService().register(message);
			//homeへsendRedirect
			response.sendRedirect("./");
		} else {
			session.setAttribute("errorMessages", messages);

			request.setAttribute("message", message);
			request.getRequestDispatcher("newMessage.jsp").forward(request, response);
		}
	}

	private boolean isValid(HttpServletRequest request, List<String>messages) {
		String text = request.getParameter("text");
		String subject = request.getParameter("subject");
		String category = request.getParameter("category");


		if(StringUtils.isEmpty(subject) == true){
			messages.add("件名を入力してください");
		}
		if(30 < subject.length()) {
			messages.add("件名は30文字以下で入力してください");
		}
		if(StringUtils.isEmpty(category) == true){
			messages.add("カテゴリを入力してください");
		}
		if(10 < category.length()) {
			messages.add("カテゴリは10文字以下で入力してください");
		}
		if(StringUtils.isEmpty(text) == true){
			messages.add("本文を入力してください");
		}
		if(1000 < text.length()) {
			messages.add("1000文字以下で入力してください");
		}
		if(messages.size() == 0){
			return true;
		} else {
			return false;
		}
	}

}
