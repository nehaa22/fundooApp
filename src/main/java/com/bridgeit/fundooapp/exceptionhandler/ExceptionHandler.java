package com.bridgeit.fundooapp.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bridgeit.fundooapp.exception.EmailException;
import com.bridgeit.fundooapp.response.Response;
import com.bridgeit.fundooapp.util.StatusHelper;
import com.bridgeit.fundooapp.exception.LabelException;
import com.bridgeit.fundooapp.exception.NoteException;
import com.bridgeit.fundooapp.exception.RegistrationException;
import com.bridgeit.fundooapp.exception.TokenException;
import com.bridgeit.fundooapp.exception.LoginException;

public class ExceptionHandler 
{
		/*Email Exception
		 * -----------------------------------------------*/
		@org.springframework.web.bind.annotation.ExceptionHandler(value = EmailException.class)
		public ResponseEntity<Response> emailExceptionHandler(EmailException e){
			Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
			return new ResponseEntity<Response> (response , HttpStatus.OK);
		}
		
		/*Login Exception
		 * -----------------------------------------------*/

		@org.springframework.web.bind.annotation.ExceptionHandler(value = LoginException.class)
		public ResponseEntity<Response> loginExceptionHandler(LoginException e){
			Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
			return new ResponseEntity<Response> (response , HttpStatus.OK);
		}
		
		/*Note Exception
		* -----------------------------------------------*/
		
		@org.springframework.web.bind.annotation.ExceptionHandler(value = NoteException.class)
		public ResponseEntity<Response> notesExceptionHandler(NoteException e){
			Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
			return new ResponseEntity<Response> (response , HttpStatus.OK);
		}
		
		/*Registration Exception
		* -----------------------------------------------*/
		
		@org.springframework.web.bind.annotation.ExceptionHandler(value = RegistrationException.class)
		public ResponseEntity<Response> registrationExceptionHandler(RegistrationException e){
			Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
			return new ResponseEntity<Response> (response , HttpStatus.OK);
		}
		
		/*Token Exception
		* -----------------------------------------------*/
		
		@org.springframework.web.bind.annotation.ExceptionHandler(value = TokenException.class)
		public ResponseEntity<Response> tokenExceptionHandler(TokenException e){
			Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
			return new ResponseEntity<Response> (response , HttpStatus.OK);
		}
		
		/*Label Exception
		* -----------------------------------------------*/
		
		@org.springframework.web.bind.annotation.ExceptionHandler(value = LabelException.class)
		public ResponseEntity<Response> labelExceptionHandler(LabelException e){
			Response response = StatusHelper.statusInfo(e.getMessage(), e.getErrorCode());
			return new ResponseEntity<Response> (response , HttpStatus.OK);
		}
}
