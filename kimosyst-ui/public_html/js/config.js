/**
 * INSPINIA - Responsive Admin Theme
 *
 * Inspinia theme use AngularUI Router to manage routing and views
 * Each view are defined as state.
 * Initial there are written state for all view in theme.
 *
 */
function config($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, IdleProvider, KeepaliveProvider) {

    // Configure Idle settings
    IdleProvider.idle(5); // in seconds
    IdleProvider.timeout(120); // in seconds

    $urlRouterProvider.otherwise("/login");

    $ocLazyLoadProvider.config({
        // Set to true if you want to see what and when is dynamically loaded
        debug: false
    });

    $stateProvider
            .state('login', {
                url: "/login",
                templateUrl: "sviews/login.html",
                data: {pageTitle: 'jrsalira', specialClass: 'gray-bg'},
                controller: loginCtrl,
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            }

                        ]);
                    }
                }
            })
            .state('mutasi', {
                abstract: true,
                url: "/mutasi",
                templateUrl: "sviews/common/content_top_navigation.html",
            })
            .state('mutasi.inputraws', {
                url: "/inputraws",
                templateUrl: "sviews/mutasi-inputraws.html",
                controller: mutasiInputRawsCtrl,
                data: {pageTitle: 'Input Raws(.csv)'},
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                name: 'datePicker',
                                files: ['css/plugins/datapicker/angular-datapicker.css', 'js/plugins/datapicker/angular-datepicker.js']
                            },
                            {
                                files: ['css/plugins/clockpicker/clockpicker.css', 'js/plugins/clockpicker/clockpicker.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            }

                        ]);
                    }
                }
            })
            .state('mutasi.inputincome', {
                url: "/inputincome",
                templateUrl: "sviews/mutasi-inputincome.html",
                data: {pageTitle: 'Income Submission'},
                controller: mutasiInputIncomeCtrl,
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            }
                            ,
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            }
                            ,
                            {
                                name: 'ui.knob',
                                files: ['js/plugins/jsKnob/jquery.knob.js', 'js/plugins/jsKnob/angular-knob.js']
                            },
                            {
                                files: ['css/plugins/ionRangeSlider/ion.rangeSlider.css', 'css/plugins/ionRangeSlider/ion.rangeSlider.skinFlat.css', 'js/plugins/ionRangeSlider/ion.rangeSlider.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                name: 'nouislider',
                                files: ['css/plugins/nouslider/jquery.nouislider.css', 'js/plugins/nouslider/jquery.nouislider.min.js', 'js/plugins/nouslider/angular-nouislider.js']
                            },
                            {
                                name: 'datePicker',
                                files: ['css/plugins/datapicker/angular-datapicker.css', 'js/plugins/datapicker/angular-datepicker.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js']
                            },
                            {
                                files: ['css/plugins/clockpicker/clockpicker.css', 'js/plugins/clockpicker/clockpicker.js']
                            },
                            {
                                name: 'ui.switchery',
                                files: ['css/plugins/switchery/switchery.css', 'js/plugins/switchery/switchery.js', 'js/plugins/switchery/ng-switchery.js']
                            },
                            {
                                name: 'colorpicker.module',
                                files: ['css/plugins/colorpicker/colorpicker.css', 'js/plugins/colorpicker/bootstrap-colorpicker-module.js']
                            },
                            {
                                name: 'ngImgCrop',
                                files: ['js/plugins/ngImgCrop/ng-img-crop.js', 'css/plugins/ngImgCrop/ng-img-crop.css']
                            },
                            {
                                serie: true,
                                files: ['js/plugins/daterangepicker/daterangepicker.js', 'css/plugins/daterangepicker/daterangepicker-bs3.css']
                            },
                            {
                                name: 'daterangepicker',
                                files: ['js/plugins/daterangepicker/angular-daterangepicker.js']
                            },
                            {
                                files: ['css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css']
                            },
                            {
                                name: 'ui.select',
                                files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                            },
                            {
                                files: ['css/plugins/touchspin/jquery.bootstrap-touchspin.min.css', 'js/plugins/touchspin/jquery.bootstrap-touchspin.min.js']
                            },
                            {
                                name: 'ngTagsInput',
                                files: ['js/plugins/ngTags/ng-tags-input.min.js', 'css/plugins/ngTags/ng-tags-input-custom.min.css']
                            },
                            {
                                files: ['js/plugins/dualListbox/jquery.bootstrap-duallistbox.js', 'css/plugins/dualListbox/bootstrap-duallistbox.min.css']
                            },
                            {
                                name: 'frapontillo.bootstrap-duallistbox',
                                files: ['js/plugins/dualListbox/angular-bootstrap-duallistbox.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js']
                            },
                            {
                                files: ['js/plugins/sweetalert/sweetalert.min.js', 'css/plugins/sweetalert/sweetalert.css']
                            },
                            {
                                name: 'oitozero.ngSweetAlert',
                                files: ['js/plugins/sweetalert/angular-sweetalert.min.js']
                            }

                        ]);
                    }
                }
            })
            .state('income', {
                abstract: true,
                url: "/income",
                templateUrl: "sviews/common/content_top_navigation.html",
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            }
                        ]);
                    }
                }
            })
            .state('vwincome', {
                abstract: true,
                url: "/vwincome",
                templateUrl: "sviews/common/content_top_navigation.html",
            })
            .state('vwincome.findall', {
                url: "/findall",
                templateUrl: "sviews/vwincome-findall.html",
                controller: vwIncomeFindAllCtrl,
                data: {pageTitle: 'E-commerce grid'},
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                name: 'datePicker',
                                files: ['css/plugins/datapicker/angular-datapicker.css', 'js/plugins/datapicker/angular-datepicker.js']
                            },
                            {
                                files: ['css/plugins/clockpicker/clockpicker.css', 'js/plugins/clockpicker/clockpicker.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            },
                            {
                                name: 'ui.select',
                                files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                            }

                        ]);
                    }
                }
            })
            .state('vwmutasi', {
                abstract: true,
                url: "/vwmutasi",
                templateUrl: "sviews/common/content_top_navigation.html",
            })
            .state('vwmutasi.findsubmission', {
                url: "/findsubmission",
                templateUrl: "sviews/vwmutasi-findsubmission.html",
                controller: vwMutasiFindSubmission,
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                name: 'datePicker',
                                files: ['css/plugins/datapicker/angular-datapicker.css', 'js/plugins/datapicker/angular-datepicker.js']
                            },
                            {
                                files: ['css/plugins/clockpicker/clockpicker.css', 'js/plugins/clockpicker/clockpicker.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            },
                            {
                                files: ['css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js']
                            },
                            {
                                name: 'ui.select',
                                files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                            }

                        ]);
                    }
                }
            })
            .state('vwmutasi.submission', {
                url: "/submission",
                templateUrl: "sviews/vwmutasi-submission.html",
                controller: vwMutasiSubmission,
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                name: 'datePicker',
                                files: ['css/plugins/datapicker/angular-datapicker.css', 'js/plugins/datapicker/angular-datepicker.js']
                            },
                            {
                                files: ['css/plugins/clockpicker/clockpicker.css', 'js/plugins/clockpicker/clockpicker.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            },
                            {
                                files: ['css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js']
                            },
                            {
                                name: 'ui.select',
                                files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                            }

                        ]);
                    }
                }
            })
            .state('vwmutasi.incomecompliance', {
                url: "/incomecompliance",
                templateUrl: "sviews/vwmutasi-incomecompliance.html",
                controller: vwMutasiIncomeCompliance,
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                name: 'datePicker',
                                files: ['css/plugins/datapicker/angular-datapicker.css', 'js/plugins/datapicker/angular-datepicker.js']
                            },
                            {
                                files: ['css/plugins/clockpicker/clockpicker.css', 'js/plugins/clockpicker/clockpicker.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            },
                            {
                                name: 'ui.select',
                                files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                            }

                        ]);
                    }
                }
            })
            .state('rfrc', {
                abstract: true,
                url: "/rfrc",
                templateUrl: "sviews/common/content_top_navigation.html",
            })
             .state('rfrc.syst', {
                url: "/syst",
                templateUrl: "sviews/rfrc-data.html",
                controller: rfrcSystDataCtrl,
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            },
                            {
                                name: 'ui.select',
                                files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                            },
                            {
                                files: ['css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js']
                            }

                        ]);
                    }
                }
            })
            .state('rfrc.data', {
                url: "/data",
                templateUrl: "sviews/rfrc-data.html",
                controller: rfrcDataCtrl,
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            },
                            {
                                name: 'ui.select',
                                files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                            },
                            {
                                files: ['css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js']
                            }

                        ]);
                    }
                }
            })
            .state('rfrc.xpression', {
                url: "/xpression",
                templateUrl: "sviews/rfrc-xpression.html",
                controller: rfrcXPressionCtrl,
                resolve: {
                    loadPlugin: function ($ocLazyLoad) {
                        return $ocLazyLoad.load([
                            {
                                files: ['js/plugins/moment/moment.min.js']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'toaster',
                                files: ['js/plugins/toastr/toastr.min.js', 'css/plugins/toastr/toastr.min.css']
                            },
                            {
                                insertBefore: '#loadBefore',
                                name: 'localytics.directives',
                                files: ['css/plugins/chosen/bootstrap-chosen.css', 'js/plugins/chosen/chosen.jquery.js', 'js/plugins/chosen/chosen.js']
                            },
                            {
                                files: ['js/plugins/jasny/jasny-bootstrap.min.js', 'css/plugins/jasny/jasny-bootstrap.min.css']
                            },
                            {
                                files: ['js/plugins/footable/footable.all.min.js', 'css/plugins/footable/footable.core.css']
                            },
                            {
                                name: 'ui.footable',
                                files: ['js/plugins/footable/angular-footable.js']
                            },
                            {
                                name: 'ui.select',
                                files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                            },
                            {
                                files: ['css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js']
                            },
                            {
                                name: 'ui.switchery',
                                files: ['css/plugins/switchery/switchery.css', 'js/plugins/switchery/switchery.js', 'js/plugins/switchery/ng-switchery.js']
                            }

                        ]);
                    }
                }
            })
            ;

}

function initiation(Auth, $rootScope, $state, $log) {

    //$rootScope.$state = $state;
    $rootScope.ksyst = {
        "title": "kimosyst"
    };

    if (Auth.isLoggedIn()) {
        $rootScope.activeUser = Auth.getAuth();
        Auth.loadAuth();
        $log.info($rootScope.activeUser);
        $log.info($rootScope.activeUser.uName + "'s time: " + new Date());
    } else {
        $state.go("login");
    }

    $rootScope.logout = function () {
        Auth.doLogout(function () {
            delete $rootScope.activeUser;
            $state.go('login');
        });
    };


}
angular.module('inspinia')
        .config(config)
        .constant("CONST", {
            formatSVRTDate: "YYYY-MM-DD HH:mm:ss",
            formatSVRDate: "YYYY-MM-DD",
            formatTDate: "yyyy-MM-dd HH:mm"
        })
        .run(initiation);
