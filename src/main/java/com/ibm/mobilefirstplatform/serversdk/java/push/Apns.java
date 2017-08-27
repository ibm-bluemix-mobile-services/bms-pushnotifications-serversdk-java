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
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.Apns.Builder.APNSNotificationType;

/**
 * 
 * Modal class for Apns which specifies the settings specific to the IOS platform.
 *
 */
public final class Apns {

	public static final Logger logger = Logger.getLogger(Apns.class.getName());

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

	public final Integer getBadge() {
		return badge;
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

	private Apns(Builder builder) {

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
	}

	/**
	 * 
	 * Builder for Apns.
	 *
	 */
	public static class Builder {
		/**
		 * The available notification type of the {@link Apns} notification
		 * message.
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

		/**
		 * 
		 * @param badge
		 *            The number to display as the badge of the application
		 *            icon.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder badge(Integer badge) {
			this.badge = badge;
			return this;
		}

		/**
		 * 
		 * @param sound
		 *            The name of the sound file in the application bundle. The
		 *            sound of this file is played as an alert
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder sound(String sound) {
			this.sound = sound;
			return this;
		}

		/**
		 * 
		 * @param iosActionKey
		 *            The title for the Action key.
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder payload(JSONObject payload) {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNodePayload = null;

			try {
				if (payload != null) {
					jsonNodePayload = mapper.readTree(payload.toString());
				}
			} catch (IOException exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}

			this.payload = jsonNodePayload;
			return this;
		}

		/**
		 * 
		 * @param interactiveCategory
		 *            The category identifier to be used for the interactive
		 *            push notifications.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder interactiveCategory(String interactiveCategory) {
			this.interactiveCategory = interactiveCategory;
			return this;
		}

		/**
		 * 
		 * @param type
		 *            {'DEFAULT', 'MIXED', 'SILENT'}
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder locArgs(String[] locArgs) {
			this.locArgs = locArgs;
			return this;
		}

		/**
		 * 
		 * @param subtitle
		 *            The subtitle of the Rich Notifications. (Supported only on
		 *            iOS 10 and above).
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder subtitle(String subtitle) {
			this.subtitle = subtitle;
			return this;
		}

		/**
		 * 
		 * @param title
		 *            The title of Rich Push notifications (Supported only on
		 *            iOS 10 and above).
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder attachmentUrl(String attachmentUrl) {
			this.attachmentUrl = attachmentUrl;
			return this;
		}

		/**
		 * 
		 * @return the {@link Apns} object.
		 */
		public Apns build() {
			return new Apns(this);

		}

	}

}