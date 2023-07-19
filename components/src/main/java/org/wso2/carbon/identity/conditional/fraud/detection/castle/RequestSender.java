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

import io.castle.client.model.CastleApiInvalidRequestTokenException;
import io.castle.client.model.CastleContext;
import io.castle.client.model.CastleRuntimeException;
import io.castle.client.model.CastleSdkConfigurationException;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.model.User;

/**
 * Interface for the Castle request sending functions.
 */
public interface RequestSender {

    public CustomCastleResponse doRequest(User user, String castleRequestToken, CastleContext castleContext,
                                          String apiSecret) throws CastleSdkConfigurationException,
            CastleApiInvalidRequestTokenException, CastleRuntimeException;

}

