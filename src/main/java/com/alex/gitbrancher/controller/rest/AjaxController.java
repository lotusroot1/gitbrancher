package com.alex.gitbrancher.controller.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alex.gitbrancher.ajax.model.AjaxResponse;
import com.alex.gitbrancher.rest.RestHelper;
import com.alex.gitbrancher.rest.RestResponse;

@RestController
public class AjaxController {

	@Autowired
	private RestHelper requestHelper;

	@Value("${brancher.github.base.url}")
	private String gitBaseUrl;

	@Value("${brancher.github.owner.username}")
	private String gitUsername;

	public static final List<String> reservedBranchNames;

	static {
		reservedBranchNames = new ArrayList<String>();
		reservedBranchNames.add("master");
	}

	@RequestMapping(value = "/search/repos")
	public AjaxResponse getRepositories() throws Exception {
		AjaxResponse result = new AjaxResponse();
		RestResponse restResponse = requestHelper.sendRequest(HttpMethod.GET,
				this.gitBaseUrl + "/users/" + this.gitUsername + "/repos");
		result.setResult(restResponse.getResult());
		return result;

	}

	@RequestMapping(value = "/search/{repo}/branches")
	public AjaxResponse getBranches(@PathVariable("repo") String repository) throws Exception {

		if (StringUtils.isEmpty(repository)) {

		}
		AjaxResponse result = new AjaxResponse();
		RestResponse restResponse = requestHelper.sendRequest(HttpMethod.GET,
				this.gitBaseUrl + "/repos/" + this.gitUsername + "/" + repository + "/branches");
		result.setResult(restResponse.getResult());
		return result;

	}

	@RequestMapping(value = "/create/branch", method = RequestMethod.POST)
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
			// Map<String, Object> jsonResponse =
			// requestHelper.getObjectRequest(HttpMethod.POST,
			// this.gitBaseUrl + "/repos/" + this.gitUsername + "/" + repository
			// + "/git/refs", paramMap);
			// result.setResult(jsonResponse);

			RestResponse restResponse = requestHelper.sendRequest(HttpMethod.POST,
					this.gitBaseUrl + "/repos/" + this.gitUsername + "/" + repository + "/git/refs", paramMap);
			result.setResult(restResponse.getResult());
		} else {

		}
		return result;

	}

	public boolean isValidBranchName(String name) {
		return !reservedBranchNames.contains(name);
	}
}