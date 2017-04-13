package com.ibm.mobilefirstplatform.serversdk.java.push.builders;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class FirefoxWeb {

	private static ObjectMapper mapper = new ObjectMapper();
	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

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
