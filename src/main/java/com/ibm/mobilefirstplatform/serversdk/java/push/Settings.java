package com.ibm.mobilefirstplatform.serversdk.java.push;

public final class Settings {

	private Apns apns;
	private Gcm gcm;
	private FirefoxWeb firefoxWeb;
	private ChromeWeb chromeWeb;
	private SafariWeb safariWeb;
	private ChromeAppExt chromeAppExt;

	public final Apns getApns() {
		return apns;
	}

	public final Gcm getGcm() {
		return gcm;
	}

	public final FirefoxWeb getFirefoxWeb() {
		return firefoxWeb;
	}

	public final ChromeWeb getChromeWeb() {
		return chromeWeb;
	}

	public final SafariWeb getSafariWeb() {
		return safariWeb;
	}

	public final ChromeAppExt getChromeAppExt() {
		return chromeAppExt;
	}

	private Settings(Builder builder) {

		this.apns = builder.apns;
		this.gcm = builder.gcm;
		this.firefoxWeb = builder.firefoxWeb;
		this.chromeWeb = builder.chromeWeb;
		this.safariWeb = builder.safariWeb;
		this.chromeAppExt = builder.chromeAppExt;
	}

	// Builder for Settings
	public static class Builder {

		private Apns apns;
		private Gcm gcm;
		private FirefoxWeb firefoxWeb;
		private ChromeWeb chromeWeb;
		private SafariWeb safariWeb;
		private ChromeAppExt chromeAppExt;

		public final Builder apns(Apns apns) {
			this.apns = apns;
			return this;
		}

		public final Builder gcm(Gcm gcm) {
			this.gcm = gcm;
			return this;
		}

		public final Builder firefoxWeb(FirefoxWeb firefoxWeb) {
			this.firefoxWeb = firefoxWeb;
			return this;
		}

		public final Builder chromeWeb(ChromeWeb chromeWeb) {
			this.chromeWeb = chromeWeb;
			return this;
		}

		public final Builder safariWeb(SafariWeb safariWeb) {
			this.safariWeb = safariWeb;
			return this;
		}

		public final Builder chromeAppExt(ChromeAppExt chromeAppExt) {
			this.chromeAppExt = chromeAppExt;
			return this;
		}

		public final Settings build() {
			return new Settings(this);
		}

	}

}