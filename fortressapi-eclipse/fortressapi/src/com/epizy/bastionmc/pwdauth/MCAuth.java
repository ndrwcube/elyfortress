package com.epizy.bastionmc.pwdauth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class MCAuth {

	public static String AccessToken(String uname, String pwd, String clientToken) throws IOException, ParseException {
		System.out.println("Authorising " + uname + " with the Ely service for " + clientToken + ".");

		URL url = new URL("https://authserver.ely.by/auth/authenticate");
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("POST");

		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		httpConn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
		writer.write("username=" + uname + "&password=" + pwd + "&clientToken=" + clientToken + "&requestUser=true");
		writer.flush();
		writer.close();
		httpConn.getOutputStream().close();

		InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
				: httpConn.getErrorStream();
		try (Scanner s2 = new Scanner(responseStream).useDelimiter("\\A")) {
			String response = s2.hasNext() ? s2.next() : "";
			s2.close();

			System.out.println();

			JSONParser jsonParser = new JSONParser();
			JSONObject result = (JSONObject) jsonParser.parse(response);
			String AccToken = (String) result.get("accessToken");

			return AccToken;
		}
	}

	// next fn: curl -d
	// 'accessToken=accToken&clientToken=clientToken&requestUser=true'
	// 'https://authserver.ely.by/auth/refresh'

	public static String RefreshedAccToken(String AccToken, String ClientToken, boolean Invalidate)
			throws IOException, ParseException {
		URL url = new URL("https://authserver.ely.by/auth/refresh");
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("POST");

		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		httpConn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
		writer.write("accessToken=" + AccToken + "&clientToken=" + ClientToken + "&requestUser=true");
		writer.flush();
		writer.close();
		httpConn.getOutputStream().close();

		InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
				: httpConn.getErrorStream();
		try (Scanner s = new Scanner(responseStream).useDelimiter("\\A")) {
			String response = s.hasNext() ? s.next() : "";
			s.close();

			JSONParser jsonParser = new JSONParser();
			JSONObject result = (JSONObject) jsonParser.parse(response);
			String RefAccToken = (String) result.get("accessToken");

			if (Invalidate) {
				Invalidate(AccToken, ClientToken);
			}

			return RefAccToken;
		}
	}

	public static void Invalidate(String accToken, String clientToken) throws IOException {

		URL url = new URL("https://authserver.ely.by/auth/invalidate");
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("POST");

		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		httpConn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
		writer.write("accessToken=" + accToken + "&clientToken=" + clientToken);
		writer.flush();
		writer.close();
		httpConn.getOutputStream().close();

		InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
				: httpConn.getErrorStream();
		Scanner s = new Scanner(responseStream).useDelimiter("\\A");
		// String response = s.hasNext() ? s.next() : "";
		s.close();
		// System.out.println(response);

	}
	
	public static void FullSignout(String Username, String Password) throws IOException {
		
		
		URL url = new URL("https://authserver.ely.by/auth/signout");
		HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
		httpConn.setRequestMethod("POST");

		httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		httpConn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
		writer.write("username=" + Username + "&password=" + Password);
		writer.flush();
		writer.close();
		httpConn.getOutputStream().close();

		InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
				: httpConn.getErrorStream();
		Scanner s = new Scanner(responseStream).useDelimiter("\\A");
		// String response = s.hasNext() ? s.next() : "";
		s.close();
		// System.out.println(response);
		
	}

}
