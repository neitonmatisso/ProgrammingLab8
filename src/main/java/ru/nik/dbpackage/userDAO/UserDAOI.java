package ru.nik.dbpackage.userDAO;

import ru.nik.serverPackage.accounts.User;

import java.util.List;

public interface UserDAOI {
    void addNewUser(User user);
    List<User> loadUsers();
    void update(User user);
}
