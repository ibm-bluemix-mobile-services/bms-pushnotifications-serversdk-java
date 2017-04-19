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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns.Builder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.Builder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.Builder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.GcmLights.Builder.GcmLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.GcmStyle.Builder.GcmStyleTypes;

public final class PushMessageModel {

	private static ObjectMapper mapper = new ObjectMapper();

	public static final Logger logger = Logger.getLogger(Notification.class.getName());

	private Message message;
	private Target target;
	private Settings settings;

	public final Message getMessage() {
		return message;
	}

	public final Target getTarget() {
		return target;
	}

	public final Settings getSettings() {
		return settings;
	}

	private PushMessageModel(Builder builder) {
		this.message = builder.message;
		this.settings = builder.settings;
		this.target = builder.target;
	}

	public static class Builder {
		private Message message;
		private Target target;
		private Settings settings;

		public final Builder message(final Message message) {
			this.message = message;
			return this;
		}

		public final Builder target(final Target target) {
			this.target = target;
			return this;
		}

		public final Builder settings(final Settings settings) {
			this.settings = settings;
			return this;
		}

		public PushMessageModel build() {
			return new PushMessageModel(this);
		}

	}

	public static final class Message {

		private String alert;
		private String url;

		public final String getAlert() {
			return alert;
		}

		public final String getUrl() {
			return url;
		}

		private Message(Builder builder) {
			this.alert = builder.alert;
			this.url = builder.url;

		}

		public static class Builder {
			private String alert;
			private String url;

			public final Builder alert(final String alert) {
				this.alert = alert;
				return this;
			}

			public final Builder url(final String url) {
				this.url = url;
				return this;
			}

			public Message build() {
				return new Message(this);
			}
		}
	}

	public static final class Target {

		private String[] deviceIds = null;
		private String[] userIds = null;
		private String[] platforms = null;
		private String[] tagNames = null;

		public final String[] getDeviceIds() {
			return deviceIds;
		}

		public final String[] getUserIds() {
			return userIds;
		}

		public final String[] getPlatforms() {
			return platforms;
		}

		public final String[] getTagNames() {
			return tagNames;
		}

		private Target(Builder builder) {
			this.deviceIds = builder.deviceIds;
			this.platforms = builder.platforms;
			this.tagNames = builder.tagNames;
			this.userIds = builder.userIds;
		}

		public static class Builder {

			public enum PushNotificationsPlatform {
				APPLE("A"), GOOGLE("G"), WEBCHROME("WEB_CHROME"), WEBFIREFOX("WEB_FIREFOX"), WEBSAFARI(
						"WEB_SAFARI"), APPEXTCHROME("APPEXT_CHROME");

				private final String platformCode;

				PushNotificationsPlatform(String code) {
					this.platformCode = code;
				}

				public final String getValue() {
					return platformCode;
				}
			}

			private String[] deviceIds = null;
			private String[] userIds = null;
			private String[] platforms = null;
			private String[] tagNames = null;

			public final Builder deviceIds(final String[] deviceIds) {
				this.deviceIds = deviceIds;
				return this;
			}

			public final Builder userIds(final String[] userIds) {
				this.userIds = userIds;
				return this;
			}

			public final Builder tagNames(final String[] tagNames) {
				this.tagNames = tagNames;
				return this;
			}

			public final Builder platforms(final PushNotificationsPlatform[] platforms) {

				String[] platformArray = null;

				if (platforms != null && platforms.length > 0) {

					platformArray = new String[platforms.length];

					for (int i = 0; i < platforms.length; i++) {
						platformArray[i] = platforms[i].getValue();
					}
				}

				this.platforms = platformArray;
				return this;
			}

			public Target build() {
				return new Target(this);
			}

		}
	}

	public static final class Settings {

		private Apns apns;
		private Gcm gcm;
		private FirefoxWeb firefoxWeb;
		private ChromeWeb chromeWeb;
		private SafariWeb safariWeb;
		private ChromeAppExt chromeAppExt;

		public final Apns getApns() {
			return apns;
		}

		public final Gcm getGcm() {
			return gcm;
		}

		public final FirefoxWeb getFirefoxWeb() {
			return firefoxWeb;
		}

		public final ChromeWeb getChromeWeb() {
			return chromeWeb;
		}

