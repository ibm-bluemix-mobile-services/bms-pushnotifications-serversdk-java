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

/**
 * 
 * Modal class for Message attributes which specifies the content of the
 * notification message.
 *
 */
public final class Message {

	private String alert;
	private String url;

	public final String getAlert() {
		return alert;
	}

	public final String getUrl() {
		return url;
	}

	private Message(Builder builder) {
		this.alert = builder.alert;
		this.url = builder.url;

	}

	/**
	 * 
	 * Builder for {@link Message}.
	 *
	 */
	public static class Builder {
		private String alert;
		private String url;

		/**
		 * 
		 * @param alert
		 *            The notification message to be shown to the user.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder alert(final String alert) {
			this.alert = alert;
			return this;
		}

		/**
		 * 
		 * @param url
		 *            An optional URL that can be sent along with the alert.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder url(final String url) {
			this.url = url;
			return this;
		}

		/**
		 * 
		 * @return the {@link Message} object.
		 */
		public Message build() {
			return new Message(this);
		}
	}
}