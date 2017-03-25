package com.ibm.mobilefirstplatform.serversdk.java.push;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.PushNotificationsPlatform;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.Visibility;

public class NotificationBuilderTest {

	@Test
	public void shouldBuildWithJustAlert()  {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		JSONObject notification = builder.build();

		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldThrowExceptionWhenGivenANullMessage() {
		try {
			new NotificationBuilder(null);
		} catch (Throwable t) {
			// Return to mark as successful
			return;
		}
		fail("Exception not thrown.");
	}


	@Test
	public void shouldAllowAllOtherParametersToBeOptionalAndNotIncludeThemInTheNotificationIfNull()  {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setMessageURL(null)
				.setAPNSSettings(null, null, null, null, null, null, null, null, null, null, null, null, null, null)
				.setGCMSettings(null, null, null, null, null, null, null, null, null, null)
				.setChromeSettings(null, null, null, null).setChromeAppExtSettings(null, null, null, null, null, null)
				.setFirefoxWebSettings(null, null, null, null).setSafariWebSettings(null, null, null)
				.setTarget(null, null, null, null);

		JSONObject notification = builder.build();

		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldUpdateExistingMessageParameterToAddURL()  {
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
	public void shouldAllowToConfigureAPNSSettings()  {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setAPNSSettings(1, "testCategory", "testiOSActionKey", new JSONObject(), "testSoundFile",
				APNSNotificationType.DEFAULT, "testTitleLocKey", "testLocKey", "testLaunchImage",
				new String[] { "testTitleLocArgs1", "testTitleLocArgs2" },
				new String[] { "testLocArgs1", "testLocArgs" }, "testTitle", "testSubtitle", "testAttachmentUrl");

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

		assertTrue(apnsSettings.has("titleLocKey"));
		assertEquals("testTitleLocKey", apnsSettings.getString("titleLocKey"));

		assertTrue(apnsSettings.has("locKey"));
		assertEquals("testLocKey", apnsSettings.getString("locKey"));

		assertTrue(apnsSettings.has("launchImage"));
		assertEquals("testLaunchImage", apnsSettings.getString("launchImage"));

		assertTrue(apnsSettings.has("titleLocArgs"));
		JSONArray jsonTitleLocArgs = apnsSettings.getJSONArray("titleLocArgs");
		assertEquals("testTitleLocArgs1", jsonTitleLocArgs.get(0));

		assertTrue(apnsSettings.has("locArgs"));
		JSONArray jsonLocArgs = apnsSettings.getJSONArray("locArgs");
		assertEquals("testLocArgs1", jsonLocArgs.get(0));

		assertTrue(apnsSettings.has("title"));
		assertEquals("testTitle", apnsSettings.getString("title"));

		assertTrue(apnsSettings.has("subtitle"));
		assertEquals("testSubtitle", apnsSettings.getString("subtitle"));

		assertTrue(apnsSettings.has("attachmentUrl"));
		assertEquals("testAttachmentUrl", apnsSettings.getString("attachmentUrl"));

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureGCMSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setGCMSettings("testCollapseKey", true, new JSONObject(), GCMPriority.MIN, "testSoundFile", 42,
				"testIcon", Visibility.PUBLIC, true, new JSONObject());

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

		assertTrue(gcm.has("icon"));
		assertEquals("testIcon", gcm.getString("icon"));

		assertTrue(gcm.has("visibility"));
		assertEquals(Visibility.PUBLIC.name(), gcm.getString("visibility"));

		assertTrue(gcm.has("sync"));
		assertEquals("true", gcm.getString("sync"));

		
		assertTrue(!gcm.has("style"));

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureChromeSettings()  {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setChromeSettings("testTitle", "testIconUrl", 42, new JSONObject());

		JSONObject notification = builder.build();

		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("chromeWeb"));

		JSONObject chromeWeb = notification.getJSONObject("settings").getJSONObject("chromeWeb");

		assertTrue(chromeWeb.has("title"));
		assertEquals("testTitle", chromeWeb.getString("title"));

		assertTrue(chromeWeb.has("iconUrl"));
		assertEquals("testIconUrl", chromeWeb.getString("iconUrl"));

		assertTrue(chromeWeb.has("timeToLive"));
		assertEquals(42, chromeWeb.getInt("timeToLive"));

		assertTrue(chromeWeb.has("payload"));
		assertNotNull(chromeWeb.getJSONObject("payload"));
		assertTrue(chromeWeb.getJSONObject("payload").keySet().isEmpty());

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureChromeExtSettings()  {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setChromeAppExtSettings("testCollapseKey", true, "testTitle", "testIconUrl", 42, new JSONObject());

		JSONObject notification = builder.build();

		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("chromeAppExt"));

		JSONObject chromeAppExt = notification.getJSONObject("settings").getJSONObject("chromeAppExt");

		assertTrue(chromeAppExt.has("collapseKey"));
		assertEquals("testCollapseKey", chromeAppExt.getString("collapseKey"));

		assertTrue(chromeAppExt.has("delayWhileIdle"));
		assertEquals("true", chromeAppExt.getString("delayWhileIdle"));

		assertTrue(chromeAppExt.has("title"));
		assertEquals("testTitle", chromeAppExt.getString("title"));

		assertTrue(chromeAppExt.has("iconUrl"));
		assertEquals("testIconUrl", chromeAppExt.getString("iconUrl"));

		assertTrue(chromeAppExt.has("timeToLive"));
		assertEquals(42, chromeAppExt.getInt("timeToLive"));

		assertTrue(chromeAppExt.has("payload"));
		assertNotNull(chromeAppExt.getJSONObject("payload"));
		assertTrue(chromeAppExt.getJSONObject("payload").keySet().isEmpty());

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureFirefoxSettings()  {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setFirefoxWebSettings("testTitle", "testIconUrl", 42, new JSONObject());

		JSONObject notification = builder.build();

		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("firefoxWeb"));

		JSONObject firefoxWeb = notification.getJSONObject("settings").getJSONObject("firefoxWeb");

		assertTrue(firefoxWeb.has("title"));
		assertEquals("testTitle", firefoxWeb.getString("title"));

		assertTrue(firefoxWeb.has("iconUrl"));
		assertEquals("testIconUrl", firefoxWeb.getString("iconUrl"));

		assertTrue(firefoxWeb.has("timeToLive"));
		assertEquals(42, firefoxWeb.getInt("timeToLive"));

		assertTrue(firefoxWeb.has("payload"));
		assertNotNull(firefoxWeb.getJSONObject("payload"));
		assertTrue(firefoxWeb.getJSONObject("payload").keySet().isEmpty());

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureSafariSettings()  {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setSafariWebSettings("testTitle", new String[] { "testUrlArgs1", "testUrlArgs2" }, "testAction");

		JSONObject notification = builder.build();

		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("safariWeb"));

		JSONObject safariWeb = notification.getJSONObject("settings").getJSONObject("safariWeb");

		assertTrue(safariWeb.has("title"));
		assertEquals("testTitle", safariWeb.getString("title"));

		assertTrue(safariWeb.has("urlArgs"));
		JSONArray jsonLocArgs = safariWeb.getJSONArray("urlArgs");
		assertEquals("testUrlArgs1", jsonLocArgs.get(0));

		assertTrue(safariWeb.has("action"));
		assertEquals("testAction", safariWeb.getString("action"));

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureAllSettingsSimultaneouslyWithoutOverridingEachOther() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setAPNSSettings(1, "testCategory", "testiOSActionKey", new JSONObject(), "testSoundFile",
				APNSNotificationType.DEFAULT, "testTitleLocKey", "testLocKey", "testLaunchImage",
				new String[] { "testTitleLocArgs1", "testTitleLocArgs2" },
				new String[] { "testLocArgs1", "testLocArgs" }, "testTitle", "testSubtitle", "testAttachmentUrl");
		builder.setGCMSettings("testCollapseKey", true, new JSONObject(), GCMPriority.MIN, "testSoundFile", 42,
				"testIcon", Visibility.PUBLIC, true, new JSONObject());

		builder.setChromeSettings("testTitle", "testIconUrl", 42, new JSONObject());

		builder.setChromeAppExtSettings("testCollapseKey", true, "testTitle", "testIconUrl", 42, new JSONObject());

		builder.setFirefoxWebSettings("testTitle", "testIconUrl", 42, new JSONObject());

		builder.setSafariWebSettings("testTitle", new String[] { "testUrlArgs1", "testUrlArgs2" }, "testAction");

		JSONObject notification = builder.build();

		// Check APNS Settings

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

		assertTrue(apnsSettings.has("titleLocKey"));
		assertEquals("testTitleLocKey", apnsSettings.getString("titleLocKey"));

		assertTrue(apnsSettings.has("locKey"));
		assertEquals("testLocKey", apnsSettings.getString("locKey"));

		assertTrue(apnsSettings.has("launchImage"));
		assertEquals("testLaunchImage", apnsSettings.getString("launchImage"));

		assertTrue(apnsSettings.has("titleLocArgs"));
		JSONArray jsonTitleLocArgs = apnsSettings.getJSONArray("titleLocArgs");
		assertEquals("testTitleLocArgs1", jsonTitleLocArgs.get(0));

		assertTrue(apnsSettings.has("locArgs"));
		JSONArray jsonLocArgsApns = apnsSettings.getJSONArray("locArgs");
		assertEquals("testLocArgs1", jsonLocArgsApns.get(0));

		assertTrue(apnsSettings.has("title"));
		assertEquals("testTitle", apnsSettings.getString("title"));

		assertTrue(apnsSettings.has("subtitle"));
		assertEquals("testSubtitle", apnsSettings.getString("subtitle"));

		assertTrue(apnsSettings.has("attachmentUrl"));
		assertEquals("testAttachmentUrl", apnsSettings.getString("attachmentUrl"));

		// Check GCM Settings

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

		assertTrue(gcm.has("icon"));
		assertEquals("testIcon", gcm.getString("icon"));

		assertTrue(gcm.has("visibility"));
		assertEquals(Visibility.PUBLIC.name(), gcm.getString("visibility"));

		assertTrue(gcm.has("sync"));
		assertEquals("true", gcm.getString("sync"));

		assertTrue(!gcm.has("style"));

		// Check Chrome Settings

		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("chromeWeb"));

		JSONObject chromeWeb = notification.getJSONObject("settings").getJSONObject("chromeWeb");

		assertTrue(chromeWeb.has("title"));
		assertEquals("testTitle", chromeWeb.getString("title"));

		assertTrue(chromeWeb.has("iconUrl"));
		assertEquals("testIconUrl", chromeWeb.getString("iconUrl"));

		assertTrue(chromeWeb.has("timeToLive"));
		assertEquals(42, chromeWeb.getInt("timeToLive"));

		assertTrue(chromeWeb.has("payload"));
		assertNotNull(chromeWeb.getJSONObject("payload"));
		assertTrue(chromeWeb.getJSONObject("payload").keySet().isEmpty());

		// Check ChromExtension settings

		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("chromeAppExt"));

		JSONObject chromeAppExt = notification.getJSONObject("settings").getJSONObject("chromeAppExt");

		assertTrue(chromeAppExt.has("collapseKey"));
		assertEquals("testCollapseKey", chromeAppExt.getString("collapseKey"));

		assertTrue(chromeAppExt.has("delayWhileIdle"));
		assertEquals("true", chromeAppExt.getString("delayWhileIdle"));

		assertTrue(chromeAppExt.has("title"));
		assertEquals("testTitle", chromeAppExt.getString("title"));

		assertTrue(chromeAppExt.has("iconUrl"));
		assertEquals("testIconUrl", chromeAppExt.getString("iconUrl"));

		assertTrue(chromeAppExt.has("timeToLive"));
		assertEquals(42, chromeAppExt.getInt("timeToLive"));

		assertTrue(chromeAppExt.has("payload"));
		assertNotNull(chromeAppExt.getJSONObject("payload"));
		assertTrue(chromeAppExt.getJSONObject("payload").keySet().isEmpty());

		// Check Firefox Settings

		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("firefoxWeb"));

		JSONObject firefoxWeb = notification.getJSONObject("settings").getJSONObject("firefoxWeb");

		assertTrue(firefoxWeb.has("title"));
		assertEquals("testTitle", firefoxWeb.getString("title"));

		assertTrue(firefoxWeb.has("iconUrl"));
		assertEquals("testIconUrl", firefoxWeb.getString("iconUrl"));

		assertTrue(firefoxWeb.has("timeToLive"));
		assertEquals(42, firefoxWeb.getInt("timeToLive"));

		assertTrue(firefoxWeb.has("payload"));
		assertNotNull(firefoxWeb.getJSONObject("payload"));
		assertTrue(firefoxWeb.getJSONObject("payload").keySet().isEmpty());

		// Check Safari Settings

		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("safariWeb"));

