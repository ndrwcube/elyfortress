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

	/**
	 * @param uname       Username of the Player/Account.
	 * @param pwd         Password associated with that account.
	 * @param clientToken Any chosen String, to be persisted.
	 * @return Access Token for the User.
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String AccessToken(String uname, String pwd, String clientToken) throws IOException, ParseException {
		URL url = new URL("https://authserver.ely.by/auth/authenticate");
		String responsereturn = "";

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
			if (result.containsKey("error")) {
				if (result.get("error").equals("ForbiddenOperationException")
						&& result.get("errorMessage").equals("Account protected with two factor auth.")) {
					responsereturn = "Error2FAMissingToken";
				}
			} else {
				responsereturn = AccToken;
			}
		}

		return responsereturn;
	}

	// next fn: curl -d
	// 'accessToken=accToken&clientToken=clientToken&requestUser=true'
	// 'https://authserver.ely.by/auth/refresh'

	/**
	 * @param AccToken    Previously obtained Access Token.
	 * @param ClientToken Client Token used when obtaining the Access Token.
	 * @param Invalidate  Should we invalidate the current Access Token?
	 * @return Newly obtained Access Token, reset expiry.
	 * @throws IOException
	 * @throws ParseException
	 */
	public static String RefreshedAccToken(String AccToken, String ClientToken, boolean Invalidate)
			throws IOException, ParseException {
		String responsereturn = "";
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

			if (result.containsKey("error")) {
				if (result.get("error").equals("ForbiddenOperationException")) {
					responsereturn = (String) result.get("errorMessage");
				}
			} else {
				if (Invalidate) {
					Invalidate(AccToken, ClientToken);
				}
				responsereturn = RefAccToken;
			}

			return responsereturn;
		}
	}

	/**
	 * @param accToken    Access Token to Invalidate.
	 * @param clientToken Client Token used when obtaining the Access Token.
	 * @throws IOException
	 */
	/**
	 * @param accToken Access Token to Invalidate.
	 * @param clientToken Client Token used when obtaining the Access Token.
	 * @return If the Access Token was successfully Invalidated.
	 * @throws IOException
	 * @throws ParseException 
	 */
	public static String Invalidate(String accToken, String clientToken) throws IOException, ParseException {
		
		String responsereturn = "";
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
		try (Scanner s = new Scanner(responseStream).useDelimiter("\\A")) {
			String response = s.hasNext() ? s.next() : "";
			s.close();
			// System.out.println(response);
			
			JSONParser jsonParser = new JSONParser();
			JSONObject result = (JSONObject) jsonParser.parse(response);
			if (result.containsKey("error")) {
				if (result.get("error").equals("ForbiddenOperationException")
						&& result.get("errorMessage").equals("Token expired.")) {
					responsereturn = "ErrorInvalidToken";
				}
			} else {
				responsereturn = "Done";
			}
		}
		return responsereturn;

	}

	/**
	 * @param Username Username of Player/Account to sign out of.
	 * @param Password Password associated with that account.
	 * @throws IOException
	 */
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
