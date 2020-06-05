package in.lendenindia.backend.code.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.lendenindia.backend.code.exception.UserServiceException;
import in.lendenindia.backend.code.io.entity.UserEntity;
import in.lendenindia.backend.code.io.repositories.UserRepository;
import in.lendenindia.backend.code.service.UserService;
import in.lendenindia.backend.code.shared.Utils;
import in.lendenindia.backend.code.shared.dto.UserDto;
import in.lendenindia.backend.code.ui.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	Utils utils;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public void createUser(UserDto user) {
		
		if(userRepository.findByEmail(user.getEmail())!= null) throw new RuntimeException("Record already exists") ;
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(user, userEntity);
		String publicUserId;
		do {
		publicUserId = utils.GenerateUserId(30);
		}while(userRepository.findByUserid(publicUserId) != null);
		userEntity.setUserid(publicUserId);
		
		userEntity.setEncryptedpassword(bCryptPasswordEncoder.encode(user.getPassword()));
		
		 userRepository.save(userEntity);
		

		
		
	}
	
	@Override
	public UserDto getUser(String email) {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		UserDto returnValue = new UserDto();
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) throw new UsernameNotFoundException(email);
		
		return new User(userEntity.getEmail(), userEntity.getEncryptedpassword(), new  ArrayList<>());
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		
		UserDto returnValue = new UserDto();
		UserEntity userEntity = userRepository.findByUserid(userId);
		
		if(userEntity == null) throw new UsernameNotFoundException("user with userid: "+userId+" does not exists");
		
		BeanUtils.copyProperties(userEntity, returnValue);
		
		return returnValue;
	}

	/*
	 * @Override public UserDto updateUser(String userId, UserDto userDto) {
	 * 
	 * UserDto returnValue = new UserDto(); UserEntity userEntity =
	 * userRepository.findByUserId(userId);
	 * 
	 * if(userEntity == null) throw new
	 * UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
	 * 
	 * userEntity.setFirstname(userDto.getFirstname());
	 * userEntity.setLastname(userDto.getLastname());
	 * 
	 * UserEntity updatedUserDetails = userRepository.save(userEntity);
	 * 
	 * BeanUtils.copyProperties(updatedUserDetails, returnValue);
	 * 
	 * return returnValue; }
	 */

	@Override
	public void deleteUser(String userId) {
		
		UserEntity userEntity = userRepository.findByUserid(userId);
		
		if(userEntity == null) throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);
		
		
	}

	@Override
	public List<UserDto> getUsers(int page, int limit) {
		
		List<UserDto> returnValue = new ArrayList<>();
		
		if(page>0) page = page-1;
		
		Pageable pageableRequest = PageRequest.of(page, limit);
		
		Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
		List<UserEntity> users = usersPage.getContent();
		
		for(UserEntity userEntity : users) {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(userEntity,userDto);
			returnValue.add(userDto);
			
		}
		return returnValue;
	}

	

}
