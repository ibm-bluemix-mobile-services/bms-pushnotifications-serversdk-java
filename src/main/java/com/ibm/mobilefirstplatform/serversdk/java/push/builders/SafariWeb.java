package com.ibm.mobilefirstplatform.serversdk.java.push.builders;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SafariWeb {

	public static final Logger logger = Logger.getLogger(NotificationBuilder.class.getName());

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

	private SafariWeb(SafariWebBuilder builder) {

		this.title = builder.title;
		this.action = builder.action;
		this.urlArgs = builder.urlArgs;
	}

	public static class SafariWebBuilder {

		private String title;
		private String action;
		private String[] urlArgs;
		private boolean checkBlankObject = false;

		public final SafariWebBuilder setTitle(String title) {
			if (title != null && title.length() > 0) {
				checkBlankObject = true;
			}
			this.title = title;
			return this;
		}

		public final SafariWebBuilder setAction(String action) {
			if (action != null && action.length() > 0) {
				checkBlankObject = true;
			}
			this.action = action;
			return this;
		}

		public final SafariWebBuilder setUrlArgs(String[] urlArgs) {

			if (urlArgs != null && urlArgs.length > 0) {
				checkBlankObject = true;
			}
			this.urlArgs = urlArgs;
			return this;
		}

		public SafariWeb build() {

			if (checkBlankObject) {
				return new SafariWeb(this);
			}
			return null;

		}
	}

}
