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

package com.ibm.mobilefirstplatform.serversdk.java.push.internal;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.ApnsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.GcmBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.GcmBuilder.GcmLights;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.GcmBuilder.GcmStyle;

public final class PushMessageModel {

	private Message message;
	private Target target;
	private Settings settings;

	public void setMessage(final Message message) {
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}

	public void setTarget(final Target target) {
		this.target = target;
	}

	public Target getTarget() {
		return target;
	}

	public void setSettings(final Settings settings) {
		this.settings = settings;
	}

	public Settings getSettings() {
		return settings;
	}

	public static final class Message {

		private String alert;
		private String url;

		public String getAlert() {
			return alert;
		}

		public String getUrl() {
			return url;
		}

		public Message setAlert(String alert) {
			this.alert = alert;
			return this;
		}

		public Message setUrl(String url) {
			this.url = url;
			return this;
		}

	}

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

		public Target setDeviceIds(String[] deviceIds) {
			this.deviceIds = deviceIds;
			return this;
		}

		public Target setUserIds(String[] userIds) {
			this.userIds = userIds;
			return this;
		}

		public Target setPlatforms(String[] platforms) {
			this.platforms = platforms;
			return this;
		}

		public Target setTagNames(String[] tagNames) {
			this.tagNames = tagNames;
			return this;
		}

	}

	public static final class Settings {

		private Apns apns;
		private Gcm gcm;
		private FirefoxWeb firefoxWeb;
		private ChromeWeb chromeWeb;
		private SafariWeb safariWeb;
		private ChromeAppExt chromeAppExt;

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

		public Settings setApns(Apns apns) {
			this.apns = apns;
			return this;
		}

		public Settings setFirefoxWeb(FirefoxWeb firefoxWeb) {
			this.firefoxWeb = firefoxWeb;
			return this;
		}

		public Settings setChromeWeb(ChromeWeb chromeWeb) {
			this.chromeWeb = chromeWeb;
			return this;
		}

		public Settings setSafariWeb(SafariWeb safariWeb) {
			this.safariWeb = safariWeb;
			return this;
		}

		public Settings setChromeAppExt(ChromeAppExt chromeAppExt) {
			this.chromeAppExt = chromeAppExt;
			return this;
		}

		public Settings setGcm(Gcm gcm) {
			this.gcm = gcm;
			return this;
		}

		public static class Apns {

			private Integer badge;
			private String sound;
			private String iosActionKey;
			private JsonNode payload;
			private String category;
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

			public String getCategory() {
				return category;
			}

			public final String getInteractiveCategory() {
				return interactiveCategory;
			}

			public final ApnsBuilder.APNSNotificationType getType() {
				return type;
			}

			public Apns setBadge(Integer badge) {
				this.badge = badge;
				return this;
			}

			public Apns setSound(String sound) {
				this.sound = sound;
				return this;
			}

			public Apns setIosActionKey(String iosActionKey) {
				this.iosActionKey = iosActionKey;
				return this;
			}

			public Apns setPayload(JsonNode payload) {
				this.payload = payload;
				return this;
			}

			public Apns setCategory(String category) {
				this.category = category;
				return this;
			}

			public Apns setInteractiveCategory(String interactiveCategory) {
				this.interactiveCategory = interactiveCategory;
				return this;
			}

			public Apns setType(ApnsBuilder.APNSNotificationType type) {
				this.type = type;
				return this;
			}

			public Apns setTitleLocKey(String titleLocKey) {
				this.titleLocKey = titleLocKey;
				return this;
			}

			public Apns setLocKey(String locKey) {
				this.locKey = locKey;
				return this;
			}

			public Apns setLaunchImage(String launchImage) {
				this.launchImage = launchImage;
				return this;
			}

			public Apns setTitleLocArgs(String[] titleLocArgs) {
				this.titleLocArgs = titleLocArgs;
				return this;
			}

			public Apns setLocArgs(String[] locArgs) {
				this.locArgs = locArgs;
				return this;
			}

			public Apns setSubtitle(String subtitle) {
				this.subtitle = subtitle;
				return this;
			}

			public Apns setTitle(String title) {
				this.title = title;
				return this;
			}

			public Apns setAttachmentUrl(String attachmentUrl) {
				this.attachmentUrl = attachmentUrl;
				return this;
			}

		}

		public static final class Gcm {

			private Boolean delayWhileIdle;
			private Integer timeToLive;
			private String collapseKey;
			private JsonNode payload;
			private Boolean sync;
			private String sound;
			private String category;
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
				return this.payload;
			}

			public final Boolean getSync() {
				return sync;
			}

			public final String getSound() {
				return sound;
			}

			public final String getCategory() {
				return category;
			}

			public final String getInteractiveCategory() {
				return interactiveCategory;
			}

			public final GcmBuilder.GCMPriority getPriority() {
				return priority;
			}

			public final GcmStyle getStyle() {
				return style;
			}

			public final GcmBuilder.Visibility getVisibility() {
				return visibility;
			}

			public final String getIcon() {
				return icon;
			}

			public final GcmLights getLights() {
				return lights;
			}

			public Gcm setCategory(String category) {
				this.category = category;
				return this;
			}

			public Gcm setInteractiveCategory(String interactiveCategory) {
				this.interactiveCategory = interactiveCategory;
				return this;
			}

			public Gcm setDelayWhileIdle(Boolean delayWhileIdle) {
				this.delayWhileIdle = delayWhileIdle;
				return this;
			}

			public Gcm setTimeToLive(Integer timeToLive) {
				this.timeToLive = timeToLive;
				return this;
			}

			public Gcm setCollapseKey(String collapseKey) {
				this.collapseKey = collapseKey;
				return this;
			}

			public Gcm setPayload(JsonNode payload) {
				this.payload = payload;
				return this;
			}

			public Gcm setSync(Boolean sync) {
				this.sync = sync;
				return this;
			}

			public Gcm setSound(String sound) {
				this.sound = sound;
				return this;
			}

			public Gcm setPriority(GcmBuilder.GCMPriority priority) {
				this.priority = priority;
				return this;
			}

			public Gcm setStyle(GcmStyle style) {
				this.style = style;
				return this;
			}

			public Gcm setVisibility(GcmBuilder.Visibility visibility) {
				this.visibility = visibility;
				return this;
			}

			public Gcm setIcon(String icon) {
				this.icon = icon;
				return this;
			}

			public Gcm setLights(GcmLights lights) {
				this.lights = lights;
				return this;
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

			public ChromeWeb setTitle(String title) {
				this.title = title;
				return this;
			}

			public ChromeWeb setIconUrl(String iconUrl) {
				this.iconUrl = iconUrl;
				return this;
			}

			public ChromeWeb setTimeToLive(Integer timeToLive) {
				this.timeToLive = timeToLive;
				return this;
			}

			public ChromeWeb setPayload(JsonNode payload) {
				this.payload = payload;
				return this;
			}

		}

		public static final class SafariWeb {

			private String title;
			private String action;
			private String[] urlArgs;

			public String getAction() {
				return action;
			}

			public String[] getUrlArgs() {
				return urlArgs;
			}

			public String getTitle() {
				return title;
			}

			public SafariWeb setTitle(String title) {
				this.title = title;
				return this;
			}

			public SafariWeb setUrlArgs(final String[] urlArgs) {
				this.urlArgs = urlArgs;
				return this;
			}

			public SafariWeb setAction(final String action) {
				this.action = action;
				return this;
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

			public FirefoxWeb setTitle(String title) {
				this.title = title;
				return this;
			}

			public FirefoxWeb setIconUrl(String iconUrl) {
				this.iconUrl = iconUrl;
				return this;
			}

			public FirefoxWeb setTimeToLive(Integer timeToLive) {
				this.timeToLive = timeToLive;
				return this;
			}

			public FirefoxWeb setPayload(JsonNode payload) {
				this.payload = payload;
				return this;
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

			public ChromeAppExt setCollapseKey(String collapseKey) {
				this.collapseKey = collapseKey;
				return this;
			}

			public ChromeAppExt setDelayWhileIdle(Boolean delayWhileIdle) {
				this.delayWhileIdle = delayWhileIdle;
				return this;
			}

			public ChromeAppExt setTitle(String title) {
				this.title = title;
				return this;
			}

			public ChromeAppExt setIconUrl(String iconUrl) {
				this.iconUrl = iconUrl;
				return this;
			}

			public ChromeAppExt setTimeToLive(Integer timeToLive) {
				this.timeToLive = timeToLive;
				return this;
			}

			public ChromeAppExt setPayload(JsonNode payload) {
				this.payload = payload;
				return this;
			}

		}

	}
}