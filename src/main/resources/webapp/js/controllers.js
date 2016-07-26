(function() {
    'use strict';

    /* Controllers */
    var controllers = angular.module('OpenXCDataInterface.controllers', []);

    controllers.controller('OpenXCDataInterfaceCtrl', function($scope, $http) {
             $scope.hideMessages = function() {
                $scope.saveSuccess = false;
                $scope.saveError = false;
                $scope.testSuccess = false;
                $scope.testError = false;
                $scope.retrievalError = false;
             };
             $scope.getBoolean = function(value) {
                 if (value === 'true')
                    return true;
                 else
                    return false;
             };
            $scope.hideMessages();
            $http.get('../OpenXCDataInterface/applicationSettings')
                .success(function (response) {
                    //alert(response);
                    $scope.applicationSettings = response;

                   $scope.originalSettings = angular.copy($scope.applicationSettings);
                })
                .error(function (response) {
                    //alert(response);
                    $scope.retrievalError = true;
                });

             $scope.saveSettings = function (settings) {
                 $scope.hideMessages();
                 blockUI();
                 $http.post('../OpenXCDataInterface/applicationSettings/save', settings).
                     success(
                        function (response) {
                            //alert($scope.msg(response.message));
                            $scope.saveSuccess = true;
                            unblockUI();
                        }
                     ).
                     error(function (response) {
                        //alert($scope.msg("OpenXCDataInterface.applicationSetting.save.error.message"));
                        $scope.saveError = true;
                        unblockUI();
                     });
             };
             $scope.testSettings = function (settings) {
                $scope.hideMessages();
                blockUI();
                $http.post('../OpenXCDataInterface/applicationSettings/test', settings).
                  success(
                     function (response) {

                         $scope.testSuccess = $scope.getBoolean(response.success);
                         $scope.testError = !$scope.getBoolean(response.success);
                         unblockUI();
                     }
                  ).
                  error(function (response) {

                     $scope.testError = true;
                     unblockUI();
                  });
             };

    });
}());
