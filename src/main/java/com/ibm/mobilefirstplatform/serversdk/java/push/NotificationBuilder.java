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

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Message;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Message.MessageBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns.ApnsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeAppExt;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeAppExt.ChromeAppExtBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeWeb.ChromeWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.FirefoxWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.FirefoxWeb.FirefoxWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SafariWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SafariWeb.SafariWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target.TargetBuilder;

/**
 * The NotificationBuilder is used to create a new push notification that is
 * going to be sent using the Push Notification service in IBMÂ® Bluemix.
 * 
 * The push notification's message that is passed in the constructor is
 * required. All other parameters are optional. Set them as needed.
 */
public class NotificationBuilder {

	protected JSONObject notification;

	private static ObjectMapper mapper = new ObjectMapper();

	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

	public enum PushNotificationsPlatform {
		APPLE("A"), GOOGLE("G"), WEBCHROME("WEB_CHROME"), WEBFIREFOX("WEB_FIREFOX"), WEBSAFARI(
				"WEB_SAFARI"), APPEXTCHROME("APPEXT_CHROME");

		private String platformCode;

		PushNotificationsPlatform(String code) {
			this.platformCode = code;
		}

		public String getValue() {
			return platformCode;
		}
	}

	public enum APNSNotificationType {
		DEFAULT, MIXED, SILENT
	}

	public enum GCMPriority {
		DEFAULT, MIN, LOW, HIGH, MAX
	}

	public enum Visibility {
		PUBLIC, PRIVATE, SECRET;
	}
	/**
	 Determines the LED value in the notifications
	 */
	public enum GcmLED {
	    
	   BLACK,
	   DARKGRAY,
	   GRAY,
	   LIGHTGRAY,
	   WHITE,
	   RED,
	   GREEN,
	   BLUE,
	   YELLOW,
	   CYAN,
	   MAGENTA,
	   TRANSPARENT
	}
	
	/**
	 * The available style type of the gcm notification message.
	 */
	public enum GcmStyleTypes {
		
		BIGTEXT_NOTIFICATION,
		INBOX_NOTIFICATION,
		PICTURE_NOTIFICATION
	}

	/**
	 * Create a new NotificationBuilder to help create a new push notification.
	 * This NotificationBuilder can be used to configure the push notification
	 * before it is sent, by configuring optional parameters.
	 * 
	 * @param alert
	 *            the message to be sent in the push notification
	 */
	public NotificationBuilder(String alert) {
		if (alert == null) {
			throw new IllegalArgumentException(PushConstants.ALERTNOTNULLEXCEPTIOPN);
		}

		final PushMessageModel model = new PushMessageModel.PushMessageModelBuilder()
				.setMessageBuilder(BuilderFactory.getBuilder(MessageBuilder.class)).build();
		final Message message = model.getMessageBuilder().setAlert(alert).build();

		notification = new JSONObject();

		final JSONObject messageObj = generateJSON(message);
		// message.put("alert", alert);

		// An empty message is the minimum required.
		notification.put(PushConstants.MESSAGE_OBJECT_KEY, messageObj);
	}

	/**
	 * Set an optional URL to be included with the push notification.
	 * 
	 * @param url
	 *            the URL to be included
	 * @return the NotificationBuilder object so that calls can be chained
	 */
	public NotificationBuilder setMessageURL(String url) {
		JSONObject message;
		message = notification.getJSONObject(PushConstants.MESSAGE_OBJECT_KEY);

		if (url != null && url.length() > 0) {

			final PushMessageModel model = new PushMessageModel.PushMessageModelBuilder()
					.setMessageBuilder(BuilderFactory.getBuilder(MessageBuilder.class)).build();
			final Message messageObj = model.getMessageBuilder().setUrl(url).build();

			final JSONObject jsonMessageObj = generateJSON(messageObj);
			if (message != null) {
				final JSONObject mergeMessage = mergeJasonObj(message, jsonMessageObj);
				notification.put(PushConstants.MESSAGE_OBJECT_KEY, mergeMessage);
			}
		}
		return this;
	}

