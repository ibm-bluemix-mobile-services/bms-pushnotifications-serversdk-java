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
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Message.MessageBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns.ApnsBuilder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.GcmLights;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.GcmStyle;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SettingsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target.TargetBuilder;

public final class PushMessageModel {

	private static ObjectMapper mapper = new ObjectMapper();

	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

	private Message message;
	private Target target;
	private Settings settings;
	private MessageBuilder messageBuilder;
	private TargetBuilder targetBuilder;
	private SettingsBuilder settingsBuilder;

	public final Message getMessage() {
		return message;
	}

	public final Target getTarget() {
		return target;
	}

	public final Settings getSettings() {
		return settings;
	}

	public final MessageBuilder getMessageBuilder() {
		return messageBuilder;
	}

	public final TargetBuilder getTargetBuilder() {
		return targetBuilder;
	}

	public final SettingsBuilder getSettingsBuilder() {
		return settingsBuilder;
	}

	private PushMessageModel(PushMessageModelBuilder builder) {
		this.message = builder.message;
		this.settings = builder.settings;
		this.target = builder.target;
		this.settingsBuilder = builder.settingsBuilder;
		this.targetBuilder = builder.targetBuilder;
		this.messageBuilder = builder.messageBuilder;
	}

	public static class PushMessageModelBuilder {
		private Message message;
		private Target target;
		private Settings settings;
		private MessageBuilder messageBuilder;
		private TargetBuilder targetBuilder;
		private SettingsBuilder settingsBuilder;

		public final PushMessageModelBuilder message(final Message message) {
			this.message = message;
			return this;
		}

		public final PushMessageModelBuilder target(final Target target) {
			this.target = target;
			return this;
		}

		public final PushMessageModelBuilder settings(final Settings settings) {
			this.settings = settings;
			return this;
		}

		public final PushMessageModelBuilder targetBuilder(TargetBuilder targetBuilder) {
			this.targetBuilder = targetBuilder;
			return this;
		}

		public final PushMessageModelBuilder settingsBuilder(SettingsBuilder settingsBuilder) {
			this.settingsBuilder = settingsBuilder;
			return this;
		}

