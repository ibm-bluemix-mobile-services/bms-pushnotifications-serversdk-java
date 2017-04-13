package com.ibm.mobilefirstplatform.serversdk.java.push.builders;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Gcm.GcmBuilder.GcmLights;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Gcm.GcmBuilder.GcmStyle;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Gcm {

	private static ObjectMapper mapper = new ObjectMapper();
	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

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

		private boolean checkBlankObject = false;

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

		public final GcmBuilder setStyle(GcmStyle style) {

			if (style != null) {
				checkBlankObject = true;
			}
			this.style = style;
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

		public final GcmBuilder setLights(GcmLights lights) {

			if (lights != null) {
				checkBlankObject = true;
			}
			this.lights = lights;
			return this;
		}

		public Gcm build() {
			if (checkBlankObject) {
				return new Gcm(this);
			} else {
				return null;
			}

		}

		@JsonInclude(JsonInclude.Include.NON_EMPTY)
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

			public GcmLights setLedArgb(GcmLED ledArgb) {
				this.ledArgb = ledArgb;
				return this;
			}

			public GcmLights setLedOnMs(Integer ledOnMs) {
				this.ledOnMs = ledOnMs;
				return this;
			}

			public GcmLights setLedOffMs(Integer ledOffMs) {
				this.ledOffMs = ledOffMs;
				return this;
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

			public String[] getLines() {
				return lines;
			}

			public GcmStyle setType(GcmStyleTypes type) {
				this.type = type;
				return this;

			}

			public GcmStyle setUrl(String url) {
				this.url = url;
				return this;
			}

			public GcmStyle setTitle(String title) {
				this.title = title;
				return this;
			}

			public GcmStyle setText(String text) {
				this.text = text;
				return this;
			}

			public GcmStyle setLines(String[] lines) {
				this.lines = lines;
				return this;
			}

		}

	}

}
