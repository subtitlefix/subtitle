package com.hu.training.stocktest.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import com.hu.training.stocktest.enums.ContentType;
import com.hu.training.stocktest.factory.AccountFactory;

public class CSVParser {
	
	private AccountFactory accountFactory;

	public CSVParser(AccountFactory accountFactory) {
		this.accountFactory = accountFactory;
	}

	public void startParse(String csv ) {
		URL url = CSVParser.class.getClassLoader().getResource(csv);
		File file = new File(url.getPath());
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = null;
			while( (line = br.readLine()) != null) {
				if(line.trim().length() == 0) {
					continue;
				}
				createAccountFromLine(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try { br.close(); } catch (IOException e) {	}
		}
		
	}

	private void createAccountFromLine(String line) {
		String[] tokens = line.split(",");
		if(tokens.length == 4) {
			try {
				accountFactory.createAccount(tokens[0], ContentType.valueOf(tokens[1]), tokens[2], Long.parseLong(tokens[3]));
			} catch (IllegalArgumentException e) {
				System.out.println("invalid line in csv:" + line);
			}
		}
		
	}

}
