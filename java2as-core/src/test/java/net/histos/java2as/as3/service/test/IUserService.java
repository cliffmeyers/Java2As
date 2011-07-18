package net.histos.java2as.as3.service.test;

import net.histos.java2as.as3.transfer.test.User;

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
