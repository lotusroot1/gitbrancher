(function($){
	
	var ApiModule = {
		getRepositories: function(callback){
			$.ajax("/brancher/search/repos", {
				success: function(data, status, jqXHR){
					var json = JSON.parse(data.result);
					if($.isFunction(callback)){
						callback(json);
					}
				}
			});
		},
		
		getBranches: function(repository, callback){
			$.ajax("/brancher/search/" + repository + "/branches", {
				success: function(data, status, jqXHR){
					var json = JSON.parse(data.result);
					if($.isFunction(callback)){
						callback(json);
					}
				}
			});
		},
		
		createNewBranch: function(repositoryName, baseBranchSha, newBranchName, callback){
			$.ajax("/brancher/create/branch", {
				method: "POST",
				data: {
					repo: repositoryName,
					sha: baseBranchSha,
					name: newBranchName
				},
				
				success: function(data, status, jqXHR){
					var json = JSON.parse(data.result);
					if($.isFunction(callback)){
						callback(json);
					}
				}
			});
		}
	};
	
	$(document).ready(function(){
		
		$('#createButton').on("click", function(event){
			var repositoryName = $('#repositoryName').val();
			var baseBranchSha = $('#baseBranch').val();
			var newBranchName = $("#newBranchName").val();
			ApiModule.createNewBranch(repositoryName, baseBranchSha, newBranchName, function(result){
				if(result){
					
				}
			});
		});
		
		$('#repositoryName').on("change", function(event){
			var repoName = this.value;
			var baseBranchSelect = $('#baseBranch');
			
			// disable while loading
			baseBranchSelect.attr("disabled", true);
			
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
		});
		
		ApiModule.getRepositories(function(data){
			var repoSelect = $('#repositoryName');
			repoSelect.attr("disabled", true);
			
			$.each(data, function(key, value) {
				repoSelect.append($("<option></option>")
					.attr("value", value.name)
					.attr("title", value.name)
					.text(value.name));
			});
			
			repoSelect.attr("disabled", false);
		});
	});
})(jQuery);