		JSONObject safariWeb = notification.getJSONObject("settings").getJSONObject("safariWeb");

		assertTrue(safariWeb.has("title"));
		assertEquals("testTitle", safariWeb.getString("title"));

		assertTrue(safariWeb.has("urlArgs"));
		JSONArray jsonLocArgs = safariWeb.getJSONArray("urlArgs");
		assertEquals("testUrlArgs1", jsonLocArgs.get(0));

		assertTrue(safariWeb.has("action"));
		assertEquals("testAction", safariWeb.getString("action"));

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToSetTarget()  {

		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		PushNotificationsPlatform[] targetPlatforms = {};

		builder.setTarget(new String[] { "device1", "device2" }, new String[] { "userId1", "userId2" },
				new PushNotificationsPlatform[] { PushNotificationsPlatform.APPLE, PushNotificationsPlatform.GOOGLE },
				new String[] { "tag1", "tag2" });

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
	public void shouldBuildWithJustAlertWhenAllOptionalParametersAreNull()  {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		builder.setAPNSSettings(null, null, null, null, null, null, null, null, null, null, null, null, null, null)
				.setAPNSSettings(null, "", "", null, "", null, "", "", "", null, null, "", "", "")
				.setGCMSettings(null, null, null, null, null, null, null, null, null, null)
				.setGCMSettings("", null, null, null, "", null, "", null, null, null)
				.setChromeSettings(null, null, null, null).setChromeSettings("", "", null, null)
				.setChromeAppExtSettings(null, null, null, null, null, null)
				.setChromeAppExtSettings("", null, "", "", null, null).setFirefoxWebSettings(null, null, null, null)
				.setFirefoxWebSettings("", "", null, null).setSafariWebSettings(null, null, null)
				.setSafariWebSettings("", null, "").setMessageURL(null).setMessageURL("")
				.setTarget(null, null, null, null).setTarget(new String[] {}, new String[] {},
						new NotificationBuilder.PushNotificationsPlatform[] {}, new String[] {});

		JSONObject notification = builder.build();

		assertEquals(1, notification.keySet().size());
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
}
