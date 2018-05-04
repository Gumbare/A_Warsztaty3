package pl.coderslab.warsztaty3.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class Solution {

	private int id;
	private Date created;
	private Date updated;
	private String description;
	private int exercise_id;
	private int users_id;

	public Solution(int id, Date created, Date updated, String description, int Exercise_id, int User_id) {
		this.id = id;
		this.created = created;
		this.updated = updated;
		this.description = description;
		this.exercise_id = Exercise_id;
		this.users_id = User_id;
	}

	public Solution() {
	}

	public void saveToDB(Connection conn) throws SQLException {
		if (this.id == 0) {
			String sql = "INSERT INTO Solution(created, updated, description, Exercise_id, User_id) VALUES (?, ?, ?, ?, ?)";
			String generatedColumns[] = { "ID" };
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql, generatedColumns);
			preparedStatement.setDate(1, (java.sql.Date) this.created);
			preparedStatement.setDate(2, (java.sql.Date) this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.exercise_id);
			preparedStatement.setInt(5, this.users_id);
			preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				this.id = rs.getInt(1);
			}
		} else {
			String sql = "UPDATE Solution SET created=?, updated=?, description=?, Exercise_id=?, User_id=? where id=?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setDate(1, (java.sql.Date) this.created);
			preparedStatement.setDate(2, (java.sql.Date) this.updated);
			preparedStatement.setString(3, this.description);
			preparedStatement.setInt(4, this.exercise_id);
			preparedStatement.setInt(5, this.users_id);
			preparedStatement.setInt(6, this.id);
			preparedStatement.executeUpdate();
		}
	}

	static public Solution loadSolutionById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Solution where id=?";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setInt(1, id);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("Exercise_id");
			loadedSolution.users_id = resultSet.getInt("User_id");
			return loadedSolution;
		}
		return null;
	}

	static public Solution[] loadAllSolution(Connection conn) throws SQLException {
		ArrayList<Solution> solutions = new ArrayList<Solution>();
		String sql = "SELECT * FROM	Solution";
		PreparedStatement preparedStatement;
		preparedStatement = conn.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			Solution loadedSolution = new Solution();
			loadedSolution.id = resultSet.getInt("id");
			loadedSolution.created = resultSet.getDate("created");
			loadedSolution.updated = resultSet.getDate("updated");
			loadedSolution.description = resultSet.getString("description");
			loadedSolution.exercise_id = resultSet.getInt("Exercise_id");
			loadedSolution.users_id = resultSet.getInt("User_id");
			solutions.add(loadedSolution);
		}
		Solution[] uArray = new Solution[solutions.size()];
		uArray = solutions.toArray(uArray);
		return uArray;
	}

	public void delete(Connection conn) throws SQLException {
		if (this.id != 0) {
			String sql = "DELETE FROM Solution WHERE id= ?";
			PreparedStatement preparedStatement;
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, this.id);
			preparedStatement.executeUpdate();
			this.id = 0;
		}
	}

}
