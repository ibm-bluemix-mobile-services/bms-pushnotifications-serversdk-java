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

/**
 * 
 * Modal class for FirefoxWeb, with settings specific to the FirefoxWeb browser.
 *
 */
public final class FirefoxWeb {

	public static final Logger logger = Logger.getLogger(FirefoxWeb.class.getName());

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

	private FirefoxWeb(Builder builder) {

		this.title = builder.title;
		this.iconUrl = builder.iconUrl;
		this.timeToLive = builder.timeToLive;
		this.payload = builder.payload;
	}

	/**
	 * 
	 * Builder for {@link FirefoxWeb}.
	 *
	 */
	public static class Builder {

		private String title;
		private String iconUrl;
		private Integer timeToLive;
		private JsonNode payload;

		/**
		 * 
		 * @param title
		 *            Specifies the title to be set for the WebPush
		 *            Notification.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder title(String title) {
			this.title = title;
			return this;
		}

		/**
		 * 
		 * @param iconUrl
		 *            The URL of the icon to be set for the WebPush
		 *            Notification.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder iconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
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
		 * @return the {@link FirefoxWeb} object.
		 */
		public FirefoxWeb build() {
			return new FirefoxWeb(this);

		}
	}

}