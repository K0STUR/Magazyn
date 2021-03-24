package pl.edu.wszib.warehouse.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.edu.wszib.warehouse.dao.IUserDAO;
import pl.edu.wszib.warehouse.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class IUserDAOImpl implements IUserDAO {

    @Autowired
    Connection connection;

    @Override
    public User getUserByLogin(String login) {
        String sql = "SELECT * FROM tuser WHERE login = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new User(resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("pass"),
                        User.Role.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUserToDB(User user) {
        String sql = "INSERT INTO tuser (login, pass, role) VALUES(?, ?, ?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPass());
            preparedStatement.setString(3, user.getRole().toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
