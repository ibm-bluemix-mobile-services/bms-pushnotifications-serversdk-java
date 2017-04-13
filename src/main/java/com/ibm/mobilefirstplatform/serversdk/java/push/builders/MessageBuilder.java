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

import com.ibm.mobilefirstplatform.serversdk.java.push.internal.PushMessageModel.Message;

public final class MessageBuilder {

	private String alert;
	private String url;

	public MessageBuilder setAlert(final String alert) {
		this.alert = alert;
		return this;
	}

	public MessageBuilder setUrl(final String url) {
		this.url = url;
		return this;
	}

	public Message build() {
		Message message = new Message();
		message.setAlert(alert).setUrl(url);
		return message;

	}
}
