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

package org.wso2.carbon.identity.conditional.fraud.detection.castle;

import io.castle.client.model.CastleResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.constant.ErrorMessageConstants;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.constant.ScoreConstants;

/**
 * The POJO class for the response from castle API.
 */
public class CustomCastleResponse {

    private static final Log LOG = LogFactory.getLog(CustomCastleResponse.class);
    CastleResponse response;
    private float riskScore;
    private float accountAbuseScore;
    private float accountTakeoverScore;
    private float botScore;

    public CustomCastleResponse(CastleResponse response) {

        this.response = response;
        setRiskScores();
    }

    public float getRiskScore() {

        return this.riskScore;
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
            LOG.error(ErrorMessageConstants.RESPONSE_NULL_MESSAGE);

            return;
        }

        LOG.info(ScoreConstants.RISK_DISPLAY_NAME + this.riskScore);
        LOG.info(ScoreConstants.ACCOUNT_ABUSE_DISPLAY_NAME + this.accountAbuseScore);
        LOG.info(ScoreConstants.ACCOUNT_TAKEOVER_DISPLAY_NAME + this.accountTakeoverScore);
        LOG.info(ScoreConstants.BOT_DISPLAY_NAME + this.botScore);
    }

    private void setRiskScores() {

        this.riskScore = response.json().getAsJsonObject().get(ScoreConstants.KEY_RISK_SCORE).getAsFloat();

        this.accountAbuseScore = response.json().getAsJsonObject().get(ScoreConstants.KEY_DETAILED_SCORES_INITIAL)
                .getAsJsonObject().get(ScoreConstants.KEY_ACCOUNT_ABUSE).getAsJsonObject()
                .get(ScoreConstants.KEY_DETAILED_SCORES_FINAL).getAsFloat();

        this.accountTakeoverScore = response.json().getAsJsonObject().get(ScoreConstants.KEY_DETAILED_SCORES_INITIAL)
                .getAsJsonObject().get(ScoreConstants.KEY_ACCOUNT_TAKEOVER).getAsJsonObject()
                .get(ScoreConstants.KEY_DETAILED_SCORES_FINAL).getAsFloat();

        this.botScore = response.json().getAsJsonObject().get(ScoreConstants.KEY_DETAILED_SCORES_INITIAL)
                .getAsJsonObject().get(ScoreConstants.KEY_BOT).getAsJsonObject()
                .get(ScoreConstants.KEY_DETAILED_SCORES_FINAL).getAsFloat();
    }

}

