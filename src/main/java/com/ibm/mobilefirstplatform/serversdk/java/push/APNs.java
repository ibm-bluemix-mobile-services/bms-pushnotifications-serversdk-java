/*
 *     Copyright 2017 IBM Corp.
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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.APNs.Builder.APNSNotificationType;

/**
 * 
 * Modal class for APNs that specifies the iOS platform settings.
 *
 */
public final class APNs {

	/**
	 * 
	 */
	public static final Logger logger = Logger.getLogger(APNs.class.getName());

	private Integer badge;
	private String sound;
	private String iosActionKey;
	private JsonNode payload;
	private String interactiveCategory;
	private APNSNotificationType type;
	private String titleLocKey;
	private String locKey;
	private String launchImage;
	private String[] titleLocArgs;
	private String[] locArgs;
	private String subtitle;
	private String title;
	private String attachmentUrl;
	private String apnsCollapseId;
	private String apnsThreadId;
	private String apnsGroupSummaryArg;
	private Integer apnsGroupSummaryArgCount;

	public final Integer getBadge() {
		return badge;
	}

	public final String getApnsThreadId() {
		return apnsThreadId;
	}

	public final String getApnsGroupSummaryArg() {
		return apnsGroupSummaryArg;
	}

	public final Integer getApnsGroupSummaryArgCount() {
		return apnsGroupSummaryArgCount;
	}

	public final String getSound() {
		return sound;
	}

	public final String getIosActionKey() {
		return iosActionKey;
	}

	@JsonRawValue
	public final JsonNode getPayload() {
		return payload;
	}

	public final String getInteractiveCategory() {
		return interactiveCategory;
	}

	public final APNSNotificationType getType() {
		return type;
	}

	public final String getTitleLocKey() {
		return titleLocKey;
	}

	public final String getLocKey() {
		return locKey;
	}

	public final String getLaunchImage() {
		return launchImage;
	}

	public final String[] getTitleLocArgs() {
		return titleLocArgs;
	}

	public final String[] getLocArgs() {
		return locArgs;
	}

	public final String getSubtitle() {
		return subtitle;
	}

	public final String getTitle() {
		return title;
	}

	public final String getAttachmentUrl() {
		return attachmentUrl;
	}

	public final String getApnsCollapseId() {
		return apnsCollapseId;
	}

	private APNs(Builder builder) {

		this.badge = builder.badge;
		this.interactiveCategory = builder.interactiveCategory;
		this.iosActionKey = builder.iosActionKey;
		this.payload = builder.payload;
		this.sound = builder.sound;
		this.titleLocKey = builder.titleLocKey;
		this.locKey = builder.locKey;
		this.launchImage = builder.launchImage;
		this.titleLocArgs = builder.titleLocArgs;
		this.locArgs = builder.locArgs;
		this.title = builder.title;
		this.subtitle = builder.subtitle;
		this.attachmentUrl = builder.attachmentUrl;
		this.type = builder.type;
		this.apnsCollapseId = builder.apnsCollapseId;
		this.apnsThreadId = builder.apnsThreadId;
		this.apnsGroupSummaryArg = builder.apnsGroupSummaryArg;
		this.apnsGroupSummaryArgCount = builder.apnsGroupSummaryArgCount;
	}

	/**
	 * 
	 * Builder for APNs.
	 *
	 */
	public static class Builder {
		/**
		 * The available notification types of the APNs notification message.
		 */
		public enum APNSNotificationType {
			DEFAULT, MIXED, SILENT
		}

		private Integer badge;
		private String sound;
		private String iosActionKey;
		private JsonNode payload;
		private String interactiveCategory;
		private APNSNotificationType type;
		private String titleLocKey;
		private String locKey;
		private String launchImage;
		private String[] titleLocArgs;
		private String[] locArgs;
		private String subtitle;
		private String title;
		private String attachmentUrl;
		private String apnsCollapseId;
		private String apnsThreadId;
		private String apnsGroupSummaryArg;
		private Integer apnsGroupSummaryArgCount;

