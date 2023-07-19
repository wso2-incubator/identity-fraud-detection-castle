# identity-fraud-detection-castle
Castle fraud detection connector for WSO2 Identity Server

## How to configure
1. Build the project using `mvn clean install`
2. Copy the built JAR artifact from `target` directory into `<IS_HOME>/repository/components/dropins`
3. Create a Castle account and obtain the `PUBLISHABLE API KEY` from `Profile` > `Configurations`.
4. Add the following configuration to `<IS_HOME>/repository/conf/deployment.toml` file. Replace `CASTLE_PUBLIC_KEY` with the key obtained from the 3rd step.

```
[authenticationendpoint.context_params]
castle_fraud_detection_enabled=true
castle_fraud_detection_public_key="CASTLE_PUBLIC_KEY"
```

5. Call getRiskFromCastle() Method with an adaptive script to get the risk score from Castle when a user logs in to an application.
6. Start the server.

```
var riskScore = getRiskFromCastle(JsAuthenticationContext context, String successStatus, String apiSecret,
                                   JsNashornServletRequest request, boolean doPrintScores);
```

Following is the description of the required parameters.
* context - Authentication context
* successStatus - Whether the authentication is successful or not. (Values: "SUCCESS" or "FAIL")
* apiSecret - Castle API Secret. (Can be obtained from Profile>Configurations)
* request - The authentication request.
* doPrintScores - Whether to print the risk scores in the logs or not. (Values: true or false)

Following is a sample method calls with an adaptive script.

```
var onLoginRequest = function(context) {
    executeStep(1, {
        onSuccess: function (context) {
            
            var score = getRiskFromCastle(
                context, 
                "SUCCESS", 
                "API_SECRET", 
                context.request,
                true
            );
        },
        onFail: function(context) {
            score = getRiskFromCastle(
                context, 
                "FAIL", 
                "API_SECRET", 
                context.request,
                true
            );
        },
    });
};
```

