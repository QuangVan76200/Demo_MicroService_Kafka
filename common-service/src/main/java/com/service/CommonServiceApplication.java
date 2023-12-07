package com.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bouncycastle.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonServiceApplication {
	
	static SecureRandom random = new SecureRandom();

	public static String randomPinCode(String[] randomPinCode) {
		int pinLength = 6;
		
		String[] generatedPinCode = new String[pinLength];
		for(int i = 0 ; i< pinLength; i++) {
			int index = random.nextInt(randomPinCode.length);
			generatedPinCode[i] = randomPinCode[index];
		}
		
		String pinCode = String.join("", generatedPinCode);
		
		return pinCode;
		
	}
	public static void main(String[] args) {
		SpringApplication.run(CommonServiceApplication.class, args);
//		String str1 = "LeQuangVan";
//		String str2 = "eQu";
//		String str3 = "abcdabcbb";
//		System.out.println(removeDuplicate(str1));
//		System.out.println(checkConstain(str1, str2));;
		String[] randomPinCode = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
		System.out.println(randomPinCode(randomPinCode));
	}

}
