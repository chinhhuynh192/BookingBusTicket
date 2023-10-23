package com.busticketbooking.busticketbooking.dao;

import com.busticketbooking.busticketbooking.models.User;

import java.sql.SQLException;

public interface UserDao {
    public boolean insertUser(User user) throws SQLException;
    public User selectById(int userId) throws SQLException;
    public User selectByMailAndPhone(String mail, String phone) throws SQLException;
    public int selectLastId() throws SQLException;
}
