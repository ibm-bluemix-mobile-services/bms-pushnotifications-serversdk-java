package com.ibm.mobilefirstplatform.serversdk.java.push.builders;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Target {

	private String[] deviceIds = null;
	private String[] userIds = null;
	private String[] platforms = null;
	private String[] tagNames = null;

	public String[] getDeviceIds() {
		return deviceIds;
	}

	public String[] getUserIds() {
		return userIds;
	}

	public String[] getTagNames() {
		return tagNames;
	}

	public String[] getPlatforms() {
		return platforms;
	}

	private Target(TargetBuilder builder) {
		this.deviceIds = builder.deviceIds;
		this.platforms = builder.platforms;
		this.tagNames = builder.tagNames;
		this.userIds = builder.userIds;
	}

	public static class TargetBuilder {

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
		private boolean checkBlankObject = false;

		public TargetBuilder setDeviceIds(final String[] deviceIds) {
			if (deviceIds != null && deviceIds.length > 0) {
				checkBlankObject = true;
			}

			this.deviceIds = deviceIds;
			return this;
		}

		public TargetBuilder setUserIds(final String[] userIds) {
			if (userIds != null && userIds.length > 0) {
				checkBlankObject = true;
			}
			this.userIds = userIds;
			return this;
		}

		public TargetBuilder setTagNames(final String[] tagNames) {
			if (tagNames != null && tagNames.length > 0) {
				checkBlankObject = true;
			}
			this.tagNames = tagNames;
			return this;
		}

		public TargetBuilder setPlatforms(final PushNotificationsPlatform[] platforms) {

			String[] platformArray = null;

			if (platforms != null && platforms.length > 0) {

				checkBlankObject = true;

				platformArray = new String[platforms.length];

				for (int i = 0; i < platforms.length; i++) {
					platformArray[i] = platforms[i].getValue();
				}
			}

			this.platforms = platformArray;
			return this;
		}

		public Target build() {
			if (checkBlankObject) {
				return new Target(this);
			} else {
				return null;
			}
		}

	}

}
