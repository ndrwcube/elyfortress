package com.epizy.bastionmc.elyapi;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class UserInfo {

	//As per https://docs.ely.by/en/api.html
	//More efficient than Parsing if you're using MCAuth
	
	//Getting the User's UUID
	//GET https://authserver.ely.by/api/users/profiles/minecraft/{username}
	//https://docs.ely.by/en/api.html#uuid
	public static String PlayerUUID(String Username) throws IOException, ParseException {
		URL url = new URL("https://authserver.ely.by/api/users/profiles/minecraft/" + Username);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("GET");

        InputStream responseStream = httpConn.getResponseCode() / 100 == 2
                ? httpConn.getInputStream()
                : httpConn.getErrorStream();
        try (Scanner s = new Scanner(responseStream).useDelimiter("\\A")) {
			String response = s.hasNext() ? s.next() : "";
			s.close();
			
			JSONParser jsonParser = new JSONParser();
			JSONObject result = (JSONObject) jsonParser.parse(response);
			return (String) result.get("id");
		}
	}
	
}
