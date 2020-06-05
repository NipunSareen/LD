package in.lendenindia.backend.code.shared;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class Utils {
	private final Random RANDOM = new SecureRandom(); 
	private final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public String GenerateUserId(int length) {
		return generateRandomSgtring(length);
	}
	private String generateRandomSgtring(int length) {
		StringBuilder returnValue = new StringBuilder();
		for(int i=0; i<length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
		
	}
	
	
	

}
