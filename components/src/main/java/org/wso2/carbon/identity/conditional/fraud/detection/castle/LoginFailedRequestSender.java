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

import com.google.common.collect.ImmutableMap;
import io.castle.client.Castle;
import io.castle.client.model.CastleApiInvalidRequestTokenException;
import io.castle.client.model.CastleContext;
import io.castle.client.model.CastleResponse;
import io.castle.client.model.CastleRuntimeException;
import io.castle.client.model.CastleSdkConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.constant.ErrorMessageConstants;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.model.User;

/**
 * Class with the Castle request sending functionality for a failed login.
 */
public class LoginFailedRequestSender implements RequestSender {

    private static final Log LOG = LogFactory.getLog(LoginFailedRequestSender.class);

    public CustomCastleResponse doRequest(User user, String castleRequestToken, CastleContext castleContext,
                                          String apiSecret) {

        Castle castle = null;

        try {
            castle = Castle.initialize(Castle.configurationBuilder().apiSecret(apiSecret).withTimeout(1000).build());
        } catch (CastleSdkConfigurationException e) {
            LOG.info(ErrorMessageConstants.ERROR_CASTLE_CONFIGURATION, e);
        }

        try {
            assert castle != null;

            CastleResponse response = castle.client().filter(ImmutableMap.builder()
                    .put("type", "$login")
                    .put("status", "$failed")
                    .put(Castle.KEY_CONTEXT, ImmutableMap.builder()
                            .put(Castle.KEY_IP, castleContext.getIp())
                            .put(Castle.KEY_HEADERS, castleContext.getHeaders())
                            .build()
                    )
                    .put(Castle.KEY_USER, ImmutableMap.builder()
                            .put("id", user.getId())
                            .put("email", user.getEmail())
                            .build()
                    )
                    .put(Castle.KEY_REQUEST_TOKEN, castleRequestToken)
                    .build()
            );

            CustomCastleResponse customCastleResponse = new CustomCastleResponse(response);

            return customCastleResponse;

        } catch (CastleApiInvalidRequestTokenException requestTokenException) {
            LOG.info(ErrorMessageConstants.ERROR_CASTLE_REQUEST_TOKEN, requestTokenException);
        } catch (CastleRuntimeException runtimeException) {
            LOG.info(ErrorMessageConstants.ERROR_CASTLE_DATA, runtimeException);
        }

        return null;
    }

}
