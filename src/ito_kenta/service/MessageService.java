package ito_kenta.service;

import static ito_kenta.utils.CloseableUtil.*;
import static ito_kenta.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ito_kenta.beans.Message;
import ito_kenta.beans.UserMessage;
import ito_kenta.dao.MessageDao;
import ito_kenta.dao.UserMessageDao;

//メッセージDAOと繋ぐ橋渡しクラス
public class MessageService {

	// メッセージを登録するメソッド
	public void register(Message message) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.insert(connection, message);

			commit(connection);
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


	// メッセージを削除するメソッド
	public void deleteMessage(int id) {

		Connection connection = null;
		try {
			connection = getConnection();

			MessageDao messageDao = new MessageDao();
			messageDao.deleteMessage(connection, id);
			commit(connection);
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



	// メッセージをhome.jspに表示するために、userMessageDAOからメッセージを取得するためのコードを入力
	public List<UserMessage> getMessage(String fromdate, String todate, String categorySelect) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessages(connection, fromdate, todate, categorySelect);

			commit(connection);

			return ret;
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
	/*
	// メッセージをhome.jspに表示するために、userMessageDAOからメッセージを取得するためのコードを入力
	public List<UserMessage> getUserMessageCategory(String fromdate, String todate) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<UserMessage> ret = messageDao.getUserMessageCategory(connection, fromdate, todate);

			commit(connection);

			return ret;
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
	*/


	public List<String> getMessageCategories() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserMessageDao messageDao = new UserMessageDao();
			List<String> ret = messageDao.getMessageCategories(connection);

			commit(connection);

			return ret;
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
