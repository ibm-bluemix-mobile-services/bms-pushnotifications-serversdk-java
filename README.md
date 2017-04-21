# Bluemix Mobile Services - Push Notifications Server-side SDK for Java
[![Build Status](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java.svg?branch=master)](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java)
[![Build Status](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java.svg?branch=development)](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fe43788a157c4c4b971a8918d29c4469)](https://www.codacy.com/app/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java&amp;utm_campaign=Badge_Grade)

The Push Notifications Server-side SDK for Java is used to send push notifications to registered devices through the [Push Notifications service in IBM® Bluemix](https://console.ng.bluemix.net/docs/services/mobilepush/index.html).

## Getting the SDK

You can get the SDK from Maven Central. For example, to get it with Maven, include the following in your dependencies:

```
<dependency>
	<groupId>com.ibm.mobilefirstplatform.serversdk.java</groupId>
	<artifactId>push</artifactId>
	<version>1.1.0</version>
</dependency>
```

## Sending a push notification

To send a push notification, first initialize the SDK with the Bluemix region of your application, and optionally, your credentials:

```
PushNotifications.init("YOUR_APPLICATION_ID", "YOUR_SECRET", PushNotifications.US_SOUTH_REGION); 
```

However, if your server is running on Bluemix as well, you can initialize it with just the region. 

You can achieve this by binding your Push Notification service to your server application in Bluemix, which will then give it access to the service's credentials. To do this, just initialize like this:

```
PushNotifications.init(PushNotifications.US_SOUTH_REGION);
```

Create Message attributes using builder.

```
Message message = new Message.Builder().alert("Testing Push Notification").url("www.example.com").build();

```
You can also configure the notification with some other optional settings.

Functionality added for FirefoxWeb, ChromeWeb, SafariWeb, ChromeAppExtension and extra optional settings introduced for Apns and GCM as shown below :

Builders are introduced which sets the optional settings for the platforms : 
```
// For APns Settings. **Note : category is deprecated, use interactiveCategory instead.
	
Apns apns = new Apns.Builder().badge(1).interactiveCategory("interactiveCategory")
.iosActionKey("iosActionKey").payload(new JSONObject()).sound("sound.wav")
.type(APNSNotificationType.DEFAULT).titleLocKey("titleLocKey").locKey("locKey")
.launchImage("launchImage").titleLocArgs(new String[] { "titlelocArgs1", "titlelocArgs2" })
.locArgs(new String[] { "locArgs1", "locArg2" }).title("title").subtitle("subtitle")
.attachmentUrl("attachmentUrl").build();

/*
 * Gcm Settings, style and lights attibute addded to Gcm optional settings
 * which can be constructed as shown below:
 */

GcmStyle gcmstyle = new GcmStyle.Builder().type(GcmStyleTypes.BIGTEXT_NOTIFICATION).text("text")
.title("title").url("url").lines(new String[] { "line1" }).build();

GcmLights gcmlights = new GcmLights.Builder().ledArgb(GcmLED.BLACK).ledOffMs(1).ledOnMs(1).build();

Gcm gcm = new Gcm.Builder().collapseKey("collapseKey").interactiveCategory("interactiveCategory")
.delayWhileIdle(true).payload(new JSONObject()).priority(GCMPriority.MIN)
.sound("mysound.wav").timeToLive(42).icon("icon").visibility(Visibility.PUBLIC).sync(true)
.style(gcmstyle).lights(gcmlights).build();

// Chrome Settings	
	
ChromeWeb chromeWeb = new ChromeWeb.Builder().title("title").iconUrl("iconUrl").timeToLive(42)
.payload(new JSONObject()).build();

/*
 * ChromeAppExtension settings. **Note: You need to provide a proper icon url 
 * for chromAppExtension notification to work properly.		
*/
ChromeAppExt chromeAppExt = new ChromeAppExt.Builder().collapseKey("collapseKey").delayWhileIdle(true).
title("title").iconUrl("iconUrl").timeToLive(42).payload(new JSONObject()).build();

// Firefox Settings		

FirefoxWeb firefoxWeb = new FirefoxWeb.Builder().title("title").iconUrl("iconUrl")
.timeToLive(42).payload(new JSONObject()).build();

// Safari Settings. For safari all the three settings are mandatory to set.	
	
SafariWeb safariWeb = new SafariWeb.Builder().title("title")
.urlArgs(new String[] {"urlArgs1"}).action("action").build();
```
**Note : We should provide either deviceIds or userIds or platforms or tagNames.
Below code snippet uses platforms, same way you can do it for deviceIds(...) or userIds (...) or tagNames(...)
```
Target target = new Target.Builder()
.platforms(new PushNotificationsPlatform[] {
PushNotificationsPlatform.APPLE, PushNotificationsPlatform.GOOGLE,
PushNotificationsPlatform.APPEXTCHROME,
PushNotificationsPlatform.WEBCHROME,
PushNotificationsPlatform.WEBSAFARI })
PushNotificationsPlatform.WEBFIREFOX,
.build();

```		
Now set optional values for all platforms to Settings object.
```
Settings settings = new Settings.Builder().apns(apns).gcm(gcm).chromeWeb(chromeWeb)
.chromeAppExt(chromeAppExt).firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();
```		

Now create a new notification as shown below:

```

Notification notification = new Notification.Builder().message(message).settings(settings).target(target).build(); 

```

Finally, pass this notification and an optional `ResponseListener` to get notified of the result:

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
