package com.epizy.bastionmc.oauth;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OAParseUsrInfo {

	/**
	 * @param usrinfo User Info JSON String. Use OAuthEly to obtain.
	 * @return UUID of the given Player/Account.
	 * @throws ParseException
	 */
	public String PlayerUUID(String usrinfo) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject result = (JSONObject) jsonParser.parse(usrinfo);
		return (String) result.get("uuid");
	}

	/**
	 * @param usrinfo User Info JSON String. Use OAuthEly to obtain.
	 * @return Username of the Player/Account.
	 * @throws ParseException
	 */
	public String PlayerUname(String usrinfo) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject result = (JSONObject) jsonParser.parse(usrinfo);
		return (String) result.get("username");
	}

	/**
	 * @param usrinfo User Info JSON String. Use OAuthEly to obtain.
	 * @return Email address for the given user account.
	 * @throws ParseException
	 */
	public String PlayerEmail(String usrinfo) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject result = (JSONObject) jsonParser.parse(usrinfo);
		return (String) result.get("email");
	}

}
