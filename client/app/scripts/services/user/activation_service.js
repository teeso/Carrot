/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc service
 * @name Carrot.ActivationService
 * @description
 * # ActivationService
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('ActivationService', function ($http, $location, $log) {
        var callbackReset = function () {
            // Clear params
            delete $location.$$search.token;
            delete $location.$$search.email;
            $location.$$compose();

            // Redirect to login
            $location.path("/login");
        };

        return {
            activate: function (token, email, callback) {
                // Activate account
                $http.get(baseURL + '/client/activate?email=' + email + '&token=' + token).
                    success(function (data, status, headers, config) {
                        $log.info("Successfully activated.");
                        callbackReset();
                        callback(true);
                    }).
                    error(function (data, status, headers, config) {
                        $log.info("Error. Not activated.");
                        callbackReset();
                        callback(false);
                    });
            },
            forget: function (email, callback) {
                $http.get(baseURL + '/client/forgot?email=' + email).
                    success(function (data, status, headers, config) {
                        $log.info("Successfully reset password.");
                        callbackReset();
                        callback(true);
                    }).
                    error(function (data, status, headers, config) {
                        $log.info("Error. Password not reset.");
                        callbackReset();
                        callback(false, status);
                    });
            },
            reset: function (token, email, callback) {
                $http.get(baseURL + '/client/reset?email=' + email + '&token=' + token).
                    success(function (data, status, headers, config) {
                        $log.info("Successfully reset password.");
                        callbackReset();
                        callback(true);
                    }).
                    error(function (data, status, headers, config) {
                        $log.info("Error. Password not reset.");
                        callbackReset();
                        callback(false);
                    });
            }
        }
    });