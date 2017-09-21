package ito_kenta.service;

import static ito_kenta.utils.CloseableUtil.*;
import static ito_kenta.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ito_kenta.beans.Comment;
import ito_kenta.beans.UserComment;
import ito_kenta.dao.CommentDao;
import ito_kenta.dao.UserCommentDao;

//コメントDAOと繋ぐ橋渡しクラス
public class CommentService {

	// コメントを登録するメソッド
	public void register(Comment comment) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.insert(connection, comment);

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


	// コメントを削除するメソッド
	public void delete(int id) {

		Connection connection = null;
		try {
			connection = getConnection();

			CommentDao commentDao = new CommentDao();
			commentDao.deleteComment(connection, id);
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


	// メッセージをhome.jspに表示するために、userCommentDAOからメッセージを取得するためのコードを入力
	public List<UserComment> getComment() {

		Connection connection = null;
		try {
			connection = getConnection();

			UserCommentDao commentDao = new UserCommentDao();
			List<UserComment> ret = commentDao.getUserComment(connection);

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
