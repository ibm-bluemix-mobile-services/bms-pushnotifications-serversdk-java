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
 * Modal class for SafariWeb, with settings specific to the SafariWeb platform.
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

	/**
	 * 
	 * Builder for {@link SafariWeb}.
	 *
	 */
	public static class Builder {

		private String title;
		private String action;
		private String[] urlArgs;

		/**
		 * 
		 * @param title
		 *            Specifies the title to be set for the Safari Push
		 *            Notifications.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder title(String title) {
			this.title = title;
			return this;
		}

		/**
		 * 
		 * @param action
		 *            The label of the action button.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder action(String action) {
			this.action = action;
			return this;
		}

		/**
		 * 
		 * @param urlArgs
		 *            The URL arguments that need to be used with this
		 *            notification. This has to provided in the form of a JSON
		 *            Array.
		 * @return The Builder object for calls to be linked.
		 */
		public final Builder urlArgs(String[] urlArgs) {
			this.urlArgs = urlArgs;
			return this;
		}

		/**
		 * 
		 * @return the {@link SafariWeb} object.
		 */
		public SafariWeb build() {
			return new SafariWeb(this);
		}
	}
}