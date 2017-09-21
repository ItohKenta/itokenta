package ito_kenta.dao;

import static ito_kenta.utils.CloseableUtil.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ito_kenta.beans.UserMessage;
import ito_kenta.exception.SQLRuntimeException;

//home.jspにメッセージを表示するために、UserMessageビューから値を取得するためのDAOを作成。
public class UserMessageDao {

	public List<UserMessage> getUserMessages(Connection connection, String fromdate, String todate, String categorySelect) {


		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages ");
			sql.append("WHERE insert_date BETWEEN ? AND ?");

			if(!(categorySelect== null || categorySelect.isEmpty())){
				sql.append(" AND category = ?");
			}
			sql.append(" ORDER BY insert_date DESC " );

			ps = connection.prepareStatement(sql.toString());


			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String strDate = sdf.format(date);

	        if(fromdate == null || fromdate.isEmpty()){
			ps.setString(1, "2017-09-01 00:00:00");
	        }else{
	        	ps.setString(1,fromdate + " 00:00:00");
	        }
	        if(todate == null || todate.isEmpty()){
			ps.setString(2, strDate);
	        }else{
	        	ps.setString(2,todate +" 23:59:59");
	        }

			if(!(categorySelect== null || categorySelect.isEmpty())){
				ps.setString(3, categorySelect);
			}

			System.out.println(ps);

			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	/*
	public List<UserMessage> getUserMessageCategory(Connection connection, String fromdate, String todate) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM users_messages ");
			sql.append("WHERE insert_date BETWEEN ? AND ?");
			//sql.append(" ORDER BY insert_date ");

			ps = connection.prepareStatement(sql.toString());

			ps.setString(1, fromdate);
			ps.setString(2, todate);
			//ps.setString(3, categorySelect);


			ResultSet rs = ps.executeQuery();
			List<UserMessage> ret = toUserMessageList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}
	*/

	public List<String> getMessageCategories(Connection connection) {

		PreparedStatement ps = null;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT DISTINCT category FROM users_messages");
			//sql.append(" ORDER BY insert_date ");

			ps = connection.prepareStatement(sql.toString());

			System.out.println(ps.toString());
			ResultSet rs = ps.executeQuery();
			List<String> ret = toStringList(rs);
			return ret;
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} finally {
			close(ps);
		}
	}

	private List<String> toStringList(ResultSet rs) throws SQLException {

		List<String> ret = new ArrayList<String>();
		try {
			while (rs.next()) {

				String category = rs.getString("category");

				ret.add(category);
			}
			return ret;
		} finally {
			close(rs);
		}
	}

	private List<UserMessage> toUserMessageList(ResultSet rs) throws SQLException {

		List<UserMessage> ret = new ArrayList<UserMessage>();
		try {
			while (rs.next()) {
				String name = rs.getString("name");
				int id = rs.getInt("id");
				String loginId = rs.getString("login_id");
				String subject = rs.getString("subject");
				String text = rs.getString("text");
				String category = rs.getString("category");
				Timestamp insertDate = rs.getTimestamp("insert_date");


				UserMessage message = new UserMessage();
				message.setName(name);
				message.setId(id);
				message.setLoginId(loginId);
				message.setSubject(subject);
				message.setText(text);
				message.setCategory(category);
				message.setInsertDate(insertDate);

				ret.add(message);
			}
			return ret;
		} finally {
			close(rs);
		}
	}



}
