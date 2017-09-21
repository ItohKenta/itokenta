package ito_kenta.dao;

import static ito_kenta.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ito_kenta.beans.User;
import ito_kenta.exception.NoRowsUpdatedRuntimeException;
import ito_kenta.exception.SQLRuntimeException;

//データベースのユーザテーブルのDAO（検索,追加,更新,削除を担当する）クラス
public class UserDao {

	// ユーザー新規登録用。ユーザーテーブルへレコードを追加するINSERT文のメソッドを作る。
	public void insert(Connection connection, User user) {

		// PreparedStatement＝プログラムとDB間のSQLの送信を行うインタフェース
		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO users ( ");
			sql.append("  login_id");
			sql.append(", password");
			sql.append(", name");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", is_stopped");
			sql.append(") VALUES (");
			sql.append("  ?"); // login_id
			sql.append(", ?"); // password
			sql.append(", ?"); // name
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // position_id
			sql.append(", ?"); // is_stopped
								// ユーザー登録は、生きたアカウントを登録するのをデフォルト値（１）としておく
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, user.getLoginId());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setInt(4, user.getBranchId());
			ps.setInt(5, user.getPositionId());
			ps.setInt(6, user.getIsStopped());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// ログイン画面用。
	public User getUser(Connection connection, String loginId, String password) {
		// PreparedStatement＝プログラムとDB間のSQLの送信を行うインタフェース
		PreparedStatement ps = null;
		// SQLにあるloginIdとpasswordの値をSELECT文にて取得
		try {
			// 変数sqlに、usersテーブルから、login_idとpasswordの項目をとってくる
			String sql = "SELECT * FROM users WHERE login_id = ? AND password = ? AND is_stopped = 1";
			// connection内のpripareStatementのsqlをpsに代入
			ps = connection.prepareStatement(sql);
			// psの項目、1つ目をloginId、2つめをpasswordとする。
			ps.setString(1, loginId);
			ps.setString(2, password);

			// ResultSetインスタンスrsにSELECT文の結果が格納される（実行する）
			ResultSet rs = ps.executeQuery();

			List<User> userList = toUserList(rs);

			if (userList.isEmpty() == true) {
				return null;
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
			//} else if (userList.isStopped == "0");
			//	return null;
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// SELECT文の結果が格納された、ResultSet（rs）を、このクラスでしか使えないList(User)に、ArrayListで格納する。そういうメソッド。
	private List<User> toUserList(ResultSet rs) throws SQLException {
		List<User> ret = new ArrayList<User>();
		try {
			while (rs.next()) {
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				int branchId = rs.getInt("branch_id");
				int positionId = rs.getInt("position_id");
				int isStopped = rs.getInt("is_stopped");

				User user = new User();
				user.setId(id);
				user.setLoginId(loginId);
				user.setPassword(password);
				user.setName(name);
				user.setBranchId(branchId);
				user.setPositionId(positionId);
				user.setIsStopped(isStopped);

				ret.add(user);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	// ユーザー管理画面用に、ユーザーテーブルから、ユーザー情報をSELECT文でとってくる
	public List<User> getUsers(Connection connection) {

		PreparedStatement ps = null;
		try {
			String sql = "SELECT * FROM users ";
			// 上記「usersからレコードを検索してください」というSQL文を、connection内で送信する＝prepareStatement
			ps = connection.prepareStatement(sql);
			// ResultSetインスタンス（rs）にSELECT文の結果(usersテーブルの全件)が格納される
			ResultSet rs = ps.executeQuery();
			// User型Listの変数userListに、UserListに変換（to）したrsに格納されたSELECT文を代入する
			List<User> userList = toUserList(rs);
			// userListにはbeansの値が入っている
			// ※スコープ注意。userListを返す。
			return userList;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// ユーザー編集画面用に、usersテーブルから、情報を取得してくるSELECT
	public User getUser(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			// 変数sqlに、SELECT文を代入
			String sql = "SELECT * FROM users WHERE id = ?";

			ps = connection.prepareStatement(sql);
			// usersテーブルから1つ目の項目int型?に、beansのidから引っ張ってきた値をpsにセットする
			ps.setInt(1, id);

			// idに値が入ったpsを、rsに格納（実行）する。
			ResultSet rs = ps.executeQuery();
			// idに該当するuser情報を、List<User>型のuserLisに、代入する
			List<User> userList = toUserList(rs);
			// userListが空ということは、idと該当するuser情報がなかったということ。
			if (userList.isEmpty() == true) {
				return null;
				// 2 <=userList.seze() ということは、2つ以上のuserListがあるということ。
			} else if (2 <= userList.size()) {
				throw new IllegalStateException("2 <= userList.size()");
				// 上記いずれでもない場合は、リストの(0)つまり、1つめの配列を、userListに値を返す
			} else {
				return userList.get(0);
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// ユーザー編集画面用。DBのユーザー情報を変更するメソッド
	public void update(Connection connection, User editUser) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" login_id = ?");
			if(!(editUser.getPassword() == null || editUser.getPassword().length() == 0 )){
			sql.append(",password = ?");
			sql.append(",name = ?");
			sql.append(",branch_id = ?");
			sql.append(",position_id = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");
			}else{
				sql.append(",name = ?");
				sql.append(",branch_id = ?");
				sql.append(",position_id = ?");
				sql.append(" WHERE");
				sql.append(" id = ?");
			}


			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, editUser.getLoginId());
			if(!(editUser.getPassword() == null || editUser.getPassword().length() == 0 )){
			ps.setString(2, editUser.getPassword());
			ps.setString(3, editUser.getName());
			ps.setInt(4, editUser.getBranchId());
			ps.setInt(5, editUser.getPositionId());
			ps.setInt(6, editUser.getId());
			}else{
				ps.setString(2, editUser.getName());
				ps.setInt(3, editUser.getBranchId());
				ps.setInt(4, editUser.getPositionId());
				ps.setInt(5, editUser.getId());
			}
			System.out.println(ps.toString());
			int count = ps.executeUpdate();

			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	//停止復活処理用。isStoppedを、変更するUPDATE文
	public void updateIsStopped(Connection connection, User isStoppedUser) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE users SET");
			sql.append(" is_stopped = ?");
			sql.append(" WHERE");
			sql.append(" id = ?");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, isStoppedUser.getIsStopped());
			ps.setInt(2, isStoppedUser.getId());

			int count = ps.executeUpdate();

			if(count == 0){
				throw new NoRowsUpdatedRuntimeException();
			}
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}