appGreenbox.factory("loginInterceptor", function($q, $location) {
	return {
		'request': function(config) {
			console.log("EXECUTOU");
			config.headers.Authorization = 'Bearer ' + localStorage.getItem("userToken");
			return config;
		},
		
		'responseError': function(rejection) {
			console.log("hello");
			$location.path("/home");
			
			return $q.reject(rejection);
		}
	};
});