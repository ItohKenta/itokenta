package ito_kenta.dao;

import static ito_kenta.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ito_kenta.beans.Branch;
import ito_kenta.exception.SQLRuntimeException;

public class BranchDao {

	// SELECT文の結果が格納された、ResultSet（rs）を、このクラスでしか使えないList(Branch)に、ArrayListで格納する。そういうメソッド。
	private List<Branch> toBranchList(ResultSet rs) throws SQLException {
		List<Branch> ret = new ArrayList<Branch>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");

				Branch branch = new Branch();
				branch.setId(id);
				branch.setName(name);

				ret.add(branch);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	// 支店IDと支店名紐付けのために、branchesテーブルから、支店情報をSELECT文でとってくる
	public List<Branch> getBranches(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM branches ";
			// 上記「branchesからレコードを検索してください」というSQL文を、connection内で送信する＝prepareStatement
			ps = connection.prepareStatement(sql);
			// ResultSetインスタンス（rs）にSELECT文の結果が格納される
			ResultSet rs = ps.executeQuery();
			// Branch型Listの変数branchListに、BranchListに変換（to）したrsに格納されたSELECT文を代入する
			List<Branch> branchList = toBranchList(rs);
			// ※スコープ注意。branchListを返す。
			return branchList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}