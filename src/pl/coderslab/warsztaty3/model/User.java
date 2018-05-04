package pl.coderslab.warsztaty3.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.mindrot.jbcrypt.BCrypt;

public class User {

	private int id;
	private String username;
	private String password;
	private String email;

	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.setPassword(password);
	}

	public User(int id, String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.setPassword(password);
	}

	public User() {
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO User(username, email, password) VALUES (?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			String sql = "UPDATE User SET username=?, email=?, password=? where id=?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.username);
			preparedStatement.setString(2, this.email);
			preparedStatement.setString(3, this.password);
			preparedStatement.setInt(4, this.id);
			preparedStatement.executeUpdate();
		}
	}

	static public User loadUserById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM User where id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			return loadedUser;
		}
		return null;
	}

	static public User[] loadAllUsers(Connection conn) throws SQLException {
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM	User";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User loadedUser = new User();
			loadedUser.id = resultSet.getInt("id");
			loadedUser.username = resultSet.getString("username");
			loadedUser.password = resultSet.getString("password");
			loadedUser.email = resultSet.getString("email");
			users.add(loadedUser);
		}
		User[] uArray = new User[users.size()];
		uArray = users.toArray(uArray);
		return uArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM User WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

}
