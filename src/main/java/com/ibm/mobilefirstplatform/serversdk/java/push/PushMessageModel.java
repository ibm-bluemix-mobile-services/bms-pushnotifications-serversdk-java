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

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Message;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Message.MessageBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Settings;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Settings.SettingsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Target;
import com.ibm.mobilefirstplatform.serversdk.java.push.builders.Target.TargetBuilder;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class PushMessageModel {

	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

	private Message message;
	private Target target;
	private Settings settings;

	private MessageBuilder messageBuilder;
	private TargetBuilder targetBuilder;
	private SettingsBuilder settingsBuilder;

	public Message getMessage() {
		return message;
	}

	public Target getTarget() {
		return target;
	}

	public Settings getSettings() {
		return settings;
	}

	public TargetBuilder getTargetBuilder() {
		return targetBuilder;
	}

	public SettingsBuilder getSettingsBuilder() {
		return settingsBuilder;
	}

	public MessageBuilder getMessageBuilder() {
		return messageBuilder;
	}

	private PushMessageModel(PushMessageModelBuilder builder) {
		this.message = builder.message;
		this.settings = builder.settings;
		this.target = builder.target;
		this.settingsBuilder = builder.settingsBuilder;
		this.targetBuilder = builder.targetBuilder;
		this.messageBuilder = builder.messageBuilder;
	}

	public static class PushMessageModelBuilder {
		private Message message;
		private Target target;
		private Settings settings;
		private MessageBuilder messageBuilder;
		private TargetBuilder targetBuilder;
		private SettingsBuilder settingsBuilder;

		public PushMessageModelBuilder setMessage(final Message message) {
			this.message = message;
			return this;
		}

		public PushMessageModelBuilder setTarget(final Target target) {
			this.target = target;
			return this;
		}

		public PushMessageModelBuilder setSettings(final Settings settings) {
			this.settings = settings;
			return this;
		}

		public PushMessageModelBuilder setTargetBuilder(TargetBuilder targetBuilder) {
			this.targetBuilder = targetBuilder;
			return this;
		}

		public PushMessageModelBuilder setSettingsBuilder(SettingsBuilder settingsBuilder) {
			this.settingsBuilder = settingsBuilder;
			return this;
		}

		public PushMessageModelBuilder setMessageBuilder(MessageBuilder messageBuilder) {
			this.messageBuilder = messageBuilder;
			return this;
		}

		public PushMessageModel build() {
				return new PushMessageModel(this);
			
		}

	}
}