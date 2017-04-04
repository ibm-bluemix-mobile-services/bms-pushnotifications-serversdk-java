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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.GcmLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.GcmStyleTypes;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.PushNotificationsPlatform;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Message.MessageBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns.ApnsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeAppExt.ChromeAppExtBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeWeb.ChromeWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.FirefoxWeb.FirefoxWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.GcmLights.GcmLightsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.GcmStyle.GcmStyleBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SafariWeb.SafariWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SettingsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target.TargetBuilder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class PushMessageModel {

	private static ObjectMapper mapper = new ObjectMapper();

	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

	private Message message;
	private Target target;
	private Settings settings;
	
	private MessageBuilder messageBuilder;
	private TargetBuilder targetBuilder;
	private SettingsBuilder settingsBuilder;

	public Message getMessage() {
		return message;
	}

	public Target getTarget() {
		return target;
	}

	public Settings getSettings() {
		return settings;
	}
	
	public TargetBuilder getTargetBuilder() {
		return targetBuilder;
	}

	public SettingsBuilder getSettingsBuilder() {
		return settingsBuilder;
	}
	
	public MessageBuilder getMessageBuilder() {
		return messageBuilder;
	}

	private PushMessageModel(PushMessageModelBuilder builder){
		this.message = builder.message;
		this.settings = builder.settings;
		this.target = builder.target;
		this.settingsBuilder = builder.settingsBuilder;
		this.targetBuilder = builder.targetBuilder;
		this.messageBuilder = builder.messageBuilder;
	}

	public static class PushMessageModelBuilder{
		private Message message;
		private Target target;
		private Settings settings;
		private MessageBuilder messageBuilder;
		private TargetBuilder targetBuilder;
		private SettingsBuilder settingsBuilder;
		
		public PushMessageModelBuilder setMessage(final Message message) {
			this.message = message;
			return this;
		}

		public PushMessageModelBuilder setTarget(final Target target) {
			this.target = target;
			return this;
		}

		public PushMessageModelBuilder setSettings(final Settings settings) {
			this.settings = settings;
			return this;
		}

		public PushMessageModelBuilder setTargetBuilder(TargetBuilder targetBuilder) {
			this.targetBuilder = targetBuilder;
			return this;
		}

		public PushMessageModelBuilder setSettingsBuilder(SettingsBuilder settingsBuilder) {
			this.settingsBuilder = settingsBuilder;
			return this;
		}
		
		public PushMessageModelBuilder setMessageBuilder(MessageBuilder messageBuilder) {
			this.messageBuilder = messageBuilder;
			return this;
		}

		public PushMessageModel build(){
			return new PushMessageModel(this);
		}
		
		

	}
	

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static final class Message {

		private String alert;
		private String url;
		
		
		public String getAlert() {
			return alert;
		}

		public String getUrl() {
			return url;
		}
		
		private Message(MessageBuilder builder){
			this.alert = builder.alert;
			this.url = builder.url;
			
		}
		public static class MessageBuilder{
			private String alert;
			private String url;
			private boolean checkBlankObject = false;
			
			public MessageBuilder setAlert(final String alert) {
				if(alert!=null && alert.length()>0){
					checkBlankObject = true;
				}
				this.alert = alert;
				return this;
			}
			
			public MessageBuilder setUrl(final String url) {
				if(url != null && url.length() >0){
					checkBlankObject=true;
				}
				this.url = url;
				return this;
			}
			
			public Message build(){
				if(checkBlankObject){
					return new Message(this);	
				}
				else{
					return null;
				}
				
			}
		}
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static final class Target {

		private String[] deviceIds = null;
		private String[] userIds = null;
		private String[] platforms = null;
		private String[] tagNames = null;

		public String[] getDeviceIds() {
			return deviceIds;
		}

		public String[] getUserIds() {
			return userIds;
		}

		public String[] getTagNames() {
			return tagNames;
		}

		public String[] getPlatforms() {
			return platforms;
		}
		
		private Target(TargetBuilder builder){
			this.deviceIds = builder.deviceIds;
			this.platforms = builder.platforms;
			this.tagNames = builder.tagNames;
			this.userIds = builder.userIds;
		}
		
		public static class TargetBuilder{
		
			private String[] deviceIds = null;
			private String[] userIds = null;
			private String[] platforms = null;
			private String[] tagNames = null;
			private boolean checkBlankObject = false;
			
			public TargetBuilder setDeviceIds(final String[] deviceIds) {
				if(deviceIds!=null && deviceIds.length>0){
					checkBlankObject = true;
				}
				
				this.deviceIds = deviceIds;
				return this;
			}
			
			public TargetBuilder setUserIds(final String[] userIds) {
				if(userIds != null && userIds.length>0){
					checkBlankObject = true;
				}
				this.userIds = userIds;
				return this;
			}

			public TargetBuilder setTagNames(final String[] tagNames) {
				if(tagNames!= null && tagNames.length > 0){
					checkBlankObject = true;
				}
				this.tagNames = tagNames;
				return this;
			}

			public TargetBuilder setPlatforms(final PushNotificationsPlatform[] platforms) {

				if(platforms!=null && platforms.length>0){
					checkBlankObject=true;
				}
				String[] platformArray = new String[platforms.length];

				for (int i = 0; i < platforms.length; i++) {
					platformArray[i] = platforms[i].getValue();
				}

				this.platforms = platformArray;
				return this;
			}

			public Target build(){
				if(checkBlankObject){
				return new Target(this);
				}
				else{
					return null;
				}
			}

		}
	}

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static final class Settings {

		private Apns apns;
		private Gcm gcm;
		private FirefoxWeb firefoxWeb;
		private ChromeWeb chromeWeb;
		private SafariWeb safariWeb;
		private ChromeAppExt chromeAppExt;

		private ApnsBuilder apnsBuilder;
		private GcmBuilder gcmBuilder;
		private FirefoxWebBuilder firefoxWebBuilder;
		private ChromeWebBuilder chromeWebBuilder;
		private SafariWebBuilder safariWebBuilder;
		private ChromeAppExtBuilder chromeAppExtBuilder;

		public Apns getApns() {
			return apns;
		}

		public Gcm getGcm() {
			return gcm;
		}

		public FirefoxWeb getFirefoxWeb() {
			return firefoxWeb;
		}

		public ChromeWeb getChromeWeb() {
			return chromeWeb;
		}

		public SafariWeb getSafariWeb() {
			return safariWeb;
		}

		public ChromeAppExt getChromeAppExt() {
			return chromeAppExt;
		}

		public ApnsBuilder getApnsBuilder() {
			return apnsBuilder;
		}

		public GcmBuilder getGcmBuilder() {
			return gcmBuilder;
		}

		public FirefoxWebBuilder getFirefoxWebBuilder() {
			return firefoxWebBuilder;
		}

		public ChromeWebBuilder getChromeWebBuilder() {
			return chromeWebBuilder;
		}

		public SafariWebBuilder getSafariWebBuilder() {
			return safariWebBuilder;
		}

		public ChromeAppExtBuilder getChromeAppExtBuilder() {
			return chromeAppExtBuilder;
		}

		private Settings(SettingsBuilder builder) {

			this.apns = builder.apns;
			this.gcm = builder.gcm;
			this.firefoxWeb = builder.firefoxWeb;
			this.chromeWeb = builder.chromeWeb;
			this.safariWeb = builder.safariWeb;
			this.chromeAppExt = builder.chromeAppExt;

			this.apnsBuilder = builder.apnsBuilder;
			this.gcmBuilder = builder.gcmBuilder;
			this.firefoxWebBuilder = builder.firefoxWebBuilder;
			this.chromeWebBuilder = builder.chromeWebBuilder;
			this.safariWebBuilder = builder.safariWebBuilder;
			this.chromeAppExtBuilder = builder.chromeAppExtBuilder;

		}

		// Builder for Settings
		public static class SettingsBuilder {

			private Apns apns;
			private Gcm gcm;
			private FirefoxWeb firefoxWeb;
			private ChromeWeb chromeWeb;
			private SafariWeb safariWeb;
			private ChromeAppExt chromeAppExt;

			private ApnsBuilder apnsBuilder;
			private GcmBuilder gcmBuilder;
			private FirefoxWebBuilder firefoxWebBuilder;
			private ChromeWebBuilder chromeWebBuilder;
			private SafariWebBuilder safariWebBuilder;
			private ChromeAppExtBuilder chromeAppExtBuilder;

			public SettingsBuilder setApns(Apns apns) {
				this.apns = apns;
				return this;
			}

			public SettingsBuilder setGcm(Gcm gcm) {
				this.gcm = gcm;
				return this;
			}

			public SettingsBuilder setFirefoxWeb(FirefoxWeb firefoxWeb) {
				this.firefoxWeb = firefoxWeb;
				return this;
			}

			public SettingsBuilder setChromeWeb(ChromeWeb chromeWeb) {
				this.chromeWeb = chromeWeb;
				return this;
			}

			public SettingsBuilder setSafariWeb(SafariWeb safariWeb) {
				this.safariWeb = safariWeb;
				return this;
			}

			public SettingsBuilder setChromeAppExt(ChromeAppExt chromeAppExt) {
				this.chromeAppExt = chromeAppExt;
				return this;
			}

			public SettingsBuilder setApnsBuilder(ApnsBuilder apnsBuilder) {
				this.apnsBuilder = apnsBuilder;
				return this;
			}

			public SettingsBuilder setGcmBuilder(GcmBuilder gcmBuilder) {
				this.gcmBuilder = gcmBuilder;
				return this;
			}

			public SettingsBuilder setFirefoxWebBuilder(FirefoxWebBuilder firefoxWebBuilder) {
				this.firefoxWebBuilder = firefoxWebBuilder;
				return this;
			}

			public SettingsBuilder setChromeWebBuilder(ChromeWebBuilder chromeWebBuilder) {
				this.chromeWebBuilder = chromeWebBuilder;
				return this;
			}

			public SettingsBuilder setSafariWebBuilder(SafariWebBuilder safariWebBuilder) {
				this.safariWebBuilder = safariWebBuilder;
				return this;
			}

			public SettingsBuilder setChromeAppExtBuilder(ChromeAppExtBuilder chromeAppExtBuilder) {
				this.chromeAppExtBuilder = chromeAppExtBuilder;
				return this;
			}

			public Settings build() {
				return new Settings(this);
			}

		}

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

			public final APNSNotificationType getType() {
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

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		public static final class Gcm {

			private Boolean delayWhileIdle;
			private Integer timeToLive;
			private String collapseKey;
			private JsonNode payload;
			private Boolean sync;
			private String sound;
			private String interactiveCategory;
			private GCMPriority priority;
			private JsonNode style;
			private Visibility visibility;
			private String icon;
			private JsonNode lights;
			private GcmStyleBuilder gcmStyleBuilder;
			private GcmLightsBuilder gcmLightsBuilder;
			
			

			public GcmStyleBuilder getGcmStyleBuilder() {
				return gcmStyleBuilder;
			}

			public GcmLightsBuilder getGcmLightsBuilder() {
				return gcmLightsBuilder;
			}

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
				return this.payload;
			}

			public final Boolean getSync() {
				return sync;
			}

			public final String getSound() {
				return sound;
			}

			public final String getinteractiveCategory() {
				return interactiveCategory;
			}

			public final GCMPriority getPriority() {
				return priority;
			}

			@JsonRawValue
			public final JsonNode getStyle() {
				return style;
			}

			public final Visibility getVisibility() {
				return visibility;
			}

			public final String getIcon() {
				return icon;
			}

			@JsonRawValue
			public final JsonNode getLights() {
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
				this.gcmStyleBuilder = builder.gcmStyleBuilder;
				this.gcmLightsBuilder = builder.gcmLightsBuilder;

			}

			public static class GcmBuilder {

				private Boolean delayWhileIdle;
				private Integer timeToLive;
				private String collapseKey;
				private JsonNode payload;

				private Boolean sync;
				private String sound;
				private String interactiveCategory;
				private GCMPriority priority;
				private JsonNode style;
				private Visibility visibility;
				private String icon;
				private JsonNode lights;
				private GcmStyleBuilder gcmStyleBuilder;
				private GcmLightsBuilder gcmLightsBuilder;
				
				private boolean checkBlankObject = false;
				
				

				public GcmBuilder setGcmStyleBuilder(GcmStyleBuilder gcmStyleBuilder) {
					if (gcmStyleBuilder != null) {
						checkBlankObject = true;
					}
					this.gcmStyleBuilder = gcmStyleBuilder;
					return this;
				}

				public GcmBuilder setGcmLightsBuilder(GcmLightsBuilder gcmLightsBuilder) {
					if (gcmLightsBuilder != null) {
						checkBlankObject = true;
					}
					this.gcmLightsBuilder = gcmLightsBuilder;
					return this;
				}

				public final GcmBuilder setDelayWhileIdle(Boolean delayWhileIdle) {

					if (delayWhileIdle != null) {
						checkBlankObject = true;
					}
					this.delayWhileIdle = delayWhileIdle;
					return this;
				}

				public final GcmBuilder setTimeToLive(Integer timeToLive) {

					if (timeToLive != null) {
						checkBlankObject = true;
					}
					this.timeToLive = timeToLive;
					return this;
				}

				public final GcmBuilder setCollapseKey(String collapseKey) {

					if (collapseKey != null && collapseKey.length() > 0) {
						checkBlankObject = true;
					}
					this.collapseKey = collapseKey;
					return this;
				}

				public final GcmBuilder setPayload(JSONObject payload) {
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

				public final GcmBuilder setSync(Boolean sync) {

					if (sync != null) {
						checkBlankObject = true;
					}
					this.sync = sync;
					return this;
				}

				public final GcmBuilder setSound(String sound) {
					if (sound != null && sound.length() > 0) {
						checkBlankObject = true;
					}
					this.sound = sound;
					return this;
				}

				public final GcmBuilder setInteractiveCategory(String interactiveCategory) {

					if (interactiveCategory != null && interactiveCategory.length() > 0) {
						checkBlankObject = true;
					}
					this.interactiveCategory = interactiveCategory;
					return this;
				}

				public final GcmBuilder setPriority(GCMPriority priority) {

					if (priority != null) {
						checkBlankObject = true;
					}
					this.priority = priority;
					return this;
				}

				public final GcmBuilder setStyle(JSONObject style) {
					try {
						if (style != null) {
							checkBlankObject = true;
							this.style = mapper.readTree(String.valueOf(style));
						}
					} catch (Exception exception) {
						logger.log(Level.SEVERE, exception.toString(), exception);
					}

					return this;
				}

				public final GcmBuilder setVisibility(Visibility visibility) {
					if (visibility != null) {
						checkBlankObject = true;
					}
					this.visibility = visibility;
					return this;
				}

				public final GcmBuilder setIcon(String icon) {
					if (icon != null && icon.length() > 0) {
						checkBlankObject = true;
					}
					this.icon = icon;
					return this;
				}

				public final GcmBuilder setLights(JSONObject lights) {

					try {
						if (lights != null) {
							checkBlankObject = true;
							this.lights = mapper.readTree(String.valueOf(lights));
							 
						}
					} catch (Exception exception) {
						logger.log(Level.SEVERE, exception.toString(), exception);
					}

					return this;
				}

				public Gcm build() {
					if (checkBlankObject) {
						return new Gcm(this);
					} else {
						return null;
					}

				}

			}
		}

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		public static final class GcmLights {
			private GcmLED ledArgb;
			private Integer ledOnMs;
			private String ledOffMs;

			public GcmLED getLedArgb() {
				return ledArgb;
			}
			
			public Integer getLedOnMs() {
				return ledOnMs;
			}

			public String getLedOffMs() {
				return ledOffMs;
			}

			private GcmLights(GcmLightsBuilder builder){
				this.ledArgb = builder.ledArgb;
				this.ledOnMs = builder.ledOnMs;
				this.ledOffMs = builder.ledOffMs;
			}
			
			public static class GcmLightsBuilder {

				private GcmLED ledArgb;
				private Integer ledOnMs;
				private String ledOffMs;

				public GcmLightsBuilder setLedArgb(GcmLED ledArgb) {
					this.ledArgb = ledArgb;
					return this;
				}

				public GcmLightsBuilder setLedOnMs(Integer ledOnMs) {
					this.ledOnMs = ledOnMs;
					return this;
				}

				public GcmLightsBuilder setLedOffMs(String ledOffMs) {
					this.ledOffMs = ledOffMs;
					return this;
				}
				
				public GcmLights build(){
					return new GcmLights(this);
				}

			}
		}

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		public static final class GcmStyle {
			private GcmStyleTypes type;
			private String url;
			private String title;
			private String text;
			private String[] lines;

			
			public GcmStyleTypes getType() {
				return type;
			}

			public String getUrl() {
				return url;
			}

			public String getTitle() {
				return title;
			}

			public String getText() {
				return text;
			}

			public void setLines(final String[] lines) {
				this.lines = lines;
			}

			public String[] getLines() {
				return lines;
			}
			
			private GcmStyle(GcmStyleBuilder builder){
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

				
				public GcmStyleBuilder setType(final GcmStyleTypes type) {
					this.type = type;
					return this;
				}

				public GcmStyleBuilder setUrl(final String url) {
					this.url = url;
					return this;
				}

				public GcmStyleBuilder setTitle(final String title) {
					this.title = title;
					return this;
				}

				public GcmStyleBuilder setText(final String text) {
					this.text = text;
					return this;
				}
				
				public GcmStyle build(){
					
					return new GcmStyle(this);
				}

			}
		}

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
				private boolean checkBlankObject = false;

				public final ChromeWebBuilder setTitle(String title) {
					if (title != null && title.length() > 0) {
						checkBlankObject = true;
					}
					this.title = title;
					return this;
				}

				public final ChromeWebBuilder setIconUrl(String iconUrl) {
					if (iconUrl != null && iconUrl.length() > 0) {
						checkBlankObject = true;
					}
					this.iconUrl = iconUrl;
					return this;
				}

				public final ChromeWebBuilder setTimeToLive(Integer timeToLive) {
					if (timeToLive != null) {
						checkBlankObject = true;
					}
					this.timeToLive = timeToLive;
					return this;
				}

				public final ChromeWebBuilder setPayload(JSONObject payload) {

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

				public ChromeWeb build() {
					if (checkBlankObject) {
						return new ChromeWeb(this);
					}
					return null;

				}
			}

		}

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
				private boolean checkBlankObject = false;

				public final SafariWebBuilder setTitle(String title) {
					if (title != null && title.length() > 0) {
						checkBlankObject = true;
					}
					this.title = title;
					return this;
				}

				public final SafariWebBuilder setAction(String action) {
					if (action != null && action.length() > 0) {
						checkBlankObject = true;
					}
					this.action = action;
					return this;
				}

				public final SafariWebBuilder setUrlArgs(String[] urlArgs) {

					if (urlArgs != null && urlArgs.length > 0) {
						checkBlankObject = true;
					}
					this.urlArgs = urlArgs;
					return this;
				}

				public SafariWeb build() {

					if (checkBlankObject) {
						return new SafariWeb(this);
					}
					return null;

				}
			}
		}

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
				private boolean checkBlankObject = false;

				public final FirefoxWebBuilder setTitle(String title) {
					if (title != null && title.length() > 0) {
						checkBlankObject = true;
					}
					this.title = title;
					return this;
				}

				public final FirefoxWebBuilder setIconUrl(String iconUrl) {

					if (iconUrl != null && iconUrl.length() > 0) {
						checkBlankObject = true;
					}
					this.iconUrl = iconUrl;
					return this;
				}

				public final FirefoxWebBuilder setTimeToLive(Integer timeToLive) {

					if (timeToLive != null) {
						checkBlankObject = true;
					}
					this.timeToLive = timeToLive;
					return this;
				}

				public final FirefoxWebBuilder setPayload(JSONObject payload) {
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

				public FirefoxWeb build() {

					if (checkBlankObject) {
						return new FirefoxWeb(this);
					}
					return null;

				}
			}

		}

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
				private boolean checkBlankObject = false;

				public final ChromeAppExtBuilder setCollapseKey(String collapseKey) {
					if (collapseKey != null && collapseKey.length() > 0) {
						checkBlankObject = true;
					}
					this.collapseKey = collapseKey;
					return this;
				}

				public final ChromeAppExtBuilder setDelayWhileIdle(Boolean delayWhileIdle) {
					if (delayWhileIdle != null) {
						checkBlankObject = true;
					}
					this.delayWhileIdle = delayWhileIdle;
					return this;
				}

				public final ChromeAppExtBuilder setTitle(String title) {
					if (title != null && title.length() > 0) {
						checkBlankObject = true;
					}
					this.title = title;
					return this;
				}

				public final ChromeAppExtBuilder setIconUrl(String iconUrl) {
					if (iconUrl != null && iconUrl.length() > 0) {
						checkBlankObject = true;
					}
					this.iconUrl = iconUrl;
					return this;
				}

				public final ChromeAppExtBuilder setTimeToLive(Integer timeToLive) {

					if (timeToLive != null) {
						checkBlankObject = true;
					}
					this.timeToLive = timeToLive;
					return this;
				}

				public final ChromeAppExtBuilder setPayload(JSONObject payload) {
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

				public ChromeAppExt build() {
					if (checkBlankObject) {
						return new ChromeAppExt(this);
					}
					return null;

				}

			}

		}

	}
}
