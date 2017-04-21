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
 * Modal class for SafariWeb optional settings.
 *
 */
public final class SafariWeb {

	private String title;
	private String action;
	private String[] urlArgs;

	public final String getTitle() {
		return title;
	}

	public final String getAction() {
		return action;
	}

	public final String[] getUrlArgs() {
		return urlArgs;
	}

	private SafariWeb(Builder builder) {

		this.title = builder.title;
		this.action = builder.action;
		this.urlArgs = builder.urlArgs;
	}

	public static class Builder {

		private String title;
		private String action;
		private String[] urlArgs;

		public final Builder title(String title) {
			this.title = title;
			return this;
		}

		public final Builder action(String action) {
			this.action = action;
			return this;
		}

		public final Builder urlArgs(String[] urlArgs) {
			this.urlArgs = urlArgs;
			return this;
		}

		public SafariWeb build() {
			return new SafariWeb(this);
		}
	}
}