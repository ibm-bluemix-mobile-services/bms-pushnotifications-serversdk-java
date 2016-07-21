package com.ibm.mobilefirstplatform.serversdk.java.push;

import static org.junit.Assert.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.PushNotificationsPlatform;

public class NotificationBuilderTest {
	
	@Test
	public void shouldBuildWithJustAlert() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		JSONObject notification = builder.build();
		
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
	
	@Test
	public void shouldThrowExceptionWhenGivenANullMessage(){
		try{
			new NotificationBuilder(null);
		}
		catch(Throwable t){
			//Return to mark as successful
			return;
		}
		fail("Exception not thrown.");
	}
	
	@Test
	public void shouldAllowAllOtherParametersToBeOptionalAndNotIncludeThemInTheNotificationIfNull(){
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		builder.setMessageURL(null)
			.setAPNSSettings(null, null, null, null, null, null)
			.setGCMSettings(null, null, null, null, null, null)
			.setTarget(null, null, null, null);
		
		JSONObject notification = builder.build();
		
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
	
	@Test
	public void shouldUpdateExistingMessageParameterToAddURL(){
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		String testURL = "http://localhost";
		builder.setMessageURL(testURL);
		
		JSONObject notification = builder.build();
		
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));

		assertTrue(notification.getJSONObject("message").has("url"));
		assertEquals(testURL, notification.getJSONObject("message").getString("url"));
	}
	
	@Test
	public void shouldAllowToConfigureAPNSSettings(){
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		builder.setAPNSSettings(1, "testCategory", "testiOSActionKey", new JSONObject(), "testSoundFile", APNSNotificationType.DEFAULT);
		
		JSONObject notification = builder.build();
		
		assertTrue(notification.has("settings"));
		
		JSONObject settings = notification.getJSONObject("settings");
		assertTrue(settings.has("apns"));

		JSONObject apnsSettings = settings.getJSONObject("apns");
		
		assertTrue(apnsSettings.has("badge"));
		assertEquals(1, apnsSettings.getInt("badge"));
		
		assertTrue(apnsSettings.has("category"));
		assertEquals("testCategory", apnsSettings.getString("category"));
		
		assertTrue(apnsSettings.has("iosActionKey"));
		assertEquals("testiOSActionKey", apnsSettings.getString("iosActionKey"));
		
		assertTrue(apnsSettings.has("payload"));
		assertNotNull(apnsSettings.getJSONObject("payload"));
		assertTrue(apnsSettings.getJSONObject("payload").keySet().isEmpty());
		
		assertTrue(apnsSettings.has("type"));
		assertEquals(APNSNotificationType.DEFAULT.name(), apnsSettings.getString("type"));
		
		assertTrue(apnsSettings.has("sound"));
		assertEquals("testSoundFile", apnsSettings.getString("sound"));
		
		//Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
	

	@Test
	public void shouldAllowToConfigureGCMSettings(){
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		builder.setGCMSettings("testCollapseKey", true, new JSONObject(), GCMPriority.MIN, "testSoundFile", 42);
		
		JSONObject notification = builder.build();
		
		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("gcm"));
		
		JSONObject gcm = notification.getJSONObject("settings").getJSONObject("gcm");
		
		assertTrue(gcm.has("collapseKey"));
		assertEquals("testCollapseKey", gcm.getString("collapseKey"));
		
		assertTrue(gcm.has("delayWhileIdle"));
		assertEquals("true", gcm.getString("delayWhileIdle"));
		
		assertTrue(gcm.has("payload"));
		assertNotNull(gcm.getJSONObject("payload"));
		assertTrue(gcm.getJSONObject("payload").keySet().isEmpty());
		
		assertTrue(gcm.has("priority"));
		assertEquals(GCMPriority.MIN.name(), gcm.getString("priority"));
		
		assertTrue(gcm.has("sound"));
		assertEquals("testSoundFile", gcm.getString("sound"));
		
		assertTrue(gcm.has("timeToLive"));
		assertEquals(42, gcm.getInt("timeToLive"));
		
		//Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
	
	@Test
	public void shouldAllowToConfigureBothGCMAndAPNSSettingsSimultaneouslyWithoutOverridingEachOther(){
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		builder.setAPNSSettings(1, "testCategory", "testiOSActionKey", new JSONObject(), "testSoundFile", APNSNotificationType.DEFAULT);
		builder.setGCMSettings("testCollapseKey", true, new JSONObject(), GCMPriority.MIN, "testSoundFile", 42);
		builder.setAPNSSettings(1, "testCategory", "testiOSActionKey", new JSONObject(), "testSoundFile", APNSNotificationType.DEFAULT);
		
		JSONObject notification = builder.build();
		
		//Check APNS settings:
		assertTrue(notification.has("settings"));
		
		JSONObject settings = notification.getJSONObject("settings");
		assertTrue(settings.has("apns"));

		JSONObject apnsSettings = settings.getJSONObject("apns");
		
		assertTrue(apnsSettings.has("badge"));
		assertEquals(1, apnsSettings.getInt("badge"));
		
		assertTrue(apnsSettings.has("category"));
		assertEquals("testCategory", apnsSettings.getString("category"));
		
		assertTrue(apnsSettings.has("iosActionKey"));
		assertEquals("testiOSActionKey", apnsSettings.getString("iosActionKey"));
		
		assertTrue(apnsSettings.has("payload"));
		assertNotNull(apnsSettings.getJSONObject("payload"));
		assertTrue(apnsSettings.getJSONObject("payload").keySet().isEmpty());
		
		assertTrue(apnsSettings.has("type"));
		assertEquals(APNSNotificationType.DEFAULT.name(), apnsSettings.getString("type"));
		
		assertTrue(apnsSettings.has("sound"));
		assertEquals("testSoundFile", apnsSettings.getString("sound"));
		
		//Check GCM Settings:
		assertTrue(notification.getJSONObject("settings").has("gcm"));
		
		JSONObject gcm = notification.getJSONObject("settings").getJSONObject("gcm");
		
		assertTrue(gcm.has("collapseKey"));
		assertEquals("testCollapseKey", gcm.getString("collapseKey"));
		
		assertTrue(gcm.has("delayWhileIdle"));
		assertEquals("true", gcm.getString("delayWhileIdle"));
		
		assertTrue(gcm.has("payload"));
		assertNotNull(gcm.getJSONObject("payload"));
		assertTrue(gcm.getJSONObject("payload").keySet().isEmpty());
		
		assertTrue(gcm.has("priority"));
		assertEquals(GCMPriority.MIN.name(), gcm.getString("priority"));
		
		assertTrue(gcm.has("sound"));
		assertEquals("testSoundFile", gcm.getString("sound"));
		
		assertTrue(gcm.has("timeToLive"));
		assertEquals(42, gcm.getInt("timeToLive"));
		
		//Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
	
	@Test
	public void shouldAllowToSetTarget(){

		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		PushNotificationsPlatform[] targetPlatforms = { };
		
		builder.setTarget(new String[]{"device1", "device2"}, 
							new String[]{"userId1", "userId2"},
							new PushNotificationsPlatform[]{PushNotificationsPlatform.APPLE, PushNotificationsPlatform.GOOGLE},
							new String[]{"tag1","tag2"});
		
		JSONObject notification = builder.build();
		
		assertTrue(notification.has("target"));
		
		JSONObject target = notification.getJSONObject("target");
		
		assertTrue(target.has("deviceIds"));
		JSONArray deviceIds = target.getJSONArray("deviceIds");
		assertEquals("device2", deviceIds.get(1));
		
		assertTrue(target.has("userIds"));
		JSONArray userIds = target.getJSONArray("userIds");
		assertEquals("userId2", userIds.get(1));
		
		assertTrue(target.has("platforms"));
		JSONArray platforms = target.getJSONArray("platforms");
		assertEquals("A", platforms.get(0));
		
		assertTrue(target.has("tagNames"));
		JSONArray tags = target.getJSONArray("tagNames");
		assertEquals("tag2", tags.get(1));
	}
	
	@Test
	public void shouldBuildWithJustAlertWhenAllOptionalParametersAreNull() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		builder.setAPNSSettings(null, null, null, null, null, null)
			.setAPNSSettings(null, "", "", null, "", null)
			.setGCMSettings(null, null, null, null, null, null)
			.setGCMSettings("", null, null, null, null, null)
			.setMessageURL(null)
			.setMessageURL("")
			.setTarget(null, null, null, null)
			.setTarget(new String[]{}, new String[]{}, new NotificationBuilder.PushNotificationsPlatform[]{}, new String[]{});
		
		JSONObject notification = builder.build();
		
		assertEquals(1, notification.keySet().size());
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
}
