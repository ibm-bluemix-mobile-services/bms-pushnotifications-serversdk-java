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

import com.ibm.mobilefirstplatform.serversdk.java.push.internal.PushMessageModel.Settings;

public final class SettingsBuilder {

	private ApnsBuilder apnsBuilder;
	private GcmBuilder gcmBuilder;
	private FirefoxWebBuilder firefoxWebBuilder;
	private ChromeWebBuilder chromeWebBuilder;
	private SafariWebBuilder safariWebBuilder;
	private ChromeAppExtBuilder chromeAppExtBuilder;

	public SettingsBuilder setApnsBuilder(ApnsBuilder apnsBuilder) {
		this.apnsBuilder = apnsBuilder;
		return this;
	}

	public SettingsBuilder setGcmBuilder(GcmBuilder gcmBuilder) {
		this.gcmBuilder = gcmBuilder;
		return this;
	}

	public SettingsBuilder setFirefoxWebBuilder(FirefoxWebBuilder firefoxWebBuilder) {
		this.firefoxWebBuilder = firefoxWebBuilder;
		return this;
	}

	public SettingsBuilder setChromeWebBuilder(ChromeWebBuilder chromeWebBuilder) {
		this.chromeWebBuilder = chromeWebBuilder;
		return this;
	}

	public SettingsBuilder setSafariWebBuilder(SafariWebBuilder safariWebBuilder) {
		this.safariWebBuilder = safariWebBuilder;
		return this;
	}

	public SettingsBuilder setChromeAppExtBuilder(ChromeAppExtBuilder chromeAppExtBuilder) {
		this.chromeAppExtBuilder = chromeAppExtBuilder;
		return this;
	}

	public Settings build() {

		Settings settings = new Settings();
		settings.setApns(apnsBuilder != null ? apnsBuilder.build() : null)
				.setChromeAppExt(chromeAppExtBuilder != null ? chromeAppExtBuilder.build() : null)
				.setChromeWeb(chromeWebBuilder != null ? chromeWebBuilder.build() : null)
				.setFirefoxWeb(firefoxWebBuilder != null ? firefoxWebBuilder.build() : null)
				.setSafariWeb(safariWebBuilder != null ? safariWebBuilder.build() : null)
				.setGcm(gcmBuilder != null ? gcmBuilder.build() : null);

		return settings;
	}

}
