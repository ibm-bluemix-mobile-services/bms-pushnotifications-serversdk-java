package com.ibm.mobilefirstplatform.serversdk.java.push;


public final class Message {

	private String alert;
	private String url;

	public final String getAlert() {
		return alert;
	}

	public final String getUrl() {
		return url;
	}

	private Message(Message.Builder builder) {
		this.alert = builder.alert;
		this.url = builder.url;

	}

	public static class Builder {
		private String alert;
		private String url;

		public final Builder alert(final String alert) {
			this.alert = alert;
			return this;
		}

		public final Builder url(final String url) {
			this.url = url;
			return this;
		}

		public Message build() {
			return new Message(this);
		}
	}
}