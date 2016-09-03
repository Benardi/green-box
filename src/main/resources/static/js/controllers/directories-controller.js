angular.module('app').controller("directoriesController", function($scope, $state, $localStorage, $http, $rootScope, $stateParams) {
	$scope.user = $localStorage.session.user;
	$scope.rootDirectory = $scope.user.userDirectory;
	$scope.currentDirectory = $scope.rootDirectory;
	$scope.openedFolders = [$scope.rootDirectory];
	
	
	$localStorage.session.currentPath = $stateParams.folderPath;
	
	$scope.newFolderName = "";
	$scope.currentType = "";
	
	$scope.folderClick = function(folder) {
		$state.go('dashboard.directories', {folderPath: folder.path});
	}
	
	$scope.actionClick = function(item){
		$localStorage.selectedItem = item;
	}
	
	
	$scope.renameClick = function(item, type){
		$localStorage.selectedItem = item;
		$scope.currentType = type;
	}
	
	$scope.fileClick = function(file) {
		$localStorage.clickedFile = file;
		$state.go('dashboard.file');
	}
	
	$scope.share = function(sharingType){
		requestData = {};
		requestData.user = $scope.user;
		requestData.userSharedWith = $scope.userSharedWith;
		requestData.fileName = $localStorage.selectedItem.name;
		
		$http.post('to-be-completed', requestData, sharingType)
		.then(function(response){
			$localStorage.session.user = response.data;
			window.alert('File successfully shared');
		}, function(response){
			window.alert(response.data.message);
			window.alert('whoops!');
		});
	}
	
	$scope.renameItem = function(){
		requestData = {};
		requestData.user = $scope.user;
		requestData.newName = $scope.newName;
		requestData.oldName = $localStorage.selectedItem.name;
		requestData.folderPath = $localStorage.session.currentPath;
		
		if($scope.currentType == 'File'){
			renameFile();
		} else if( $scope.currentType == 'Folder'){
			renameFolder();
		} else{
			window.alert('NOT FILE NOR FOLDER');
		}
	}
	
	function renameFile(){
		$http.post('/server/userdirectory/renamefile', requestData)
		.then(function(response){
			$localStorage.session.user = response.data;
			window.alert('File renamed successfully');
			update();
			goToPath($stateParams.folderPath);
		}, function(response){
			window.alert(response.data.message);
			window.alert('whoops!');
		});
	}
	
	function renameFolder(){
		$http.post('/server/userdirectory/renamefolder', requestData)
		.then(function(response){
			$localStorage.session.user = response.data;
			window.alert('Folder renamed successfully');
			update();
			goToPath($stateParams.folderPath);
		}, function(response){
			window.alert(response.data.message);
			window.alert('whoops!');
		});
		
	}
	
	$scope.newFolder = function() {
		
		requestData = {};
		requestData.user = $scope.user;
		requestData.folderName = $scope.newFolderName;
		requestData.folderPath = $scope.currentDirectory.path;		
		
		
		$http.post('/server/userdirectory/newfolder', requestData)
			.then(function(response) {
				
				$localStorage.session.user = response.data;
				update();
				goToPath($stateParams.folderPath);
			}, function(response) {
				
				window.alert(response.data.message);
				
		});
	} 
	
	$scope.getFilesNFolders = function() {
		return $scope.getFolders().concat($scope.getFiles());
	}
	
	$scope.getFiles = function() {
		return $scope.currentDirectory.files;	
	}	

	$scope.getFolders = function() {
		return $scope.currentDirectory.children;
	}
	
	$scope.newFilePage = function() {
		$localStorage.clickedFile = null;
		$state.go('dashboard.file');
	}
	
	$scope.filesNFoldersToShow = $scope.getFilesNFolders();
	goToPath($stateParams.folderPath);
	
	function findFileOrFolderByName(name, directory) {
		var foldersNFiles = directory.children.concat(directory.files);
		
		for (j = 0; j < foldersNFiles.length; j++) {
			if (foldersNFiles[j].name == name) {
				return foldersNFiles[j];
			}
		}
	
		return null;
	}
	
	function update () {
		$scope.user = $localStorage.session.user;
		$scope.rootDirectory = $scope.user.userDirectory;
		$scope.currentDirectory = $scope.rootDirectory;
		$scope.openedFolders = [$scope.rootDirectory];
	}
	
	function goToPath(path) {
		var directoryNames = path.split("/");
		$scope.currentDirectory = $scope.rootDirectory;
		$scope.filesNFoldersToShow = $scope.getFilesNFolders();
		
		for (i = 1; i < directoryNames.length; i++) {
			$scope.currentDirectory = findFileOrFolderByName(directoryNames[i], $scope.currentDirectory);
			goForward($scope.currentDirectory);
		}
	}
	
	function goForward(folder) {
		$scope.currentDirectory = folder;
		$scope.openedFolders.push(folder);
		$scope.filesNFoldersToShow = $scope.getFilesNFolders();
	}
});