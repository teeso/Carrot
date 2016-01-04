/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:ForgetController
 * @description
 * # ForgetController
 *
 * Controller retrieving forgotten passwords
 */
angular.module('Carrot')
    .controller('ForgetController', function ($scope, $rootScope, $routeParams, $location, flash, ActivationService) {
        $scope.forget = function () {
            ActivationService.forget($scope.email, function (success, status) {
                $rootScope.$on('$routeChangeSuccess', function () {
                    if (success) {
                        flash.success = "Check your emails to reset your password.";
                    } else if (parseInt(status) === 404) {
                        flash.error = "There is no account registered with that email address.";
                    } else {
                        flash.error = "There was an error while trying to reset your password.";
                    }
                    $rootScope.$$listeners['$routeChangeSuccess'].pop();
                });
            });
        }
    });