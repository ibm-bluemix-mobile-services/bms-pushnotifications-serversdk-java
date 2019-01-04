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
 * The Notification is used to create a new push notification sent using the IBM
 * IBM Cloud Push Notification service. The push notification's message that is
 * passed in the constructor is required. All other parameters are optional. Set
 * them as required.
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
		 *            The object that is set with all message attributes.
		 * 
		 * @return The NotificationBuilder object for calls to be linked.
		 */

		public Builder message(Message message) {
			this.message = message;
			return this;
		}

		/**
		 * Sets Target object to the NotificationBuilder.
		 * 
		 * @param target
		 *            The object that is set with all target attributes.
		 * 
		 * @return The NotificationBuilder object for calls to be linked.
		 */

		public Builder target(Target target) {
			this.target = target;
			return this;
		}

		/**
		 * Sets Settings object to the NotificationBuilder.
		 * 
		 * @param settings
		 *            The object that is set with all settings attributes.
		 * 
		 * @return The NotificationBuilder object for calls to be linked.
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
		 * @return The push notification built as specified, ready to be
		 *         dispatched.
		 */
		public Notification build() {

			if (message != null) {
				Message msg = message;
				if (msg.getAlert() == null) {
					throw new IllegalArgumentException(PushConstants.PushServerSDKExceptions.ALERT_NOT_NULL_EXCEPTION);
				}
			} else {
				throw new IllegalArgumentException(PushConstants.PushServerSDKExceptions.ALERT_NOT_NULL_EXCEPTION);
			}

			return new Notification(this);
		}

	}
}