package com.itmo.assassins;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class AssassinsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssassinsApplication.class, args);
	}
}