		public final SafariWeb getSafariWeb() {
			return safariWeb;
		}

		public final ChromeAppExt getChromeAppExt() {
			return chromeAppExt;
		}

		private Settings(Builder builder) {

			this.apns = builder.apns;
			this.gcm = builder.gcm;
			this.firefoxWeb = builder.firefoxWeb;
			this.chromeWeb = builder.chromeWeb;
			this.safariWeb = builder.safariWeb;
			this.chromeAppExt = builder.chromeAppExt;
		}

		// Builder for Settings
		public static class Builder {

			private Apns apns;
			private Gcm gcm;
			private FirefoxWeb firefoxWeb;
			private ChromeWeb chromeWeb;
			private SafariWeb safariWeb;
			private ChromeAppExt chromeAppExt;

			public final Builder apns(Apns apns) {
				this.apns = apns;
				return this;
			}

			public final Builder gcm(Gcm gcm) {
				this.gcm = gcm;
				return this;
			}

			public final Builder firefoxWeb(FirefoxWeb firefoxWeb) {
				this.firefoxWeb = firefoxWeb;
				return this;
			}

			public final Builder chromeWeb(ChromeWeb chromeWeb) {
				this.chromeWeb = chromeWeb;
				return this;
			}

			public final Builder safariWeb(SafariWeb safariWeb) {
				this.safariWeb = safariWeb;
				return this;
			}

			public final Builder chromeAppExt(ChromeAppExt chromeAppExt) {
				this.chromeAppExt = chromeAppExt;
				return this;
			}

			public final Settings build() {
				return new Settings(this);
			}

		}

		public static final class Apns {

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

		public static final class Gcm {

			private Boolean delayWhileIdle;
			private Integer timeToLive;
			private String collapseKey;
			private JsonNode payload;
			private Boolean sync;
			private String sound;
			private String interactiveCategory;
			private GCMPriority priority;
			private GcmStyle style;
			private Visibility visibility;
			private String icon;
			private GcmLights lights;

			public final Boolean getDelayWhileIdle() {
				return delayWhileIdle;
			}

			public final Integer getTimeToLive() {
				return timeToLive;
			}

			public final String getCollapseKey() {
				return collapseKey;
			}

			@JsonRawValue
			public final JsonNode getPayload() {
				return payload;
			}

			public final Boolean getSync() {
				return sync;
			}

			public final String getSound() {
				return sound;
			}

			public final String getInteractiveCategory() {
				return interactiveCategory;
			}

			public final GCMPriority getPriority() {
				return priority;
			}

			public final GcmStyle getStyle() {
				return style;
			}

			public final Visibility getVisibility() {
				return visibility;
			}

			public final String getIcon() {
				return icon;
			}

			public final GcmLights getLights() {
				return lights;
			}

			private Gcm(Builder builder) {

				this.delayWhileIdle = builder.delayWhileIdle;
				this.timeToLive = builder.timeToLive;
				this.collapseKey = builder.collapseKey;
				this.payload = builder.payload;
				this.sync = builder.sync;
				this.sound = builder.sound;
				this.interactiveCategory = builder.interactiveCategory;
				this.priority = builder.priority;
				this.style = builder.style;
				this.visibility = builder.visibility;
				this.icon = builder.icon;
				this.lights = builder.lights;

			}

			public static class Builder {

				public enum GCMPriority {
					DEFAULT, MIN, LOW, HIGH, MAX
				}

				public enum Visibility {
					PUBLIC, PRIVATE, SECRET;
				}

				private Boolean delayWhileIdle;
				private Integer timeToLive;
				private String collapseKey;
				private JsonNode payload;
				private Boolean sync;
				private String sound;
				private String interactiveCategory;
				private GCMPriority priority;
				private GcmStyle style;
				private Visibility visibility;
				private String icon;
				private GcmLights lights;

				public final Builder delayWhileIdle(Boolean delayWhileIdle) {
					this.delayWhileIdle = delayWhileIdle;
					return this;
				}

				public final Builder timeToLive(Integer timeToLive) {
					this.timeToLive = timeToLive;
					return this;
				}

				public final Builder collapseKey(String collapseKey) {
					this.collapseKey = collapseKey;
					return this;
				}

