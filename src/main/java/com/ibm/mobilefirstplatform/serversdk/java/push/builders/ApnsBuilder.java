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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.internal.PushMessageModel.Settings.Apns;

public final class ApnsBuilder {

	public static final Logger logger = Logger.getLogger(ApnsBuilder.class.getName());

	public enum APNSNotificationType {
		DEFAULT, MIXED, SILENT
	}

	private Integer badge;
	private String sound;
	private String iosActionKey;
	private JsonNode payload;
	private String category;
	private String interactiveCategory;
	private APNSNotificationType type;
	private String titleLocKey;
	private String locKey;
	private String launchImage;
	private String[] titleLocArgs;
	private String[] locArgs;
	private String subtitle;
	private String title;
	private String attachmentUrl;

	public final ApnsBuilder setBadge(Integer badge) {
		this.badge = badge;
		return this;
	}

	public final ApnsBuilder setSound(String sound) {
		this.sound = sound;
		return this;
	}

	public final ApnsBuilder setIosActionKey(String iosActionKey) {
		this.iosActionKey = iosActionKey;
		return this;
	}

	public final ApnsBuilder setPayload(JSONObject payload) {
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
	public final ApnsBuilder setCategory(String category) {
		this.category = category;
		return this;
	}
	
	public final ApnsBuilder setInteractiveCategory(String interactiveCategory) {
		this.interactiveCategory = interactiveCategory;
		return this;
	}

	public final ApnsBuilder setType(APNSNotificationType type) {
		this.type = type;
		return this;
	}

	public final ApnsBuilder setTitleLocKey(String titleLocKey) {
		this.titleLocKey = titleLocKey;
		return this;
	}

	public final ApnsBuilder setLocKey(String locKey) {
		this.locKey = locKey;
		return this;
	}

	public final ApnsBuilder setLaunchImage(String launchImage) {
		this.launchImage = launchImage;
		return this;
	}

	public final ApnsBuilder setTitleLocArgs(String[] titleLocArgs) {
		this.titleLocArgs = titleLocArgs;
		return this;
	}

	public final ApnsBuilder setLocArgs(String[] locArgs) {
		this.locArgs = locArgs;
		return this;
	}

	public final ApnsBuilder setSubtitle(String subtitle) {
		this.subtitle = subtitle;
		return this;
	}

	public final ApnsBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public final ApnsBuilder setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
		return this;
	}

	public Apns build() {
		Apns apns = new Apns();
		apns.setBadge(badge).setAttachmentUrl(attachmentUrl).setInteractiveCategory(interactiveCategory)
				.setIosActionKey(iosActionKey).setLaunchImage(launchImage).setLocArgs(locArgs).setLocKey(locKey)
				.setPayload(payload).setSound(sound).setSubtitle(subtitle).setTitle(title)
				.setTitleLocArgs(titleLocArgs).setTitleLocKey(titleLocKey).setType(type).setCategory(category);
		return apns;
	}
}