	/**
	 * Specify the targets that will receive the push notification.
	 * 
	 * @param deviceIds
	 *            an optional array of device ids specified as strings that the
	 *            push notification will be sent to
	 * @param userIds
	 *            an optional array of user ids specified as strings for whose
	 *            devices the push notification will be sent to
	 * @param platforms
	 *            an optional array of {@link PushNotificationsPlatform} enums
	 *            used to specify which platforms to send to
	 * @param tagNames
	 *            an optional string array with the list of tags that will
	 *            receive the notification
	 * @return the NotificationBuilder object so that calls can be chained
	 */
	@Deprecated
	public NotificationBuilder setTarget(String[] deviceIds, String[] userIds, PushNotificationsPlatform[] platforms,
			String[] tagNames) {

		PushMessageModel model = null;
		Target targetObj = null;

		if (checkTargetParams(deviceIds, userIds, platforms, tagNames)) {

			targetObj = new PushMessageModel.PushMessageModelBuilder()
					.setTargetBuilder(BuilderFactory.getBuilder(TargetBuilder.class)).build().getTargetBuilder()
					.setDeviceIds(deviceIds).setUserIds(userIds).setPlatforms(platforms).setTagNames(tagNames).build();
			model = new PushMessageModel.PushMessageModelBuilder().setTarget(targetObj).build();
		}

		if (targetObj != null) {

			final JSONObject target = generateJSON(model);

			if (!target.keySet().isEmpty()) {
				notification = mergeJasonObj(target, notification);

			}
		}

		return this;
	}

