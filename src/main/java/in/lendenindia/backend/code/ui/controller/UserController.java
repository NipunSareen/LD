package in.lendenindia.backend.code.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.lendenindia.backend.code.service.UserService;
import in.lendenindia.backend.code.shared.dto.UserDto;
import in.lendenindia.backend.code.ui.request.UserDetailsRequestModel;
import in.lendenindia.backend.code.ui.response.OperationStatusModel;
import in.lendenindia.backend.code.ui.response.RequestOperationStatus;
import in.lendenindia.backend.code.ui.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@GetMapping(path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) {
		
		UserRest returnvalue = new UserRest();
		UserDto userDto = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDto, returnvalue);
		return returnvalue;
	}
	
	@PostMapping(
			consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE },
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	
	
	public String postUser(@RequestBody UserDetailsRequestModel userDetails)throws Exception {
		
		
		//if(userDetails.getFirstname().isEmpty()) throw new NullPointerException("The Object is null, HAHAHAHAHAH");//throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(userDetails, userDto);
		
		userService.createUser(userDto);
		
		
		
		return "Registration Sussesfull";
	}
	
	/*
	 * @PutMapping(path="/{id}", consumes = { MediaType.APPLICATION_XML_VALUE,
	 * MediaType.APPLICATION_JSON_VALUE }, produces = {
	 * MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }) public
	 * UserRest putUser(@PathVariable String id, @RequestBody
	 * UserDetailsRequestModel userDetails) {
	 * 
	 * UserRest returnvalue = new UserRest();
	 * 
	 * UserDto userDto = new UserDto();
	 * 
	 * BeanUtils.copyProperties(userDetails, userDto);
	 * 
	 * UserDto updateUser = userService.updateUser(id, userDto);
	 * 
	 * BeanUtils.copyProperties(updateUser, returnvalue);
	 * 
	 * return returnvalue; }
	 */
	
	@DeleteMapping(path="/{id}",
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public OperationStatusModel deleteUser(@PathVariable String id) {
		
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());
		
		userService.deleteUser(id);
		
		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		
		return returnValue;
	}
	
	@GetMapping(
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int Page,
									@RequestParam(value = "limit", defaultValue = "25") int limit){
		
		List<UserRest> returnValue = new ArrayList<>();
		
		List<UserDto> users = userService.getUsers(Page, limit);
		
		for(UserDto userDto : users) {
			UserRest userModel = new UserRest();
			BeanUtils.copyProperties(userDto, userModel);
			returnValue.add(userModel);
			
		}
		return returnValue;
		
		
	}

}
