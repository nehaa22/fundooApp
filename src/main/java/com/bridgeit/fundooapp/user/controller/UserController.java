package com.bridgeit.fundooapp.user.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgeit.fundooapp.user.service.UserServices;
import com.bridgeit.fundooapp.response.Response;
import com.bridgeit.fundooapp.user.dto.UserDTO;

@RestController
@CrossOrigin(allowedHeaders = "*" ,origins = "*")
@RequestMapping("/user")
//annotation for set environment file 
@PropertySource("classpath:message.properties")
public class UserController 
{

	//Injecting bean of IUserServices------------------------------------------------
	@Autowired
	private UserServices userServices;
	
	//Register-----------------------------------------------------------------------
	@PostMapping("/register") 
	public ResponseEntity<Response> register(@Valid @RequestBody UserDTO userDTO)
	{
		Response statusResponse = userServices.register(userDTO);
		return new ResponseEntity<Response>(statusResponse, HttpStatus.OK);
	}
	

}
