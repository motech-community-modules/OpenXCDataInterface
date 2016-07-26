
(function () {
    'use strict';

    /* App Module */

    angular.module('OpenXCDataInterface', ['motech-dashboard', 'OpenXCDataInterface.controllers', 'OpenXCDataInterface.directives', 'OpenXCDataInterface.services', 'ngCookies'])
        .config(['$routeProvider',
        function ($routeProvider) {
            $routeProvider.
                when('/OpenXCDataInterface/settings', {templateUrl: '../OpenXCDataInterface/resources/partials/settings.html', controller: 'OpenXCDataInterfaceCtrl'});
    }]);
}());
