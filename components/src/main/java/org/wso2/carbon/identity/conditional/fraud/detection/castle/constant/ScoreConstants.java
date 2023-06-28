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
 * Constants used to extract the risk scores from the response.
 */
public abstract class ScoreConstants {

    public static final String RISK_SCORE_KEY = "risk";
    public static final String DETAILED_SCORES_INITIAL_KEY = "scores";
    public static final String DETAILED_SCORES_FINAL_KEY = "score";
    public static final String ACCOUNT_ABUSE_KEY = "account_abuse";
    public static final String ACCOUNT_TAKEOVER_KEY = "account_takeover";
    public static final String BOT_KEY = "bot";

    public static final String RISK_DISPLAY_NAME = "Risk: ";
    public static final String ACCOUNT_ABUSE_DISPLAY_NAME = "Account Abuse Risk Score: ";
    public static final String ACCOUNT_TAKEOVER_DISPLAY_NAME = "Account Takeover Risk Score: ";
    public static final String BOT_DISPLAY_NAME = "Bot Risk Score: ";

    public static final String RESPONSE_NULL_MESSAGE = "Castle response is null.";

}

