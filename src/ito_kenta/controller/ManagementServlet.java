package ito_kenta.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ito_kenta.beans.Branch;
import ito_kenta.beans.Position;
import ito_kenta.beans.User;
import ito_kenta.service.BranchService;
import ito_kenta.service.PositionService;
import ito_kenta.service.UserService;

@WebServlet(urlPatterns = { "/management" })
// ユーザー情報の変更機能（トップ画面の設定のリンク先）のクラス
public class ManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// UserServiceから取得した、Userの配列をリクエスト領域に入れる
		List<User> users = new UserService().getUsers();
		request.setAttribute("users", users);

		// BranchServiceから取得した、Branchの配列をリクエスト領域に入れる
		List<Branch> branches = new BranchService().getBranches();
		request.setAttribute("branches", branches);

		// PositionServiceから取得した、Positionの配列をリクエスト領域に入れる
		List<Position> positions = new PositionService().getPositions();
		request.setAttribute("positions", positions);

		request.getRequestDispatcher("/management.jsp").forward(request, response);
	}
}
