# Bluemix Mobile Services - Push Notifications Server-side SDK for Java
[![Build Status](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java.svg?branch=master)](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java)
[![Build Status](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java.svg?branch=development)](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java)

The Push Notifications Server-side SDK for Java is used to send push notifications to registered devices through the [Push Notifications service in IBM® Bluemix](https://console.ng.bluemix.net/docs/services/mobilepush/index.html).

##Getting the SDK

You can get the SDK from Maven Central. For example, to get it with Maven, include the following in your dependencies:

```
<dependency>
	<groupId>com.ibm.mobilefirstplatform.serversdk.java</groupId>
	<artifactId>push</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

##Sending a push notification

To send a push notification, first initialize the SDK with the Bluemix region of your application, and optionally, your credentials:

```
PushNotifications.init("YOUR_APPLICATION_ID", "YOUR_SECRET", PushNotifications.US_SOUTH_REGION);
```

However, if your server is running on Bluemix as well, you can initialize it with just the region. 

You can achieve this by binding your Push Notification service to your server application in Bluemix, which will then give it access to the service's credentials. To do this, just initialize like this:

```
PushNotifications.init(PushNotifications.US_SOUTH_REGION);
```

Then create a new notification using `NotificationBuilder`:

```
NotificationBuilder builder = new NotificationBuilder("This is the notification's text!");
```
You can also configure the notification with some other optional settings, such as:

```
builder.setMessageURL(urlToBeIncludedWithThePushNotification)
	.setTarget(deviceIdArray, platformArray, tagNameArray)
	.setAPNSSettings(badge, category, iosActionKey, payload, soundFile, APNSNotificationType)
	.setGCMSettings(collapseKey, delayWhileIdle, jsonPayload, GCMPriority.HIGH, soundFile, secondsToLive)
```
(Note that you can chain the different calls to `NotificationBuilder`.)

Finally, pass this notification using `NotificationBuilder.build()` and an optional `ResponseListener` to get notified of the result:

```
	PushNotifications.send(notification, new PushNotificationsResponseListener(){

		@Override
		public void onSuccess(int statusCode, String responseBody) {
			System.out.println("Successfully sent push notification! Status code: " + statusCode + " Response body: " + responseBody);
			
		}

		@Override
		public void onFailure(Integer statusCode, String responseBody, Throwable t) {
			System.out.println("Failed sent push notification. Status code: " + statusCode + " Response body: " + responseBody);
				
			if(t != null){
				t.printStackTrace();
			}
		}
			
	});
```

If you have a device that is registered to receive push notifications, you will now receive the notification that was sent.

## License

Copyright 2016 IBM Corp.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.