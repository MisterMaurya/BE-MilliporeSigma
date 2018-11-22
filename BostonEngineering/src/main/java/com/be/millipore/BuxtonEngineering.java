package com.be.millipore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BuxtonEngineering {

	public static void main(String[] args) {

		SpringApplication.run(BuxtonEngineering.class, args);
		String aString = "Thispddn   . ";
		String a[] = aString.split("[.]");
		for (String i : a) {

			System.out.println(i + 1);

		}

	}

}
