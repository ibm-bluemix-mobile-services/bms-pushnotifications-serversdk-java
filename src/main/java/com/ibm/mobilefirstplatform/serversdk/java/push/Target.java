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
 * Modal class for notification target which specifies the recipients of the
 * notification.
 *
 */
public final class Target {

	private String[] deviceIds = null;
	private String[] userIds = null;
	private String[] platforms = null;
	private String[] tagNames = null;

	public final String[] getDeviceIds() {
		return deviceIds;
	}

	public final String[] getUserIds() {
		return userIds;
	}

	public final String[] getPlatforms() {
		return platforms;
	}

	public final String[] getTagNames() {
		return tagNames;
	}

	private Target(Builder builder) {
		this.deviceIds = builder.deviceIds;
		this.platforms = builder.platforms;
		this.tagNames = builder.tagNames;
		this.userIds = builder.userIds;
	}

	/**
	 * 
	 * Builder for {@link Target}.
	 *
	 */
	public static class Builder {
		/**
		 * 
		 * Determines platforms for notification.
		 *
		 */
		public enum PushNotificationsPlatform {
			APPLE("A"), GOOGLE("G"), WEBCHROME("WEB_CHROME"), WEBFIREFOX("WEB_FIREFOX"), WEBSAFARI(
					"WEB_SAFARI"), APPEXTCHROME("APPEXT_CHROME");

			private final String platformCode;

			PushNotificationsPlatform(String code) {
				this.platformCode = code;
			}

			public final String getValue() {
				return platformCode;
			}
		}

		private String[] deviceIds = null;
		private String[] userIds = null;
		private String[] platforms = null;
		private String[] tagNames = null;

		/**
		 * 
		 * @param deviceIds
		 *            Send notification to the list of specified devices.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder deviceIds(final String[] deviceIds) {
			this.deviceIds = deviceIds;
			return this;
		}

		/**
		 * 
		 * @param userIds
		 *            Send notification to the specified userIds.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder userIds(final String[] userIds) {
			this.userIds = userIds;
			return this;
		}

		/**
		 * 
		 * @param tagNames
		 *            Send notification to the devices that have subscribed to
		 *            any of these tags.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder tagNames(final String[] tagNames) {
			this.tagNames = tagNames;
			return this;
		}

		/**
		 * 
		 * @param platforms
		 *            Send notification to the devices of the specified
		 *            platforms. 'A' for apple (iOS) devices, 'G' for google
		 *            (Android) devices, 'WEB_CHROME' for Chrome Web Browsers,
		 *            'WEB_FIREFOX' for Firefox Web Browsers, 'WEB_SAFARI' for
		 *            Safari Push Notifications and 'APPEXT_CHROME' for Chrome
		 *            App Extension.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder platforms(final PushNotificationsPlatform[] platforms) {

			String[] platformArray = null;

			if (platforms != null && platforms.length > 0) {

				platformArray = new String[platforms.length];

				for (int i = 0; i < platforms.length; i++) {
					platformArray[i] = platforms[i].getValue();
				}
			}

			this.platforms = platformArray;
			return this;
		}

		/**
		 * 
		 * @return the {@link Target} object.
		 */
		public Target build() {
			return new Target(this);
		}

	}
}