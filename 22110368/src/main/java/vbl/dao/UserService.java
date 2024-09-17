package vbl.dao;

import vbl.models.User;

public interface UserService {
	User login(String username, String password);
	User get(String username);
}
