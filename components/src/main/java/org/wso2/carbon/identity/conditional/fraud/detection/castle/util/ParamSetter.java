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

package org.wso2.carbon.identity.conditional.fraud.detection.castle.util;

import io.castle.client.internal.utils.CastleContextBuilder;
import io.castle.client.model.CastleContext;
import io.castle.client.model.CastleHeader;
import io.castle.client.model.CastleHeaders;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.JsAuthenticationContext;
import org.wso2.carbon.identity.application.authentication.framework.config.model.graph.js.nashorn.JsNashornServletRequest;
import org.wso2.carbon.identity.application.authentication.framework.exception.UserIdNotFoundException;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.constant.ErrorMessageConstants;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.internal.CastleIntegrationDataHolder;
import org.wso2.carbon.identity.conditional.fraud.detection.castle.model.User;
import org.wso2.carbon.identity.core.util.IdentityTenantUtil;
import org.wso2.carbon.identity.core.util.IdentityUtil;
import org.wso2.carbon.user.api.UserStoreException;
import org.wso2.carbon.user.api.UserStoreManager;
import org.wso2.carbon.user.core.UniqueIDUserStoreManager;
import org.wso2.carbon.user.core.service.RealmService;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with the functionality to set up all the required parameters.
 */
public class ParamSetter {

    private JsAuthenticationContext context;
    private JsNashornServletRequest request;
    private UniqueIDUserStoreManager uniqueIDUserStoreManager;
    private String internalUserId = "internalUserId";
    private String userId = "userId";
    private int tenantId = 0;
    private String userEmail = "userEmail";
    private String userAgent = "User-Agent";
    private String host = "Host";
    private String ip = "ip";
    private String requestToken = "requestToken";
    private static final Log LOG = LogFactory.getLog(ParamSetter.class);

    public ParamSetter(JsNashornServletRequest request, JsAuthenticationContext context) {

        this.context = context;
        this.request = request;
        setupParameters();
    }

    public User getUser() {

        return new User(userId, userEmail);
    }

    public String getRequestToken() {

        return requestToken;
    }

    public CastleContext createContext() {

        CastleContextBuilder contextBuilder = new CastleContextBuilder(null, null);

        //set ip
        contextBuilder = contextBuilder.ip(this.ip);

        //set headers
        CastleHeader castleHeader1 = new CastleHeader("User-Agent", this.userAgent);
        CastleHeader castleHeader2 = new CastleHeader("Host", this.host);
        List<CastleHeader> headers = new ArrayList<>();
        headers.add(castleHeader1);
        headers.add(castleHeader2);
        contextBuilder = contextBuilder.headers(new CastleHeaders(headers));

        //creating context
        CastleContext castleContext = contextBuilder.build();

        return castleContext;
    }

    private UniqueIDUserStoreManager getUniqueIdEnabledUserStoreManager(int tenantId) throws UserStoreException {

        RealmService realmService = CastleIntegrationDataHolder.getInstance().getRealmService();
        UserStoreManager userStoreManager = realmService.getTenantUserRealm(tenantId).getUserStoreManager();

        if (!(userStoreManager instanceof UniqueIDUserStoreManager)) {
            LOG.error(ErrorMessageConstants.ERROR_GETTING_USER_STORE);
        }

        return (UniqueIDUserStoreManager) userStoreManager;
    }

    private void setupParameters() {

        if (context.getContext().getSubject() != null) {
            try {
                internalUserId = context.getContext().getSubject().getUserId();
            } catch (UserIdNotFoundException e) {
                LOG.error(ErrorMessageConstants.ERROR_USER_ID, e);
            }
            userId = context.getContext().getSubject().getAuthenticatedSubjectIdentifier();
        }

        this.userAgent = request.getWrapped().getWrapped().getHeader("User-Agent");
        this.host = request.getWrapped().getWrapped().getHeader("Host");
        this.ip = IdentityUtil.getClientIpAddress(request.getWrapped().getWrapped());
        this.tenantId = IdentityTenantUtil.getTenantId(context.getContext().getTenantDomain());
        this.requestToken = request.getWrapped().getWrapped().getParameterMap()
                .get("castleRequestToken")[0];

        try {
            uniqueIDUserStoreManager = getUniqueIdEnabledUserStoreManager(tenantId);
            userEmail = uniqueIDUserStoreManager.getUserClaimValueWithID(internalUserId,
                    "http://wso2.org/claims/emailaddress", null);
        } catch (UserStoreException e) {
            LOG.error(ErrorMessageConstants.ERROR_USER_EMAIL, e);
        }
    }

}

