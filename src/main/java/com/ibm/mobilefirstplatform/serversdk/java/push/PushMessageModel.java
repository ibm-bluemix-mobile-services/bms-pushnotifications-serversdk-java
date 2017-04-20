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

public final class PushMessageModel {

	private Message message;
	private Target target;
	private Settings settings;

	public final Message getMessage() {
		return message;
	}

	public final Target getTarget() {
		return target;
	}

	public final Settings getSettings() {
		return settings;
	}

	private PushMessageModel(Builder builder) {
		this.message = builder.message;
		this.settings = builder.settings;
		this.target = builder.target;
	}

	public static class Builder {
		private Message message;
		private Target target;
		private Settings settings;

		public final Builder message(final Message message) {
			this.message = message;
			return this;
		}

		public final Builder target(final Target target) {
			this.target = target;
			return this;
		}

		public final Builder settings(final Settings settings) {
			this.settings = settings;
			return this;
		}

		public PushMessageModel build() {
			return new PushMessageModel(this);
		}

	}
}
