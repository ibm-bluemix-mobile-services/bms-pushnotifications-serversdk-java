package com.ibm.mobilefirstplatform.serversdk.java.push;

import com.ibm.mobilefirstplatform.serversdk.java.push.Target.Builder.Platform;

public class NotificationImpl {

	public static void main(String[] args) {

    	PushNotifications.init("7319b1fd-c4a0-4a22-907f-6c4196fdec47", "1076a7f1-14b2-47d4-bf18-b21d080da225", PushNotifications.US_SOUTH_REGION); 
    	Message message = new Message.Builder().alert("20% Off Offer for you").url("www.ibm.com").build();
    	Target target = new Target.Builder()
    			.platforms(new Platform[] {
    			Platform.APPLE})
    			.build();
    	System.out.println(System.getProperty("TLS"));
    	
    	
    	System.out.println(System.getProperty("java.version"));
    	Notification notification = new Notification.Builder().message(message).target(target).build(); 
    	PushNotifications.send(notification, new PushNotificationsResponseListener(){
    		
    		public void onSuccess(int statusCode, String responseBody) {
    			System.out.println(responseBody);
    			System.out.println("Successfully sent push notification! Status code: " + statusCode + " Response body: " + responseBody);
    		}
    		
    		public void onFailure(Integer statusCode, String responseBody, Throwable t) {
    			System.out.println("Failed sent push notification. Status code: " + statusCode + " Response body: " + responseBody);
    			if(t != null){
    				t.printStackTrace();
    			}
    		}
    	});
	}

	
}
