package ito_kenta.service;

import static ito_kenta.utils.CloseableUtil.*;
import static ito_kenta.utils.DBUtil.*;

import java.sql.Connection;

import ito_kenta.beans.User;
import ito_kenta.dao.UserDao;
import ito_kenta.utils.CipherUtil;

//loginクラスに入ったデータをDAOに渡す橋渡しクラス
public class LoginService {
	// Userがログインするメソッド。
	public User login(String loginId, String password) {

		// connection（DBMSへの接続や切断を行う）インタフェース
		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			String encPassword = CipherUtil.encrypt(password);
			User user = userDao.getUser(connection, loginId, encPassword);

			commit(connection);

			return user;
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
