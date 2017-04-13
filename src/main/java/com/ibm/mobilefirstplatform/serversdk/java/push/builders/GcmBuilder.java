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
package com.ibm.mobilefirstplatform.serversdk.java.push.builders;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.internal.PushMessageModel.Settings.Gcm;

public final class GcmBuilder {

	private static ObjectMapper mapper = new ObjectMapper();
	public static final Logger logger = Logger.getLogger(ChromeAppExtBuilder.class.getName());

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

	public final GcmBuilder setDelayWhileIdle(Boolean delayWhileIdle) {
		this.delayWhileIdle = delayWhileIdle;
		return this;
	}

	public final GcmBuilder setTimeToLive(Integer timeToLive) {
		this.timeToLive = timeToLive;
		return this;
	}

	public final GcmBuilder setCollapseKey(String collapseKey) {
		this.collapseKey = collapseKey;
		return this;
	}

	public final GcmBuilder setPayload(JSONObject payload) {
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

	public final GcmBuilder setSync(Boolean sync) {
		this.sync = sync;
		return this;
	}

	public final GcmBuilder setSound(String sound) {
		this.sound = sound;
		return this;
	}

	public final GcmBuilder setInteractiveCategory(String interactiveCategory) {
		this.interactiveCategory = interactiveCategory;
		return this;
	}

	public final GcmBuilder setPriority(GCMPriority priority) {
		this.priority = priority;
		return this;
	}

	public final GcmBuilder setStyle(GcmStyle style) {
		this.style = style;
		return this;
	}

	public final GcmBuilder setVisibility(Visibility visibility) {
		this.visibility = visibility;
		return this;
	}

	public final GcmBuilder setIcon(String icon) {
		this.icon = icon;
		return this;
	}

	public final GcmBuilder setLights(GcmLights lights) {
		this.lights = lights;
		return this;
	}

	public Gcm build() {

		Gcm gcm = new Gcm();
		gcm.setCollapseKey(collapseKey).setDelayWhileIdle(delayWhileIdle).setIcon(icon)
				.setInteractiveCategory(interactiveCategory).setLights(lights).setPayload(payload).setPriority(priority)
				.setSound(sound).setStyle(style).setSync(sync).setTimeToLive(timeToLive).setVisibility(visibility);
		return gcm;

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
