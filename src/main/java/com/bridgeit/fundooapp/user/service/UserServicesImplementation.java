package com.bridgeit.fundooapp.user.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgeit.fundooapp.user.model.Email;
import com.bridgeit.fundooapp.user.repository.UserRepository;
import com.bridgeit.fundooapp.util.MailService;
import com.bridgeit.fundooapp.util.UserToken;
import com.bridgeit.fundooapp.exception.EmailException;
import com.bridgeit.fundooapp.exception.LoginException;
import com.bridgeit.fundooapp.response.ResponseToken;
import com.bridgeit.fundooapp.user.dto.LoginDTO;
import com.bridgeit.fundooapp.user.dto.PasswordDTO;
import com.bridgeit.fundooapp.util.StatusHelper;
import com.bridgeit.fundooapp.exception.RegistrationException;
import com.bridgeit.fundooapp.user.model.User;
import com.bridgeit.fundooapp.response.Response;
import com.bridgeit.fundooapp.user.dto.UserDTO;

@Service("userService")
@PropertySource("classpath:message.properties")
public class UserServicesImplementation implements UserServices
{
	@Autowired
	private Environment environment;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MailService mailServise;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserToken userToken;
	 
	@Autowired
	private Email email;
	
	public Response register(UserDTO userDTO) 
	{
		Response response = null;
		
		/* find user by email-----------------------------------------------------*/
		
		Optional<User> avaiability = userRepository.findByEmail(userDTO.getEmail());
		
		/* check already present or not-------------------------------------------*/
		
		if(avaiability.isPresent()){
			throw new RegistrationException("User exist", -2);
		}
		
		/* if not prenent  -  set password by encoding-----------------------------*/
			
			userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			
			//map
			User user = modelMapper.map(userDTO, User.class);
			
			user.setRegisteredDate(LocalDate.now());

			User saveResponse = userRepository.save(user);
			
			if(saveResponse==null)
			{
				throw new RegistrationException("Data is not saved in database", -2);

			}
			
			System.out.println(user.getUserId());
			email.setFrom("nehapalekar026@gmail.com");
			email.setTo(userDTO.getEmail());
			email.setSubject("Email Verification ");
			System.out.println(user.getUserId());
			
			try {
				email.setBody( mailServise.getLink("http://localhost:8080/user/emailvalidation/",user.getUserId()));
			} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			mailServise.send(email);

			response = StatusHelper.statusInfo(environment.getProperty("status.register.success"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		
		
	}
	
	@Override
	public ResponseToken login(LoginDTO loginDto) {
		ResponseToken response = null;
		//getting user record by email
		java.util.Optional<User> user = userRepository.findByEmail(loginDto.getEmail());
	
		//Checking whether user is registered
		if(user.isPresent()) {
			
			//Checking whether user is verified
			if(user.get().isVarified() == true) {
				if(passwordEncoder.matches(loginDto.getPassword(), user.get().getPassword())) {
					String generatedToken = userToken.generateToken(user.get().getUserId());
					response = StatusHelper.tokenStatusInfo(environment.getProperty("status.login.success"),Integer.parseInt(environment.getProperty("status.success.code")),generatedToken,user.get().getName());
					return response;
				}else {
					throw new LoginException("Invalid Password ", -3);
				}
			}else {
				throw new LoginException("Email is not verified ", -3);
			}
		}
		else {
			throw new LoginException("Invalid EmailId", -3);
		}
		
	}


	
	/* (non-Javadoc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#validateEmail(java.lang.String)
	 */
	@Override
	public Response validateEmail(String token) {
		Response response = null;
		long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id).map(this::verify);
		if(user.isPresent()) {
			response = StatusHelper.statusInfo(environment.getProperty("status.email.verified"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}
		else {
			throw new LoginException("EmailId is not verified", -3);
		}
	}
	
	/**
	 * Method to verify user
	 * @param user
	 * @return
	 */
	private User verify(User user) {
		//log.info("User : " + user);
		user.setVarified(true);
		user.setUpdatedDate(LocalDate.now());
		//log.info("User : "+user);
		return userRepository.save(user);
	}

	/* (non-Java doc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#forgotPassword(java.lang.String, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Response forgotPassword(String email ) {
		Email emailObj = new Email();
		Response response = null;
		//log.info("Email of user is :" + email);
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent()) {
			throw new EmailException("No user exist ", -4);
		}
		
		emailObj.setFrom("syenare97@gmail.com");
		emailObj.setTo(email);
		emailObj.setSubject("Forgot Password ");
		try {
			emailObj.setBody( mailServise.getLink("http://localhost:4200/user/resetpassword/",user.get().getUserId()));
		} catch (IllegalArgumentException | UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		mailServise.send(emailObj);
		
		response = StatusHelper.statusInfo(environment.getProperty("status.forgot.emailSent"),Integer.parseInt(environment.getProperty("status.success.code")));
		return response;
	}

	
	/* (non-Java doc)
	 * @see com.bridgelabz.fundoo.user.service.IUserServices#resetPassword(com.bridgelabz.fundoo.user.dto.PasswordDTO, java.lang.String)
	 */
	@Override
	public Response resetPassword(PasswordDTO passwordDto, String token) {
		Response response = null;
		long id = userToken.tokenVerify(token);
		Optional<User> user = userRepository.findById(id);
		if(passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
			user.get().setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
			userRepository.save(user.get());
			//log.info("Password Reset Successfully");
			response = StatusHelper.statusInfo(environment.getProperty("status.resetPassword.success"),Integer.parseInt(environment.getProperty("status.success.code")));
			return response;
		}

		response = StatusHelper.statusInfo(environment.getProperty("status.passreset.failed"),Integer.parseInt(environment.getProperty("status.login.errorCode")));
		return response;
	}

	@Override
	public User getUserInfo(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if(!user.isPresent())
			throw new RegistrationException("No user exist", -2);
		User userInfo = user.get();
		return userInfo;
	}
}

