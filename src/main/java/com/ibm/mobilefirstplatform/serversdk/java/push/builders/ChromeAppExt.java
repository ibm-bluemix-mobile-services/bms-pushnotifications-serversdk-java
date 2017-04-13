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
public final class ChromeAppExt {

	private static ObjectMapper mapper = new ObjectMapper();
	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

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
