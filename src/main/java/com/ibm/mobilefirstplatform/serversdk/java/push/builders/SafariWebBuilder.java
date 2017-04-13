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

import com.ibm.mobilefirstplatform.serversdk.java.push.internal.PushMessageModel.Settings.SafariWeb;

public final class SafariWebBuilder {

	private String title;
	private String action;
	private String[] urlArgs;

	public final SafariWebBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public final SafariWebBuilder setAction(String action) {
		this.action = action;
		return this;
	}

	public final SafariWebBuilder setUrlArgs(String[] urlArgs) {
		this.urlArgs = urlArgs;
		return this;
	}

	public SafariWeb build() {

		SafariWeb safariWeb = new SafariWeb();
		safariWeb.setAction(action).setTitle(title).setUrlArgs(urlArgs);
		return safariWeb;

	}
}
