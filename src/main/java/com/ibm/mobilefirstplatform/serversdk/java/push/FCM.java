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
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.Builder.FCMNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.Builder.FCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.Builder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMLights.Builder.FCMLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMStyle.Builder.FCMStyleTypes;

/**
 * 
 * Modal class for FCM, with settings specific to the Android platform.
 *
 */
public final class FCM {

	public static final Logger logger = Logger.getLogger(FCM.class.getName());

	private Boolean delayWhileIdle;
	private Integer timeToLive;
	private String collapseKey;
	private JsonNode payload;
	private Boolean sync;
	private String sound;
	private String interactiveCategory;
	private FCMPriority priority;
	private FCMStyle style;
	private Visibility visibility;
	private String icon;
	private FCMLights lights;
	private FCMNotificationType type;
	private String androidTitle;
	private String groupId;

	public final Boolean getDelayWhileIdle() {
		return delayWhileIdle;
	}

	public final String getGroupId() {
		return groupId;
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

	public final FCMPriority getPriority() {
		return priority;
	}

	public final FCMStyle getStyle() {
		return style;
	}

	public final Visibility getVisibility() {
		return visibility;
	}

	public final String getIcon() {
		return icon;
	}

	public final FCMLights getLights() {
		return lights;
	}
	
	public final FCMNotificationType getType() {
		return type;
	}

	public final String getAndroidTitle() {
		return androidTitle;
	}

	private FCM(Builder builder) {

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
		this.type = builder.type;
		this.androidTitle = builder.androidTitle;
		this.groupId = builder.groupId;
	}

	/**
	 * 
	 * Builder for {@link FCM}.
	 *
	 */
	public static class Builder {
		
		/**
		 * The available notification types of the FCM notification message.
		 */
		public enum FCMNotificationType {
			DEFAULT, SILENT
		}
		/**
		 * 
		 * Determines the priority of the notification.
		 *
		 */
		public enum FCMPriority {
			DEFAULT, MIN, LOW, HIGH, MAX
		}

		/**
		 * 
		 * Determines visibility of the notification.
		 *
		 */
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
		private FCMPriority priority;
		private FCMStyle style;
		private Visibility visibility;
		private String icon;
		private FCMLights lights;
		private FCMNotificationType type;
		private String androidTitle;
		private String groupId;



		/**
		 * 
		 * @param androidTitle
		 *            androidTitle- The title of Android push 
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder androidTitle(String androidTitle) {
			this.androidTitle = androidTitle;
			return this;
		}

		/**
		 * 
		 * @param groupId 
		 * 			Set this notification to be part of a group of notifications sharing the same key. Grouped notifications may display in a cluster or stack on devices which support such rendering.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder groupId(String groupId) {
			this.groupId = groupId;
			return this;
		}

		/**
		 * 
		 * @param delayWhileIdle
		 *            When this parameter is set to true, it indicates that the
		 *            message should not be sent until the device becomes
		 *            active.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder delayWhileIdle(Boolean delayWhileIdle) {
			this.delayWhileIdle = delayWhileIdle;
			return this;
		}

		/**
		 * 
		 * @param timeToLive
		 *            This parameter specifies the duration (in seconds) for
		 *            which the message should be kept in FCM, if the device is
		 *            offline.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder timeToLive(Integer timeToLive) {
			this.timeToLive = timeToLive;
			return this;
		}

		/**
		 * 
		 * @param collapseKey
		 *            The parameter identifies a group of messages.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder collapseKey(String collapseKey) {
			this.collapseKey = collapseKey;
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
		 * @param sync
		 *            Device group messaging makes it possible for every app
		 *            instance in a group to reflect the latest messaging state.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder sync(Boolean sync) {
			this.sync = sync;
			return this;
		}

		/**
		 * 
		 * @param sound
		 *            The sound file (on device) that will be attempted to play
		 *            when the notification arrives on the device.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder sound(String sound) {
			this.sound = sound;
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
		 * @param priority
		 *            A string value that indicates the priority of this
		 *            notification. Allowed values are 'max', 'high', 'default',
		 *            'low' and 'min'. High/Max priority notifications along
		 *            with 'sound' field may be used for Heads up notification
		 *            in Android 5.0 or higher.sampleval='low' , sound (string,
		 *            optional): The sound file (on device) that will be
		 *            attempted to play when the notification arrives on the
		 *            device.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder priority(FCMPriority priority) {
			this.priority = priority;
			return this;
		}

		/**
		 * 
		 * @param style
		 *            Options to specify for Android expandable notifications.
		 *            The types of expandable notifications are
		 *            picture_notification, bigtext_notification,
		 *            inbox_notification.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder style(FCMStyle style) {
			this.style = style;
			return this;
		}

		/**
		 * 
		 * @param visibility
		 *            private/public - Visibility of this notification, which
		 *            affects how and when the notifications are revealed on a
		 *            secure locked screen.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder visibility(Visibility visibility) {
			this.visibility = visibility;
			return this;
		}

		/**
		 * 
		 * @param icon
		 *            Specify the name of the icon to be displayed for the
		 *            notification. Make sure the icon is already packaged with
		 *            the client application.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder icon(String icon) {
			this.icon = icon;
			return this;
		}

		/**
		 * 
		 * @param lights
		 *            Allows setting the notification LED color on receiving
		 *            push notification.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder lights(FCMLights lights) {
			this.lights = lights;
			return this;
		}
		
		/**
		 * 
		 * @param type
		 *            {'DEFAULT', 'SILENT'}
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder type(FCMNotificationType type) {
			this.type = type;
			return this;
		}

		/**
		 * 
		 * @return the {@link FCM} object.
		 */
		public FCM build() {
			return new FCM(this);
		}

	}

	/**
	 * 
	 * Modal class for FCMLights.
	 *
	 */
	public static final class FCMLights {
		private FCMLED ledArgb;
		private Integer ledOnMs;
		private Integer ledOffMs;

		public FCMLED getLedArgb() {
			return ledArgb;
		}

		public Integer getLedOnMs() {
			return ledOnMs;
		}

		public Integer getLedOffMs() {
			return ledOffMs;
		}

		private FCMLights(Builder builder) {
			this.ledArgb = builder.ledArgb;
			this.ledOnMs = builder.ledOnMs;
			this.ledOffMs = builder.ledOffMs;
		}

		/**
		 * 
		 * Builder for {@link FCMLights}
		 *
		 */
		public static class Builder {
			/**
			 * Determines the LED value in the notifications.
			 */
			public enum FCMLED {

				BLACK, DARKGRAY, GRAY, LIGHTGRAY, WHITE, RED, GREEN, BLUE, YELLOW, CYAN, MAGENTA, TRANSPARENT
			}

			private FCMLED ledArgb;
			private Integer ledOnMs;
			private Integer ledOffMs;

			/**
			 * 
			 * @param ledArgb
			 *            The color of the LED. The hardware will do its best
			 *            approximation.
			 * @return The Builder object for calls to be linked.
			 */
			public Builder ledArgb(FCMLED ledArgb) {
				this.ledArgb = ledArgb;
				return this;
			}

			/**
			 * 
			 * @param ledOnMs
			 *            The number of milliseconds for the LED to be on while
			 *            it's flashing. The hardware will do its best
			 *            approximation.
			 * @return The Builder object for calls to be linked.
			 */
			public Builder ledOnMs(Integer ledOnMs) {
				this.ledOnMs = ledOnMs;
				return this;
			}

			/**
			 * 
			 * @param ledOffMs
			 *            The number of milliseconds for the LED to be off while
			 *            it's flashing. The hardware will do its best
			 *            approximation.
			 * @return The Builder object for calls to be linked.
			 */
			public Builder ledOffMs(Integer ledOffMs) {
				this.ledOffMs = ledOffMs;
				return this;
			}

			/**
			 * 
			 * @return the {@link FCMLights} object.
			 */
			public FCMLights build() {
				return new FCMLights(this);
			}
		}

	}

	/**
	 * 
	 * Modal class for FCMStyle.
	 *
	 */
	public static final class FCMStyle {

		private FCMStyleTypes type;
		private String url;
		private String title;
		private String text;
		private String[] lines;

		public final FCMStyleTypes getType() {
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

		private FCMStyle(Builder builder) {
			this.type = builder.type;
			this.url = builder.url;
			this.title = builder.title;
			this.text = builder.text;
			this.lines = builder.lines;
		}

		/**
		 * 
		 * Builder for {@link FCMStyle}.
		 *
		 */
		public static class Builder {
			/**
			 * The available style type of the {@link FCM} notification message.
			 */
			public enum FCMStyleTypes {

				BIGTEXT_NOTIFICATION, INBOX_NOTIFICATION, PICTURE_NOTIFICATION
			}

			private FCMStyleTypes type;
			private String url;
			private String title;
			private String text;
			private String[] lines;

			/**
			 * 
			 * @param type
			 *            Specifies the type of expandable notifications. The
			 *            possible values are bigtext_notification,
			 *            picture_notification, inbox_notification.
			 * @return The Builder object for calls to be linked.
			 */
			public final Builder type(FCMStyleTypes type) {
				this.type = type;
				return this;
			}

			/**
			 * 
			 * @param url
			 *            An URL from which the picture has to be obtained for
			 *            the notification. Must be specified for
			 *            picture_notification.
			 * @return The Builder object for calls to be linked.
			 */
			public final Builder url(String url) {
				this.url = url;
				return this;
			}

			/**
			 * 
			 * @param title
			 *            Specifies the title of the notification. The title is
			 *            displayed when the notification is expanded. Title
			 *            must be specified for all three expandable
			 *            notification.
			 * @return The Builder object for calls to be linked.
			 */
			public final Builder title(String title) {
				this.title = title;
				return this;
			}

			/**
			 * 
			 * @param text
			 *            The big text that needs to be displayed on expanding a
			 *            bigtext_notification. Must be specified for
			 *            bigtext_notification.
			 * @return The Builder object for calls to be linked.
			 */
			public final Builder text(String text) {
				this.text = text;
				return this;
			}

			/**
			 * 
			 * @param lines
			 *            An array of strings that is to be displayed in inbox
			 *            style for inbox_notification. Must be specified for
			 *            inbox_notification.
			 * @return The Builder object for calls to be linked.
			 */
			public final Builder lines(String[] lines) {
				this.lines = lines;
				return this;
			}

			/**
			 * 
			 * @return the {@link FCMStyle} object.
			 */
			public FCMStyle build() {
				return new FCMStyle(this);

			}

		}

	}
}
