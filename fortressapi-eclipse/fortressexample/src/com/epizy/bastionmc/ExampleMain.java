package com.epizy.bastionmc;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.epizy.bastionmc.pwdauth.PwdAccToken;

public class ExampleMain {

	public static void main(String[] args) {

		if (args.length == 3) {
			try {
				String AccToken = PwdAccToken.AccessToken(args[0], args[1], args[2]);
				String RefAccToken = PwdAccToken.RefreshedAccToken(AccToken, args[2], true);
				print(AccToken);
				print(RefAccToken);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void print(String txt) {
		System.out.println(txt);
	}

	public static void print(int txt) {
		System.out.println(txt);
	}

	public static void print() {
		System.out.println();
	}

}
