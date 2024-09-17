package vbl.dao;

import vbl.models.User;

public interface UserDao {
	User get(String username);

}