				public final Builder payload(JSONObject payload) {
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

				public final Builder sync(Boolean sync) {
					this.sync = sync;
					return this;
				}

				public final Builder sound(String sound) {
					this.sound = sound;
					return this;
				}

				public final Builder interactiveCategory(String interactiveCategory) {
					this.interactiveCategory = interactiveCategory;
					return this;
				}

				public final Builder priority(GCMPriority priority) {
					this.priority = priority;
					return this;
				}

				public final Builder style(GcmStyle style) {
					this.style = style;
					return this;
				}

				public final Builder visibility(Visibility visibility) {
					this.visibility = visibility;
					return this;
				}

				public final Builder icon(String icon) {
					this.icon = icon;
					return this;
				}

				public final Builder lights(GcmLights lights) {
					this.lights = lights;
					return this;
				}

				public Gcm build() {
					return new Gcm(this);
				}

			}
		}

		public static final class GcmLights {
			private GcmLED ledArgb;
			private Integer ledOnMs;
			private Integer ledOffMs;

			public GcmLED getLedArgb() {
				return ledArgb;
			}

			public Integer getLedOnMs() {
				return ledOnMs;
			}

			public Integer getLedOffMs() {
				return ledOffMs;
			}

			private GcmLights(Builder builder) {
				this.ledArgb = builder.ledArgb;
				this.ledOnMs = builder.ledOnMs;
				this.ledOffMs = builder.ledOffMs;
			}

			public static class Builder {
				/**
				 * Determines the LED value in the notifications
				 */
				public enum GcmLED {

					BLACK, DARKGRAY, GRAY, LIGHTGRAY, WHITE, RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA, TRANSPARENT
				}

				private GcmLED ledArgb;
				private Integer ledOnMs;
				private Integer ledOffMs;

				public Builder ledArgb(GcmLED ledArgb) {
					this.ledArgb = ledArgb;
					return this;
				}

				public Builder ledOnMs(Integer ledOnMs) {
					this.ledOnMs = ledOnMs;
					return this;
				}

				public Builder ledOffMs(Integer ledOffMs) {
					this.ledOffMs = ledOffMs;
					return this;
				}

				public GcmLights build() {
					return new GcmLights(this);
				}
			}

		}

		public static final class GcmStyle {

			private GcmStyleTypes type;
			private String url;
			private String title;
			private String text;
			private String[] lines;

			public final GcmStyleTypes getType() {
				return type;
			}

			public final String getUrl() {
				return url;
			}

			public final String getTitle() {
				return title;
			}

			public final String getText() {
				return text;
			}

			public final String[] getLines() {
				return lines;
			}

			private GcmStyle(Builder builder) {
				this.type = builder.type;
				this.url = builder.url;
				this.title = builder.title;
				this.text = builder.text;
				this.lines = builder.lines;
			}

			public static class Builder {
				/**
				 * The available style type of the gcm notification message.
				 */
				public enum GcmStyleTypes {

					BIGTEXT_NOTIFICATION, INBOX_NOTIFICATION, PICTURE_NOTIFICATION
				}

				private GcmStyleTypes type;
				private String url;
				private String title;
				private String text;
				private String[] lines;

				public final Builder type(GcmStyleTypes type) {
					this.type = type;
					return this;
				}

				public final Builder url(String url) {
					this.url = url;
					return this;
				}

				public final Builder title(String title) {
					this.title = title;
					return this;
				}

				public final Builder text(String text) {
					this.text = text;
					return this;
				}

				public final Builder lines(String[] lines) {
					this.lines = lines;
					return this;
				}

				public GcmStyle build() {
					return new GcmStyle(this);

				}

			}

		}

		public static final class ChromeWeb {

			private String title;
			private String iconUrl;
			private Integer timeToLive;
			private JsonNode payload;

			public final String getTitle() {
				return title;
			}

			public final String getIconUrl() {
				return iconUrl;
			}

			public final Integer getTimeToLive() {
				return timeToLive;
			}

			@JsonRawValue
			public final JsonNode getPayload() {
				return this.payload;
			}

			private ChromeWeb(Builder builder) {

				this.title = builder.title;
				this.iconUrl = builder.iconUrl;
				this.timeToLive = builder.timeToLive;
				this.payload = builder.payload;
			}

			public static class Builder {

				private String title;
				private String iconUrl;
				private Integer timeToLive;
				private JsonNode payload;

				public final Builder title(String title) {
					this.title = title;
					return this;
				}

