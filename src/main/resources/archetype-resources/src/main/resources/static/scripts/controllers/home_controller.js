(function () {
    "use strict";

    var app = angular.module('App.controllers');

    app.controller('HomeCtrl', function (Credentials, $routeParams, $location) {
        var vm = this;
        vm.courses = [];
        vm.state = 'waiting';
        
        function init() {
            if (Credentials.authenticatedUser()) {
                vm.state = 'ready';
            }
        }
        
        init();
    });
})();