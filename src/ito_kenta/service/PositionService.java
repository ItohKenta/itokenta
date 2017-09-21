package ito_kenta.service;

import static ito_kenta.utils.CloseableUtil.*;
import static ito_kenta.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ito_kenta.beans.Position;
import ito_kenta.dao.PositionDao;

public class PositionService {

	// 役職IDと役職名の紐付け用。Positionsから値をgetするために、引数を指定する必要はない。（すべての情報がほしいので）
	public List<Position> getPositions() {

		// try文の前で、connectionをnullにする。
		Connection connection = null;
		try {
			// java.sqlパッケージからConnectionインタフェースを取得する
			connection = getConnection();

			// BranchDaoクラスのgetBranchesメソッドのconnectionから、Branch型のリストを生成する（変数名branch）
			PositionDao positionDao = new PositionDao();
			List<Position> position = positionDao.getPositions(connection);

			commit(connection);
			// List<Branch>型のbranchの値を、戻り値List<Branch>に返す
			return position;
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