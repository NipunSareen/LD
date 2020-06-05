package in.lendenindia.backend.code.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import in.lendenindia.backend.code.shared.dto.UserDto;

public interface UserService extends UserDetailsService {
	
	void createUser(UserDto user);
	
	UserDto getUser(String email);
	
	UserDto getUserByUserId(String id);
	
	/* UserDto updateUser(String id, UserDto userDto); */

    void deleteUser(String id);

	List<UserDto> getUsers(int page, int limit);
	
	

}
