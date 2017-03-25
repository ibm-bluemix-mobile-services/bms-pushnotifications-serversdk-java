package com.ibm.mobilefirstplatform.serversdk.java.push;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class PushNotificationsTest {
	@Before
	public void cleanup(){
		PushNotifications.pushMessageEndpointURL = null;
		PushNotifications.secret = null;
	}

	@Test
	public void shouldCreateEndpointURLAndStoreSecretWhenInitializing(){
		PushNotifications.init("testTenantId", "testPushSecret", PushNotifications.US_SOUTH_REGION);

		assertEquals("https://imfpush" + PushNotifications.US_SOUTH_REGION + ":443/imfpush/v1/apps/testTenantId/messages", PushNotifications.pushMessageEndpointURL);
		assertEquals("testPushSecret", PushNotifications.secret);
	}

	@Test
	public void shouldCreateCorrectPostRequest() {
		PushNotifications.pushMessageEndpointURL = "http://www.testendpoint.com";
		PushNotifications.secret = "testAppSecret";

		JSONObject notification = (new NotificationBuilder("testMessage")).build();

		HttpPost post = PushNotifications.createPushPostRequest(notification);

		assertNotNull(post.getFirstHeader(HTTP.CONTENT_TYPE));
		assertEquals("application/json", post.getFirstHeader(HTTP.CONTENT_TYPE).getValue());

		assertNotNull(post.getFirstHeader("appSecret"));
		assertEquals("testAppSecret", post.getFirstHeader("appSecret").getValue());

		HttpEntity bodyEntity = post.getEntity();
		StringEntity expectedBodyEntity = new StringEntity(notification.toString(), "UTF-8");

		assertEquals(expectedBodyEntity.getContentLength(), bodyEntity.getContentLength());
		assertEquals(expectedBodyEntity.getContentType().getValue(), bodyEntity.getContentType().getValue());

		try {
			ByteArrayOutputStream expectedBody = new ByteArrayOutputStream();
			expectedBodyEntity.writeTo(expectedBody);

			ByteArrayOutputStream body = new ByteArrayOutputStream();
			bodyEntity.writeTo(body);

			assertTrue(Arrays.equals(expectedBody.toByteArray(), body.toByteArray()));
		} catch (IOException e) {
			fail("Failed to convert entities to byte arrays.");
		}
	}

	@Test
	public void shouldFailInitializingWhenCredentialsAreNotInEnvironmentVariables(){
		try{
			PushNotifications.init("bluemixRegion");
		}
		catch(Throwable e){
			//Did fail; test was successful.
			return;
		}

		fail("Initialization did not fail when credentials are not in environment variables.");
	}

	@Test
	public void shouldFailSendingMessageWhenNotInitialized(){
		PushNotifications.send(null, new PushNotificationsResponseListener() {
			@Override
			public void onSuccess(int statusCode, String responseBody) {
				fail("Sending did not fail when PushNotifications was not initialized.");
			}

			@Override
			public void onFailure(Integer statusCode, String responseBody, Throwable t) {
				// Successfully failed.
			}
		});
	}

	@Test
	public void shouldFailSendingMessageWhenNotificationIsNull(){
		PushNotifications.init("a", "b", "c");

		PushNotifications.send(null, new PushNotificationsResponseListener() {
			@Override
			public void onSuccess(int statusCode, String responseBody) {
				fail("Sending did not fail when PushNotifications was not initialized.");
			}

			@Override
			public void onFailure(Integer statusCode, String responseBody, Throwable t) {
				// Successfully failed.
			}
		});
	}

	@Test
	public void shouldSendResponseToListener(){
		CloseableHttpResponse responseMock = mock(CloseableHttpResponse.class);

		when(responseMock.getEntity()).thenReturn(null);
		when(responseMock.getStatusLine()).thenReturn(new StatusLine(){
			@Override
			public ProtocolVersion getProtocolVersion() {
				return null;
			}

			@Override
			public int getStatusCode() {
				return HttpStatus.SC_ACCEPTED;
			}

			@Override
			public String getReasonPhrase() {
				return null;
			}
		});

		PushNotificationsResponseListener successListener = new PushNotificationsResponseListener() {

			@Override
			public void onSuccess(int statusCode, String responseBody) {
				assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
				assertTrue(responseBody == null || responseBody.length() == 0);
			}

			@Override
			public void onFailure(Integer statusCode, String responseBody, Throwable t) {
				fail("Should not have called failure callback when the Status Code is the correct one.");				
			}
		};

		try {
			PushNotifications.sendResponseToListener(responseMock, successListener);
		} catch (IOException e) {
			fail("Should not fail because the response is null.");
		}

		PushNotificationsResponseListener failureListener = new PushNotificationsResponseListener() {

			@Override
			public void onSuccess(int statusCode, String responseBody) {
				fail("Should not have called success callback when the Status Code is not the valid one.");
			}

			@Override
			public void onFailure(Integer statusCode, String responseBody, Throwable t) {
				assertNull(statusCode);
				
				//The response body is empty if mocked:
				assertTrue(responseBody == null || responseBody.length() == 0);
			}
		};

		//Return null status code
		when(responseMock.getStatusLine()).thenReturn(null);
		when(responseMock.getEntity()).thenReturn(mock(HttpEntity.class));

		try {
			PushNotifications.sendResponseToListener(responseMock, failureListener);
		} catch (IOException e) {
			fail("Should not fail because the response is null.");
		}	
	}
	
	@Test
	public void shouldCallSuccessCallbackWhenTheResponseIsCorrect() throws Throwable{
		CloseableHttpClient clientMock = mock(CloseableHttpClient.class);
		CloseableHttpResponse responseMock = mock(CloseableHttpResponse.class);
		
		when(responseMock.getStatusLine()).thenReturn(new StatusLine() {
			
			@Override
			public int getStatusCode() {
				return HttpStatus.SC_ACCEPTED;
			}
			
			@Override
			public String getReasonPhrase() {
				return null;
			}

			@Override
			public ProtocolVersion getProtocolVersion() {
				return null;
			}
		});
		when(clientMock.execute(null)).thenReturn(responseMock);

		PushNotifications.executePushPostRequest(null, clientMock, new PushNotificationsResponseListener() {

			@Override
			public void onSuccess(int statusCode, String responseBody) {
				assertEquals(HttpStatus.SC_ACCEPTED, statusCode);
				assertTrue(responseBody == null || responseBody.length() == 0);
			}

			@Override
			public void onFailure(Integer statusCode, String responseBody, Throwable t) {
				fail("The status code should have been correct, and there should not have been any exceptions");
			}
		});
	}

	@Test
	public void shouldCallFailureCallbackIfExceptionOccursWhenExecutingRequest() throws ClientProtocolException, IOException{
		CloseableHttpClient clientMock = mock(CloseableHttpClient.class);

		when(clientMock.execute(any(HttpUriRequest.class))).thenThrow(new ClientProtocolException());

		PushNotifications.executePushPostRequest(null, clientMock, new PushNotificationsResponseListener() {

			@Override
			public void onSuccess(int statusCode, String responseBody) {
				fail("Should not have succeeded.");
			}

			@Override
			public void onFailure(Integer statusCode, String responseBody, Throwable t) {
				assertNull(statusCode);
				assertNull(responseBody);
				assertTrue(t instanceof ClientProtocolException);
			}
		});
	}
}
