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

import io.castle.client.model.CastleContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.nashorn.JsNashornServletRequest;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.model.User;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.util.ParamSetter;


/**
 * Interface for the Castle login authentication functions.
 */
public class RiskAnalyzerImpl implements RiskAnalyzer {

    public float getRisk(JsAuthenticationContext context, String successStatus, String apiSecret,
                         JsNashornServletRequest request) {

        RequestSender requestSender = null;

        if (successStatus.equals("success")) {
            requestSender = new LoginSuccessRequestSender();
        } else if (successStatus.equals("fail")) {
            requestSender = new LoginFailedRequestSender();
        }

        ParamSetter paramSetter = new ParamSetter(request, context);
        CastleContext castleContext = paramSetter.createContext();
        User user = paramSetter.getUser();
        String reqToken = paramSetter.getRequestToken();
        CustomCastleResponse response = requestSender.doRequest(user, reqToken, castleContext, apiSecret);

        if (response != null) {
            response.displayRiskScores();
        }

        return response.getRiskScore();
    }

}
