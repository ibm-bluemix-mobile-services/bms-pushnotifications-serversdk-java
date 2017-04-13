package com.ibm.mobilefirstplatform.serversdk.java.push.builders;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class Message {

	private String alert;
	private String url;

	public String getAlert() {
		return alert;
	}

	public String getUrl() {
		return url;
	}

	private Message(MessageBuilder builder) {
		this.alert = builder.alert;
		this.url = builder.url;

	}

	public static class MessageBuilder {
		private String alert;
		private String url;
		private boolean checkBlankObject = false;

		public MessageBuilder setAlert(final String alert) {
			if (alert != null && alert.length() > 0) {
				checkBlankObject = true;
			}
			this.alert = alert;
			return this;
		}

		public MessageBuilder setUrl(final String url) {
			if (url != null && url.length() > 0) {
				checkBlankObject = true;
			}
			this.url = url;
			return this;
		}

		public Message build() {
			if (checkBlankObject) {
				return new Message(this);
			} else {
				return null;
			}

		}
	}

}
