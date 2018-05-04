package pl.coderslab.warsztaty3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserGroup {

	private int id;
	private String name;

	public UserGroup(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public UserGroup() {
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO UserGroup(name) VALUES (?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setString(1, this.name);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			String sql = "UPDATE UserGroup SET where id=?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, this.name);
			preparedStatement.setInt(2, this.id);
			preparedStatement.executeUpdate();
		}
	}

	static public UserGroup loadGroupById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM UserGroup where id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			UserGroup loadedGroup = new UserGroup();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			return loadedGroup;
		}
		return null;
	}

	static public UserGroup[] loadAllGroups(Connection conn) throws SQLException {
		ArrayList<UserGroup> groups = new ArrayList<UserGroup>();
		String sql = "SELECT * FROM	UserGroup";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			UserGroup loadedGroup = new UserGroup();
			loadedGroup.id = resultSet.getInt("id");
			loadedGroup.name = resultSet.getString("name");
			groups.add(loadedGroup);
		}
		UserGroup[] uArray = new UserGroup[groups.size()];
		uArray = groups.toArray(uArray);
		return uArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM UserGroup WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}
}