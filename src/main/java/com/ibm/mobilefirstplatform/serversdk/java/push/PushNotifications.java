/*
 *     Copyright 2016 IBM Corp.
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/**
 * This class is used to send push notifications from a Java server to mobile devices
 * using the Push Notification service in IBM® Bluemix.
 */
public class PushNotifications {
	public static final String US_SOUTH_REGION = ".ng.bluemix.net";
	public static final String UK_REGION = ".eu-gb.bluemix.net";
	public static final String SYDNEY_REGION = ".au-syd.bluemix.net";
	
	protected static String secret;
	
	protected static String pushMessageEndpointURL;
	
	/**
	 * Specify the credentials and Bluemix region for your push notification service.
	 *  
	 * @param tenantId the tenant ID for the Bluemix application that the Push Notifications service is bound to
	 * @param pushSecret the credential required for Push Notifications service authorization
	 * @param bluemixRegion the Bluemix region where the Push Notifications service is hosted, such as {@link PushNotifications#US_SOUTH_REGION}
	 */
	public static void init(String tenantId, String pushSecret, String bluemixRegion){
		secret = pushSecret;
		
		pushMessageEndpointURL = "https://imfpush" + bluemixRegion + ":443/imfpush/v1/apps/" + tenantId + "/messages";
	}
	
	/**
	 * If your application server is running on Bluemix, and your push notification service is bound to your application,
	 * you can use this method for initialization which will get the credentials from Bluemix's environment variables.
	 * 
	 * @param bluemixRegion the Bluemix region where the Push Notifications service is hosted, such as {@link PushNotifications#US_SOUTH_REGION}
	 */
	public static void init(String bluemixRegion){
		String tenantId = null;
		String pushSecret = null;
		
		tenantId = getApplicationIdFromVCAP();
		pushSecret = getPushSecretFromVCAP();
		
		if(tenantId != null && pushSecret != null){
			init(tenantId,pushSecret,bluemixRegion);
		}
		else{
			throw new IllegalArgumentException("Credentials could not be found in environment variables. Make sure they are available, or use the other constructor.");
		}
	}

	protected static String getApplicationIdFromVCAP() {
		String vcapApplicationAsString = getEnvironmentVariable("VCAP_APPLICATION");
		if(vcapApplicationAsString != null){
			JSONObject vcapApplication = new JSONObject(vcapApplicationAsString);

			JSONObject appObject = vcapApplication.optJSONObject("app");
			
			if(appObject != null){
				return appObject.optString("application_id");
			}	
		}
		return null;
	}

	protected static String getEnvironmentVariable(String name) {
		return System.getenv(name);
	}

	protected static String getPushSecretFromVCAP() {
		String vcapServicesAsString = getEnvironmentVariable("VCAP_SERVICES");
		
		if(vcapServicesAsString != null){
			JSONObject vcapServices = new JSONObject(vcapServicesAsString);
			JSONObject servicesObject = vcapServices.optJSONObject("services");
			
			if(servicesObject != null && servicesObject.has("imfpush")){
				JSONObject imfPushCredentials = servicesObject.getJSONArray("imfpush").optJSONObject(0).optJSONObject("credentials");
				
				if(imfPushCredentials != null){
					return imfPushCredentials.optString("appSecret");
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Send the given push notification, as configured, to devices using the Push Notification service. 
	 * 
	 * @param notification the push notification to be sent
	 * @param listener an optional {@link PushNotificationsResponseListener} to listen to the result of this operation
	 */
	public static void send(JSONObject notification, PushNotificationsResponseListener listener){
		if(pushMessageEndpointURL == null || pushMessageEndpointURL.length() == 0){
			listener.onFailure(null, null, new RuntimeException("PushNotifications has not been properly initialized."));
		}
		
		if(notification == null){
			listener.onFailure(null, null, new IllegalArgumentException("Cannot send null notification."));
		}
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost pushPost = createPushPostRequest(notification);

		executePushPostRequest(pushPost, httpClient, listener);
	}

	protected static HttpPost createPushPostRequest(JSONObject notification) {
		HttpPost pushPost = new HttpPost(pushMessageEndpointURL);
		
		pushPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		pushPost.addHeader("appSecret", secret);
		
		StringEntity body = new StringEntity(notification.toString(), "UTF-8");
		pushPost.setEntity(body);
		
		return pushPost;
	}

	protected static void executePushPostRequest(HttpPost pushPost,
			CloseableHttpClient httpClient, PushNotificationsResponseListener listener) {
		CloseableHttpResponse response = null;
		
		try {
			response = httpClient.execute(pushPost);
			
			sendResponseToListener(response, listener);
		} catch (ClientProtocolException e) {
			listener.onFailure(null, null, e);
		} catch (IOException e) {
			listener.onFailure(null, null, e);
		}
		finally {
			if(response != null){
				try {
					response.close();
				} catch (IOException e) {
					// Closing response is merely a best effort.
				}
			}
		}
	}

	protected static void sendResponseToListener(CloseableHttpResponse response,
			PushNotificationsResponseListener listener) throws IOException {
		String responseBody = null;
		
		if(response.getEntity() != null){
			ByteArrayOutputStream outputAsByteArray = new ByteArrayOutputStream();
			response.getEntity().writeTo(outputAsByteArray);
			
			responseBody = new String(outputAsByteArray.toByteArray());
		}
		
		Integer statusCode = null;
		
		if(response.getStatusLine() != null){
			statusCode = response.getStatusLine().getStatusCode();
		}
		
		if(statusCode == HttpStatus.SC_ACCEPTED){
			listener.onSuccess(response.getStatusLine().getStatusCode(), responseBody);
		}
		else{
			listener.onFailure(statusCode, responseBody, null);
		}
	}
}
