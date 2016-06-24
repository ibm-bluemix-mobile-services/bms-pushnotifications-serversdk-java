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
import java.io.UnsupportedEncodingException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

/**
 * TODO:
 */
public class PushNotifications {
	public static final String US_SOUTH_REGION = ".ng.bluemix.net";
	public static final String UK_REGION = ".eu-gb.bluemix.net";
	public static final String SYDNEY_REGION = ".au-syd.bluemix.net";
	
	protected static String secret;
	
	protected static String pushMessageEndpointURL;
	
	/**
	 * TODO:
	 * @param tenantId
	 * @param pushSecret
	 * @param bluemixRegion
	 */
	public static void init(String tenantId, String pushSecret, String bluemixRegion){
		secret = pushSecret;
		
		pushMessageEndpointURL = "https://imfpush" + bluemixRegion + ":443/imfpush/v1/apps/" + tenantId + "/messages";
	}
	
	/**
	 * TODO:
	 * @param bluemixRegion
	 */
	public static void init(String bluemixRegion){
		//TODO: get tenantId and app secret from VCAP_SERVICES
		String vcapServicesAsString = System.getenv("VCAP_SERVICES");
		
		String tenantId = null;
		String pushSecret = null;
		
		if(vcapServicesAsString != null){
			JSONObject vcapServices = new JSONObject(vcapServicesAsString);
			
			JSONObject appObject = vcapServices.optJSONObject("app");
			
			if(appObject != null){
				tenantId = appObject.optString("application_id");
			}
			
			JSONObject servicesObject = vcapServices.optJSONObject("services");
			
			if(servicesObject != null && servicesObject.has("imfpush")){
				JSONObject imfPushCredentials = servicesObject.getJSONArray("imfpush").optJSONObject(0).optJSONObject("credentials"); //TODO
				
				if(imfPushCredentials != null){
					pushSecret = imfPushCredentials.optString("appSecret");
				}
			}
		}
		
		if(tenantId != null && pushSecret != null){
			init(tenantId,pushSecret,bluemixRegion);
		}
		else{
			//TODO: exception?
		}
	}
	
	/**
	 * TODO:
	 * @param notification
	 * @param listener
	 */
	public static void send(JSONObject notification, PushNotificationsResponseListener listener){
		if(pushMessageEndpointURL == null || pushMessageEndpointURL.length() == 0){
			listener.onFailure(null, null, new RuntimeException("PushNotifications has not been properly initialized."));
		}
		
		if(notification == null){
			listener.onFailure(null, null, new RuntimeException("Cannot send null notification."));
		}
		
		//TODO:
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost pushPost = new HttpPost(pushMessageEndpointURL);
		
		pushPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		pushPost.addHeader("appSecret", secret);
		
		StringEntity body;
		CloseableHttpResponse response = null;
		
		try {
			body = new StringEntity(notification.toString());
			pushPost.setEntity(body);
			
			response = httpClient.execute(pushPost);
			
			ByteArrayOutputStream outputAsByteArray = new ByteArrayOutputStream();
			response.getEntity().writeTo(outputAsByteArray);
			
			String responseBody = new String(outputAsByteArray.toByteArray());
			
			listener.onSuccess(response.getStatusLine().getStatusCode(), responseBody);
			
		} catch (UnsupportedEncodingException e) {
			// Will never happen, since it will always be a proper JSON Object.
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
}
