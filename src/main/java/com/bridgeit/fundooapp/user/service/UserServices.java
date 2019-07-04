package com.bridgeit.fundooapp.user.service;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

import com.bridgeit.fundooapp.response.Response;
import com.bridgeit.fundooapp.user.dto.UserDTO;
import com.bridgeit.fundooapp.user.dto.PasswordDTO;
import com.bridgeit.fundooapp.user.model.User;
import com.bridgeit.fundooapp.response.ResponseToken;
import com.bridgeit.fundooapp.user.dto.LoginDTO;

@Service
public interface UserServices 
{
	public Response register(UserDTO userDto);
	public ResponseToken login(LoginDTO loginDto) throws IllegalArgumentException, UnsupportedEncodingException;
	public Response validateEmail(String token);
	public Response forgotPassword(String email);
	public Response resetPassword(PasswordDTO passwordDto , String token);
	public User getUserInfo(String email);

}
