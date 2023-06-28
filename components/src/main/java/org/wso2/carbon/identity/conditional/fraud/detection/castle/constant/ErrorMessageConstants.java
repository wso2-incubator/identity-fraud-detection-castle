/*
 * Copyright (c) 2023, WSO2 LLC. (http://www.wso2.com).
 *
 * WSO2 LLC. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.conditional.fraud.detection.castle.constant;

/**
 * Constants used in Error Messages.
 */
public abstract class ErrorMessageConstants {

    public static final String ERROR_USER_ID = "An error occurred while getting user id.";
    public static final String ERROR_USER_EMAIL = "An error occurred while getting user email.";
    public static final String ERROR_CASTLE_CONFIGURATION = "Castle SDK configuration error.";
    public static final String ERROR_CASTLE_REQUEST_TOKEN = "Invalid Request Token.";
    public static final String ERROR_CASTLE_DATA = "Data missing or invalid.";
    public static final String ERROR_GETTING_USER_STORE = "Error while getting user store.";
    public static final String RESPONSE_NULL_MESSAGE = "Castle response is null.";

}

