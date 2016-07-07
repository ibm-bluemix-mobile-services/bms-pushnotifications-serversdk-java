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
 * The push notification's message that is passed in the constructor is required. 
 * All other parameters are optional. Set them as needed.
 */
public class NotificationBuilder {
	private static final String MESSAGE_OBJECT_KEY = "message";
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
	
	/**
	 * Create a new NotificationBuilder to help create a new push notification.
	 * This NotificationBuilder can be used to configure the push notification before it is sent,
	 * by configuring optional parameters.
	 * 
	 * @param alert the message to be sent in the push notification
	 */
	public NotificationBuilder(String alert){
		if(alert == null){
			throw new IllegalArgumentException("The alert cannot be null.");
		}
		
		notification = new JSONObject();
		
		JSONObject message = new JSONObject();
		message.put("alert", alert);
		
		//An empty message is the minimum required.
		notification.put(MESSAGE_OBJECT_KEY, message);
	}
	
	/**
	 * Set an optional URL to be included with the push notification.
	 * 
	 * @param url the URL to be included
	 * @return the NotificationBuilder object so that calls can be chained
	 */
	public NotificationBuilder setMessageURL(String url){
		JSONObject message;

		message = notification.getJSONObject(MESSAGE_OBJECT_KEY);
		
		if(url != null && url.length() > 0){
			message.put("url", url);
			notification.put(MESSAGE_OBJECT_KEY, message);
		}
		
		return this;
	}
	
	/**
	 * Specify the targets that will receive the push notification.
	 * @param deviceIds an optional array of device ids specified as strings that the push notification will be sent to
	 * @param platforms an optional array of {@link PushNotificationsPlatform} enums used to specify which platforms to send to
	 * @param tagNames an optional string array with the list of tags that will receive the notification
	 * @return the NotificationBuilder object so that calls can be chained
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
			
			target.put("platforms", platforms);
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
	 * Configure specific Apple Push Notification Service (APNS) settings for iOS devices.
	 *  
	 * @param badge the number to display as the badge of the application icon
	 * @param category the category identifier to be used for intereactive push notifications
	 * @param iosActionKey the title for the Action key
	 * @param payload custom JSON payload that will be sent as part of the notification message
	 * @param soundFile the name of the sound file in the application bundle; the sound of this file is played as an alert
	 * @param type determines whether an alert is shown or the message is placed in the notification center; specified with the {@link APNSNotificationType} enum
	 * @return the NotificationBuilder object so that calls can be chained
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
	 * Configure specific Google Cloud Messaging (GCM) settings.
	 * 
	 * @param collapseKey key that identifies a group of push notifications that can be replaced with the latest one
	 * @param delayWhileIdle indicates whether the message should not be sent until the device becomes active
	 * @param payload custom JSON payload that will be sent as part of the notification message
	 * @param priority the priority of the message, specified using the {@link GCMPriority} enum
	 * @param soundFile the sound file (on device) that will be attempted to play when the notification arrives on the device
	 * @param secondsToLive specifies how long (in seconds) the message should be kept in GCM storage if the device is offline
	 * @return the NotificationBuilder object so that calls can be chained
	 */
	public NotificationBuilder setGCMSettings(String collapseKey, Boolean delayWhileIdle, JSONObject payload, GCMPriority priority, String soundFile, Integer secondsToLive ){
		JSONObject gcmSettings = new JSONObject();
		
		if(collapseKey != null && collapseKey.length() > 0){
			gcmSettings.put("collapseKey", collapseKey);
		}
		
		if(delayWhileIdle != null){
			gcmSettings.put("delayWhileIdle", delayWhileIdle.toString());
		}
		
		if(payload != null){
			gcmSettings.put("payload", payload);
		}
		
		if(priority != null){
			gcmSettings.put("priority", priority.name());
		}
		
		if(soundFile != null && soundFile.length() > 0){
			gcmSettings.put("sound", soundFile);
		}
		
		if(secondsToLive != null){
			gcmSettings.put("timeToLive", secondsToLive.intValue());
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
	 * Build the push notification as configured. The result of this method is to be passed to {@link PushNotifications#send(JSONObject, PushNotificationsResponseListener)} as a parameter.
	 * @return the push notification built as specified, ready to be sent
	 */
	public JSONObject build(){
		JSONObject builtNotification = notification;
		
		notification = null;
		
		return builtNotification;
	}
	
}
