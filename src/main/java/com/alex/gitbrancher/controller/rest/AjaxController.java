package com.alex.gitbrancher.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alex.gitbrancher.helper.RequestHelper;
import com.alex.gitbrancher.model.ajax.AjaxResponse;

@RestController
public class AjaxController {

	@Autowired
	private RequestHelper requestHelper;

	public static final List<String> reservedBranchNames;

	static {
		reservedBranchNames = new ArrayList<String>();
		reservedBranchNames.add("master");
	}

	private String getGitApiBaseUrl() {
		return "https://api.github.com";
	}

	private String getUserName() {
		return "lotusroot1";
	}

	@RequestMapping(value = "/search/repos")
	@ResponseBody
	public AjaxResponse getRepositories() throws Exception {
		AjaxResponse result = new AjaxResponse();
		String jsonResponse = requestHelper.sendRequest(HttpMethod.GET,
				getGitApiBaseUrl() + "/users/" + getUserName() + "/repos");
		result.setResult(jsonResponse);
		return result;

	}

	@RequestMapping(value = "/search/{repo}/branches")
	@ResponseBody
	public AjaxResponse getBranches(@PathVariable("repo") String repository) throws Exception {

		if (StringUtils.isEmpty(repository)) {

		}
		AjaxResponse result = new AjaxResponse();
		String jsonResponse = requestHelper.sendRequest(HttpMethod.GET,
				getGitApiBaseUrl() + "/repos/" + getUserName() + "/" + repository + "/branches");
		result.setResult(jsonResponse);
		return result;

	}

	@RequestMapping(value = "/create/branch", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse createBranch(@RequestParam(name = "repo", required = true) String repository,
			@RequestParam(name = "sha", required = true) String sha,
			@RequestParam(name = "name", required = true) String name) throws Exception {
		AjaxResponse result = new AjaxResponse();

		boolean paramsValid = true;
		// validate
		repository = StringUtils.trimToEmpty(repository);
		name = StringUtils.trimToEmpty(name);
		sha = StringUtils.trimToEmpty(sha);
		if (StringUtils.isEmpty(repository) || StringUtils.isEmpty(sha) || StringUtils.isEmpty(name)) {
			// throw something
			paramsValid = false;
		}

		paramsValid = paramsValid && isValidBranchName(name);

		if (paramsValid) {
			// /repos/:owner/:repo/git/refs
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("sha", sha);
			paramMap.put("ref", "refs/heads/" + name);
			String jsonResponse = requestHelper.sendRequest(HttpMethod.POST,
					getGitApiBaseUrl() + "/repos/" + getUserName() + "/" + repository + "/git/refs", paramMap);
			result.setResult(jsonResponse);
		} else {

		}

		result.setStatus(paramsValid ? "SUCCESS" : "FAIL");
		return result;

	}

	public boolean isValidBranchName(String name) {
		return !reservedBranchNames.contains(name);
	}
}