		/**
		 * 
		 * @param badge
		 *            The number to display as the badge of the application
		 *            icon.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder badge(Integer badge) {
			this.badge = badge;
			return this;
		}

		/**
		 * 
		 * @param apnsThreadId
		 *            An app-specific identifier for grouping related notifications. This value corresponds to the threadIdentifier property in the UNNotificationContent object.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder apnsThreadId(String apnsThreadId) {
			this.apnsThreadId = apnsThreadId;
			return this;
		}
		/**
		 * 
		 * @param apnsGroupSummaryArg
		 * 			  The string the notification adds to the category’s summary format string.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder apnsGroupSummaryArg(String apnsGroupSummaryArg) {
			this.apnsGroupSummaryArg = apnsGroupSummaryArg;
			return this;
		}

		/**
		 * 
		 * @param apnsGroupSummaryArgCount
		 *            The number of items the notification adds to the category’s summary format string.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder apnsGroupSummaryArgCount(Integer apnsGroupSummaryArgCount) {
			this.apnsGroupSummaryArgCount = apnsGroupSummaryArgCount;
			return this;
		}

		/**
		 * 
		 * @param sound
		 *            The name of the sound file in the application bundle. The
		 *            sound of this file is played as an alert.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder sound(String sound) {
			this.sound = sound;
			return this;
		}

		/**
		 * 
		 * @param iosActionKey
		 *            The title for the Action key.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder iosActionKey(String iosActionKey) {
			this.iosActionKey = iosActionKey;
			return this;
		}

		/**
		 * 
		 * @param payload
		 *            Custom JSON payload that will be sent as part of the
		 *            notification message.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder payload(JSONObject payload) {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNodePayload = null;

			try {
				if (payload != null) {
					jsonNodePayload = mapper.readTree(payload.toString());
				}
			} catch (JsonProcessingException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			} catch (IOException e) {
				logger.log(Level.SEVERE, e.toString(), e);
			}

			this.payload = jsonNodePayload;
			return this;
		}

		/**
		 * 
		 * @param interactiveCategory
		 *            The category identifier to be used for the interactive
		 *            push notifications.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder interactiveCategory(String interactiveCategory) {
			this.interactiveCategory = interactiveCategory;
			return this;
		}

		/**
		 * 
		 * @param type
		 *            {'DEFAULT', 'MIXED', 'SILENT'}
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder type(APNSNotificationType type) {
			this.type = type;
			return this;
		}

		/**
		 * 
		 * @param titleLocKey
		 *            The key to a title string in the Localizable.strings file
		 *            for the current localization. The key string can be
		 *            formatted with %@ and %n$@ specifiers to take the
		 *            variables specified in the titleLocArgs array.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder titleLocKey(String titleLocKey) {
			this.titleLocKey = titleLocKey;
			return this;
		}

		/**
		 * 
		 * @param locKey
		 *            A key to an alert-message string in a Localizable.strings
		 *            file for the current localization (which is set by the
		 *            user�s language preference). The key string can be
		 *            formatted with %@ and %n$@ specifiers to take the
		 *            variables specified in the locArgs array.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder locKey(String locKey) {
			this.locKey = locKey;
			return this;
		}

		/**
		 * 
		 * @param launchImage
		 *            The filename of an image file in the app bundle, with or
		 *            without the filename extension. The image is used as the
		 *            launch image when users tap the action button or move the
		 *            action slider.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder launchImage(String launchImage) {
			this.launchImage = launchImage;
			return this;
		}

		/**
		 * 
		 * @param titleLocArgs
		 *            Variable string values to appear in place of the format
		 *            specifiers in title-loc-key.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder titleLocArgs(String[] titleLocArgs) {
			this.titleLocArgs = titleLocArgs;
			return this;
		}

		/**
		 * 
		 * @param locArgs
		 *            Variable string values to appear in place of the format
		 *            specifiers in locKey.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder locArgs(String[] locArgs) {
			this.locArgs = locArgs;
			return this;
		}

		/**
		 * 
		 * @param subtitle
		 *            The subtitle of the Rich Notifications. (Supported on iOS
		 *            10 or later).
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder subtitle(String subtitle) {
			this.subtitle = subtitle;
			return this;
		}

		/**
		 * 
		 * @param title
		 *            title - The title of Rich Push notifications (Supported on
		 *            iOS 10 or later).
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder title(String title) {
			this.title = title;
			return this;
		}

		/**
		 * 
		 * @param attachmentUrl
		 *            The link to the iOS notifications media (video, audio,
		 *            GIF, images - Supported only on iOS 10 and above).
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder attachmentUrl(String attachmentUrl) {
			this.attachmentUrl = attachmentUrl;
			return this;
		}

		/**
		 * 
		 * @param apnsCollapseId
		 * 			Multiple notifications with the same collapse identifier are displayed to the user as a single notification
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder apnsCollapseId(String apnsCollapseId) {
			this.apnsCollapseId = apnsCollapseId;
			return this;
		}

		/**
		 * 
		 * @return the {@link APNs} object.
		 */
		public APNs build() {
			return new APNs(this);

		}

	}

}