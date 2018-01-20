(function () {
    "use strict";

    var app = angular.module('App.controllers');

    app.controller('LoginCtrl', function (Credentials, $routeParams, $location, Login) {
        var vm = this;
        vm.credentials = {};
        
        if ($routeParams.param === 'error' || $routeParams.param === 'logout') {
            Credentials.user = {};
            vm.state = 'login';
        } else if (Credentials.authenticatedUser()) {
            $location.path(Credentials.fromView);
        } else {
            vm.state = 'login';
            Credentials.user = {};
        }

        vm.login = function () {
            if (vm.credentials.login && vm.credentials.password) {
                vm.state = 'waiting';
                Login.save({username: vm.credentials.login, password: vm.credentials.password}).$promise.then(function () {
                    vm.state = 'login';
                    vm.credentials.password = "";
                    Credentials.registerUser(vm.credentials.login);
                    var next = Credentials.fromView;
                    Credentials.fromView = "/home";
                    $location.path(next);
                }, function () {
                    vm.state = 'login';
                    Credentials.logout();
                    vm.credentials.password = "";
                    vm.credentials.error = "Usuario o contraseña no validos";
                });
            }
        };
    });
})();