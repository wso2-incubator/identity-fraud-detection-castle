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
import org.wso2.carbon.identity.conditional.fraud.detection.castle.constant.CastleInitializeConstants;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.constant.RequestConstants;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.model.User;


/**
 * Class with the Castle request sending functionality for a successful login.
 */
public class LoginSuccessRequestSender implements RequestSender {

    public CustomCastleResponse doRequest(User user, String castleRequestToken, CastleContext castleContext,
                                          String apiSecret) throws CastleSdkConfigurationException,
            CastleApiInvalidRequestTokenException, CastleRuntimeException {

        Castle castle = null;

        try {
            castle = Castle.initialize(Castle.configurationBuilder().apiSecret(apiSecret)
                    .withTimeout(CastleInitializeConstants.CASTLE_INITIALIZE_TIMEOUT).build());

            assert castle != null;

            CastleResponse response = castle.client().risk(ImmutableMap.builder()
                    .put(RequestConstants.KEY_TYPE, RequestConstants.VALUE_LOGIN)
                    .put(RequestConstants.KEY_STATUS, RequestConstants.VALUE_SUCCESS)
                    .put(Castle.KEY_CONTEXT, ImmutableMap.builder()
                            .put(Castle.KEY_IP, castleContext.getIp())
                            .put(Castle.KEY_HEADERS, castleContext.getHeaders())
                            .build()
                    )
                    .put(Castle.KEY_USER, ImmutableMap.builder()
                            .put(RequestConstants.KEY_USER_ID, user.getId())
                            .put(RequestConstants.KEY_USER_EMAIL, user.getEmail())
                            .build()
                    )
                    .put(Castle.KEY_REQUEST_TOKEN, castleRequestToken)
                    .build()
            );

            CustomCastleResponse customCastleResponse = new CustomCastleResponse(response);

            return customCastleResponse;
        } catch (CastleSdkConfigurationException e) {
            throw e;
        } catch (CastleApiInvalidRequestTokenException e) {
            throw e;
        } catch (CastleRuntimeException e) {
            throw e;
        }
    }

}

