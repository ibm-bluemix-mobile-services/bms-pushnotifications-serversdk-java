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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The NotificationBuilder is used to create a new push notification that is going to be sent
 * using the Push Notification service in IBM® Bluemix.
 * 
 * All parameters are optional. Set them as needed.
 */
public class NotificationBuilder {
	private static final String SETTINGS_OBJECT_KEY = "settings";
	protected JSONObject notification;
	
	public enum PushNotificationsPlatform {
		APPLE("A"),
		GOOGLE("G");
		
		private String platformCode;
		
		PushNotificationsPlatform(String code){
			this.platformCode = code;
		}
		public String getValue(){
			return platformCode;
		}
	}
	
	public enum APNSNotificationType {
		DEFAULT,
		MIXED,
		SILENT
	}
	
	public enum GCMPriority {
		DEFAULT,
		MIN,
		LOW,
		HIGH,
		MAX
	}
	
	public NotificationBuilder(){
		notification = new JSONObject();
		
		//An empty message is the minimum required.
		notification.put("message", new JSONObject());
	}
	
	/**
	 * TODO:write doc
	 * @param alert
	 * @param url
	 * @return
	 */
	public NotificationBuilder setMessage(String alert, String url){
		JSONObject message = new JSONObject();
		if(alert != null && alert.length() > 0){
			message.put("alert", alert);
		}
		
		if(url != null && url.length() > 0){
			message.put("url", url);
		}
		
		if(!message.keySet().isEmpty()){
			notification.put("message", message);
		}
		
		return this;
	}
	
	/**
	 * TODO:write doc
	 * @param deviceIds
	 * @param platforms
	 * @param tagNames
	 * @return
	 */
	public NotificationBuilder setTarget(String[] deviceIds, PushNotificationsPlatform[] platforms, String[] tagNames){
		JSONObject target = new JSONObject();
		
		if(deviceIds != null && deviceIds.length > 0){
			target.put("deviceIds", new JSONArray(deviceIds));
		}
		
		if(platforms != null && platforms.length > 0){
			JSONArray platformArray = new JSONArray();
			
			for(PushNotificationsPlatform platform : platforms){
				platformArray.put(platform.getValue());
			}
			
			target.put("platforms", new JSONArray(platforms));
		}
		
		if(tagNames != null && tagNames.length > 0){
			target.put("tagNames", new JSONArray(tagNames));
		}
		
		if(!target.keySet().isEmpty()){
			notification.put("target", target);
		}
		
		return this;
	}
	
	/**
	 * TODO:write doc
	 * @param badge
	 * @param category
	 * @param iosActionKey
	 * @param payload
	 * @param soundFile
	 * @param type
	 * @return
	 */
	public NotificationBuilder setAPNSSettings(Integer badge, String category, String iosActionKey, JSONObject payload, String soundFile, APNSNotificationType type){
		JSONObject apnsSettings = new JSONObject();
		
		if(badge != null){
			apnsSettings.put("badge", badge.intValue());
		}
		
		if(category != null && category.length() > 0){
			apnsSettings.put("category", category);
		}
		
		if(iosActionKey != null && iosActionKey.length() > 0){
			apnsSettings.put("iosActionKey", iosActionKey);
		}
		
		if(payload != null){
			apnsSettings.put("payload", payload);
		}
		
		if(soundFile != null && soundFile.length() > 0){
			apnsSettings.put("sound", soundFile);
		}
		
		if(type != null){
			apnsSettings.put("type", type.name());
		}
		
		if(!apnsSettings.keySet().isEmpty()){
			JSONObject settings;

			if(notification.has(SETTINGS_OBJECT_KEY)){
				settings = notification.getJSONObject(SETTINGS_OBJECT_KEY);
			}
			else{
				settings = new JSONObject();
			}

			settings.put("apns", apnsSettings);

			notification.put(SETTINGS_OBJECT_KEY, settings);
		}
		
		return this;
	}
	
	/**
	 * TODO:write doc
	 * @param collapseKey
	 * @param delayWhileIdle
	 * @param payload
	 * @param priority
	 * @param soundFile
	 * @param secondsToLive
	 * @return
	 */
	public NotificationBuilder setGCMSettings(String collapseKey, Boolean delayWhileIdle, JSONObject payload, GCMPriority priority, String soundFile, Integer secondsToLive ){
		JSONObject gcmSettings = new JSONObject();
		
		if(collapseKey != null && collapseKey.length() > 0){
			gcmSettings.put("collapseKey", collapseKey);
		}
		
		if(delayWhileIdle != null){
			gcmSettings.put("delayWhileIdle", delayWhileIdle.booleanValue());
		}
		
		if(payload != null){
			gcmSettings.put("payload", payload);
		}
		
		if(priority != null){
			gcmSettings.put("priority", priority.name().toLowerCase());
		}
		
		if(soundFile != null && soundFile.length() > 0){
			gcmSettings.put("sound", soundFile);
		}
		
		if(secondsToLive != null){
			gcmSettings.put("secondsToLive", secondsToLive.intValue());
		}
		
		if(!gcmSettings.keySet().isEmpty()){
			JSONObject settings;

			if(notification.has(SETTINGS_OBJECT_KEY)){
				settings = notification.getJSONObject(SETTINGS_OBJECT_KEY);
			}
			else{
				settings = new JSONObject();
			}

			settings.put("gcm", gcmSettings);

			notification.put(SETTINGS_OBJECT_KEY, settings);
		}

		return this;
	}
	
	/**
	 * Build the push notification as configured. The result of this method is to be passed to PushNotification.send() as a parameter.
	 * @return the push notification built as specified, ready to be sent
	 */
	public JSONObject build(){
		JSONObject builtNotification = notification;
		
		notification = null;
		
		return builtNotification;
	}
	
}
