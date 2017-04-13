package com.ibm.mobilefirstplatform.serversdk.java.push.builders;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Apns {

	private static ObjectMapper mapper = new ObjectMapper();
	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

	private Integer badge;
	private String sound;
	private String iosActionKey;
	private JsonNode payload;
	private String interactiveCategory;
	private ApnsBuilder.APNSNotificationType type;
	private String titleLocKey;
	private String locKey;
	private String launchImage;
	private String[] titleLocArgs;
	private String[] locArgs;
	private String subtitle;
	private String title;
	private String attachmentUrl;

	public final String getTitle() {

		return title;
	}

	public final String getAttachmentUrl() {
		return attachmentUrl;
	}

	public final String getSubtitle() {
		return subtitle;
	}

	public final Integer getBadge() {
		return badge;
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

	public final String getSound() {
		return sound;
	}

	public final String getIosActionKey() {
		return iosActionKey;
	}

	@JsonRawValue
	public final JsonNode getPayload() {
		return this.payload;
	}

	public final String getInteractiveCategory() {
		return interactiveCategory;
	}

	public final ApnsBuilder.APNSNotificationType getType() {
		return type;
	}

	private Apns(ApnsBuilder builder) {

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

	// Builder for Apns
	public static class ApnsBuilder {

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
		private boolean checkBlankObject = false;

		public final ApnsBuilder setBadge(Integer badge) {

			if (badge != null) {

				checkBlankObject = true;
			}

			this.badge = badge;

			return this;
		}

		public final ApnsBuilder setSound(String sound) {
			if (sound != null && sound.length() > 0) {

				checkBlankObject = true;
			}
			this.sound = sound;
			return this;
		}

		public final ApnsBuilder setIosActionKey(String iosActionKey) {

			if (iosActionKey != null && iosActionKey.length() > 0) {

				checkBlankObject = true;
			}

			this.iosActionKey = iosActionKey;
			return this;
		}

		public final ApnsBuilder setPayload(JSONObject payload) {
			JsonNode jsonNodePayload = null;

			try {
				if (payload != null) {
					checkBlankObject = true;
					jsonNodePayload = mapper.readTree(payload.toString());
				}
			} catch (Exception exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}

			this.payload = jsonNodePayload;
			return this;
		}

		public final ApnsBuilder setInteractiveCategory(String interactiveCategory) {

			if (interactiveCategory != null && interactiveCategory.length() > 0) {

				checkBlankObject = true;
			}

			this.interactiveCategory = interactiveCategory;
			return this;
		}

		public final ApnsBuilder setType(APNSNotificationType type) {

			if (type != null) {
				checkBlankObject = true;
			}

			this.type = type;
			return this;
		}

		public final ApnsBuilder setTitleLocKey(String titleLocKey) {

			if (titleLocKey != null && titleLocKey.length() > 0) {

				checkBlankObject = true;
			}
			this.titleLocKey = titleLocKey;
			return this;
		}

		public final ApnsBuilder setLocKey(String locKey) {

			if (locKey != null && locKey.length() > 0) {

				checkBlankObject = true;
			}
			this.locKey = locKey;
			return this;
		}

		public final ApnsBuilder setLaunchImage(String launchImage) {

			if (launchImage != null && launchImage.length() > 0) {

				checkBlankObject = true;
			}
			this.launchImage = launchImage;
			return this;
		}

		public final ApnsBuilder setTitleLocArgs(String[] titleLocArgs) {

			if (titleLocArgs != null && titleLocArgs.length > 0) {

				checkBlankObject = true;
			}
			this.titleLocArgs = titleLocArgs;
			return this;
		}

		public final ApnsBuilder setLocArgs(String[] locArgs) {

			if (locArgs != null && locArgs.length > 0) {

				checkBlankObject = true;
			}
			this.locArgs = locArgs;
			return this;
		}

		public final ApnsBuilder setSubtitle(String subtitle) {

			if (subtitle != null && subtitle.length() > 0) {

				checkBlankObject = true;
			}
			this.subtitle = subtitle;
			return this;
		}

		public final ApnsBuilder setTitle(String title) {

			if (title != null && title.length() > 0) {

				checkBlankObject = true;
			}

			this.title = title;
			return this;
		}

		public final ApnsBuilder setAttachmentUrl(String attachmentUrl) {

			if (attachmentUrl != null && attachmentUrl.length() > 0) {

				checkBlankObject = true;
			}
			this.attachmentUrl = attachmentUrl;
			return this;
		}

		public Apns build() {
			if (checkBlankObject) {
				return new Apns(this);
			} else {
				return null;
			}

		}

	}


}
