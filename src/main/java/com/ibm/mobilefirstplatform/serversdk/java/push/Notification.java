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

/**
 * The Notification is used to create a new push notification that is going to
 * be sent using the Push Notification service in IBMÂ® Bluemix.
 * 
 * The push notification's message that is passed in the constructor is
 * required. All other parameters are optional. Set them as needed.
 */
public class Notification {

	private Message message;
	private Target target;
	private Settings settings;

	public final Message getMessage() {
		return message;
	}

	public final Target getTarget() {
		return target;
	}

	public final Settings getSettings() {
		return settings;
	}

	private Notification(Builder builder) {
		this.message = builder.message;
		this.target = builder.target;
		this.settings = builder.settings;
	}

	public static class Builder {

		private Message message;
		private Target target;
		private Settings settings;

		/**
		 * Sets the Message object to the NotificationBuilder.
		 * 
		 * @param message
		 *            object with all message attributes set.
		 * 
		 * @return the NotificationBuilder object so that calls can be chained.
		 */

		public Builder message(Message message) {
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

		public Builder target(Target target) {
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
		public Builder settings(Settings settings) {
			this.settings = settings;
			return this;
		}

		/**
		 * Build the push notification as configured. The result of this method
		 * is to be passed to
		 * {@link PushNotifications#send(Notification, PushNotificationsResponseListener)}
		 * as a parameter.
		 * 
		 * @return the push notification built as specified, ready to be sent
		 */
		public Notification build() {

			if (message != null) {
				Message msg = message;
				if (msg.getAlert() == null) {
					throw new IllegalArgumentException(PushConstants.ALERT_NOT_NULL_EXCEPTIOPN);
				}
			} else {
				throw new IllegalArgumentException(PushConstants.ALERT_NOT_NULL_EXCEPTIOPN);
			}

			return new Notification(this);
		}

	}
}