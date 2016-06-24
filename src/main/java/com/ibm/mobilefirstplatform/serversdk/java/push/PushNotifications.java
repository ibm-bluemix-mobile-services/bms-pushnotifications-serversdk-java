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

public class PushNotifications {
	public static final String US_SOUTH_REGION = ".ng.bluemix.net";
	public static final String UK_REGION = ".eu-gb.bluemix.net";
	public static final String SYDNEY_REGION = ".au-syd.bluemix.net";
	
	protected static String secret;
	
	protected static String pushMessageEndpointURL;
	
	public static void init(String tenantId, String pushSecret, String bluemixRegion){
		secret = pushSecret;
		
		pushMessageEndpointURL = "https://imfpush" + bluemixRegion + ":443/imfpush/v1/apps/" + tenantId + "/messages";
	}
	
	public static void init(String bluemixRegion){
		//TODO: get tenantId and app secret from VCAP_SERVICES
		String vcapServicesAsString = System.getenv("VCAP_SERVICES");
		
		if(vcapServicesAsString != null){
			JSONObject vcapServices = new JSONObject(vcapServicesAsString);
			
			JSONObject appObject = vcapServices.optJSONObject("app");
			
			if(appObject != null){
				appObject.get("application_id");
			}
			
			JSONObject servicesObject = vcapServices.optJSONObject("services");
			
			if(servicesObject != null){
				servicesObject.get(""); //TODO
			}
		}
	}
	
	
	//TODO: add callback
	public static void send(JSONObject notificationToBeSent){
		if(pushMessageEndpointURL == null || pushMessageEndpointURL.length() == 0){
			throw new RuntimeException("PushNotifications has not been properly initialized.");
		}
		
		if(notificationToBeSent == null){
			throw new RuntimeException("Cannot send null notification.");
		}
		
		//TODO:
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		HttpPost pushPost = new HttpPost(pushMessageEndpointURL);
		
		pushPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
		pushPost.addHeader("appSecret", secret);
		
		StringEntity body;
		CloseableHttpResponse response = null;
		
		try {
			body = new StringEntity(notificationToBeSent.toString());
			pushPost.setEntity(body);
			
			response = httpClient.execute(pushPost);
			
			ByteArrayOutputStream outputAsByteArray = new ByteArrayOutputStream();
			response.getEntity().writeTo(outputAsByteArray);
			
			String responseBody = new String(outputAsByteArray.toByteArray());
			
			//TODO
			System.out.println(responseBody);
			
		} catch (UnsupportedEncodingException e) {
			// Will never happen, since it will always be a proper JSON Object.
		} catch (ClientProtocolException e) {
			// TODO 
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 
			e.printStackTrace();
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
