/*
 *     Copyright 2016 IBM Corp.
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
 * Listener interface to be used to specify callbacks for the
 * {@link PushNotifications#send(Notification, PushNotificationsResponseListener)}
 * method.
 */
public interface PushNotificationsResponseListener {
	/**
	 * This method will be called when the push notification was successfully
	 * sent to the IBM® Cloud Push Notification service.
	 * 
	 * @param statusCode
	 *            the status code of the response
	 * @param responseBody
	 *            the body of the response, as a String
	 */
	void onSuccess(int statusCode, String responseBody);

	/**
	 * This method will be called when anything goes wrong while sending the
	 * push notification. All three of the parameters might be null.
	 * 
	 * @param statusCode
	 *            The status code of the response. It can be null, if there was
	 *            no response.
	 * @param responseBody
	 *            The body of the response. It can be null, if no body was
	 *            received.
	 * @param t
	 *            The exception or throwable that caused the failure, or null,
	 *            if there was no Throwable.
	 */
	void onFailure(Integer statusCode, String responseBody, Throwable t);
}
