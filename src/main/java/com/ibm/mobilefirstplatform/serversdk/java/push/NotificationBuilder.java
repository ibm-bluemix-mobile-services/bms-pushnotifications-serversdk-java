/*
 *     Copyright 2016, 2017 IBM Corp.
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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Message;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Android;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeAppExt;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.FirefoxWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SafariWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Style;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target;

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

		PushMessageModel model = new PushMessageModel();

		Message messageObj = new Message();
		messageObj.setAlert(alert);
		model.setMessage(messageObj);

		notification = new JSONObject();

		JSONObject message = generateJSON(model);
		// message.put("alert", alert);

		// An empty message is the minimum required.
		notification = message;
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

			Message messageObj = new Message();
			messageObj.setUrl(url);

			JSONObject jsonMessageObj = generateJSON(messageObj);
			if (message != null) {
				JSONObject mergeMessage = mergeJasonObj(message, jsonMessageObj);
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
	public NotificationBuilder setTarget(String[] deviceIds, String[] userIds, PushNotificationsPlatform[] platforms,
			String[] tagNames)  {
		// JSONObject target = new JSONObject();

		PushMessageModel model = null;
		Target targetObj = null;

		if ((deviceIds != null && deviceIds.length > 0) || (userIds != null && userIds.length > 0)
				|| (platforms != null && platforms.length > 0) || (tagNames != null && tagNames.length > 0)) {
			targetObj = new Target();
			model = new PushMessageModel();
		}

		if (deviceIds != null && deviceIds.length > 0) {
			targetObj.setDeviceIds(deviceIds);
		}

		if (userIds != null && userIds.length > 0) {
			targetObj.setUserIds(userIds);
		}

		if (platforms != null && platforms.length > 0) {
			targetObj.setPlatforms(platforms);
		}

		if (tagNames != null && tagNames.length > 0) {
			targetObj.setTagNames(tagNames);
		}
		if (targetObj != null) {
			model.setTarget(targetObj);

			JSONObject target = generateJSON(model);

			if (!target.keySet().isEmpty()) {
				notification = mergeJasonObj(target, notification);

			}
		}

		return this;
	}

	/**
	 * Configure specific SafariWeb settings for SafariWeb Browser.
	 * 
	 * @param title
	 *            Specifies the title to be set for the Safari Push
	 *            Notifications.
	 * @param urlArgs
	 *            The URL arguments that need to be used with this notification.
	 * @param action
	 *            The label of the action button.
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder setSafariWebSettings(String title, String[] urlArgs, String action)
			 {

		Settings settings = null;
		SafariWeb safariWeb = null;

		if ((title != null && title.length() > 0) || (urlArgs != null && urlArgs.length > 0)
				|| (action != null && action.length() > 0)) {

			settings = new Settings();
			safariWeb = new SafariWeb();
		}

		if (title != null && title.length() > 0) {
			safariWeb.setTitle(title);
		}

		if (urlArgs != null && urlArgs.length > 0) {
			safariWeb.setUrlArgs(urlArgs);
		}

		if (action != null && action.length() > 0) {
			safariWeb.setAction(action);
		}

		if (safariWeb != null) {
			settings.setSafariWeb(safariWeb);

			JSONObject safariSettings = generateJSON(settings);

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
	 * Configure specific FireFox settings for FireFox browser.
	 * 
	 * @param title
	 *            Specifies the title to be set for the WebPush Notification.
	 * @param iconUrl
	 *            The URL of the icon to be set for the WebPush Notification.
	 * @param secondsToLive
	 *            This parameter specifies how long (in seconds) the message
	 *            should be kept in GCM storage if the device is offline.
	 * @param payload
	 *            Custom JSON payload that will be sent as part of the
	 *            notification message.
	 * @return the NotificationBuilder object so that calls can be chained.
	 * 
	 */
	public NotificationBuilder setFirefoxWebSettings(String title, String iconUrl, Integer secondsToLive,
			JSONObject payload) {

		Settings settings = null;
		FirefoxWeb chromeWeb = null;

		if ((title != null && title.length() > 0) || (iconUrl != null && iconUrl.length() > 0)
				|| (secondsToLive != null) || (payload != null)) {
			settings = new Settings();
			chromeWeb = new FirefoxWeb();
		}

		if (title != null && title.length() > 0) {
			chromeWeb.setTitle(title);
		}
		if (iconUrl != null && iconUrl.length() > 0) {
			chromeWeb.setIconUrl(iconUrl);
		}
		if (secondsToLive != null) {
			chromeWeb.setTimeToLive(secondsToLive);
		}
		if (payload != null) {
			JsonNode jsonNodePayload=null;
			try {
			 jsonNodePayload = mapper.readTree(payload.toString());	
			} catch (Exception exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}
			
			chromeWeb.setPayload(jsonNodePayload);
		}

		if (chromeWeb != null) {
			settings.setFirefoxWeb(chromeWeb);

			JSONObject firefoxSettings = generateJSON(settings);

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
	 * Configure specific ChromAppExtension settings.
	 * 
	 * @param collapseKey
	 *            This parameter identifies a group of messages.
	 * @param delayWhileIdle
	 *            When this parameter is set to true, it indicates that the
	 *            message should not be sent until the device becomes active.
	 * @param title
	 *            Specifies the title to be set for the WebPush Notification.
	 * @param iconUrl
	 *            The URL of the icon to be set for the WebPush Notification.
	 * @param secondsToLive
	 *            This parameter specifies how long (in seconds) the message
	 *            should be kept in GCM storage if the device is offline.
	 * @param payload
	 *            Custom JSON payload that will be sent as part of the
	 *            notification message.
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	public NotificationBuilder setChromeAppExtSettings(String collapseKey, Boolean delayWhileIdle, String title,
			String iconUrl, Integer secondsToLive, JSONObject payload){

		Settings settings = null;
		ChromeAppExt chromeAppExt = null;

		if ((collapseKey != null && collapseKey.length() > 0) || (delayWhileIdle != null)
				|| (title != null && title.length() > 0) || (iconUrl != null && iconUrl.length() > 0)
				|| (secondsToLive != null) || (payload != null)) {
			settings = new Settings();
			chromeAppExt = new ChromeAppExt();
		}

		if (collapseKey != null && collapseKey.length() > 0) {
			chromeAppExt.setCollapseKey(collapseKey);
		}

		if (delayWhileIdle != null) {
			chromeAppExt.setDelayWhileIdle(delayWhileIdle.toString());
		}

		if (title != null && title.length() > 0) {
			chromeAppExt.setTitle(title);
		}
		if (iconUrl != null && iconUrl.length() > 0) {
			chromeAppExt.setIconUrl(iconUrl);
		}
		if (secondsToLive != null) {
			chromeAppExt.setTimeToLive(secondsToLive);
		}
		if (payload != null) {
			JsonNode jsonNodePayload=null;
			try {
			 jsonNodePayload = mapper.readTree(payload.toString());	
			} catch (Exception exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}
			chromeAppExt.setPayload(jsonNodePayload);
		}

		if (chromeAppExt != null) {
			settings.setChromeAppExt(chromeAppExt);
			JSONObject chromExtSettings = generateJSON(settings);

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
	 * Configure specific ChromeWeb settings for ChromWeb browser.
	 * 
	 * @param title
	 *            Specifies the title to be set for the WebPush Notification.
	 * @param iconUrl
	 *            The URL of the icon to be set for the WebPush Notification .
	 * @param secondsToLive
	 *            This parameter specifies how long (in seconds) the message
	 *            should be kept in GCM storage if the device is offline.
	 * @param payload
	 *            Custom JSON payload that will be sent as part of the
	 *            notification message.
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	public NotificationBuilder setChromeSettings(String title, String iconUrl, Integer secondsToLive,
			JSONObject payload)  {

		Settings settings = null;
		ChromeWeb chromeWeb = null;

		if ((title != null && title.length() > 0) || (iconUrl != null && iconUrl.length() > 0)
				|| (secondsToLive != null) || (payload != null)) {
			settings = new Settings();
			chromeWeb = new ChromeWeb();
		}

		if (title != null && title.length() > 0) {
			chromeWeb.setTitle(title);
		}
		if (iconUrl != null && iconUrl.length() > 0) {
			chromeWeb.setIconUrl(iconUrl);
		}
		if (secondsToLive != null) {
			chromeWeb.setTimeToLive(secondsToLive);
		}
		if (payload != null) {
			JsonNode jsonNodePayload=null;
			try {
			 jsonNodePayload = mapper.readTree(payload.toString());	
			} catch (Exception exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}
			chromeWeb.setPayload(jsonNodePayload);
		}

		if (chromeWeb != null) {
			settings.setChromeWeb(chromeWeb);
			if (settings != null && settings.getChromeWeb() != null) {

				JSONObject chromSettings = generateJSON(settings);

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
		}
		return this;
	}

	/**
	 * Configure specific Apple Push Notification Service (APNS) settings for
	 * iOS devices.
	 * 
	 * @param badge
	 *            the number to display as the badge of the application icon
	 * @param category
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
	 * @param titleLockKey
	 *            The key to a title string in the Localizable.strings file for
	 *            the current localization. The key string can be formatted with
	 *            %@ and %n$@ specifiers to take the variables specified in the
	 *            title-loc-args array.
	 * @param locKey
	 *            A key to an alert-message string in a Localizable.strings file
	 *            for the current localization (which is set by the user’s
	 *            language preference). The key string can be formatted with %@
	 *            and %n$@ specifiers to take the variables specified in the
	 *            loc-args array.
	 * @param launchImage
	 *            The filename of an image file in the app bundle, with or
	 *            without the filename extension. The image is used as the
	 *            launch image when users tap the action button or move the
	 *            action slider.
	 * @param titleLocArgs
	 *            Variable string values to appear in place of the format
	 *            specifiers in title-loc-key.
	 * @param locArgs
	 *            Variable string values to appear in place of the format
	 *            specifiers in loc-key.
	 * @param title
	 *            The title of Rich Push notifications (Supported only on iOS 10
	 *            and above)
	 * @param subtitle
	 *            The subtitle of the Rich Notifications. (Supported only on iOS
	 *            10 and above).
	 * @param attachmentUrl
	 *            The link to the iOS notifications media (video, audio, GIF,
	 *            images - Supported only on iOS 10 and above).
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder setAPNSSettings(Integer badge, String category, String iosActionKey, JSONObject payload,
			String soundFile, APNSNotificationType type, String titleLockKey, String locKey, String launchImage,
			String[] titleLocArgs, String[] locArgs, String title, String subtitle, String attachmentUrl)
			{

		Settings settings = null;

		Apns apns = null;

		if ((badge != null) || (category != null && category.length() > 0)
				|| (iosActionKey != null && iosActionKey.length() > 0) || (payload != null)
				|| (soundFile != null && soundFile.length() > 0) || (type != null)
				|| (titleLockKey != null && titleLockKey.length() > 0) || (locKey != null && locKey.length() > 0)
				|| (launchImage != null && launchImage.length() > 0)
				|| (titleLocArgs != null && titleLocArgs.length > 0) || (locArgs != null && locArgs.length > 0)
				|| (title != null && title.length() > 0) || (subtitle != null && subtitle.length() > 0)
				|| (attachmentUrl != null && attachmentUrl.length() > 0)) {

			settings = new Settings();
			apns = new Apns();

		}

		if (badge != null) {
			apns.setBadge(badge.intValue());
		}

		if (category != null && category.length() > 0) {
			apns.setCategory(category);
		}

		if (iosActionKey != null && iosActionKey.length() > 0) {
			apns.setIosActionKey(iosActionKey);
		}

		if (payload != null) {
			JsonNode jsonNodePayload=null;
			try {
			 jsonNodePayload = mapper.readTree(payload.toString());	
			} catch (Exception exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}
			apns.setPayload(jsonNodePayload);
		}

		if (soundFile != null && soundFile.length() > 0) {
			apns.setSound(soundFile);
		}

		if (type != null) {
			apns.setType(type.name());
		}

		if (titleLockKey != null && titleLockKey.length() > 0) {
			apns.setTitleLocKey(titleLockKey);
		}

		if (locKey != null && locKey.length() > 0) {
			apns.setLocKey(locKey);
		}

		if (launchImage != null && launchImage.length() > 0) {
			apns.setLaunchImage(launchImage);
		}

		if (titleLocArgs != null && titleLocArgs.length > 0) {
			apns.setTitleLocArgs(titleLocArgs);
		}

		if (locArgs != null && locArgs.length > 0) {
			apns.setLocArgs(locArgs);
		}

		if (title != null && title.length() > 0) {
			apns.setTitle(title);
		}

		if (subtitle != null && subtitle.length() > 0) {
			apns.setSubtitle(subtitle);
		}

		if (attachmentUrl != null && attachmentUrl.length() > 0) {
			apns.setAttachmentUrl(attachmentUrl);
		}

		if (apns != null) {
			settings.setApns(apns);

			JSONObject apnsSettings = generateJSON(settings);

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
	 * @param icon
	 *            Specify the name of the icon to be displayed for the
	 *            notification. Make sure the icon is already packaged with the
	 *            client application.
	 * @param visibility
	 *            private/public - Visibility of this notification, which
	 *            affects how and when the notifications are revealed on a
	 *            secure locked screen; specified with the {@link Visibility}
	 *            enum
	 * @param sync
	 *            Device group messaging makes it possible for every app
	 *            instance in a group to reflect the latest messaging state.
	 * @param style
	 *            Options to specify for Android expandable notifications. The
	 *            types of expandable notifications are picture_notification,
	 *            bigtext_notification, inbox_notification; the JSONObject
	 *            should have same keys as attributes specified in the
	 *            {@link Style} Class
	 * @return
	 * 			  the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder setGCMSettings(String collapseKey, Boolean delayWhileIdle, JSONObject payload,
			GCMPriority priority, String soundFile, Integer secondsToLive, String icon, Visibility visibility,
			Boolean sync, JSONObject style) throws JsonProcessingException {

		Settings settings = null;
		Android android = null;

		if ((collapseKey != null && collapseKey.length() > 0) || (delayWhileIdle != null) || (payload != null)
				|| (priority != null) || (soundFile != null && soundFile.length() > 0) || (secondsToLive != null)
				|| (icon != null && icon.length() > 0) || (visibility != null) || (sync != null) || (style != null && style.length() > 0)) {
			settings = new Settings();
			android = new Android();
		}

		if (collapseKey != null && collapseKey.length() > 0) {
			android.setCollapseKey(collapseKey);
		}

		if (delayWhileIdle != null) {
			android.setDelayWhileIdle(delayWhileIdle.toString());
		}

		if (payload != null) {
			JsonNode jsonNodePayload=null;
			try {
			 jsonNodePayload = mapper.readTree(payload.toString());	
			} catch (Exception exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}
			android.setPayload(jsonNodePayload);
		}

		if (priority != null) {
			android.setPriority(priority.name());
		}

		if (soundFile != null && soundFile.length() > 0) {
			android.setSound(soundFile);
		}

		if (secondsToLive != null) {
			android.setTimeToLive(secondsToLive.intValue());
		}

		if (icon != null && icon.length() > 0) {
			android.setIcon(icon);
		}

		if (visibility != null) {
			android.setVisibility(visibility.name());
		}

		if (sync != null) {
			android.setSync(sync.toString());
		}

		if (style != null && style.length() > 0) {
			JsonNode jsonNodeStyle=null;
			try {
			jsonNodeStyle = mapper.readTree(style.toString());	
			} catch (Exception exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}
			android.setStyle(jsonNodeStyle);
		}

		if (android != null) {
			settings.setGcm(android);

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

	private static JSONObject generateJSON(Object obj)  {
		String jsonString = null;
		try {
			jsonString = mapper.writeValueAsString(obj);
					
		} catch (JsonProcessingException exception) {
			
			logger.log(Level.SEVERE, exception.toString(), exception);
		}
		JSONObject jason = jsonString!=null?new JSONObject(jsonString):new JSONObject();	

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
