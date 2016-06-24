package com.ibm.mobilefirstplatform.serversdk.java.push;

import org.json.JSONArray;
import org.json.JSONObject;

public class NotificationBuilder {
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
	
	public enum GCMPriority {
		DEFAULT,
		MIN,
		LOW,
		HIGH,
		MAX
	}
	
	public NotificationBuilder(){
		notification = new JSONObject();
	}
	
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
	 * 
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
	
	public NotificationBuilder setAPNSSettings(){
		//TODO:
		return this;
	}
	
	public NotificationBuilder setGCMSettings(String collapseKey, Boolean delayWhileIdle, JSONObject payload, GCMPriority priority, String soundFile, Integer secondsToLive ){
		if(collapseKey != null && collapseKey.length() > 0){
			notification.put("collapseKey", collapseKey);
		}
		
		if(delayWhileIdle != null){
			notification.put("delayWhileIdle", "" + delayWhileIdle.booleanValue());
		}
		
		if(payload != null){
			notification.put("payload", payload);
		}
		
		if(priority != null){
			notification.put("priority", priority.name().toLowerCase());
		}
		
		if(soundFile != null && soundFile.length() > 0){
			notification.put("soundFile", soundFile);
		}
		
		if(secondsToLive != null){
			notification.put("secondsToLive", "" + secondsToLive.intValue());
		}
		
		
		return this;
	}
	
	public JSONObject build(){
		JSONObject builtNotification = notification;
		
		notification = null;
		
		return builtNotification;
	}
	
}
