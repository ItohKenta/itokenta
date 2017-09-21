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

import ito_kenta.beans.Branch;
import ito_kenta.beans.Position;
import ito_kenta.beans.User;
import ito_kenta.service.BranchService;
import ito_kenta.service.PositionService;
import ito_kenta.service.UserService;

//ユーザーの登録機能の実装
@WebServlet(urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// HttpServletインタフェースにリソースを呼ぶ
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// BranchServiceから取得した、Branchの配列をリクエスト領域に入れる
		List<Branch> branches = new BranchService().getBranches();
		request.setAttribute("branches", branches);

		// PositionServiceから取得した、Positionの配列をリクエスト領域に入れる
		List<Position> positions = new PositionService().getPositions();
		request.setAttribute("positions", positions);

		request.getRequestDispatcher("/signup.jsp").forward(request, response);

	}

	// doPostメソッド。SignUp.jspで入力した内容を、更新する。
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		List<Position> positions = new PositionService().getPositions();
		List<Branch> branches = new BranchService().getBranches();
		List<String> messages = new ArrayList<String>();
		HttpSession session = request.getSession();

		User user = new User();
		user.setLoginId(request.getParameter("loginId"));
		user.setPassword(request.getParameter("password"));
		user.setName(request.getParameter("name"));
		user.setBranchId(Integer.parseInt(request.getParameter("branchId")));
		user.setPositionId(Integer.parseInt(request.getParameter("positionId")));
		user.setIsStopped(1);

		if (isValid(request, messages) == true) {
			new UserService().register(user);
			response.sendRedirect("management");
		} else {
			session.setAttribute("errorMessages", messages);
			request.setAttribute("user", user);
			request.setAttribute("positions", positions);
			request.setAttribute("branches", branches);
			request.getRequestDispatcher("signup.jsp").forward(request, response);
		}
	}

	// このクラスのみで使用するbuulean型には、tureかfalseの値が戻ってくる。（上記処理をするための準備）
	private boolean isValid(HttpServletRequest request, List<String> messages) {
		// String型の変数loginIdを、このメソッド用に、宣言。宣言した変数に、beansのrequest領域にあるloginIdの値をgetして代入。
		String loginId = request.getParameter("loginId");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String branchId = request.getParameter("branchId");
		String positionId = request.getParameter("positionId");
		String checkPassword = request.getParameter("checkPassword");

		//System.out.println(checkPassword);
		// loginIDが空なら、ログインIDを入力してくださいという文を、messagesに加える。
		if (StringUtils.isEmpty(loginId) == true) {
			messages.add("ログインIDを入力してください");
		}else if (loginId.length() > 20){
			messages.add("ログインIDは20文字以下です");
		}else if (loginId.length() < 6){
			messages.add("ログインIDは6文字以上です");
		}else if (!loginId.matches("^[0-9a-zA-Z]+$")){
			messages.add("ログインIDは半角英数字です");
		}

		if (StringUtils.isEmpty(password) == true) {
			messages.add("パスワードを入力してください");
		}else if (password.length() > 20){
			messages.add("パスワードは20文字以下です");
		}else if (password.length() < 6){
			messages.add("パスワードは6文字以上です");
		}else if (!(password.matches("^[0-9a-zA-Z]+$")|password.matches("_-@+\\*;:#$%&"))){
			messages.add("パスワードは記号を含む半角文字です");
		}else if (StringUtils.isEmpty(checkPassword) == true) {
			messages.add("確認用パスワードを入力してください");
		}else if (!(checkPassword.equals(password))){
			messages.add("確認用パスワードが一致しません");
		}
		if (StringUtils.isEmpty(name) == true) {
			messages.add("氏名を入力してください");
		}else if (name.length() > 10){
			messages.add("氏名は10文字以下です");
		}
		if (StringUtils.isEmpty(branchId) == true) {
			messages.add("支店名IDを入力してください");
		}
		if (branchId.equals("1") && positionId.equals("3")) {
			messages.add("「支店名」が本社の場合、「部署・役職」に支店長を選べません");
		}
		if (branchId.equals("2") && positionId.equals("1")) {
			messages.add("「支店名」が支店Aの場合、「部署・役職」に総務人事担当を選べません");
		}
		if (branchId.equals("2") && positionId.equals("2")) {
			messages.add("「支店名」が支店Aの場合、「部署・役職」に情報管理担当を選べません");
		}
		if (branchId.equals("3") && positionId.equals("1")) {
			messages.add("「支店名」が支店Bの場合、「部署・役職」に総務人事担当を選べません");
		}
		if (branchId.equals("3") && positionId.equals("2")) {
			messages.add("「支店名」が支店Bの場合、「部署・役職」に情報管理担当を選べません");
		}
		if (branchId.equals("4") && positionId.equals("1")) {
			messages.add("「支店名」が支店Cの場合、「部署・役職」に総務人事担当を選べません");
		}
		if (branchId.equals("4") && positionId.equals("2")) {
			messages.add("「支店名」が支店Cの場合、「部署・役職」に情報管理担当を選べません");
		}

		if (StringUtils.isEmpty(positionId) == true) {
			messages.add("部署･役職IDを入力してください");
		}

		// messsagesが0であれば、未入力エラーがないということなので、boolean型isValidにtureという値を返す
		if (messages.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}