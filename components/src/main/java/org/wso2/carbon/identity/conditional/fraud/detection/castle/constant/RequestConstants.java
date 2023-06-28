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
 * Constants used to do requests using Castle SDK.
 */
public abstract class RequestConstants {

    public static final String KEY_TYPE = "type";
    public static final String KEY_STATUS = "status";
    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_AGENT = "User-Agent";
    public static final String KEY_HOSTNAME = "Host";
    public static final String KEY_CASTLE_REQUEST_TOKEN = "castleRequestToken";
    public static final String VALUE_LOGIN = "$login";
    public static final String VALUE_SUCCESS = "$succeeded";
    public static final String VALUE_FAIL = "$failed";
    public static final String URL_USER_STORE_EMAIL = "http://wso2.org/claims/emailaddress";

}

