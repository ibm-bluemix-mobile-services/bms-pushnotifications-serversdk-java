/*
 *     Copyright 2016 IBM Corp.
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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Message;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target;

/**
 * The NotificationBuilder is used to create a new push notification that is
 * going to be sent using the Push Notification service in IBMÂ® Bluemix.
 * 
 * The push notification's message that is passed in the constructor is
 * required. All other parameters are optional. Set them as needed.
 */
public class NotificationBuilder {

	protected JSONObject notification;

	private static ObjectMapper mapper = new ObjectMapper();

	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

	private Message message;
	private Target target;
	private Settings settings;

	// Default constructor
	public NotificationBuilder() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Sets the Message object to the NotificationBuilder.
	 * 
	 * @param message
	 *            object with all message attributes set.
	 * 
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder message(Message message) {
		this.message = message;
		return this;
	}

	/**
	 * Sets Target object to the NotificationBuilder.
	 * 
	 * @param target
	 *            object with all target attributes set.
	 * 
	 * @return the NotificationBuilder object so that calls can be chained.
	 */

	public NotificationBuilder target(Target target) {
		this.target = target;
		return this;
	}

	/**
	 * Sets Settings object to the NotificationBuilder.
	 * 
	 * @param settings
	 *            object with all settings attributes set.
	 * 
	 * @return the NotificationBuilder object so that calls can be chained.
	 */
	public NotificationBuilder settings(Settings settings) {
		this.settings = settings;
		return this;
	}

	/**
	 * API converts object to json format.
	 * 
	 * @param obj
	 *            The object which needs to be serialized as json string.
	 * 
	 * @return Return a JSONOject for the passed object.
	 */
	public static JSONObject generateJSON(Object obj) {
		String jsonString = null;
		try {
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			jsonString = mapper.writeValueAsString(obj);

		} catch (JsonProcessingException exception) {

			logger.log(Level.SEVERE, exception.toString(), exception);
		}

		JSONObject json = jsonString != null ? new JSONObject(jsonString) : new JSONObject();

		return json;
	}

	/**
	 * Build the push notification as configured. The result of this method is
	 * to be passed to
	 * {@link PushNotifications#send(JSONObject, PushNotificationsResponseListener)}
	 * as a parameter.
	 * 
	 * @return the push notification built as specified, ready to be sent
	 */
	public JSONObject build() {

		if (message != null) {
			Message msg = message;
			if (msg.getAlert() == null) {
				throw new IllegalArgumentException(PushConstants.ALERT_NOT_NULL_EXCEPTIOPN);
			}
		} else {
			throw new IllegalArgumentException(PushConstants.ALERT_NOT_NULL_EXCEPTIOPN);
		}

		PushMessageModel model = new PushMessageModel.PushMessageModelBuilder().message(message).target(target)
				.settings(settings).build();

		return generateJSON(model);
	}

}