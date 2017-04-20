package com.ibm.mobilefirstplatform.serversdk.java.push;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.Apns.Builder.APNSNotificationType;


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

	// Builder for Apns
	public static class Builder {

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

		public final Builder badge(Integer badge) {
			this.badge = badge;
			return this;
		}

		public final Builder sound(String sound) {
			this.sound = sound;
			return this;
		}

		public final Builder iosActionKey(String iosActionKey) {
			this.iosActionKey = iosActionKey;
			return this;
		}

		public final Builder payload(JSONObject payload) {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNodePayload = null;

			try {
				if (payload != null) {
					jsonNodePayload = mapper.readTree(payload.toString());
				}
			} catch (Exception exception) {
				logger.log(Level.SEVERE, exception.toString(), exception);
			}

			this.payload = jsonNodePayload;
			return this;
		}

		public final Builder interactiveCategory(String interactiveCategory) {
			this.interactiveCategory = interactiveCategory;
			return this;
		}

		public final Builder type(APNSNotificationType type) {
			this.type = type;
			return this;
		}

		public final Builder titleLocKey(String titleLocKey) {
			this.titleLocKey = titleLocKey;
			return this;
		}

		public final Builder locKey(String locKey) {
			this.locKey = locKey;
			return this;
		}

		public final Builder launchImage(String launchImage) {
			this.launchImage = launchImage;
			return this;
		}

		public final Builder titleLocArgs(String[] titleLocArgs) {
			this.titleLocArgs = titleLocArgs;
			return this;
		}

		public final Builder locArgs(String[] locArgs) {
			this.locArgs = locArgs;
			return this;
		}

		public final Builder subtitle(String subtitle) {
			this.subtitle = subtitle;
			return this;
		}

		public final Builder title(String title) {
			this.title = title;
			return this;
		}

		public final Builder attachmentUrl(String attachmentUrl) {
			this.attachmentUrl = attachmentUrl;
			return this;
		}

		public Apns build() {
			return new Apns(this);

		}

	}

}