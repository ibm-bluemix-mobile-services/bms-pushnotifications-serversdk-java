package com.ibm.mobilefirstplatform.serversdk.java.push;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ChromeWeb {

	public static final Logger logger = Logger.getLogger(ChromeWeb.class.getName());

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

	private ChromeWeb(Builder builder) {

		this.title = builder.title;
		this.iconUrl = builder.iconUrl;
		this.timeToLive = builder.timeToLive;
		this.payload = builder.payload;
	}

	public static class Builder {

		private String title;
		private String iconUrl;
		private Integer timeToLive;
		private JsonNode payload;

		public final Builder title(String title) {
			this.title = title;
			return this;
		}

		public final Builder iconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
			return this;
		}

		public final Builder timeToLive(Integer timeToLive) {
			this.timeToLive = timeToLive;
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

		public ChromeWeb build() {
			return new ChromeWeb(this);
		}
	}

}