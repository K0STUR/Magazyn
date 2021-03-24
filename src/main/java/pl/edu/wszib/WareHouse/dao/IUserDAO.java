package pl.edu.wszib.warehouse.dao;

import pl.edu.wszib.warehouse.model.User;

public interface IUserDAO {
    User getUserByLogin(String login);
    boolean addUserToDB(User user);
}
