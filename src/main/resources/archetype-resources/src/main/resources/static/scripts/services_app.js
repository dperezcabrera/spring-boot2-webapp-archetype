
(function () {
    "use strict";

    var app = angular.module('App.services', []);
    var actions = {update: {method: 'PUT'}};

    app.factory('Users', function ($resource) {
        return $resource('/users/:id', {id: '@id'}, actions);
    }).factory('Login', function ($resource) {
        return $resource('/login', {}, actions);
    });
    
    app.factory('httpRequestInterceptor', function ($q, $location, Credentials) {
        return {
            'responseError': function (rejection) {
                if (rejection.data && rejection.status === 403) {
                    $location.path('/login/error');
                    Credentials.logout();
                    return {};
                }
                return $q.reject(rejection);
            }
        };
    });
    
    app.service('Credentials', function () {
        this.fromView = "/home";

        this.user = {};

        this.listeners = [];

        this.registerUser = function (username) {
            this.user.username = username;
            this.listeners.forEach(function (listener) {
                listener({event: 'authenticated', user: username});
            });
        };

        this.registerAuthenticationListener = function (callback) {
            if (callback) {
                this.listeners.push(callback);
            }
        };

        this.logout = function () {
            var userName = this.user.username;
            this.user = {};
            this.listeners.forEach(function (listener) {
                listener({event: 'logout', user: userName});
            });
        };

        this.authenticatedUser = function () {
            return this.user.username;
        };
    });
})();

