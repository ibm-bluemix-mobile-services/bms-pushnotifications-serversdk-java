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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.ApnsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.GcmBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.MessageBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.SettingsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.TargetBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.internal.PushMessageModel;
import com.ibm.mobilefirstplatform.serversdk.java.push.internal.PushMessageModel.Message;

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

	private MessageBuilder messageBuilder;
	private TargetBuilder targetBuilder;
	private SettingsBuilder settingsBuilder;

	@Deprecated
	public enum PushNotificationsPlatform {
		APPLE("A"), GOOGLE("G");

		private String platformCode;

		PushNotificationsPlatform(String code) {
			this.platformCode = code;
		}

		public String getValue() {
			return platformCode;
		}
	}

	@Deprecated
	public enum APNSNotificationType {
		DEFAULT, MIXED, SILENT
	}

	@Deprecated
	public enum GCMPriority {
		DEFAULT, MIN, LOW, HIGH, MAX
	}

	// Default constructor
	public NotificationBuilder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Create a new NotificationBuilder to help create a new push notification.
	 * This NotificationBuilder can be used to configure the push notification
	 * before it is sent, by configuring optional parameters.
	 * 
	 * @param alert
	 *            the message to be sent in the push notification
	 */
	@Deprecated
	public NotificationBuilder(String alert) {
		if (alert == null) {
			throw new IllegalArgumentException(PushConstants.ALERT_NOT_NULL_EXCEPTIOPN);
		}
		messageBuilder = new MessageBuilder();
		messageBuilder.setAlert(alert);
	}

	/**
	 * Set an optional URL to be included with the push notification.
	 * 
	 * @param url
	 *            the URL to be included
	 * @return the NotificationBuilder object so that calls can be chained
	 */
	@Deprecated
	public NotificationBuilder setMessageURL(String url) {
		if (url != null && url.length() > 0) {
			if (messageBuilder == null) {
				messageBuilder = new MessageBuilder();
			}
			messageBuilder.setUrl(url);
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

		if (checkTargetParams(deviceIds, userIds, platforms, tagNames)) {

			TargetBuilder.PushNotificationsPlatform[] finalPlatforms = new TargetBuilder.PushNotificationsPlatform[platforms.length];
			int i = 0;
			for (TargetBuilder.PushNotificationsPlatform platform : TargetBuilder.PushNotificationsPlatform.values()) {
				if (i < platforms.length && platform.name().equalsIgnoreCase(platforms[i].name())) {
					finalPlatforms[i++] = platform;
				} else if (i >= platforms.length) {
					break;
				}
			}

			targetBuilder = new TargetBuilder();
			if (deviceIds.length > 0) {
				targetBuilder.setDeviceIds(deviceIds);
			}
			if (userIds.length > 0) {
				targetBuilder.setUserIds(userIds);
			}
			if (platforms.length > 0) {
				targetBuilder.setPlatforms(finalPlatforms);
			}
			if (tagNames.length > 0) {
				targetBuilder.setTagNames(tagNames);
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

		if (checkAPNSParams(badge, interactiveCategory, iosActionKey, payload, soundFile, type)) {

			ApnsBuilder.APNSNotificationType finalType = null;

			for (ApnsBuilder.APNSNotificationType apnsType : ApnsBuilder.APNSNotificationType.values()) {

				if (apnsType.name().equalsIgnoreCase(type.name())) {
					finalType = apnsType;
				}
			}
			if (settingsBuilder == null) {
				settingsBuilder = new SettingsBuilder();
			}
			ApnsBuilder apnsBuilder = new ApnsBuilder();
			apnsBuilder.setBadge(badge).setInteractiveCategory(interactiveCategory).setIosActionKey(iosActionKey)
					.setPayload(payload).setSound(soundFile).setType(finalType);

			settingsBuilder.setApnsBuilder(apnsBuilder);
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

		if (checkGCMParams(collapseKey, delayWhileIdle, payload, priority, soundFile, secondsToLive)) {

			GcmBuilder.GCMPriority finalPriority = null;

			for (GcmBuilder.GCMPriority gcmPriority : GcmBuilder.GCMPriority.values()) {

				if (gcmPriority.name().equalsIgnoreCase(priority.name())) {
					finalPriority = gcmPriority;
				}
			}
			if (settingsBuilder == null) {
				settingsBuilder = new SettingsBuilder();
			}
			GcmBuilder gcmBuilder = new GcmBuilder();
			gcmBuilder.setCollapseKey(collapseKey).setDelayWhileIdle(delayWhileIdle).setPayload(payload)
					.setPriority(finalPriority).setSound(soundFile).setTimeToLive(secondsToLive);

			settingsBuilder.setGcmBuilder(gcmBuilder);
		}

		return this;
	}

	private boolean checkGCMParams(String collapseKey, Boolean delayWhileIdle, JSONObject payload, GCMPriority priority,
			String soundFile, Integer secondsToLive) {
		return (collapseKey != null && collapseKey.length() > 0) || (delayWhileIdle != null) || (payload != null)
				|| (priority != null) || (soundFile != null && soundFile.length() > 0) || (secondsToLive != null);
	}

	/**
	 * Sets the MessageBuilder to the NotificationBuilder.
	 * 
	 * @param messageBuilder
	 *            builder object with all message attributes set.
	 * 
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder messageBuilder(MessageBuilder messageBuilder) {

		this.messageBuilder = messageBuilder;

		return this;
	}

	/**
	 * Sets the TargetBuilder to the NotificationBuilder.
	 * 
	 * @param targetBuilder
	 *            builder object with all target attributes set.
	 * 
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder targetBuilder(TargetBuilder targetBuilder) {
		this.targetBuilder = targetBuilder;
		return this;
	}

	/**
	 * Sets the SettingsBuilder to the NotificationBuilder.
	 * 
	 * @param settingsBuilder
	 *            builder object with all settings attributes set.
	 * 
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	public NotificationBuilder settingsBuilder(SettingsBuilder settingsBuilder) {
		this.settingsBuilder = settingsBuilder;
		return this;
	}

	/**
	 * API converts object to json format.
	 * 
	 * @param obj
	 * 			  The object which needs to be serialized as json string.
	 * 
	 * @return Return a JSONOject for the passed object.
	 */
	public static JSONObject generateJSON(Object obj) {
		String jsonString = null;
		try {
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			jsonString = mapper.writeValueAsString(obj);

		} catch (JsonProcessingException exception) {

			logger.log(Level.SEVERE, exception.toString(), exception);
		}
        
		JSONObject json = jsonString != null ? new JSONObject(jsonString) : new JSONObject();

		return json;
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

		PushMessageModel model = new PushMessageModel();

		if (messageBuilder != null) {
			Message msg = messageBuilder.build();
			if (msg.getAlert() == null) {
				throw new IllegalArgumentException(PushConstants.ALERT_NOT_NULL_EXCEPTIOPN_NEW);
			}
			model.setMessage(msg);
		} else {
			throw new IllegalArgumentException(PushConstants.ALERT_NOT_NULL_EXCEPTIOPN_NEW);
		}

		model.setTarget(targetBuilder != null ? targetBuilder.build() : null);

		model.setSettings(settingsBuilder != null ? settingsBuilder.build() : null);

		return generateJSON(model);
	}

}