	/**
	 * Configure specific to Target.
	 * 
	 * Accepts an argument of type {@link Target} class which has the following
	 * members below:
	 * <ul>
	 * <li>deviceIds(String[])<code>:</code>&nbsp; an optional array of device
	 * ids specified as strings that the push notification will be sent to.</li>
	 * <li>userIds(String[])<code>:</code>&nbsp; an optional array of user ids
	 * specified as strings for whose devices the push notification will be sent
	 * to.</li>
	 * <li>platforms(String[])<code>:</code>&nbsp; an optional array of
	 * {@link PushNotificationsPlatform} enums used to specify which platforms
	 * to send to.</li>
	 * <li>tagNames(String[])<code>:</code>&nbsp; an optional string array with
	 * the list of tags that will receive the notification</li>
	 * </ul>
	 
	 * Below is the code snippet for usage of this method:
	 * 
	 * <pre>
	 * NotificationBuilder builder = new NotificationBuilder("Test");
	 * </pre>
	 * 
	 * You need to create {@link PushMessageModel} object as mentioned below:
	 * 
	 * <pre>
	 * PushMessageModel model = new PushMessageModel.PushMessageModelBuilder()
	 * 		.setTargetBuilder(BuilderFactory.getBuilder(TargetBuilder.class)).build();
	 * </pre>
	 * 
	 * Using above model object you can easily create {@link TargetBuilder}
	 * which is will act as a builder for {@link Target} and set its all or only
	 * members required by you in one go as shown below:
	 * 
	 * <pre>
	 * 
	 * builder.setTarget(model.getTargetBuilder().setDeviceIds(new String[] {
	 * "device1", "device2" }).setUserIds(new String[] { "userId1", "userId2"
	 * }).setPlatforms(new PushNotificationsPlatform[] {
	 * PushNotificationsPlatform.APPLE, PushNotificationsPlatform.GOOGLE })
	 * .setTagNames(new String[] { "tag1", "tag2" }).build());
	 * //build() returns Target Object
	 * </pre> 
	 * @param target 
	 *				target object with either deviceIds or userIds or platforms or tagNames.
	 *
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	 
	 

	public NotificationBuilder setTargetValues(Target target) {

		if (target != null) {

			PushMessageModel model = new PushMessageModel.PushMessageModelBuilder().setTarget(target).build();
			final JSONObject targetObj = generateJSON(model);

			if (!targetObj.keySet().isEmpty()) {
				notification = mergeJasonObj(targetObj, notification);

			}
		}

		return this;
	}

	private boolean checkTargetParams(String[] deviceIds, String[] userIds, PushNotificationsPlatform[] platforms,
			String[] tagNames) {
		return (deviceIds != null && deviceIds.length > 0) || (userIds != null && userIds.length > 0)
				|| (platforms != null && platforms.length > 0) || (tagNames != null && tagNames.length > 0);
	}

	/**
	 * Configure specific to SafariWeb settings.
	 * 
	 * Accepts an argument of type {@link SafariWeb} class which has the
	 * following members below:
	 * <ul>
	 * <li>title (String)<code>:</code>&nbsp;Specifies the title to be set for
	 * the SafariWeb Push Notifications.</li>
	 * 
	 * <li>urlArgs (String [])<code>:</code>&nbsp;The URL arguments that need to
	 * be used with this notification. This has to provided in the form of a
	 * JSON Array.</li>
	 * 
	 * <li>action (String)<code>:</code>&nbsp;The label of the action
	 * button.</li>
	 * </ul>
	 * Below is the code snippet for usage of this method:
	 * 
	 * <pre>
	 * NotificationBuilder builder = new NotificationBuilder("Test");
	 * </pre>
	 * 
	 * You need to create {@link Settings} object as mentioned below:
	 * 
	 * <pre>
	 * Settings settings = new Settings.SettingsBuilder()
	 * 		.setSafariWebBuilder(BuilderFactory.getBuilder(SafariWebBuilder.class)).build();
	 * </pre>
	 * 
	 * Using above settings object you can easily create
	 * {@link SafariWebBuilder} which is will act as a builder for
	 * {@link SafariWeb} and set its all or only members required by you in one
	 * go as shown below:
	 * 
	 * <pre>
	 * 
	 * builder.setSafariWebSettings(settings.getSafariWebBuilder().setTitle("testTitle")
	 * 		.setUrlArgs(new String[] {"testUrlArgs1"}).setAction("testAction").build());
	 * // build() returns SafariWeb Object
	 * </pre>
	 * 
	 * @param safariWeb
	 * 				  safariWeb object with safariWeb settings.
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder setSafariWebSettings(SafariWeb safariWeb) {

		if (safariWeb != null) {

			Settings settings = new Settings.SettingsBuilder().setSafariWeb(safariWeb).build();

			final JSONObject safariSettings = generateJSON(settings);

			if (!safariSettings.keySet().isEmpty()) {
				JSONObject jasonSettings = null;
				JSONObject jsonNotification = null;

				if (notification.has(PushConstants.SETTINGS_OBJECT_KEY)) {

					jsonNotification = notification.getJSONObject(PushConstants.SETTINGS_OBJECT_KEY);

					jasonSettings = mergeJasonObj(jsonNotification, safariSettings);
				} else {

					jasonSettings = safariSettings;
				}

				notification.put(PushConstants.SETTINGS_OBJECT_KEY, jasonSettings);
			}
		}
		return this;
	}

	/**
	 * Configure specific to FirefoxWeb.
	 * 
	 * Accepts an argument of type {@link FirefoxWeb} class which has the
	 * following members below:
	 * <ul>
	 * <li>title (string)<code>:</code>&nbsp;Specifies the title to be set for
	 * the FirefoxWeb Push Notifications.</li>
	 * 
	 * <li>iconUrl (string)<code>:</code>&nbsp;The URL of the icon to be set for
	 * the WebPush Notification.</li>
	 * 
	 * <li>timeToLive (string)<code>:</code>&nbsp;This parameter specifies how
	 * long (in seconds) the message should be kept in GCM storage if the device
	 * is offline.</li>
	 * 
	 * <li>payload (Object)<code>:</code>&nbsp;Custom JSON payload that will be
	 * sent as part of the notification message.</li>
	 * </ul>
	 * Below is the code snippet for usage of this method:
	 * 
	 * <pre>
	 * NotificationBuilder builder = new NotificationBuilder("Test");
	 * </pre>
	 * 
	 * You need to create {@link Settings} object as mentioned below:
	 * 
	 * <pre>
	 * Settings settings = new Settings.SettingsBuilder()
	 * 		.setFirefoxWebBuilder(BuilderFactory.getBuilder(FirefoxWebBuilder.class)).build();
	 * </pre>
	 * 
	 * Using above settings object you can easily create
	 * {@link FirefoxWebBuilder} which is will act as a builder for
	 * {@link FirefoxWeb} and set its all or only members required by you in one
	 * go as shown below:
	 * 
	 * <pre>
	 * 
	 * builder.setFirefoxWebSettings(settings.getFirefoxWebBuilder().setTitle("testTitle").setIconUrl("testIconUrl")
	 * 		.setTimeToLive(42).setPayload(new JSONObject()).build()); // build() returns FirefoxWeb object
	 * </pre>
	 * 
	 * @param firefoxWeb
	 * 				 	 firefoxWeb object with firefoxWeb settings.	
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder setFirefoxWebSettings(FirefoxWeb firefoxWeb) {

		if (firefoxWeb != null) {

			Settings settings = new Settings.SettingsBuilder().setFirefoxWeb(firefoxWeb).build();

			final JSONObject firefoxSettings = generateJSON(settings);

			if (!firefoxSettings.keySet().isEmpty()) {
				JSONObject jasonSettings = null;
				JSONObject jsonNotification = null;

				if (notification.has(PushConstants.SETTINGS_OBJECT_KEY)) {

					jsonNotification = notification.getJSONObject(PushConstants.SETTINGS_OBJECT_KEY);

					jasonSettings = mergeJasonObj(jsonNotification, firefoxSettings);
				} else {

					jasonSettings = firefoxSettings;
				}

				notification.put(PushConstants.SETTINGS_OBJECT_KEY, jasonSettings);
			}
		}
		return this;
	}

	/**
	 * Configure specific to ChromeAppExt platform.
	 * 
	 * Accepts an argument of type {@link ChromeAppExt} class which has the
	 * following members below:
	 * <ul>
	 * <li>title (string)<code>:</code>&nbsp;Specifies the title to be set for the
	 * WebPush Notification.</li>
	 * <li>iconUrl (string)<code>:</code>&nbsp;The URL of the icon to be set for the
	 * WebPush Notification ,if you set this setting then proper icon url should be provided or else chromeAppExtension would not work.</li>
	 * <li>timeToLive (integer)<code>:</code>&nbsp;This parameter specifies how long
	 * (in seconds) the message should be kept in GCM storage if the device is
	 * offline.</li>
	 * <li>payload (Object)<code>:</code>&nbsp;Custom JSON payload that will be sent
	 * as part of the notification message.</li>
	 * </ul>
	 * Below is the code snippet for usage of this method:.
	 * 
	 * <pre>
	 * NotificationBuilder builder = new NotificationBuilder("Test");
	 * </pre>
	 * 
	 * You need to create {@link Settings} object as mentioned below:
	 * 
	 * <pre>
	 * Settings settings = new Settings.SettingsBuilder()
	 * 		.setChromeAppExtBuilder(BuilderFactory.getBuilder(ChromeAppExtBuilder.class)).build();
	 * </pre>
	 * 
	 * Using above settings object you can easily create
	 * {@link ChromeAppExtBuilder} which is will act as a builder for
	 * {@link ChromeAppExt} and set its all or only members required by you in
	 * one go as shown below:
	 * 
	 * <pre>
	 * 
	 * builder.setChromeAppExtSettings(settings.getChromeAppExtBuilder().setCollapseKey("testCollapseKey")
	 * 		.setDelayWhileIdle(true).setTitle("testTitle").setIconUrl("testIconUrl").setTimeToLive(42)
	 * 		.setPayload(new JSONObject()).build()); // build() returns ChromeAppExt object
	 * </pre>
	 * 
	 * @param chromeAppExt
	 * 		 			   chromeAppExt object with chromeAppExtension settings.
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	public NotificationBuilder setChromeAppExtSettings(ChromeAppExt chromeAppExt) {

		if (chromeAppExt != null) {

			Settings settings = new Settings.SettingsBuilder().setChromeAppExt(chromeAppExt).build();

			final JSONObject chromExtSettings = generateJSON(settings);

			if (!chromExtSettings.keySet().isEmpty()) {
				JSONObject jasonSettings = null;
				JSONObject jsonNotification = null;

				if (notification.has(PushConstants.SETTINGS_OBJECT_KEY)) {

					jsonNotification = notification.getJSONObject(PushConstants.SETTINGS_OBJECT_KEY);

					jasonSettings = mergeJasonObj(jsonNotification, chromExtSettings);
				} else {

					jasonSettings = chromExtSettings;
				}

				notification.put(PushConstants.SETTINGS_OBJECT_KEY, jasonSettings);
			}
		}
		return this;
	}

	/**
	 * Configure specific for Chrome Browser.
	 * 
	 * Accepts an argument of type {@link ChromeWeb} class which has the
	 * following members below:
	 * <ul>
	 * <li>title (string)<code>:</code>&nbsp;Specifies the title to be set for the
	 * WebPush Notification.</li>
	 * <li>iconUrl (string)<code>:</code>&nbsp;The URL of the icon to be set for the
	 * WebPush Notification.</li>
	 * <li>timeToLive (integer)<code>:</code>&nbsp;This parameter specifies how
	 * long (in seconds) the message should be kept in GCM storage if the device
	 * is offline.</li>
	 * <li>payload (string)<code>:</code>&nbsp;Custom JSON payload that will be sent
	 * as part of the notification message.</li>
	 * </ul>
	 * Below is the code snippet for usage of this method:.
	 * 
	 * <pre>
	 * NotificationBuilder builder = new NotificationBuilder("Test");
	 * </pre>
	 * 
	 * You need to create {@link Settings} object as mentioned below:
	 * 
	 * <pre>
	 * Settings settings = new Settings.SettingsBuilder()
	 * 		.setChromeWebBuilder(BuilderFactory.getBuilder(ChromeWebBuilder.class)).build();
	 * </pre>
	 * 
	 * Using above settings object you can easily create
	 * {@link ChromeWebBuilder} which is will act as a builder for
	 * {@link ChromeWeb} and set its all or only members required by you in one
	 * go as shown below:
	 * 
	 * <pre>
	 * 
	 * builder.setChromeSettings(settings.getChromeWebBuilder().setTitle("testTitle").setIconUrl("testIconUrl")
	 * 		.setTimeToLive(42).setPayload(new JSONObject()).build()); 
	 * // build() returns ChromeWeb Object
	 * </pre>
	 * 
	 * @param chromeWeb
	 * 					chromeWeb object with chromeWeb settings.
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	public NotificationBuilder setChromeSettings(ChromeWeb chromeWeb) {

		if (chromeWeb != null) {

			Settings settings = new Settings.SettingsBuilder().setChromeWeb(chromeWeb).build();

			final JSONObject chromSettings = generateJSON(settings);

			if (!chromSettings.keySet().isEmpty()) {
				JSONObject jasonSettings = null;
				JSONObject jsonNotification = null;

				if (notification.has(PushConstants.SETTINGS_OBJECT_KEY)) {

					jsonNotification = notification.getJSONObject(PushConstants.SETTINGS_OBJECT_KEY);

					jasonSettings = mergeJasonObj(jsonNotification, chromSettings);
				} else {

					jasonSettings = chromSettings;
				}

				notification.put(PushConstants.SETTINGS_OBJECT_KEY, jasonSettings);

			}
		}
		return this;
	}

	/**
	 * Configure specific Apple Push Notification Service (APNS) settings for
	 * iOS devices.
	 * 
	 * @param badge
	 *            the number to display as the badge of the application icon
	 * @param interactiveCategory
	 *            the category identifier to be used for intereactive push
	 *            notifications
	 * @param iosActionKey
	 *            the title for the Action key
	 * @param payload
	 *            custom JSON payload that will be sent as part of the
	 *            notification message
	 * @param soundFile
	 *            the name of the sound file in the application bundle; the
	 *            sound of this file is played as an alert
	 * @param type
	 *            determines whether an alert is shown or the message is placed
	 *            in the notification center; specified with the
	 *            {@link APNSNotificationType} enum
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	@Deprecated
	public NotificationBuilder setAPNSSettings(Integer badge, String interactiveCategory, String iosActionKey,
			JSONObject payload, String soundFile, APNSNotificationType type) {

		Settings settings = null;

		Apns apns = null;

		if (checkAPNSParams(badge, interactiveCategory, iosActionKey, payload, soundFile, type)) {

			apns = new Settings.SettingsBuilder().setApnsBuilder(new ApnsBuilder()).build().getApnsBuilder()
					.setBadge(badge).setInteractiveCategory(interactiveCategory).setIosActionKey(iosActionKey)
					.setPayload(payload).setSound(soundFile).setType(type).build();

			settings = new Settings.SettingsBuilder().setApns(apns).build();

		}

		if (apns != null) {

			final JSONObject apnsSettings = generateJSON(settings);

			if (!apnsSettings.keySet().isEmpty()) {
				JSONObject jasonSettings = null;
				JSONObject jsonNotification = null;

				if (notification.has(PushConstants.SETTINGS_OBJECT_KEY)) {

					jsonNotification = notification.getJSONObject(PushConstants.SETTINGS_OBJECT_KEY);

					jasonSettings = mergeJasonObj(jsonNotification, apnsSettings);
				} else {

					jasonSettings = apnsSettings;
				}

				notification.put(PushConstants.SETTINGS_OBJECT_KEY, jasonSettings);
			}
		}

		return this;
	}

	private boolean checkAPNSParams(Integer badge, String category, String iosActionKey, JSONObject payload,
			String soundFile, APNSNotificationType type) {
		return (badge != null) || (category != null && category.length() > 0)
				|| (iosActionKey != null && iosActionKey.length() > 0) || (payload != null)
				|| (soundFile != null && soundFile.length() > 0) || (type != null);
	}

	/**
	 * Configure specific SafariWeb settings for APNS Platform.
	 * 
	 * Accepts an argument of type {@link Apns} class which has the following
	 * members below:
	 * <ul>
	 * <li>title<code>:</code>&nbsp;Specifies the title to be set for the
	 * SafariWeb Push Notifications.</li>
	 * 
	 * <li>badge (integer)<code>:</code>&nbsp;The number to display as the badge
	 * of the application icon.</li>
	 * <li>interactiveCategory (string)<code>:</code>&nbsp;The category identifier to
	 * be used for the interactive push notifications.</li>
	 * <li>iosActionKey (string)<code>:</code>&nbsp;The title for the Action key.</li>
	 * <li>payload (object)<code>:</code>&nbsp;Custom JSON payload that will be
	 * sent as part of the notification message.</li>
	 * <li>sound (string)<code>:</code>&nbsp;The name of the sound file in the
	 * application bundle. The sound of this file is played as an alert.</li>
	 * <li>titleLocKey (string)<code>:</code>&nbsp;The key to a title string in
	 * the Localizable.strings file for the current localization. The key string
	 * can be formatted with %@ and %n$@ specifiers to take the variables
	 * specified in the titleLocArgs array.</li>
	 * <li>locKey (string)<code>:</code>&nbsp;A key to an alert-message string in
	 * a Localizable.strings file for the current localization (which is set by
	 * the users language preference). The key string can be formatted with %@
	 * and %n$@ specifiers to take the variables specified in the locArgs array.</li>
	 * <li>launchImage (string)<code>:</code>&nbsp;The filename of an image file
	 * in the app bundle, with or without the filename extension. The image is
	 * used as the launch image when users tap the action button or move the
	 * action slider.</li>
	 * <li>titleLocArgs (Array[string])<code>:</code>&nbsp;Variable string values
	 * to appear in place of the format specifiers in title-loc-key.</li>
	 * <li>locArgs (Array[string])<code>:</code>&nbsp;Variable string values to
	 * appear in place of the format specifiers in locKey.</li>
	 * <li>title (string)<code>:</code>&nbsp;The title of Rich Push notifications
	 * (Supported only on iOS 10 and above).</li>
	 * <li>subtitle (string)<code>:</code>&nbsp;The subtitle of the Rich
	 * Notifications. (Supported only on iOS 10 and above)</li>
	 * <li>attachmentUrl (string)<code>:</code>&nbsp;The link to the iOS
	 * notifications media (video, audio, GIF, images - Supported only on iOS 10
	 * and above).</li>
	 * <li>type (string) = ['DEFAULT', 'MIXED', 'SILENT']</li> 
	 * </ul>
	 * Below is the code snippet for usage of this method:
	 * 
	 * <pre>
	 * NotificationBuilder builder = new NotificationBuilder("Test");
	 * </pre>
	 * 
	 * You need to create {@link Settings} object as mentioned below:
	 * 
	 * <pre>
	 * Settings settings = new Settings.SettingsBuilder().setApnsBuilder(BuilderFactory.getBuilder(ApnsBuilder.class))
	 * 		.build();
	 * </pre>
	 * 
	 * Using above settings object you can easily create {@link ApnsBuilder}
	 * which is will act as a builder for Apns and set its all or only members
	 * required by you in one go as shown below:
	 * 
	 * <pre>
	 * 
	 * builder.setAPNSSettings(settings.getApnsBuilder().setBadge(1).setInteractiveCategory("testInteractiveCategory")
	 * 		.setIosActionKey("testiOSActionKey").setPayload(new JSONObject()).setSound("testSoundFile")
	 * 		.setType(APNSNotificationType.DEFAULT).setTitleLocKey("testTitleLocKey").setLocKey("testLocKey")
	 * 		.setLaunchImage("testLaunchImage")
	 * 		.setTitleLocArgs(new String[] { "testTitleLocArgs1", "testTitleLocArgs2" })
	 * 		.setLocArgs(new String[] { "testLocArgs1", "testLocArgs" }).setTitle("testTitle")
	 * 		.setSubtitle("testSubtitle").setAttachmentUrl("testAttachmentUrl").build()); 
	 * // build() returns the Apns object.
	 * 
	 * </pre>
	 * 
	 * @param apns
	 * 				apns object with apns settings.
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder setAPNSSettingsValues(Apns apns) {

		if (apns != null) {

			Settings settings = new Settings.SettingsBuilder().setApns(apns).build();

			final JSONObject apnsSettings = generateJSON(settings);

			if (!apnsSettings.keySet().isEmpty()) {
				JSONObject jasonSettings = null;
				JSONObject jsonNotification = null;

				if (notification.has(PushConstants.SETTINGS_OBJECT_KEY)) {

					jsonNotification = notification.getJSONObject(PushConstants.SETTINGS_OBJECT_KEY);

					jasonSettings = mergeJasonObj(jsonNotification, apnsSettings);
				} else {

					jasonSettings = apnsSettings;
				}

				notification.put(PushConstants.SETTINGS_OBJECT_KEY, jasonSettings);
			}
		}

		return this;
	}

	/**
	 * Configure specific Google Cloud Messaging (GCM) settings.
	 * 
	 * @param collapseKey
	 *            key that identifies a group of push notifications that can be
	 *            replaced with the latest one
	 * @param delayWhileIdle
	 *            indicates whether the message should not be sent until the
	 *            device becomes active
	 * @param payload
	 *            custom JSON payload that will be sent as part of the
	 *            notification message
	 * @param priority
	 *            the priority of the message, specified using the
	 *            {@link GCMPriority} enum
	 * @param soundFile
	 *            the sound file (on device) that will be attempted to play when
	 *            the notification arrives on the device
	 * @param secondsToLive
	 *            specifies how long (in seconds) the message should be kept in
	 *            GCM storage if the device is offline
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	@Deprecated
	public NotificationBuilder setGCMSettings(String collapseKey, Boolean delayWhileIdle, JSONObject payload,
			GCMPriority priority, String soundFile, Integer secondsToLive) {

		Settings settings = null;
		Gcm gcm = null;

		if (checkGCMParams(collapseKey, delayWhileIdle, payload, priority, soundFile, secondsToLive)) {

			gcm = new Settings.SettingsBuilder().setGcmBuilder(new GcmBuilder()).build().getGcmBuilder()
					.setCollapseKey(collapseKey).setDelayWhileIdle(delayWhileIdle).setPayload(payload)
					.setPriority(priority).setSound(soundFile).setTimeToLive(secondsToLive).build();
			settings = new Settings.SettingsBuilder().setGcm(gcm).build();
		}

		if (gcm != null) {

			JSONObject gcmSettings = generateJSON(settings);

			if (!gcmSettings.keySet().isEmpty()) {
				JSONObject jsonSettings = null;
				JSONObject jsonNotification = null;

				if (notification.has(PushConstants.SETTINGS_OBJECT_KEY)) {
					jsonNotification = notification.getJSONObject(PushConstants.SETTINGS_OBJECT_KEY);

					jsonSettings = mergeJasonObj(jsonNotification, gcmSettings);

				} else {
					jsonSettings = gcmSettings;
				}

				notification.put(PushConstants.SETTINGS_OBJECT_KEY, jsonSettings);
			}
		}
		return this;
	}

	private boolean checkGCMParams(String collapseKey, Boolean delayWhileIdle, JSONObject payload, GCMPriority priority,
			String soundFile, Integer secondsToLive) {
		return (collapseKey != null && collapseKey.length() > 0) || (delayWhileIdle != null) || (payload != null)
				|| (priority != null) || (soundFile != null && soundFile.length() > 0) || (secondsToLive != null);
	}

	/**
	 * Configure specific to GCM platform.
	 * 
	 * Accepts an argument of type {@link Gcm} class which has the following
	 * members below:
	 * <ul>
	 * <li>collapseKey (string)<code>:</code>&nbsp;This parameter identifies a group
	 * of messages.</li>
	 * <li>interactiveCategory (string)<code>:</code>&nbsp;The category identifier to
	 * be used for the interactive push notifications.</li>
	 * <li>icon (string)<code>:</code>&nbsp;Specify the name of the icon to be
	 * displayed for the notification. Make sure the icon is already packaged
	 * with the client application.</li>
	 * <li>delayWhileIdle (boolean)<code>:</code>&nbsp;When this parameter is set to
	 * true, it indicates that the message should not be sent until the device
	 * becomes active.</li>
	 * <li>sync (boolean)<code>:</code>&nbsp;Device group messaging makes it possible
	 * for every app instance in a group to reflect the latest messaging state</li>
	 * <li>visibility (string)<code>:</code>&nbsp;private/public - Visibility of this
	 * notification, which affects how and when the notifications are revealed
	 * on a secure locked screen.</li>
	 * <li>payload (object)<code>:</code>&nbsp;Custom JSON payload that will be sent
	 * as part of the notification message.</li>
	 * <li>priority (string)<code>:</code>&nbsp;A string value that indicates the
	 * priority of this notification. Allowed values are 'max', 'high',
	 * 'default', 'low' and 'min'. High/Max priority notifications along with
	 * 'sound' field may be used for Heads up notification in Android 5.0 or
	 * higher.sampleval='low'.</li>
	 * <li>sound (string)<code>:</code>&nbsp;The sound file (on device) that will be
	 * attempted to play when the notification arrives on the device.</li>
	 * <li>timeToLive (integer)<code>:</code>&nbsp;This parameter specifies how long
	 * (in seconds) the message should be kept in GCM storage if the device is
	 * offline.</li>
	 * <li>lights (lights)<code>:</code>&nbsp;Allows setting the notification LED
	 * color on receiving push notification . This should be in JSON and should
	 * have following keys {{@code} {@link GcmLED} ledArgb; Integer ledOnMs; Integer
	 * ledOffMs;}.</li>
	 * <li>style (style)<code>:</code>&nbsp;Options to specify for Android expandable
	 * notifications. The types of expandable notifications are
	 * picture_notification, bigtext_notification, inbox_notification.This
	 * should be in JSON and should have following keys {{@code} {@link GcmStyleTypes} type;
	 * String url; String title; String text; String [] lines;}</li> 
	 * </ul>
	 * Below is the code snippet for usage of this method:.
	 * 
	 * <pre>
	 * NotificationBuilder builder = new NotificationBuilder("Test");
	 * </pre>
	 * 
	 * You need to create {@link Settings} object as mentioned below:
	 * 
	 * <pre>
	 * Settings settings = new Settings.SettingsBuilder().setGcmBuilder(BuilderFactory.getBuilder(GcmBuilder.class))
	 * 		.build();
	 * </pre>
	 * 
	 * Using above settings object you can easily create {@link GcmBuilder}
	 * which is will act as a builder for Gcm and set its all or only members
	 * required by you in one go as shown below:
	 * 
	 * <pre>
	 * 
	 * builder.setGCMSettings(settings.getGcmBuilder().setCollapseKey("testCollapseKey").setDelayWhileIdle(true)
	 * 		.setPayload(new JSONObject()).setPriority(GCMPriority.MIN).setSound("testSoundFile").setTimeToLive(42)
	 * 		.setIcon("testIcon").setVisibility(Visibility.PUBLIC).setSync(true)
	 * 		.setStyle(new JSONObject().put("type", "testType"))
	 * 		.setLights(new JSONObject().put("ledArgb", "testLedArgb")).build()); 
	 * // build() returns the Gcm object.
	 * </pre>
	 * 
	 * @param gcm
	 * 			  gcm object with gcm settings.	
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder setGCMSettingsValues(Gcm gcm) {

		if (gcm != null) {

			Settings settings = new Settings.SettingsBuilder().setGcm(gcm).build();

			JSONObject gcmSettings = generateJSON(settings);

			if (!gcmSettings.keySet().isEmpty()) {
				JSONObject jsonSettings = null;
				JSONObject jsonNotification = null;

				if (notification.has(PushConstants.SETTINGS_OBJECT_KEY)) {
					jsonNotification = notification.getJSONObject(PushConstants.SETTINGS_OBJECT_KEY);

					jsonSettings = mergeJasonObj(jsonNotification, gcmSettings);

				} else {
					jsonSettings = gcmSettings;
				}

				notification.put(PushConstants.SETTINGS_OBJECT_KEY, jsonSettings);
			}
		}
		return this;
	}
/**
 * API converts object to json format.
 * @param obj
 * @return
 *        Return a JSONOject for the passed object.
 */
	public static JSONObject generateJSON(Object obj) {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(obj);

		} catch (JsonProcessingException exception) {

			logger.log(Level.SEVERE, exception.toString(), exception);
		}

		JSONObject jason = jsonString != null ? new JSONObject(jsonString) : new JSONObject();

		return jason;
	}

	private JSONObject mergeJasonObj(JSONObject Obj1, JSONObject Obj2) {
		JSONObject merged = new JSONObject();
		JSONObject[] objs = new JSONObject[] { Obj1, Obj2 };
		for (JSONObject obj : objs) {
			Iterator it = obj.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				merged.put(key, obj.get(key));
			}
		}
		return merged;
	}

	/**
	 * Build the push notification as configured. The result of this method is
	 * to be passed to
	 * {@link PushNotifications#send(JSONObject, PushNotificationsResponseListener)}
	 * as a parameter.
	 * 
	 * @return the push notification built as specified, ready to be sent
	 */
	public JSONObject build() {
		JSONObject builtNotification = notification;

		notification = null;

		return builtNotification;
	}

}