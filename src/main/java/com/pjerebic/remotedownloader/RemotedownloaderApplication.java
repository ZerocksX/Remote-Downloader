package com.pjerebic.remotedownloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Arrays;

/**
 * Web app that downloads torrent data from the given link
 */
@SpringBootApplication
public class RemotedownloaderApplication {

	/**
	 * Starting method
	 * @param args arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(RemotedownloaderApplication.class, args);
		Arrays.stream(File.listRoots()).forEach(System.out::println);
	}
}
