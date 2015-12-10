(function($){
	
	var ApiModule = {
		getRepositories: function(callback){
			$.ajax(Context.path + "/search/repos", {
				success: function(data, status, jqXHR){
					var json = JSON.parse(data.result);
					if($.isFunction(callback)){
						callback(json);
					}
				}
			});
		},
		
		getBranches: function(repository, callback){
			$.ajax(Context.path + "/search/" + repository + "/branches", {
				success: function(data, status, jqXHR){
					var json = JSON.parse(data.result);
					if($.isFunction(callback)){
						callback(json);
					}
				}
			});
		},
		
		createNewBranch: function(repositoryName, baseBranchSha, newBranchName, callback){
			if($.trim(repositoryName) === "" || $.trim(baseBranchSha) === "" || $.trim(newBranchName) === ""){
				return;
			}else{
				$.ajax(Context.path + "/create/branch", {
					method: "POST",
					data: {
						repo: repositoryName,
						sha: baseBranchSha,
						name: newBranchName
					},
					
					success: function(data, status, jqXHR){
						if($.isFunction(callback)){
							callback(data);
						}
					}
				});
			}
		}
	};
	
	var FormModule = {
		resetSelect: function(select){
			select.find("option").remove();
			select.attr("disabled", true);
		},
		
		resetInput: function(input){
			input.val("");
		},
		
		resetForm: function(){
			this.resetSelect($('#repositoryName'));
			this.resetSelect($('#baseBranch'));
			this.resetInput($("#newBranchName"));
			
			ApiModule.getRepositories(function(data){
				var repoSelect = $('#repositoryName');
				repoSelect.attr("disabled", true);
				
				repoSelect.find("option").remove();
				$.each(data, function(key, value) {
					repoSelect.append($("<option></option>")
						.attr("value", value.name)
						.attr("title", value.name)
						.text(value.name));
				});
				
				repoSelect.attr("disabled", false);
			});
		},
		
		resetForm: function(callback){
			var baseBranchSelect = $('#baseBranch');
			baseBranchSelect.attr("disabled", true);
			
			ApiModule.getRepositories(function(data){
				var repoSelect = $('#repositoryName');
				repoSelect.attr("disabled", true);
				
				repoSelect.find("option").remove();
				var defaultOptionText = "Select a repository";
				repoSelect.append($("<option></option>")
						.attr("value", "")
						.attr("selected", "selected")
						.attr("title", defaultOptionText)
						.text(defaultOptionText));
				$.each(data, function(key, value) {
					repoSelect.append($("<option></option>")
						.attr("value", value.name)
						.attr("title", value.name)
						.text(value.name));
				});
				
				repoSelect.attr("disabled", false);
				
				$("#result").empty();
				if($.isFunction(callback)){
					callback();
				}
			});
		}
	};
		
	$(document).ready(function(){
		
		$('#createButton').on("click", function(event){
			var repositoryName = $('#repositoryName').val();
			var baseBranchSha = $('#baseBranch').val();
			var newBranchName = $("#newBranchName").val();
			ApiModule.createNewBranch(repositoryName, baseBranchSha, newBranchName, function(data){
				if(data.status == "SUCCESS"){
					var json = JSON.parse(data.result);
					FormModule.resetForm(function(){
						$("#result").append("Branch created [" + json.url + "].");
					});
				}
			});
		});
		
		$('#repositoryName').on("change", function(event){
			var repoName = this.value;
			var baseBranchSelect = $('#baseBranch');
			
			// disable while loading
			baseBranchSelect.attr("disabled", true);
			
			if(repoName != null){
				repoName = $.trim(repoName);
				
				if(repoName.length > 0){
					ApiModule.getBranches(repoName, function(data){
						baseBranchSelect.find("option").remove();
						$.each(data, function(key, value) {
							baseBranchSelect.append($("<option></option>")
									.attr("value", value.commit.sha)
									.attr("title", value.name + "(" + value.commit.sha + ")")
									.text(value.name));
						});
						
						// enable after callback success
						baseBranchSelect.attr("disabled", false);
						
					});
				}
			}
		});
		
		FormModule.resetForm();
	});
})(jQuery);
