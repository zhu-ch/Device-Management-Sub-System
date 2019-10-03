package com.management.admin.modules.sys.dao;

import com.management.admin.modules.sys.entity.Dept;

import java.util.List;

public interface UserDao {
    String queryPassword(String id);
    int changePassword(String id, String password);
    List<Dept> getSubFromDept();
}
