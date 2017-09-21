package ito_kenta.dao;

import static ito_kenta.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ito_kenta.beans.Position;
import ito_kenta.exception.SQLRuntimeException;

public class PositionDao {

	// SELECT文の結果が格納された、ResultSet（rs）を、このクラスでしか使えないList(Branch)に、ArrayListで格納する。そういうメソッド。
	private List<Position> toPositionList(ResultSet rs) throws SQLException {
		List<Position> ret = new ArrayList<Position>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Position position = new Position();
				position.setId(id);
				position.setName(name);

				ret.add(position);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	// 役職IDと役職名紐付けのために、positionsテーブルから、役職情報をSELECT文でとってくる
	public List<Position> getPositions(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM positions ";
			// 上記「positionsからレコードを検索してください」というSQL文を、connection内で送信する＝prepareStatement
			ps = connection.prepareStatement(sql);
			// ResultSetインスタンス（rs）にSELECT文の結果が格納される
			ResultSet rs = ps.executeQuery();
			// Branch型Listの変数branchListに、BranchListに変換（to）したrsに格納されたSELECT文を代入する
			List<Position> positionList = toPositionList(rs);
			// ※スコープ注意。positionListを返す。
			return positionList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}