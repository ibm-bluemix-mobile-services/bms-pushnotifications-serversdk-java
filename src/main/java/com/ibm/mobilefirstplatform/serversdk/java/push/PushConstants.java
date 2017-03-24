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
	public static final String US_SOUTH_REGION = ".ng.bluemix.net";
	public static final String UK_REGION = ".eu-gb.bluemix.net";
	public static final String SYDNEY_REGION = ".au-syd.bluemix.net";
	public static final String BASEURL = "https://imfpush";
	public static final String PORTURL = ":443/imfpush/v1/apps/";
	public static final String PROJECT = "/messages";
	public static final String PUSHINITEXCEPTION = "PushNotifications could not be initialized. Credentials could not be found in environment variables. Make sure they are available, or use the other constructor.";
	public static final String VCAP_SERVICES = "VCAP_SERVICES";
	public static final String IMFPUSH = "imfpush";
	public static final String CREDENTIALS = "credentials";
	public static final String APPGUID = "appGuid";
	public static final String APPSECRET = "appSecret";
	public static final String NOTPROPERLYINITIALIZEDEXCEPTION = "PushNotifications has not been properly initialized.";
	public static final String NULLNOTIFICATIONEXCEPTION = "Cannot send a null push notification.";
	public static final String CONTENTTYPE = "application/json";
	public static final String UTFEIGHT = "UTF-8";
	public static final String ALERTNOTNULLEXCEPTIOPN = "The alert cannot be null.";
	
	private PushConstants() {

	}
}
