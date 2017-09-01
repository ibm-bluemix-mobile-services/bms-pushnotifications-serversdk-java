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
import com.ibm.mobilefirstplatform.serversdk.java.push.Gcm.Builder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.Gcm.Builder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.Gcm.GcmLights.Builder.GcmLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.Gcm.GcmStyle.Builder.GcmStyleTypes;

/**
 * 
 * Modal class for Gcm which specifies the settings specific to the Android platform.
 *
 */
public final class Gcm {

	public static final Logger logger = Logger.getLogger(Gcm.class.getName());

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

	/**
	 * 
	 * Builder for {@link Gcm}.
	 *
	 */
	public static class Builder {
		/**
		 * 
		 * Determines the priority of the notification.
		 *
		 */
		public enum GCMPriority {
			DEFAULT, MIN, LOW, HIGH, MAX
		}

		/**
		 * 
		 * Determines the visibility of the notification.
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
		private GCMPriority priority;
		private GcmStyle style;
		private Visibility visibility;
		private String icon;
		private GcmLights lights;

		/**
		 * 
		 * @param delayWhileIdle
		 *            When this parameter is set to true, it indicates that the
		 *            message should not be sent until the device becomes
		 *            active.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder delayWhileIdle(Boolean delayWhileIdle) {
			this.delayWhileIdle = delayWhileIdle;
			return this;
		}

		/**
		 * 
		 * @param timeToLive
		 *            This parameter specifies how long (in seconds) the message
		 *            should be kept in GCM storage if the device is offline.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder timeToLive(Integer timeToLive) {
			this.timeToLive = timeToLive;
			return this;
		}

		/**
		 * 
		 * @param collapseKey
		 *            The parameter identifies a group of messages.
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
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
			}
			catch (IOException e) {
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
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder priority(GCMPriority priority) {
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
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder style(GcmStyle style) {
			this.style = style;
			return this;
		}

		/**
		 * 
		 * @param visibility
		 *            private/public - Visibility of this notification, which
		 *            affects how and when the notifications are revealed on a
		 *            secure locked screen.
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
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
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder lights(GcmLights lights) {
			this.lights = lights;
			return this;
		}

		/**
		 * 
		 * @return the {@link Gcm} object.
		 */
		public Gcm build() {
			return new Gcm(this);
		}

	}

	/**
	 * 
	 * Modal class for GcmLights.
	 *
	 */
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

		/**
		 * 
		 * Builder for {@link GcmLights}
		 *
		 */
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

			/**
			 * 
			 * @param ledArgb
			 *            The color of the led. The hardware will do its best
			 *            approximation.
			 * @return the Builder object so that calls can be chained.
			 */
			public Builder ledArgb(GcmLED ledArgb) {
				this.ledArgb = ledArgb;
				return this;
			}

			/**
			 * 
			 * @param ledOnMs
			 *            The number of milliseconds for the LED to be on while
			 *            it's flashing. The hardware will do its best
			 *            approximation.
			 * @return the Builder object so that calls can be chained.
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
			 * @return the Builder object so that calls can be chained.
			 */
			public Builder ledOffMs(Integer ledOffMs) {
				this.ledOffMs = ledOffMs;
				return this;
			}

			/**
			 * 
			 * @return the {@link GcmLights} object.
			 */
			public GcmLights build() {
				return new GcmLights(this);
			}
		}

	}

	/**
	 * 
	 * Modal class for GcmStyle.
	 *
	 */
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

		/**
		 * 
		 * Builder for {@link GcmStyle}.
		 *
		 */
		public static class Builder {
			/**
			 * The available style type of the {@link Gcm} notification message.
			 */
			public enum GcmStyleTypes {

				BIGTEXT_NOTIFICATION, INBOX_NOTIFICATION, PICTURE_NOTIFICATION
			}

			private GcmStyleTypes type;
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
			 * @return the Builder object so that calls can be chained.
			 */
			public final Builder type(GcmStyleTypes type) {
				this.type = type;
				return this;
			}

			/**
			 * 
			 * @param url
			 *            An URL from which the picture has to be obtained for
			 *            the notification. Must be specified for
			 *            picture_notification.
			 * @return the Builder object so that calls can be chained.
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
			 * @return the Builder object so that calls can be chained.
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
			 * @return the Builder object so that calls can be chained.
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
			 * @return the Builder object so that calls can be chained.
			 */
			public final Builder lines(String[] lines) {
				this.lines = lines;
				return this;
			}

			/**
			 * 
			 * @return the {@link GcmStyle} object.
			 */
			public GcmStyle build() {
				return new GcmStyle(this);

			}

		}

	}
}
