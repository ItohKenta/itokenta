package ito_kenta.dao;

import static ito_kenta.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ito_kenta.beans.Comment;
import ito_kenta.exception.NoRowsUpdatedRuntimeException;
import ito_kenta.exception.SQLRuntimeException;

public class CommentDao {

	// レコードの追加
	public void insert(Connection connection, Comment comment) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO comments ( ");
			sql.append(" message_id");
			sql.append(", text");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", user_id");
			sql.append(", insert_date");
			sql.append(") VALUES (");
			sql.append(" ?"); // message_id
			sql.append(", ?"); // text
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // position_id
			sql.append(", ?"); // user_id
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setInt(1, comment.getMessageId());
			ps.setString(2, comment.getText());
			ps.setInt(3, comment.getBranchId());
			ps.setInt(4, comment.getPositionId());
			ps.setInt(5, comment.getUserId());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	// コメントの削除メソッド
	public void deleteComment(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM comments WHERE id = ?";
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			int count = ps.executeUpdate();
			if (count == 0) {
				throw new NoRowsUpdatedRuntimeException();
			}
		}catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
}