
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
    'use strict';
    angular.getApplicationModule()
            .factory('Auth', AuthenticationService);
    function AuthenticationService($http, $localStorage, $log) {
        
        return {
            doLogin: doLogin,
            doLogout: doLogout,
            isLoggedIn: isLoggedIn,
            getAuth: getAuth,
            loadAuth: loadAuth,
            doReAuth: doReAuth
        };
        
        function doReAuth() {
            return {};
        }

        function getAuth() {
            return {"uName": $localStorage.ksys.authorization.username, "requestLock": false};
        }

        function loadAuth() {
            // add jwt token to auth header for all requests made by the $http service
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.ksys.authorization.token;
            
        }

        function isLoggedIn() {
            return $localStorage.ksys && $localStorage.ksys.authorization;
        }

        function doLogin(username, password, actkey, cBack, errCBack) {
            $http.post('/ksyt-pblc/auth', {username: username, password: password, actKey: actkey})
                    .success(function (response) {
                        $localStorage.ksys = {};
                        $localStorage.ksys.authorization = {"token": response.token, "username": username};
                        loadAuth();
                        cBack(response);
                    }).error(function () {
                        $log.info("error auth!");
                errCBack();
            });
        }

        function doLogout(fnCBack) {
            // remove user from local storage and clear http auth header
            delete $localStorage.ksys.authorization;
            $http.defaults.headers.common.Authorization = undefined;
            $log.info("isLoggedIn:"+isLoggedIn());
            fnCBack();
        }
    }
})();
