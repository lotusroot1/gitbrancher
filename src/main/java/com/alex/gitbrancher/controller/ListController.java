package com.alex.gitbrancher.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alex.gitbrancher.model.User;

@RequestMapping("/list")
public class ListController extends BaseController {

	@RequestMapping(value = "/{token}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, List<User>> getLists(@PathVariable String token, HttpServletResponse httpResponse)
			throws IOException {
		Map<String, List<User>> response = new HashMap<String, List<User>>();
		User user = null;
		if (user != null && user.getUserId() != null) {
		}
		return response;

	}

}