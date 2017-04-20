package com.ibm.mobilefirstplatform.serversdk.java.push;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.Gcm.Builder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.Gcm.Builder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.Gcm.GcmLights.Builder.GcmLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.Gcm.GcmStyle.Builder.GcmStyleTypes;


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

		private GcmLights(GcmLights.Builder builder) {
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

		private GcmStyle(GcmStyle.Builder builder) {
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

			public final GcmStyle.Builder type(GcmStyleTypes type) {
				this.type = type;
				return this;
			}

			public final GcmStyle.Builder url(String url) {
				this.url = url;
				return this;
			}

			public final GcmStyle.Builder title(String title) {
				this.title = title;
				return this;
			}

			public final GcmStyle.Builder text(String text) {
				this.text = text;
				return this;
			}

			public final GcmStyle.Builder lines(String[] lines) {
				this.lines = lines;
				return this;
			}

			public GcmStyle build() {
				return new GcmStyle(this);

			}

		}

	}
}