				public final Builder iconUrl(String iconUrl) {
					this.iconUrl = iconUrl;
					return this;
				}

				public final Builder timeToLive(Integer timeToLive) {
					this.timeToLive = timeToLive;
					return this;
				}

				public final Builder payload(JSONObject payload) {

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

				public ChromeWeb build() {
					return new ChromeWeb(this);
				}
			}

		}

		public static final class SafariWeb {

			private String title;
			private String action;
			private String[] urlArgs;

			public final String getTitle() {
				return title;
			}

			public final String getAction() {
				return action;
			}

			public final String[] getUrlArgs() {
				return urlArgs;
			}

			private SafariWeb(Builder builder) {

				this.title = builder.title;
				this.action = builder.action;
				this.urlArgs = builder.urlArgs;
			}

			public static class Builder {

				private String title;
				private String action;
				private String[] urlArgs;

				public final Builder title(String title) {
					this.title = title;
					return this;
				}

				public final Builder action(String action) {
					this.action = action;
					return this;
				}

				public final Builder urlArgs(String[] urlArgs) {
					this.urlArgs = urlArgs;
					return this;
				}

				public SafariWeb build() {
					return new SafariWeb(this);
				}
			}
		}

		public static final class FirefoxWeb {

			private String title;
			private String iconUrl;
			private Integer timeToLive;
			private JsonNode payload;

			public final String getTitle() {
				return title;
			}

			public final String getIconUrl() {
				return iconUrl;
			}

			public final Integer getTimeToLive() {
				return timeToLive;
			}

			@JsonRawValue
			public final JsonNode getPayload() {
				return this.payload;
			}

			private FirefoxWeb(Builder builder) {

				this.title = builder.title;
				this.iconUrl = builder.iconUrl;
				this.timeToLive = builder.timeToLive;
				this.payload = builder.payload;
			}

			public static class Builder {

				private String title;
				private String iconUrl;
				private Integer timeToLive;
				private JsonNode payload;

				public final Builder title(String title) {
					this.title = title;
					return this;
				}

				public final Builder iconUrl(String iconUrl) {
					this.iconUrl = iconUrl;
					return this;
				}

				public final Builder timeToLive(Integer timeToLive) {
					this.timeToLive = timeToLive;
					return this;
				}

				public final Builder payload(JSONObject payload) {
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

				public FirefoxWeb build() {
					return new FirefoxWeb(this);

				}
			}

		}

		public static final class ChromeAppExt {
			private String collapseKey;
			private Boolean delayWhileIdle;
			private String title;
			private String iconUrl;
			private Integer timeToLive;
			private JsonNode payload;

			public final String getTitle() {
				return title;
			}

			public final String getIconUrl() {
				return iconUrl;
			}

			public final String getCollapseKey() {
				return collapseKey;
			}

			public final Boolean getDelayWhileIdle() {
				return delayWhileIdle;
			}

			public final Integer getTimeToLive() {
				return timeToLive;
			}

			@JsonRawValue
			public final JsonNode getPayload() {
				return payload;
			}

			private ChromeAppExt(Builder builder) {

				this.collapseKey = builder.collapseKey;
				this.delayWhileIdle = builder.delayWhileIdle;
				this.title = builder.title;
				this.iconUrl = builder.iconUrl;
				this.timeToLive = builder.timeToLive;
				this.payload = builder.payload;

			}

			public static class Builder {

				private String collapseKey;
				private Boolean delayWhileIdle;
				private String title;
				private String iconUrl;
				private Integer timeToLive;
				private JsonNode payload;

				public final Builder collapseKey(String collapseKey) {
					this.collapseKey = collapseKey;
					return this;
				}

				public final Builder delayWhileIdle(Boolean delayWhileIdle) {
					this.delayWhileIdle = delayWhileIdle;
					return this;
				}

				public final Builder title(String title) {
					this.title = title;
					return this;
				}

				public final Builder iconUrl(String iconUrl) {
					this.iconUrl = iconUrl;
					return this;
				}

				public final Builder timeToLive(Integer timeToLive) {
					this.timeToLive = timeToLive;
					return this;
				}

				public final Builder payload(JSONObject payload) {
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

				public ChromeAppExt build() {
					return new ChromeAppExt(this);
				}

			}

		}

	}
}
