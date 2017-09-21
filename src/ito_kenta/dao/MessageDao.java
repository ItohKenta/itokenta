package ito_kenta.dao;

import static ito_kenta.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import ito_kenta.beans.Message;
import ito_kenta.exception.NoRowsUpdatedRuntimeException;
import ito_kenta.exception.SQLRuntimeException;

public class MessageDao {

	// レコードの追加
	public void insert(Connection connection, Message message) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO messages ( ");
			sql.append(" subject");
			sql.append(", text");
			sql.append(", category");
			sql.append(", branch_id");
			sql.append(", position_id");
			sql.append(", user_id");
			sql.append(", insert_date");
			sql.append(") VALUES (");
			sql.append(" ?"); // subject
			sql.append(", ?"); // text
			sql.append(", ?"); // category
			sql.append(", ?"); // branch_id
			sql.append(", ?"); // position_id
			sql.append(", ?"); // user_id
			sql.append(", CURRENT_TIMESTAMP"); // insert_date
			sql.append(")");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, message.getSubject());
			ps.setString(2, message.getText());
			ps.setString(3, message.getCategory());
			ps.setInt(4, message.getBranchId());
			ps.setInt(5, message.getPositionId());
			ps.setInt(6, message.getUserId());

			System.out.println(ps.toString());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}


	// コメントの削除メソッド
	public void deleteMessage(Connection connection, int id) {

		PreparedStatement ps = null;
		try {
			String sql = "DELETE FROM messages WHERE id = ?";
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
