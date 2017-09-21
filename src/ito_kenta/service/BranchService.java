package ito_kenta.service;

import static ito_kenta.utils.CloseableUtil.*;
import static ito_kenta.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ito_kenta.beans.Branch;
import ito_kenta.dao.BranchDao;

public class BranchService {

	// 支店IDと支店名の紐付け用。Branchesから値をgetするために、引数を指定する必要はない。（すべての情報がほしいので）
	public List<Branch> getBranches() {

		// try文の前で、connectionをnullにする。
		Connection connection = null;
		try {
			// java.sqlパッケージからConnectionインタフェースを取得する
			connection = getConnection();

			// BranchDaoクラスのgetBranchesメソッドのconnectionから、Branch型のリストを生成する（変数名branch）
			BranchDao branchDao = new BranchDao();
			List<Branch> branch = branchDao.getBranches(connection);

			commit(connection);
			// List<Branch>型のbranchの値を、戻り値List<Branch>に返す
			return branch;
		} catch (RuntimeException e) {
			rollback(connection);
			throw e;
		} catch (Error e) {
			rollback(connection);
			throw e;
		} finally {
			close(connection);
		}
	}
}