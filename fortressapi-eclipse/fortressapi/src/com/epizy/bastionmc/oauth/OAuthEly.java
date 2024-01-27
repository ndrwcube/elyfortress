package com.epizy.bastionmc.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class OAuthEly {

	// get and auth refToken
	public static String GetRefToken(String cID, String cS, String authCode, String redirURI) {
		String reftoken = "";
		try {
			URL url = new URL("https://account.ely.by/api/oauth2/v1/token");
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");

			httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			httpConn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
			writer.write("client_id=" + cID + "&client_secret=" + cS + "&redirect_uri=" + redirURI
					+ "&grant_type=authorization_code&code=" + authCode);
			writer.flush();
			writer.close();
			httpConn.getOutputStream().close();

			InputStream responseStream = httpConn.getResponseCode() / 100 == 2 ? httpConn.getInputStream()
					: httpConn.getErrorStream();
			Scanner s = new Scanner(responseStream).useDelimiter("\\A");
			String response = s.hasNext() ? s.next() : "";
			s.close();

			System.out.println("");
			System.out.println("Received JSON data from Ely.by: ");
			System.out.println(response);

			JSONParser parser = new JSONParser();
			try {
				Object obj = parser.parse(response);
				JSONObject jsonObject = (JSONObject) obj;
				reftoken = (String) jsonObject.get("refresh_token");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return reftoken;
	}

	// thus auth the reftoken for an accesstoken
	public static String AuthRefToken(String cID, String cS, String redirURI, String reftoken) throws IOException {
		// authreftoken

		URL urlb = new URL("https://account.ely.by/api/oauth2/v1/token");
		HttpURLConnection httpConnb = (HttpURLConnection) urlb.openConnection();
		httpConnb.setRequestMethod("POST");

		httpConnb.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		httpConnb.setDoOutput(true);
		OutputStreamWriter writerb = new OutputStreamWriter(httpConnb.getOutputStream());
		writerb.write("client_id=" + cID + "&client_secret=" + cS + "&redirect_uri=" + redirURI
				+ "&scope=account_info account_email minecraft_server_session&refresh_token=" + reftoken
				+ "&grant_type=refresh_token");
		writerb.flush();
		writerb.close();
		httpConnb.getOutputStream().close();

		InputStream responseStreamb = httpConnb.getResponseCode() / 100 == 2 ? httpConnb.getInputStream()
				: httpConnb.getErrorStream();
		Scanner sb = new Scanner(responseStreamb).useDelimiter("\\A");
		String responseb = sb.hasNext() ? sb.next() : "";
		sb.close();

		System.out.println(responseb);

		// getusrinfo

		String acctoken = "";

		JSONParser parser2 = new JSONParser();
		try {
			Object obj2 = parser2.parse(responseb);
			JSONObject jsonObject2 = (JSONObject) obj2;
			acctoken = (String) jsonObject2.get("access_token");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return acctoken;
	}

	// get user info as JSON, may parse in another class
	public static String usrinfo(String acctoken) {
		String responsec = "";
		try {
			URL urlc = new URL("https://account.ely.by/api/account/v1/info");
			HttpURLConnection httpConnc = (HttpURLConnection) urlc.openConnection();
			httpConnc.setRequestMethod("GET");

			httpConnc.setRequestProperty("Authorization", "Bearer " + acctoken);

			InputStream responseStreamc = httpConnc.getResponseCode() / 100 == 2 ? httpConnc.getInputStream()
					: httpConnc.getErrorStream();
			Scanner sc = new Scanner(responseStreamc).useDelimiter("\\A");
			responsec = sc.hasNext() ? sc.next() : "";
			sc.close();
			// System.out.println(responsec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responsec;
	}

}
