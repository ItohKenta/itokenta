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

import ito_kenta.beans.User;
import ito_kenta.service.LoginService;

@WebServlet(urlPatterns = { "/login" })

// ログイン機能を実装するクラス
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// jspに、request領域においたものをもっていく指示をする。
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// login.jspの<form>タグactionで、このservlet(/login)に飛ばされた値(パラメータ)が、request領域にあるので、getする。

		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");

		// LoginServiceクラスの変数loginServiceに、LoginServiceインスタンスを生成し、変数loginServiceに代入
		LoginService loginService = new LoginService();
		User user = loginService.login(loginId, password);

		// ユーザー情報をセッションスコープに保存
		HttpSession session = request.getSession();
		//DAOでチェックされた戻り値がnullでなければ。
			// loginUserというハコに、userを入れる。session(データを長く保持する領域)に置く。

		if (user != null) {
			session.setAttribute("loginUser", user);
			// "./"のあるURLに処理を飛ばす（あとで、JSPで<a>タグ./が、どこにリンクするかを確認してみる）
			response.sendRedirect("./");

		} else {
			List<String> messages = new ArrayList<String>();
			messages.add("ログインに失敗しました");
			User missuser = new User();
			missuser.setLoginId(request.getParameter("loginId"));
			request.setAttribute("missuser", missuser);
			request.setAttribute("errorMessages", messages);
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
