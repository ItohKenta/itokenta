package ito_kenta.dao;

import static ito_kenta.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import ito_kenta.beans.UserComment;
import ito_kenta.exception.SQLRuntimeException;

//home.jspにメッセージを表示するために、UserMessageビューから値を取得するためのDAOを作成。
public class UserCommentDao {

	public List<UserComment> getUserComment(Connection connection) {

		PreparedStatement ps = null;
		try {
			// user_commentからレコードを取得するSELECT文
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_comments ");
			sql.append("ORDER BY insert_date ");

			ps = connection.prepareStatement(sql.toString());

			// SQL文を実行するコード。SELECT文の場合は引数にSQL文を渡し、executeQueryメソッドを実行する
			ResultSet rs = ps.executeQuery();
			List<UserComment> ret = toUserCommentList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<UserComment> toUserCommentList(ResultSet rs) throws SQLException {

		List<UserComment> ret = new ArrayList<UserComment>();
		try {
			while (rs.next()) {
				String name = rs.getString("name");
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String text = rs.getString("text");
				int messageId = rs.getInt("message_id");
				Timestamp insertDate = rs.getTimestamp("insert_date");

				UserComment comment = new UserComment();
				comment.setName(name);
				comment.setId(id);
				comment.setLoginId(loginId);
				comment.setText(text);
				comment.setMessageId(messageId);
				comment.setInsertDate(insertDate);

				ret.add(comment);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

}