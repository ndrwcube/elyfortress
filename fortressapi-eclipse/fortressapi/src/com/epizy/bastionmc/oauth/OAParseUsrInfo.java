package com.epizy.bastionmc.oauth;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class OAParseUsrInfo {

	public String PlayerUUID(String usrinfo) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject result = (JSONObject) jsonParser.parse(usrinfo);
		return (String) result.get("uuid");
	}

	public String PlayerUname(String usrinfo) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject result = (JSONObject) jsonParser.parse(usrinfo);
		return (String) result.get("username");
	}

	public String PlayerEmail(String usrinfo) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject result = (JSONObject) jsonParser.parse(usrinfo);
		return (String) result.get("email");
	}

}
