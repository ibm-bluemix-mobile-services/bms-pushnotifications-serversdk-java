package com.ibm.mobilefirstplatform.serversdk.java.push.builders;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Apns.ApnsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.ChromeAppExt.ChromeAppExtBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.ChromeWeb.ChromeWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.FirefoxWeb.FirefoxWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Gcm.GcmBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.SafariWeb.SafariWebBuilder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Settings {

	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

	private Apns apns;
	private Gcm gcm;
	private FirefoxWeb firefoxWeb;
	private ChromeWeb chromeWeb;
	private SafariWeb safariWeb;
	private ChromeAppExt chromeAppExt;

	public Apns getApns() {
		return apns;
	}

	public Gcm getGcm() {
		return gcm;
	}

	public FirefoxWeb getFirefoxWeb() {
		return firefoxWeb;
	}

	public ChromeWeb getChromeWeb() {
		return chromeWeb;
	}

	public SafariWeb getSafariWeb() {
		return safariWeb;
	}

	public ChromeAppExt getChromeAppExt() {
		return chromeAppExt;
	}

	private Settings(SettingsBuilder builder) {

		this.apns = builder.apnsBuilder != null ? builder.apnsBuilder.build() : null;
		this.gcm = builder.gcmBuilder != null ? builder.gcmBuilder.build() : null;
		this.firefoxWeb = builder.firefoxWebBuilder != null ? builder.firefoxWebBuilder.build() : null;
		this.chromeWeb = builder.chromeWebBuilder != null ? builder.chromeWebBuilder.build() : null;
		this.safariWeb = builder.safariWebBuilder != null ? builder.safariWebBuilder.build() : null;
		this.chromeAppExt = builder.chromeAppExtBuilder != null ? builder.chromeAppExtBuilder.build() : null;

	}

	// Builder for Settings
	public static class SettingsBuilder {

		private ApnsBuilder apnsBuilder;
		private GcmBuilder gcmBuilder;
		private FirefoxWebBuilder firefoxWebBuilder;
		private ChromeWebBuilder chromeWebBuilder;
		private SafariWebBuilder safariWebBuilder;
		private ChromeAppExtBuilder chromeAppExtBuilder;
		private boolean checkBlankObject = false;

		public SettingsBuilder setApnsBuilder(ApnsBuilder apnsBuilder) {

			if (apnsBuilder != null && apnsBuilder.build() != null) {
				checkBlankObject = true;
			}
			this.apnsBuilder = apnsBuilder;
			return this;
		}

		public SettingsBuilder setGcmBuilder(GcmBuilder gcmBuilder) {
			if (gcmBuilder != null && gcmBuilder.build() != null) {
				checkBlankObject = true;
			}

			this.gcmBuilder = gcmBuilder;
			return this;
		}

		public SettingsBuilder setFirefoxWebBuilder(FirefoxWebBuilder firefoxWebBuilder) {

			if (firefoxWebBuilder != null && firefoxWebBuilder.build() != null) {
				checkBlankObject = true;
			}

			this.firefoxWebBuilder = firefoxWebBuilder;
			return this;
		}

		public SettingsBuilder setChromeWebBuilder(ChromeWebBuilder chromeWebBuilder) {

			if (chromeWebBuilder != null && chromeWebBuilder.build() != null) {
				checkBlankObject = true;
			}
			this.chromeWebBuilder = chromeWebBuilder;
			return this;
		}

		public SettingsBuilder setSafariWebBuilder(SafariWebBuilder safariWebBuilder) {
			if (safariWebBuilder != null && safariWebBuilder.build() != null) {
				checkBlankObject = true;
			}
			this.safariWebBuilder = safariWebBuilder;
			return this;
		}

		public SettingsBuilder setChromeAppExtBuilder(ChromeAppExtBuilder chromeAppExtBuilder) {
			if (chromeAppExtBuilder != null && chromeAppExtBuilder.build() != null) {
				checkBlankObject = true;
			}
			this.chromeAppExtBuilder = chromeAppExtBuilder;
			return this;
		}

		public Settings build() {
			if (checkBlankObject) {
				return new Settings(this);
			} else {
				return null;
			}
		}

	}

}