package com.ibm.mobilefirstplatform.serversdk.java.push;

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