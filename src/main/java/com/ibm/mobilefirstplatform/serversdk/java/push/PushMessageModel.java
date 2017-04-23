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
 * Parent modal class for notification message.
 *
 */
public final class PushMessageModel {

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

	private PushMessageModel(Builder builder) {
		this.message = builder.message;
		this.settings = builder.settings;
		this.target = builder.target;
	}

	/**
	 * 
	 * Builder for {@link PushMessageModel}.
	 *
	 */
	public static class Builder {
		private Message message;
		private Target target;
		private Settings settings;

		/**
		 * 
		 * @param message
		 *            Message object with attributes alert and url.
		 * @return the Builder object so that calls can be chained.
		 * 
		 */
		public final Builder message(final Message message) {
			this.message = message;
			return this;
		}

		/**
		 * 
		 * @param target
		 *            Target object with attributes deviceIds, userIds,
		 *            platforms and tagNames.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder target(final Target target) {
			this.target = target;
			return this;
		}

		/**
		 * 
		 * @param settings
		 *            Settings object with platforms settings like apns, gcm,
		 *            safari etc.
		 * @return the Builder object so that calls can be chained.
		 */

		public final Builder settings(final Settings settings) {
			this.settings = settings;
			return this;
		}
		/**
		 * 
		 * @return the {@link PushMessageModel} object.
		 */
		public PushMessageModel build() {
			return new PushMessageModel(this);
		}

	}
}
