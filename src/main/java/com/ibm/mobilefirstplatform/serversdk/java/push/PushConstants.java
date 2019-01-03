/*
 *     Copyright 2017 IBM Corp.
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.ibm.mobilefirstplatform.serversdk.java.push;

/**
 * The interface for all the constants.
 */
public final class PushConstants {

	public static final String MESSAGE_OBJECT_KEY = "message";
	public static final String SETTINGS_OBJECT_KEY = "settings";
	public static final String HOST = "https://imfpush";
	public static final String URL = ":/imfpush/v1/apps/";
	public static final String API = "/messages";
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String IMFPUSH = "imfpush";
	public static final String CREDENTIALS = "credentials";
	public static final String APPGUID = "appGuid";
	public static final String APPSECRET = "appSecret";
	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String APIKEY = "apiKey";
	public static final String CONTENT_TYPE = "application/json";
	public static final String IAM_CONTENT_TYPE = "application/x-www-form-urlencoded";	
	public static final String IAM_TOKEN_PATH = "/identity/token";
	public static final String IAM_URI = "https://iam";
	public static final String GRANT_TYPE = "grant_type";
	public static final String GRANT_TYPE_VALUE_APIKEY = "urn:ibm:params:oauth:grant-type:apikey";
	public static final String UTFEIGHT = "UTF-8";
	public static final String ACCESS_TOKEN = "access_token";
	public static final String EXPIRATION = "expiration";
	public static final String BEARER = "Bearer";
	public static final String EMPTY_SPACE = " ";
	public static final String ALERT_NOT_NULL_EXCEPTION = "The alert cannot be null. Please use MessageBuilder to set alert";
	public static final String TLS_VERSION = "TLSv1.2";

	private PushConstants() {

	}
	
	public static final class PushServerSDKExceptions {
		public static final String PUSH_INIT_EXCEPTION = "FPSDK0001A: PushNotifications could not be initialized. Credentials could not be found in environment variables. Make sure they are available, or use the other constructor.";
		public static final String NOT_PROPERLY_INITIALIZED_EXCEPTION = "FPSDK0002A: PushNotifications has not been properly initialized.";
		public static final String NULL_NOTIFICATION_EXCEPTION = "FPSDK0003A: Cannot send a null push notification.";
		public static final String IAM_FAILURE_EXCEPTION	= "FPSDK0004A: Error in fetching service access token";
		public static final String ALERT_NOT_NULL_EXCEPTION = "FPSDK0005A: The alert cannot be null. Please use MessageBuilder to set alert";
		public static final String JSON_PARSER_EXCEPTION = "FPSDK0006A:  Header elements cannot be parsed";
		public static final String JSON_IO_EXCEPTION = "FPSDK0007A: An error occurs reading the input stream";
	}
}
