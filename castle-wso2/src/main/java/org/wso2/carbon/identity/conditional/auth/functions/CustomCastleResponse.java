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

package org.wso2.carbon.identity.conditional.auth.functions;

import io.castle.client.model.CastleResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The POJO class for the response from castle API.
 */
public class CustomCastleResponse {

    private static final Log LOG = LogFactory.getLog(CustomCastleResponse.class);
    CastleResponse response;
    private float risk;
    private float accountAbuseScore;
    private float accountTakeoverScore;
    private float botScore;

    public CustomCastleResponse(CastleResponse response) {
        this.response = response;
        setRiskScores();
    }

    public float getRisk() {
        return this.risk;
    }
    public float getAccountAbuseScore() {
        return this.accountAbuseScore;
    }

    public float getAccountTakeoverScore() {
        return this.accountTakeoverScore;
    }

    public float getBotScore() {
        return this.botScore;
    }

    public void displayRiskScores() {

        if (this.response == null) {
            LOG.info("Castle response is null");
            return;
        }

        LOG.info("Risk: " + this.risk);
        LOG.info("Account Abuse Score: " + this.accountAbuseScore);
        LOG.info("Account Takeover Score: " + this.accountTakeoverScore);
        LOG.info("Bot Score: " + this.botScore);
    }

    private void setRiskScores() {

        this.risk = response.json().getAsJsonObject().get("risk").getAsFloat();

        this.accountAbuseScore = response.json().getAsJsonObject().get("scores").getAsJsonObject().get("account_abuse")
                .getAsJsonObject().get("score").getAsFloat();

        this.accountTakeoverScore = response.json().getAsJsonObject().get("scores").getAsJsonObject()
                .get("account_takeover").getAsJsonObject().get("score").getAsFloat();

        this.botScore = response.json().getAsJsonObject().get("scores").getAsJsonObject().get("bot").getAsJsonObject()
                .get("score").getAsFloat();
    }

}
