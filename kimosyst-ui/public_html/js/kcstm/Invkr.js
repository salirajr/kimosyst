
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
    'use strict';
    angular.getApplicationModule()
            .factory('Ivkr', InvokerService);
    function InvokerService(Auth, $http, $rootScope, $log, $state, $uibModal, $window) {
        return {
            post: doPost,
            get: doGet,
            delete: doDelete,
            upload: doUpload
        };


        function doPost(uri, payload, fnCBack, fnOnErrorCBack) {

            if (!Auth.isLoggedIn()) {
                $state.go("login");
            } else {
                $http.post(uri, payload).success(function (rsp) {
                    $rootScope.activeUser.requestLock = false;
                    fnCBack(rsp);
                }).error(function (err, status) {
                    if ((status === 406 || status === 401)) {
                        if (!$rootScope.activeUser.requestLock) {
                            $rootScope.activeUser.requestLock = true;
                            $log.error("ReAuthentication Required");
                            $uibModal.open({
                                backdrop: 'static',
                                keyboard: false,
                                templateUrl: 'sviews/modal-authorization.html',
                                controller: 'ReAuthorizationCtrl',
                                size: "sm"
                            }).closed.then(function () {
                                $log.info('Recalling!');
                                $rootScope.activeUser.requestLock = false;
                                doPost(uri, payload, fnCBack, fnOnErrorCBack);
                            });

                        } else {
                            $log.err('Recalling hold!');
                            fnOnErrorCBack(err);
                        }

                    } else {
                        $log.error('Err! ' + status);
                        $rootScope.activeUser.requestLock = false;
                        fnOnErrorCBack(err);
                    }

                });
            }

        }

        function doUpload(uri, payload, reqDef, fnCBack, fnOnErrorCBack) {

            if (!Auth.isLoggedIn()) {
                $state.go("login");
            } else {
                $http.post(uri, payload, reqDef).success(function (rsp) {
                    $log.info('Success!');
                    $rootScope.activeUser.requestLock = false;
                    fnCBack(rsp);
                }).error(function (err, status) {
                    if ((status === 406 || status === 401)) {
                        if (!$rootScope.activeUser.requestLock) {
                            $rootScope.activeUser.requestLock = true;
                            $log.error("ReAuthentication Required");
                            $uibModal.open({
                                backdrop: 'static',
                                keyboard: false,
                                templateUrl: 'sviews/modal-authorization.html',
                                controller: 'ReAuthorizationCtrl',
                                size: "sm"
                            }).closed.then(function () {
                                $log.info('Recalling!');
                                doUpload(uri, payload, reqDef, fnCBack, fnOnErrorCBack);
                            });
                        } else {
                            $log.err('Recalling hold!');
                            fnOnErrorCBack(err);
                        }


                    } else {
                        $log.error('Err! ' + status);
                        $rootScope.activeUser.requestLock = false;
                        fnOnErrorCBack(err);
                    }

                });
            }

        }

        function doGet(uri, fnCBack, fnOnErrorCBack) {

            if (!Auth.isLoggedIn()) {
                $state.go("login");
            } else {
                $http.get(uri).success(function (rsp) {
                    $rootScope.activeUser.requestLock = false;
                    fnCBack(rsp);
                }).error(function (err, status) {
                    if ((status === 406 || status === 401)) {
                        if (!$rootScope.activeUser.requestLock) {
                            $rootScope.activeUser.requestLock = true;
                            $log.error("ReAuthentication Required");
                            $uibModal.open({
                                backdrop: 'static',
                                keyboard: false,
                                templateUrl: 'sviews/modal-authorization.html',
                                controller: 'ReAuthorizationCtrl',
                                size: "sm"
                            }).closed.then(function () {
                                $log.info('Recalling!');
                                doGet(uri, fnCBack, fnOnErrorCBack);
                            });
                        } else {
                            $log.err('Recalling hold!');
                            fnOnErrorCBack(err);
                        }


                    } else {
                        $log.error('Err! ' + status);
                        $rootScope.activeUser.requestLock = false;
                        fnOnErrorCBack(err);
                    }

                });
            }

        }

        function doDelete(uri, fnCBack, fnOnErrorCBack) {

            if (!Auth.isLoggedIn()) {
                $state.go("login");
            } else {
                $http.delete(uri).success(function (rsp) {
                    $rootScope.activeUser.requestLock = false;
                    fnCBack(rsp);
                }).error(function (err, status) {
                    if ((status === 406 || status === 401)) {
                        if (!$rootScope.activeUser.requestLock) {
                            $rootScope.activeUser.requestLock = true;
                            $log.error("ReAuthentication Required");
                            $uibModal.open({
                                backdrop: 'static',
                                keyboard: false,
                                templateUrl: 'sviews/modal-authorization.html',
                                controller: 'ReAuthorizationCtrl',
                                size: "sm"
                            }).closed.then(function () {
                                $log.info('Recalling!');
                                doDelete(uri, fnCBack, fnOnErrorCBack);
                            });
                        } else {
                            $log.err('Recalling hold!');
                            fnOnErrorCBack(err);
                        }
                    } else {
                        $log.error('Err! ' + status);
                        $rootScope.activeUser.requestLock = false;
                        fnOnErrorCBack(err);
                    }

                });
            }
        }
    }
})();
