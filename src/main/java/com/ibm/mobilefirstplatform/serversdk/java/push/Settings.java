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
 * Parent Modal class for all platforms optional settings which specifies the
 * additional properties that can be configured for the notification.
 *
 */
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

	/**
	 * 
	 * Builder for {@link Settings}.
	 *
	 */
	public static class Builder {

		private Apns apns;
		private Gcm gcm;
		private FirefoxWeb firefoxWeb;
		private ChromeWeb chromeWeb;
		private SafariWeb safariWeb;
		private ChromeAppExt chromeAppExt;

		/**
		 * 
		 * @param apns
		 *            Apns object with optional settings.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder apns(Apns apns) {
			this.apns = apns;
			return this;
		}

		/**
		 * 
		 * @param gcm
		 *            Gcm object with optional settings.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder gcm(Gcm gcm) {
			this.gcm = gcm;
			return this;
		}

		/**
		 * 
		 * @param firefoxWeb
		 *            FirefoxWeb object with optional settings.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder firefoxWeb(FirefoxWeb firefoxWeb) {
			this.firefoxWeb = firefoxWeb;
			return this;
		}

		/**
		 * 
		 * @param chromeWeb
		 *            ChromeWeb object with optional settings.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder chromeWeb(ChromeWeb chromeWeb) {
			this.chromeWeb = chromeWeb;
			return this;
		}

		/**
		 * 
		 * @param safariWeb
		 *            SafariWeb object with optional settings.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder safariWeb(SafariWeb safariWeb) {
			this.safariWeb = safariWeb;
			return this;
		}

		/**
		 * 
		 * @param chromeAppExt
		 *            ChromeAppExtension object with optional settings.
		 * @return the Builder object so that calls can be chained.
		 */
		public final Builder chromeAppExt(ChromeAppExt chromeAppExt) {
			this.chromeAppExt = chromeAppExt;
			return this;
		}

		/**
		 * 
		 * @return the {@link Settings} object.
		 */
		public final Settings build() {
			return new Settings(this);
		}

	}

}