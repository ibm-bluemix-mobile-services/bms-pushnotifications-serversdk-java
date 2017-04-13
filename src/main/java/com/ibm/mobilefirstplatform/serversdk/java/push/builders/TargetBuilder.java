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

import com.ibm.mobilefirstplatform.serversdk.java.push.internal.PushMessageModel.Target;

public final class TargetBuilder {

		public enum PushNotificationsPlatform {
			APPLE("A"), GOOGLE("G"), WEBCHROME("WEB_CHROME"), WEBFIREFOX("WEB_FIREFOX"), WEBSAFARI(
					"WEB_SAFARI"), APPEXTCHROME("APPEXT_CHROME");

			private String platformCode;

			PushNotificationsPlatform(String code) {
				this.platformCode = code;
			}

			public String getValue() {
				return platformCode;
			}
		}

		private String[] deviceIds = null;
		private String[] userIds = null;
		private String[] platforms = null;
		private String[] tagNames = null;

		public TargetBuilder setDeviceIds(final String[] deviceIds) {
			this.deviceIds = deviceIds;
			return this;
		}

		public TargetBuilder setUserIds(final String[] userIds) {
			this.userIds = userIds;
			return this;
		}

		public TargetBuilder setTagNames(final String[] tagNames) {
			this.tagNames = tagNames;
			return this;
		}

		public TargetBuilder setPlatforms(final PushNotificationsPlatform[] platforms) {

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

		public Target build() {
			
			Target target = new Target();
			target.setDeviceIds(deviceIds).setPlatforms(platforms).setTagNames(tagNames).setUserIds(userIds);
			return target;
		}

	}

