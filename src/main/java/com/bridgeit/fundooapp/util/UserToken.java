package com.bridgeit.fundooapp.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.bridgeit.fundooapp.exception.TokenException;
import com.bridgeit.fundooapp.util.UserToken;

@Component
@PropertySource("classpath:message.properties")
public class UserToken {
	
	static final Logger log = LoggerFactory.getLogger(UserToken.class);
	private static String TOKEN;
	
	/*----------------------------------------------------------
	 * Creating token
	 * ----------------------------------------------------------*/
	
	public  String generateToken(long id) {
		TOKEN="Tasif";
		Algorithm algorithm = null;
		
			try {
				algorithm = Algorithm.HMAC256(TOKEN);
			} catch (IllegalArgumentException | UnsupportedEncodingException e) {
				log.error(e.getMessage(), e);
				//e.printStackTrace();
			}
		
		String token=JWT.create().withClaim("ID", id).sign(algorithm);
		return token;		
	}
	
	/*------------------------------------------------------------
	 * decoding token
	 * -----------------------------------------------------------*/
	
	public  long tokenVerify(String token){
		TOKEN="Tasif";

		long userid;
		//here verify the given token's algorithm
		Verification verification = null;
		
		try {
			verification = JWT.require(Algorithm.HMAC256(UserToken.TOKEN));
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			throw new TokenException("Invalid Token", -1);
		}
		
		JWTVerifier jwtverifier=verification.build();
		DecodedJWT decodedjwt=jwtverifier.verify(token);
		Claim claim=decodedjwt.getClaim("ID");
		userid=claim.asLong();	
		System.out.println(userid);
		return userid;
	}

}
