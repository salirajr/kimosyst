
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

(function () {
    'use strict';
    angular.getApplicationModule()
            .factory('Dalg', DialogService);
    function DialogService($uibModal, $log) {
        return {
            confirm: askConfirm, // yes or no
            delete: askDeletion//deletion
        };


        function dialog(input, fnConfirmed, fnDismissed) {
            var ask = $uibModal.open({
                backdrop: 'static',
                keyboard: false,
                templateUrl: 'sviews/modal-confirm.html',
                size: "sm",
                controller: "ConfirmationDialogCtrl",
                resolve: {
                    input: function () {
                        return input;
                    }
                }

            });

            ask.result.then(fnConfirmed, fnDismissed);

        }

        function askConfirm(quest, fnConfirmed) {

            var input = {"quest": quest, "askReason": false, "buttonType":"DismissProceed"};
            dialog(input, fnConfirmed, function () {});
        }

        function askDeletion(quest, fnConfirmed, fnDismissed) {
            var input = {"quest": quest, "askReason": true, "buttonType":"DeleteCancel"};
            dialog(input, fnConfirmed, fnDismissed);
        }
    }
})();
