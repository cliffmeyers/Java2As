package com.foo.service;

import com.foo.dto.User;

import java.util.List;

/**
 * Description
 *
 * @author cliff.meyers
 */
public interface IUserService {

	List<User> listUsers();

	void saveUser(User user);

}
