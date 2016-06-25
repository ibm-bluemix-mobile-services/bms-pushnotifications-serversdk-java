package com.ibm.mobilefirstplatform.serversdk.java.push;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
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
	public void shouldCreateCorrectPostRequest(){
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
}
