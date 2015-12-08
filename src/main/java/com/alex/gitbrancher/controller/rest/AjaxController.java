package com.alex.gitbrancher.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alex.gitbrancher.helper.RequestHelper;
import com.alex.gitbrancher.model.ajax.AjaxResponse;

@RestController
public class AjaxController {

	@Autowired
	private RequestHelper requestHelper;

	// @JsonView(Views.Public.class)
	@RequestMapping(value = "/search/repos")
	@ResponseBody
	public AjaxResponse getSearchResultViaAjax() throws Exception {

		AjaxResponse result = new AjaxResponse();
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		List<JSONObject> theList = requestHelper.getArrayRequest(HttpMethod.GET,
				"https://api.github.com/users/aye/repos");
		for (JSONObject obj : theList) {
			response.add(jsonToMap(obj));
		}
		result.setResult(response);
		return result;

	}

	public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
		Map<String, Object> retMap = new HashMap<String, Object>();

		if (json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}

	public static Map<String, Object> toMap(JSONObject object) throws JSONException {
		Map<String, Object> map = new HashMap<String, Object>();

		Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			map.put(key, value);
		}
		return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}
}