package com.epizy.bastionmc.pwdauth;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PWParseUsrInfo {

	public String PlayerUUID(String usrinfo) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject result = (JSONObject) jsonParser.parse(usrinfo);
		return (String) result.get("id");
	}

	public String PlayerUname(String usrinfo) throws ParseException {
		JSONParser jsonParser = new JSONParser();
		JSONObject result = (JSONObject) jsonParser.parse(usrinfo);
		return (String) result.get("username");
	}

}
