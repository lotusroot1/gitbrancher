package com.alex.gitbrancher.controller.rest;

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
import com.alex.gitbrancher.ajax.model.AjaxStatus;
import com.alex.gitbrancher.rest.RestHelper;
import com.alex.gitbrancher.rest.RestResponse;
import com.alex.gitbrancher.validator.BrancherValidator;
import com.alex.gitbrancher.validator.ValidatorResult;
import com.alex.gitbrancher.validator.create.BrancherValidatorCreate;

@RestController
public class AjaxController {

	@Autowired
	private RestHelper requestHelper;

	@Value("${brancher.github.base.url}")
	private String gitBaseUrl;

	@Value("${brancher.github.owner.username}")
	private String gitUsername;

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
		String url = this.gitBaseUrl + "/repos/" + this.gitUsername + "/" + repository + "/branches";
		// if you change this, please check the js too, "ApiModule.getBranches"
		boolean useMapResponse = true;
		if (useMapResponse) {
			List<Object> restResponse = requestHelper.getArrayRequest(HttpMethod.GET, url, null);
			result.setResult(restResponse);
		} else {
			RestResponse restResponse = requestHelper.sendRequest(HttpMethod.GET, url);
			result.setResult(restResponse.getResult());
		}
		return result;
	}

	@RequestMapping(value = "/create/branch", method = RequestMethod.POST)
	public AjaxResponse createBranch(@RequestParam(name = "repo", required = true) String repository,
			@RequestParam(name = "sha", required = true) String sha,
			@RequestParam(name = "name", required = true) String name) throws Exception {
		AjaxResponse result = new AjaxResponse();

		/**
		 * @TODO scan and class-load all BrancherValidator implementations, then
		 *       fire all ValidatorStep.PRE_CREATE validators
		 */
		BrancherValidator validator = new BrancherValidatorCreate();
		ValidatorResult validatorResult = validator.validate(repository, sha, name);

		boolean useMapResponse = false;
		if (validatorResult.getIsSuccess()) {
			// /repos/:owner/:repo/git/refs
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("sha", sha);
			paramMap.put("ref", "refs/heads/" + name);
			String url = this.gitBaseUrl + "/repos/" + this.gitUsername + "/" + repository + "/git/refs";
			if (useMapResponse) {
				Map<String, Object> jsonResponse = requestHelper.getObjectRequest(HttpMethod.POST, url, paramMap);
				result.setResult(jsonResponse);
			} else {
				RestResponse restResponse = requestHelper.sendRequest(HttpMethod.POST, url, paramMap);
				result.setResult(restResponse.getResult());
			}

			/**
			 * @TODO scan and class-load all BrancherValidator implementations,
			 *       then fire all ValidatorStep.POST_CREATE validators
			 */
		} else {
			// don't care about warnings right now

			// display the first error message for now
			String message = "Generic validator error response.";
			if (validatorResult.getHasError()) {
				message = validatorResult.getErrors().get(0).getMessage();
			}
			result.setResult(message);
			result.setStatus(AjaxStatus.FAILURE);
		}
		return result;

	}
}