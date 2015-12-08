package com.alex.gitbrancher.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			// response.add(jsonToMap(obj));
		}
		result.setResult(response);
		return result;

	}
}