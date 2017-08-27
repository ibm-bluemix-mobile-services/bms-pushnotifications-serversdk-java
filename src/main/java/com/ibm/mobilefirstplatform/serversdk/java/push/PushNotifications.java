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
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to send push notifications from a Java server to mobile
 * devices using the Push Notification service in IBM® Bluemix.
 */
public class PushNotifications {
	public static final String US_SOUTH_REGION = ".ng.bluemix.net";
	public static final String UK_REGION = ".eu-gb.bluemix.net";
	public static final String SYDNEY_REGION = ".au-syd.bluemix.net";
	public static final String FRANKFURT = "eu-de.bluemix.net";

	public static final Logger logger = Logger.getLogger(PushNotifications.class.getName());

	protected static String secret;

	protected static String pushMessageEndpointURL;

	/**
	 * Specify the credentials and Bluemix region for your push notification
	 * service.
	 * 
	 * @param tenantId
	 *            the tenant ID for the Bluemix application that the Push
	 *            Notifications service is bound to
	 * @param pushSecret
	 *            the credential required for Push Notifications service
	 *            authorization
	 * @param bluemixRegion
	 *            the Bluemix region where the Push Notifications service is
	 *            hosted, such as {@link PushNotifications#US_SOUTH_REGION}
	 */
	public static void init(String tenantId, String pushSecret, String bluemixRegion) {
		secret = pushSecret;

		pushMessageEndpointURL = PushConstants.BASEURL + bluemixRegion + PushConstants.URL + tenantId
				+ PushConstants.PROJECT;
	}

	/**
	 * If your application server is running on Bluemix, and your push
	 * notification service is bound to your application, you can use this
	 * method for initialization which will get the credentials from Bluemix's
	 * environment variables.
	 * 
	 * @param bluemixRegion
	 *            the Bluemix region where the Push Notifications service is
	 *            hosted, such as {@link PushNotifications#US_SOUTH_REGION}
	 * 
	 * @throws IllegalArgumentException
	 *             if either the push application ID or the secret are not found
	 *             in the environment variables
	 */
	public static void init(String bluemixRegion) {
		String tenantId = null;
		String pushSecret = null;

		tenantId = getApplicationIdFromVCAP();
		pushSecret = getPushSecretFromVCAP();

		if (tenantId != null && pushSecret != null) {
			init(tenantId, pushSecret, bluemixRegion);
		} else {
			IllegalArgumentException exception = new IllegalArgumentException(PushConstants.PUSH_INIT_EXCEPTION);
			logger.log(Level.SEVERE, exception.toString(), exception);
			throw exception;
		}
	}

	protected static String getApplicationIdFromVCAP() {
		String vcapServicesAsString = getEnvironmentVariable(PushConstants.VCAP_SERVICES);

		if (vcapServicesAsString != null) {
			JSONObject vcapServices = new JSONObject(vcapServicesAsString);

			if (vcapServices.has(PushConstants.IMFPUSH)) {
				JSONObject imfPushCredentials = vcapServices.getJSONArray(PushConstants.IMFPUSH).optJSONObject(0)
						.optJSONObject(PushConstants.CREDENTIALS);

				if (imfPushCredentials != null) {
					return imfPushCredentials.optString(PushConstants.APPGUID);
				}
			}
		}
		return null;
	}

	protected static String getEnvironmentVariable(String name) {
		return System.getenv(name);
	}

	protected static String getPushSecretFromVCAP() {
		String vcapServicesAsString = getEnvironmentVariable(PushConstants.VCAP_SERVICES);

		if (vcapServicesAsString != null) {
			JSONObject vcapServices = new JSONObject(vcapServicesAsString);

			if (vcapServices.has(PushConstants.IMFPUSH)) {
				JSONObject imfPushCredentials = vcapServices.getJSONArray(PushConstants.IMFPUSH).optJSONObject(0)
						.optJSONObject(PushConstants.CREDENTIALS);

				if (imfPushCredentials != null) {
					return imfPushCredentials.optString(PushConstants.APPSECRET);
				}
			}
		}

		return null;
	}

	/**
	 * Send the given push notification, as configured, to devices using the
	 * Push Notification service.
	 * 
	 * @param notification
	 *            the push notification to be sent
	 * @param listener
	 *            an optional {@link PushNotificationsResponseListener} to
	 *            listen to the result of this operation
	 */
	public static void send(Notification notification, PushNotificationsResponseListener listener) {
		if (pushMessageEndpointURL == null || pushMessageEndpointURL.length() == 0) {
			Throwable exception = new RuntimeException(PushConstants.NOT_PROPERLY_INITIALIZED_EXCEPTION);
			logger.log(Level.SEVERE, exception.toString(), exception);

			if (listener != null) {
				listener.onFailure(null, null, exception);
			}
			return;
		}

		if (notification == null) {
			Throwable exception = new IllegalArgumentException(PushConstants.NULL_NOTIFICATION_EXCEPTION);
			logger.log(Level.SEVERE, exception.toString(), exception);
			if (listener != null) {
				listener.onFailure(null, null, exception);
			}
			return;
		}

		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notification.getMessage()).target(notification.getTarget())
				.settings(notification.getSettings()).build();

		JSONObject notificationJson = generateJSON(model);

		HttpPost pushPost = createPushPostRequest(notificationJson);

		executePushPostRequest(pushPost, httpClient, listener);
	}
	
	/**
	 * API converts object to json format.
	 * 
	 * @param obj
	 *            The object which needs to be serialized as json string.
	 * 
	 * @return Return a JSONOject for the passed object.
	 */
	private static JSONObject generateJSON(Object obj) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonString = null;
		try {
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			jsonString = mapper.writeValueAsString(obj);

		} catch (JsonProcessingException exception) {

			logger.log(Level.SEVERE, exception.toString(), exception);
		}

		JSONObject json = jsonString != null ? new JSONObject(jsonString) : new JSONObject();

		return json;
	}

	protected static HttpPost createPushPostRequest(JSONObject notification) {
		HttpPost pushPost = new HttpPost(pushMessageEndpointURL);

		pushPost.addHeader(HTTP.CONTENT_TYPE, PushConstants.CONTENT_TYPE);
		pushPost.addHeader(PushConstants.APPSECRET, secret);

		StringEntity body = new StringEntity(notification.toString(), PushConstants.UTFEIGHT);
		pushPost.setEntity(body);

		return pushPost;
	}

	protected static void executePushPostRequest(HttpPost pushPost, CloseableHttpClient httpClient,
			PushNotificationsResponseListener listener) {
		CloseableHttpResponse response = null;

		try {
			response = httpClient.execute(pushPost);

			if (listener != null) {
				sendResponseToListener(response, listener);
			}
		} catch (ClientProtocolException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			if (listener != null) {
				listener.onFailure(null, null, e);
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, e.toString(), e);
			if (listener != null) {
				listener.onFailure(null, null, e);
			}
		} finally {
			if (response != null) {
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

		if (response.getEntity() != null) {
			ByteArrayOutputStream outputAsByteArray = new ByteArrayOutputStream();
			response.getEntity().writeTo(outputAsByteArray);

			responseBody = new String(outputAsByteArray.toByteArray());
		}

		Integer statusCode = null;

		if (response.getStatusLine() != null) {
			statusCode = response.getStatusLine().getStatusCode();
		}

		if (statusCode != null && statusCode == HttpStatus.SC_ACCEPTED) {
			listener.onSuccess(statusCode, responseBody);
		} else {
			listener.onFailure(statusCode, responseBody, null);
		}
	}
}