		public final PushMessageModelBuilder messageBuilder(MessageBuilder messageBuilder) {
			this.messageBuilder = messageBuilder;
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

		private Message(MessageBuilder builder) {
			this.alert = builder.alert;
			this.url = builder.url;

		}

		public static class MessageBuilder {
			private String alert;
			private String url;

			public final MessageBuilder alert(final String alert) {
				this.alert = alert;
				return this;
			}

			public final MessageBuilder url(final String url) {
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

		private Target(TargetBuilder builder) {
			this.deviceIds = builder.deviceIds;
			this.platforms = builder.platforms;
			this.tagNames = builder.tagNames;
			this.userIds = builder.userIds;
		}

		public static class TargetBuilder {

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

			public final TargetBuilder deviceIds(final String[] deviceIds) {
				this.deviceIds = deviceIds;
				return this;
			}

			public final TargetBuilder userIds(final String[] userIds) {
				this.userIds = userIds;
				return this;
			}

			public final TargetBuilder tagNames(final String[] tagNames) {
				this.tagNames = tagNames;
				return this;
			}

			public final TargetBuilder platforms(final PushNotificationsPlatform[] platforms) {

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

		private Settings(SettingsBuilder builder) {

			this.apns = builder.apns;
			this.gcm = builder.gcm;
			this.firefoxWeb = builder.firefoxWeb;
			this.chromeWeb = builder.chromeWeb;
			this.safariWeb = builder.safariWeb;
			this.chromeAppExt = builder.chromeAppExt;
		}

		// Builder for Settings
		public static class SettingsBuilder {

			private Apns apns;
			private Gcm gcm;
			private FirefoxWeb firefoxWeb;
			private ChromeWeb chromeWeb;
			private SafariWeb safariWeb;
			private ChromeAppExt chromeAppExt;

			public final SettingsBuilder apns(Apns apns) {
				this.apns = apns;
				return this;
			}

			public final SettingsBuilder gcm(Gcm gcm) {
				this.gcm = gcm;
				return this;
			}

			public final SettingsBuilder firefoxWeb(FirefoxWeb firefoxWeb) {
				this.firefoxWeb = firefoxWeb;
				return this;
			}

			public final SettingsBuilder chromeWeb(ChromeWeb chromeWeb) {
				this.chromeWeb = chromeWeb;
				return this;
			}

			public final SettingsBuilder safariWeb(SafariWeb safariWeb) {
				this.safariWeb = safariWeb;
				return this;
			}

			public final SettingsBuilder chromeAppExt(ChromeAppExt chromeAppExt) {
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
			private ApnsBuilder.APNSNotificationType type;
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

				public final ApnsBuilder badge(Integer badge) {
					this.badge = badge;
					return this;
				}

				public final ApnsBuilder sound(String sound) {
					this.sound = sound;
					return this;
				}

				public final ApnsBuilder iosActionKey(String iosActionKey) {
					this.iosActionKey = iosActionKey;
					return this;
				}

				public final ApnsBuilder payload(JSONObject payload) {
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

				public final ApnsBuilder interactiveCategory(String interactiveCategory) {
					this.interactiveCategory = interactiveCategory;
					return this;
				}

				public final ApnsBuilder type(APNSNotificationType type) {
					this.type = type;
					return this;
				}

				public final ApnsBuilder titleLocKey(String titleLocKey) {
					this.titleLocKey = titleLocKey;
					return this;
				}

				public final ApnsBuilder locKey(String locKey) {
					this.locKey = locKey;
					return this;
				}

				public final ApnsBuilder launchImage(String launchImage) {
					this.launchImage = launchImage;
					return this;
				}

				public final ApnsBuilder titleLocArgs(String[] titleLocArgs) {
					this.titleLocArgs = titleLocArgs;
					return this;
				}

				public final ApnsBuilder locArgs(String[] locArgs) {
					this.locArgs = locArgs;
					return this;
				}

				public final ApnsBuilder subtitle(String subtitle) {
					this.subtitle = subtitle;
					return this;
				}

				public final ApnsBuilder title(String title) {
					this.title = title;
					return this;
				}

				public final ApnsBuilder attachmentUrl(String attachmentUrl) {
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
			private GcmBuilder.GCMPriority priority;
			private GcmStyle style;
			private GcmBuilder.Visibility visibility;
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

			private Gcm(GcmBuilder builder) {

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

			public static class GcmBuilder {

				public enum GCMPriority {
					DEFAULT, MIN, LOW, HIGH, MAX
				}

				public enum Visibility {
					PUBLIC, PRIVATE, SECRET;
				}

				/**
				 * The available style type of the gcm notification message.
				 */
				public enum GcmStyleTypes {

					BIGTEXT_NOTIFICATION, INBOX_NOTIFICATION, PICTURE_NOTIFICATION
				}

				/**
				 * Determines the LED value in the notifications
				 */
				public enum GcmLED {

					BLACK, DARKGRAY, GRAY, LIGHTGRAY, WHITE, RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA, TRANSPARENT
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

				public final GcmBuilder delayWhileIdle(Boolean delayWhileIdle) {
					this.delayWhileIdle = delayWhileIdle;
					return this;
				}

				public final GcmBuilder timeToLive(Integer timeToLive) {
					this.timeToLive = timeToLive;
					return this;
				}

				public final GcmBuilder collapseKey(String collapseKey) {
					this.collapseKey = collapseKey;
					return this;
				}

				public final GcmBuilder payload(JSONObject payload) {
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

				public final GcmBuilder sync(Boolean sync) {
					this.sync = sync;
					return this;
				}

				public final GcmBuilder sound(String sound) {
					this.sound = sound;
					return this;
				}

				public final GcmBuilder interactiveCategory(String interactiveCategory) {
					this.interactiveCategory = interactiveCategory;
					return this;
				}

				public final GcmBuilder priority(GCMPriority priority) {
					this.priority = priority;
					return this;
				}

				public final GcmBuilder style(GcmStyle style) {
					this.style = style;
					return this;
				}

				public final GcmBuilder visibility(Visibility visibility) {
					this.visibility = visibility;
					return this;
				}

				public final GcmBuilder icon(String icon) {
					this.icon = icon;
					return this;
				}

				public final GcmBuilder lights(GcmLights lights) {
					this.lights = lights;
					return this;
				}

				public Gcm build() {
					return new Gcm(this);
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

					private GcmLights(GcmLightsBuilder builder) {
						this.ledArgb = builder.ledArgb;
						this.ledOnMs = builder.ledOnMs;
						this.ledOffMs = builder.ledOffMs;
					}

					public static class GcmLightsBuilder {
						private GcmLED ledArgb;
						private Integer ledOnMs;
						private Integer ledOffMs;

						public GcmLightsBuilder ledArgb(GcmLED ledArgb) {
							this.ledArgb = ledArgb;
							return this;
						}

						public GcmLightsBuilder ledOnMs(Integer ledOnMs) {
							this.ledOnMs = ledOnMs;
							return this;
						}

						public GcmLightsBuilder ledOffMs(Integer ledOffMs) {
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

					private GcmStyle(GcmStyleBuilder builder) {
						this.type = builder.type;
						this.url = builder.url;
						this.title = builder.title;
						this.text = builder.text;
						this.lines = builder.lines;
					}

					public static class GcmStyleBuilder {

						private GcmStyleTypes type;
						private String url;
						private String title;
						private String text;
						private String[] lines;

						public final GcmStyleBuilder type(GcmStyleTypes type) {
							this.type = type;
							return this;
						}

						public final GcmStyleBuilder url(String url) {
							this.url = url;
							return this;
						}

						public final GcmStyleBuilder title(String title) {
							this.title = title;
							return this;
						}

						public final GcmStyleBuilder text(String text) {
							this.text = text;
							return this;
						}

						public final GcmStyleBuilder lines(String[] lines) {
							this.lines = lines;
							return this;
						}

						public GcmStyle build() {
							return new GcmStyle(this);

						}

					}

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

			private ChromeWeb(ChromeWebBuilder builder) {

				this.title = builder.title;
				this.iconUrl = builder.iconUrl;
				this.timeToLive = builder.timeToLive;
				this.payload = builder.payload;
			}

			public static class ChromeWebBuilder {

				private String title;
				private String iconUrl;
				private Integer timeToLive;
				private JsonNode payload;

				public final ChromeWebBuilder title(String title) {
					this.title = title;
					return this;
				}

				public final ChromeWebBuilder iconUrl(String iconUrl) {
					this.iconUrl = iconUrl;
					return this;
				}

				public final ChromeWebBuilder timeToLive(Integer timeToLive) {
					this.timeToLive = timeToLive;
					return this;
				}

				public final ChromeWebBuilder payload(JSONObject payload) {

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

			private SafariWeb(SafariWebBuilder builder) {

				this.title = builder.title;
				this.action = builder.action;
				this.urlArgs = builder.urlArgs;
			}

			public static class SafariWebBuilder {

				private String title;
				private String action;
				private String[] urlArgs;

				public final SafariWebBuilder title(String title) {
					this.title = title;
					return this;
				}

				public final SafariWebBuilder action(String action) {
					this.action = action;
					return this;
				}

				public final SafariWebBuilder urlArgs(String[] urlArgs) {
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

			private FirefoxWeb(FirefoxWebBuilder builder) {

				this.title = builder.title;
				this.iconUrl = builder.iconUrl;
				this.timeToLive = builder.timeToLive;
				this.payload = builder.payload;
			}

			public static class FirefoxWebBuilder {

				private String title;
				private String iconUrl;
				private Integer timeToLive;
				private JsonNode payload;

				public final FirefoxWebBuilder title(String title) {
					this.title = title;
					return this;
				}

				public final FirefoxWebBuilder iconUrl(String iconUrl) {
					this.iconUrl = iconUrl;
					return this;
				}

				public final FirefoxWebBuilder timeToLive(Integer timeToLive) {
					this.timeToLive = timeToLive;
					return this;
				}

				public final FirefoxWebBuilder payload(JSONObject payload) {
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

			private ChromeAppExt(ChromeAppExtBuilder builder) {

				this.collapseKey = builder.collapseKey;
				this.delayWhileIdle = builder.delayWhileIdle;
				this.title = builder.title;
				this.iconUrl = builder.iconUrl;
				this.timeToLive = builder.timeToLive;
				this.payload = builder.payload;

			}

			public static class ChromeAppExtBuilder {

				private String collapseKey;
				private Boolean delayWhileIdle;
				private String title;
				private String iconUrl;
				private Integer timeToLive;
				private JsonNode payload;

				public final ChromeAppExtBuilder collapseKey(String collapseKey) {
					this.collapseKey = collapseKey;
					return this;
				}

				public final ChromeAppExtBuilder delayWhileIdle(Boolean delayWhileIdle) {
					this.delayWhileIdle = delayWhileIdle;
					return this;
				}

				public final ChromeAppExtBuilder title(String title) {
					this.title = title;
					return this;
				}

				public final ChromeAppExtBuilder iconUrl(String iconUrl) {
					this.iconUrl = iconUrl;
					return this;
				}

				public final ChromeAppExtBuilder timeToLive(Integer timeToLive) {
					this.timeToLive = timeToLive;
					return this;
				}

				public final ChromeAppExtBuilder payload(JSONObject payload) {
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
