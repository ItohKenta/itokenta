package ito_kenta.service;

import static ito_kenta.utils.CloseableUtil.*;
import static ito_kenta.utils.DBUtil.*;

import java.sql.Connection;
import java.util.List;

import ito_kenta.beans.User;
import ito_kenta.dao.UserDao;
import ito_kenta.utils.CipherUtil;

//userの登録情報のService(橋渡し)クラス。①servletからデータクラスに入ったデータ②つなぐDBを支持するデータ(接続情報)を受け取って、整えてDAOに渡す。
public class UserService {

	// ユーザー新規登録用。登録するメソッド
	public void register(User user) {
		Connection connection = null;
		try {
			// Connectionインタフェースは、DBMSへの接続や切断を行う
			connection = getConnection();

			String encPassword = CipherUtil.encrypt(user.getPassword());
			user.setPassword(encPassword);

			// insert文（レコードの追加）でuserDaoにuserを追加
			UserDao userDao = new UserDao();
			userDao.insert(connection, user);

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

	// ユーザー管理画面用。Usersから値をgetするために、引数を指定する必要はない。（すべての情報がほしいので）
	public List<User> getUsers() {

		// try文の前で、connectionをnullにする。
		Connection connection = null;
		try {
			// java.sqlパッケージからConnectionインタフェースを取得する
			connection = getConnection();

			// UserDaoクラスのgetUsersメソッドのconnectionから、User型のリストを生成する（変数名user）
			UserDao userDao = new UserDao();
			List<User> user = userDao.getUsers(connection);

			commit(connection);
			// List<User>型のuserの値を、戻り値List<User>に返す
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

	// ユーザー編集画面用。User型のgetUserメソッドを作成。int型のuserIdに、利用者が値を与える
	public User getUser(int id) {

		Connection connection = null;
		try {
			// java.sqlパッケージからConnectionインタフェースを取得する
			connection = getConnection();
			// UserDaoクラスのgetUsersメソッドのconnectionから、User型のリストを生成する（変数名user）
			UserDao userDao = new UserDao();
			// User型のuserに入る値は、入力したuserIdに適応したuserList（配列）。
			User user = userDao.getUser(connection, id);

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

	// ユーザー情報の変更メソッド
	public void update(User editUser) {

		Connection connection = null;
		try {
			connection = getConnection();

			if(!(editUser.getPassword() == null || editUser.getPassword().length() == 0 )){
				String encPassword = CipherUtil.encrypt(editUser.getPassword());
				editUser.setPassword(encPassword);
			}

			UserDao userDao = new UserDao();
			userDao.update(connection, editUser);

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
	// 停止復活処理用の変更メソッド
	public void updateIsStopped(User isStoppedUser) {

		Connection connection = null;
		try {
			connection = getConnection();

			UserDao userDao = new UserDao();
			userDao.updateIsStopped(connection, isStoppedUser);

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

}