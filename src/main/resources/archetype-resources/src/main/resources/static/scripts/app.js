
(function () {
    "use strict";

    var app = angular.module('App', ['ngRoute', 'ngResource', 'App.controllers', 'App.services']);

    var authenticationChecker = function (Credentials, $location) {
        if (Credentials.authenticatedUser()) {
            return true;
        } else {
            Credentials.fromView = $location.path();
            $location.path("/login");
        }
        return false;
    };

    app.config(function ($routeProvider) {
        $routeProvider.when('/home', {
            templateUrl: '/views/home.html',
            controller: 'HomeCtrl',
            controllerAs: 'vm',
            resolve: {factory: authenticationChecker}
        }).when('/login', {
            templateUrl: '/views/login.html',
            controller: 'LoginCtrl',
            controllerAs: 'vm'
        }).otherwise({
            redirectTo: '/home'
        });
    });
})();

