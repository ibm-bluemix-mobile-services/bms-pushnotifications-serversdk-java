# IBM Cloud Mobile Services - Push Notifications server-side SDK for Java
[![Build Status](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java.svg?branch=master)](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java)
[![Build Status](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java.svg?branch=development)](https://travis-ci.org/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/fe43788a157c4c4b971a8918d29c4469)](https://www.codacy.com/app/ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ibm-bluemix-mobile-services/bms-pushnotifications-serversdk-java&amp;utm_campaign=Badge_Grade)

The [IBM Cloud Push Notifications service](https://cloud.ibm.com/catalog/services/push-notifications) provides a unified push service to send real-time notifications to mobile and web applications. The Push Notifications Server-side SDK for Java is used to send push notifications to registered devices using the Push Notifications service.

Ensure that you go through [IBM Cloud Push Notifications service documentation](https://cloud.ibm.com/docs/services/mobilepush?topic=mobile-pushnotification-gettingstartedtemplate#gettingstartedtemplate) before you start.

## Contents

- [Installation](#installation)
- [Initialize SDK](#initialize-sdk)
- [Simple notification](#simple-notification)
- [Notification options](#notification-options)
- [Send bulk Push Notifications](#send-bulk-push-notifications)
- [Samples and videos](#samples-and-videos)

## Installation

You can get the SDK from Maven Central. To get it with Maven, include the following in your dependencies:

```
<dependency>
	<groupId>com.ibm.mobilefirstplatform.serversdk.java</groupId>
	<artifactId>push</artifactId>
	<version>1.5.0</version>
</dependency>
```

## Initialize SDK
 Initialize the SDK with the IBM Cloud region of your application, and optionally, your credentials:
	
- Initialize with AppSecret
	
	```
	PushNotifications.init("YOUR_APPLICATION_ID", "YOUR_SECRET", PushNotifications.US_SOUTH_REGION); 
	```

	However, if your server is running on IBM Cloud as well, you can initialize it with just the region. You can achieve this by binding your Push Notification service to your server application in IBM Cloud, which will then give it access to the service's credentials. To initialize using the region:

	```
	PushNotifications.init(PushNotifications.US_SOUTH_REGION);
	```

- Initialize with ApiKey

	```
	PushNotifications.initWithApiKey("YOUR_APPLICATION_ID", "YOUR-PUSH-APIKEY", PushNotifications.US_SOUTH_REGION);
	```
		
	**Note:** If you are using dedicated service, use overrideServerHost and add any of the bluemixRegion (IBM Cloud region) value.

	The IBM Cloud regions where the Push Notifications service is hosted are,
	- `PushNotifications.US_SOUTH_REGION`
	- `PushNotifications.UK_REGION`
	- `PushNotifications.SYDNEY_REGION`
	- `PushNotifications.FRANKFURT_REGION`
	- `PushNotifications.US_EAST_REGION`
	
	```
	PushNotifications.overrideServerHost = "YOUR_SERVICE_HOST";
	PushNotifications.init("YOUR_APPLICATION_ID", "YOUR_SECRET", PushNotifications.US_SOUTH_REGION); 
	```


## Simple notification

 Create Message attributes using builder.
 
 	Message message = new Message.Builder().alert("20% Off Offer for you").url("www.ibm.com").build();
	
 
## Notification options

You can also configure the notification with some other optional settings. Functionality added for FirefoxWeb, ChromeWeb, SafariWeb, ChromeAppExtension and extra optional settings introduced for APNs and FCM are listed in this document.

Builders are introduced which sets the optional settings for the platforms: 

- APNs
	
	```
		// For APNs settings.
		APNs apns = new APNs.Builder().badge(1).interactiveCategory("Accept")
			.iosActionKey("PUSH_OFFER").payload(new JSONObject().put("alert" , "20% Off for you"))
			.sound("sound.wav")
			.type(APNSNotificationType.DEFAULT).titleLocKey("OFFER").locKey("REPLYTO")
			.launchImage("launchImage1.png")
			.titleLocArgs(new String[] {"Jenna", "Frank"})
			.locArgs(new String[] { "Jenna","Frank" }).title("IBM").subtitle("IBM Cloud")
			.attachmentUrl("https://developer.blackberry.com/native/files/documentation/images/text_messages_icon.png")
			.build();
	```
- FCM

	```
	
		//For FCM settings
		/* Style and lights attibute addded to FCM optional settings which 
		/* can be constructed as shown below. Also the timetolive setting is 
		/* provided which specifies the duration (in seconds). 
		/* The message should be kept in FCM storage if the device is offline.*/
	
		FCMStyle fcmstyle = new FCMStyle.Builder().type(FCMStyleTypes.BIGTEXT_NOTIFICATION).text("BIG TEXT NOTIFICATION")
			.title("Big Text Notification")
			.url("https://developer.blackberry.com/native/files/documentation/images/text_messages_icon.png")
			.lines(new String[] { "IBM", "IBM Cloud", "Big Text Notification" }).build();
		FCMLights fcmlights = new FCMLights.Builder().ledArgb(FCMLED.GREEN).ledOffMs(1).ledOnMs(1).build();
		FCM fcm = new FCM.Builder().androidTitle("androidTitle").collapseKey("ping").interactiveCategory("Accept")
			.delayWhileIdle(true).payload(new JSONObject().put("alert" , "20% Off for you"))
			.priority(FCMPriority.MIN)
			.sound("mysound.wav").timeToLive(3)
			.icon("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTptVxkAVpfhZO0h2KXbnQLg16yvDa7uF-y1t5KGmABDxJ13XoHR1YklGM")
			.visibility(Visibility.PUBLIC).sync(true).style(fcmstyle).lights(fcmlights).build();
		
	```
- Chrome

	```
		// Chrome settings	
		ChromeWeb chromeWeb = new ChromeWeb.Builder().title("IBM Push Offer")
			.iconUrl("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTptVxkAVpfhZO0h2KXbnQLg16yvDa7uF-y1t5KGmABDxJ13XoHR1YklGM")
			.timeToLive(3).payload(new JSONObject().put("alert" , "20% Off for you")).build();
		
	```
- ChromeAppExtension

	```
		//ChromeAppExtension settings.  
		//You need to provide a proper icon urlfor chromAppExtension notification to work properly.		
		ChromeAppExt chromeAppExt = new ChromeAppExt.Builder().collapseKey("ping").delayWhileIdle(true)
			.title("IBM Push Offer")
			.iconUrl("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTptVxkAVpfhZO0h2KXbnQLg16yvDa7uF-y1t5KGmABDxJ13XoHR1YklGM")
			.timeToLive(3).payload(new JSONObject().put("alert" , "20% Off for you")).build();
	```
- Firefox

	```
		// Firefox Settings		
		FirefoxWeb firefoxWeb = new FirefoxWeb.Builder().title("IBM Offer").iconUrl("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTptVxkAVpfhZO0h2KXbnQLg16yvDa7uF-y1t5KGmABDxJ13XoHR1YklGM")
			.timeToLive(3).payload(new JSONObject().put("alert" , "20% Off for you")).build();
			
	```
- Safari

	```
		// Safari Settings. For safari all the three settings are mandatory to set.	
		SafariWeb safariWeb = new SafariWeb.Builder().title("IBM Offer")
			.urlArgs(new String[] {"www.IBM.com"}).action("View").build();
	```

	**Note** : Ensure that you provide either deviceIds or userIds or platforms or tagNames.The following code snippet uses platforms, same way you can do it for deviceIds(...) or userIds (...) or tagNames(...).
	
	```
	Target target = new Target.Builder()
	.platforms(new Platform[] {
	Platform.APPLE, Platform.GOOGLE,
	Platform.APPEXTCHROME,
	Platform.WEBCHROME,
	Platform.WEBSAFARI, 
	Platform.WEBFIREFOX})
	.build();
	```		

1. 	Set optional values for all platforms to Settings object.
	```
	Settings settings = new Settings.Builder().apns(apns).fcm(fcm).chromeWeb(chromeWeb)
		.chromeAppExt(chromeAppExt).firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();
	```		

2. Create a new notification.
	```
	Notification notification = new Notification.Builder().message(message).settings(settings).target(target).build(); 
	```

3. Pass the notification.
	
	An optional callback `ResponseListener` is provided if you want to get notified of the result or do some processing with response `satusCode` or `responseBody` returned by this callback.

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

You will now receive the notification that was sent, on the device that you had registered.


## Send bulk Push Notifications

To send bulk push notifications do the following,

```
	Notification[] ff = new Notification[]{notification1,notification2};
	PushNotifications.sendBulk(ff, new PushNotificationsResponseListener(){
		public void onSuccess(int statusCode, String responseBody) {
			System.out.println("Successfully sent push notification! Status code: " + statusCode + " Response body: " + responseBody);
		}
		public void onFailure(Integer statusCode, String responseBody, Throwable t) {
			System.out.println("Failed sent push notification. Status code: " + statusCode + " Response body: " + responseBody);
			if(t != null){
				t.printStackTrace();
			}
		}
	});
```

For Javadocs please follow the link - https://www.javadoc.io/doc/com.ibm.mobilefirstplatform.serversdk.java/push

## Samples and videos

* For samples, visit - [Github Sample](https://github.com/ibm-bluemix-mobile-services/bms-samples-swift-hellopush)

* For video tutorials visit - [IBM Cloud Push Notifications](https://www.youtube.com/playlist?list=PLTroxxTPN9dIZYn9IU-IOcQePO-u5r0r4)

### Learning more

* Visit the **[IBM Cloud Developers Community](https://developer.ibm.com/depmodels/cloud/)**.

* [Getting started with IBM MobileFirst Platform for iOS](https://cloud.ibm.com/docs/mobile)

### Connect with IBM Cloud

[Twitter](https://twitter.com/IBMCloud) |
[YouTube](https://www.youtube.com/watch?v=AVPoBWScRQc) |
[Blog](https://developer.ibm.com/depmodels/cloud/) |
[Facebook](https://www.facebook.com/ibmcloud) |


=======================
Copyright 2020-21 IBM Corp.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.