package com.ibm.mobilefirstplatform.serversdk.java.push;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.GcmLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.GcmStyleTypes;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.PushNotificationsPlatform;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns.ApnsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeAppExt.ChromeAppExtBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeWeb.ChromeWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.FirefoxWeb.FirefoxWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.GcmLights;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.GcmLights.GcmLightsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.GcmStyle;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.GcmStyle.GcmStyleBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SafariWeb.SafariWebBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SettingsBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target.TargetBuilder;

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
	public void shouldAllowAllOtherParametersToBeOptionalAndNotIncludeThemInTheNotificationIfNull() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		Settings settings = new Settings.SettingsBuilder().setApnsBuilder(new ApnsBuilder())
				.setGcmBuilder(new GcmBuilder()).setChromeAppExtBuilder(new ChromeAppExtBuilder())
				.setChromeWebBuilder(new ChromeWebBuilder()).setFirefoxWebBuilder(new FirefoxWebBuilder())
				.setSafariWebBuilder(new SafariWebBuilder()).build();

		builder.setMessageURL(null)
				.setAPNSSettingsValues(settings.getApnsBuilder().setBadge(null).setAttachmentUrl(null)
						.setInteractiveCategory(null).setIosActionKey(null).setLaunchImage(null).setLocArgs(null)
						.setLocKey(null).setPayload(null).setSound(null).setSubtitle(null).setTitle(null)
						.setTitleLocArgs(null).setTitleLocKey(null).setType(null).build())
				.setGCMSettingsValues(settings.getGcmBuilder().setCollapseKey(null).setDelayWhileIdle(null).setIcon(null)
						.setInteractiveCategory(null).setLights(null).setPayload(null).setPriority(null).setSound(null)
						.setStyle(null).setSync(null).setTimeToLive(null).setVisibility(null).build())
				.setChromeSettings(settings.getChromeWebBuilder().setIconUrl(null).setPayload(null).setTimeToLive(null)
						.setTitle(null).build())
				.setFirefoxWebSettings(settings.getFirefoxWebBuilder().setIconUrl(null).setPayload(null)
						.setTimeToLive(null).setTitle(null).build())
				.setSafariWebSettings(
						settings.getSafariWebBuilder().setAction(null).setTitle(null).setUrlArgs(null).build())
				.setTargetValues(null);

		JSONObject notification = builder.build();

		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldUpdateExistingMessageParameterToAddURL() {
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
	
	
	// Old test cases for deprecated API'S
	
	@Test
	public void shouldAllowToConfigureAPNSSettingsOld(){
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		builder.setAPNSSettings(1, "testCategory", "testiOSActionKey", new JSONObject(), "testSoundFile", APNSNotificationType.DEFAULT);
		
		JSONObject notification = builder.build();
		
		checkApnsSettingsOld(notification);
		
		//Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	private void checkApnsSettingsOld(JSONObject notification) {
		assertTrue(notification.has("settings"));
		
		JSONObject settings = notification.getJSONObject("settings");
		assertTrue(settings.has("apns"));

		JSONObject apnsSettings = settings.getJSONObject("apns");
		
		assertTrue(apnsSettings.has("badge"));
		assertEquals(1, apnsSettings.getInt("badge"));
		
		assertTrue(apnsSettings.has("interactiveCategory"));
		assertEquals("testCategory", apnsSettings.getString("interactiveCategory"));
		
		assertTrue(apnsSettings.has("iosActionKey"));
		assertEquals("testiOSActionKey", apnsSettings.getString("iosActionKey"));
		
		assertTrue(apnsSettings.has("payload"));
		assertNotNull(apnsSettings.getJSONObject("payload"));
		assertTrue(apnsSettings.getJSONObject("payload").keySet().isEmpty());
		
		assertTrue(apnsSettings.has("type"));
		assertEquals(APNSNotificationType.DEFAULT.name(), apnsSettings.getString("type"));
		
		assertTrue(apnsSettings.has("sound"));
		assertEquals("testSoundFile", apnsSettings.getString("sound"));
	}
	

	@Test
	public void shouldAllowToConfigureGCMSettingsOld(){
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		builder.setGCMSettings("testCollapseKey", true, new JSONObject(), GCMPriority.MIN, "testSoundFile", 42);
		
		JSONObject notification = builder.build();
		
		checkGcmSettingsOld(notification);
		
		//Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	private void checkGcmSettingsOld(JSONObject notification) {
		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("gcm"));
		
		JSONObject gcm = notification.getJSONObject("settings").getJSONObject("gcm");
		
		assertTrue(gcm.has("collapseKey"));
		assertEquals("testCollapseKey", gcm.getString("collapseKey"));
		
		assertTrue(gcm.has("delayWhileIdle"));
		assertEquals(true, gcm.getBoolean("delayWhileIdle"));
		
		assertTrue(gcm.has("payload"));
		assertNotNull(gcm.getJSONObject("payload"));
		assertTrue(gcm.getJSONObject("payload").keySet().isEmpty());
		
		assertTrue(gcm.has("priority"));
		assertEquals(GCMPriority.MIN.name(), gcm.getString("priority"));
		
		assertTrue(gcm.has("sound"));
		assertEquals("testSoundFile", gcm.getString("sound"));
		
		assertTrue(gcm.has("timeToLive"));
		assertEquals(42, gcm.getInt("timeToLive"));
	}
	
	@Test
	public void shouldAllowToConfigureBothGCMAndAPNSSettingsSimultaneouslyWithoutOverridingEachOther(){
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		
		builder.setAPNSSettings(1, "testCategory", "testiOSActionKey", new JSONObject(), "testSoundFile", APNSNotificationType.DEFAULT);
		builder.setGCMSettings("testCollapseKey", true, new JSONObject(), GCMPriority.MIN, "testSoundFile", 42);
		builder.setAPNSSettings(1, "testCategory", "testiOSActionKey", new JSONObject(), "testSoundFile", APNSNotificationType.DEFAULT);
		
		JSONObject notification = builder.build();
		
		checkApnsSettingsOld(notification);
		
		//Check GCM Settings:
		checkGcmSettingsOld(notification);
		
		//Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
	
	@Test
	public void shouldAllowToSetTargetOld(){

		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		PushNotificationsPlatform[] targetPlatforms = { };
		
		builder.setTarget(new String[]{"device1", "device2"}, 
							new String[]{"userId1", "userId2"},
							new PushNotificationsPlatform[]{PushNotificationsPlatform.APPLE, PushNotificationsPlatform.GOOGLE},
							new String[]{"tag1","tag2"});
		
		JSONObject notification = builder.build();
		
		checkTargetOld(notification);
	}

	private void checkTargetOld(JSONObject notification) {
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
	public void shouldBuildWithJustAlertWhenAllOptionalParametersAreNullOld() {
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

// Old Test Cases End


	@Test
	public void shouldAllowToConfigureAPNSSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		Settings settings = new Settings.SettingsBuilder().setApnsBuilder(BuilderFactory.getBuilder(ApnsBuilder.class))
				.build();

		builder.setAPNSSettingsValues(settings.getApnsBuilder().setBadge(1).setInteractiveCategory("testInteractiveCategory")
				.setIosActionKey("testiOSActionKey").setPayload(new JSONObject()).setSound("testSoundFile")
				.setType(APNSNotificationType.DEFAULT).setTitleLocKey("testTitleLocKey").setLocKey("testLocKey")
				.setLaunchImage("testLaunchImage")
				.setTitleLocArgs(new String[] { "testTitleLocArgs1", "testTitleLocArgs2" })
				.setLocArgs(new String[] { "testLocArgs1", "testLocArgs" }).setTitle("testTitle")
				.setSubtitle("testSubtitle").setAttachmentUrl("testAttachmentUrl").build());

		JSONObject notification = builder.build();

		checkAPNSSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureGCMSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		SettingsBuilder settingBuilder = new Settings.SettingsBuilder()
				.setGcmBuilder(BuilderFactory.getBuilder(GcmBuilder.class));
		Settings settings = settingBuilder.build();

		Gcm gcm = settings.getGcmBuilder().setGcmStyleBuilder(BuilderFactory.getBuilder(GcmStyleBuilder.class))
				.setGcmLightsBuilder(BuilderFactory.getBuilder(GcmLightsBuilder.class)).build();

		GcmStyle style = gcm.getGcmStyleBuilder().setType(GcmStyleTypes.BIGTEXT_NOTIFICATION).setText("text").setTitle("title").setUrl("url").setLines(new String[] {"line1"}).build();

		GcmLights lights = gcm.getGcmLightsBuilder().setLedArgb(GcmLED.BLACK).setLedOffMs(1).setLedOnMs(1).build();

		builder.setGCMSettingsValues(settings.getGcmBuilder().setCollapseKey("testCollapseKey").setDelayWhileIdle(true)
				.setPayload(new JSONObject()).setPriority(GCMPriority.MIN).setSound("testSoundFile").setTimeToLive(42)
				.setIcon("testIcon").setVisibility(Visibility.PUBLIC).setSync(true)
				.setStyle(NotificationBuilder.generateJSON(style)).setLights(NotificationBuilder.generateJSON(lights))
				.build());

		JSONObject notification = builder.build();

		checkGCMSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureChromeSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		Settings settings = new Settings.SettingsBuilder()
				.setChromeWebBuilder(BuilderFactory.getBuilder(ChromeWebBuilder.class)).build();

		builder.setChromeSettings(settings.getChromeWebBuilder().setTitle("testTitle").setIconUrl("testIconUrl")
				.setTimeToLive(42).setPayload(new JSONObject()).build());

		JSONObject notification = builder.build();

		checkChromeSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureChromeExtSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		Settings settings = new Settings.SettingsBuilder()
				.setChromeAppExtBuilder(BuilderFactory.getBuilder(ChromeAppExtBuilder.class)).build();

		builder.setChromeAppExtSettings(settings.getChromeAppExtBuilder().setCollapseKey("testCollapseKey")
				.setDelayWhileIdle(true).setTitle("testTitle").setIconUrl("testIconUrl").setTimeToLive(42)
				.setPayload(new JSONObject()).build());

		JSONObject notification = builder.build();

		checkChromeExtensionSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureFirefoxSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		Settings settings = new Settings.SettingsBuilder()
				.setFirefoxWebBuilder(BuilderFactory.getBuilder(FirefoxWebBuilder.class)).build();

		builder.setFirefoxWebSettings(settings.getFirefoxWebBuilder().setTitle("testTitle").setIconUrl("testIconUrl")
				.setTimeToLive(42).setPayload(new JSONObject()).build());

		JSONObject notification = builder.build();

		checkFirefoxSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureSafariSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		Settings settings = new Settings.SettingsBuilder()
				.setSafariWebBuilder(BuilderFactory.getBuilder(SafariWebBuilder.class)).build();

		builder.setSafariWebSettings(settings.getSafariWebBuilder().setTitle("testTitle")
				.setUrlArgs(new String[] { "testUrlArgs1", "testUrlArgs2" }).setAction("testAction").build());

		JSONObject notification = builder.build();

		checkSafariSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureAllSettingsSimultaneouslyWithoutOverridingEachOther() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		Settings settings = new Settings.SettingsBuilder().setApnsBuilder(BuilderFactory.getBuilder(ApnsBuilder.class))
				.setGcmBuilder(BuilderFactory.getBuilder(GcmBuilder.class))
				.setChromeAppExtBuilder(BuilderFactory.getBuilder(ChromeAppExtBuilder.class))
				.setChromeWebBuilder(BuilderFactory.getBuilder(ChromeWebBuilder.class))
				.setFirefoxWebBuilder(BuilderFactory.getBuilder(FirefoxWebBuilder.class))
				.setSafariWebBuilder(BuilderFactory.getBuilder(SafariWebBuilder.class)).build();

		builder.setAPNSSettingsValues(settings.getApnsBuilder().setBadge(1).setInteractiveCategory("testInteractiveCategory")
				.setIosActionKey("testiOSActionKey").setPayload(new JSONObject()).setSound("testSoundFile")
				.setType(APNSNotificationType.DEFAULT).setTitleLocKey("testTitleLocKey").setLocKey("testLocKey")
				.setLaunchImage("testLaunchImage")
				.setTitleLocArgs(new String[] { "testTitleLocArgs1", "testTitleLocArgs2" })
				.setLocArgs(new String[] { "testLocArgs1", "testLocArgs" }).setTitle("testTitle")
				.setSubtitle("testSubtitle").setAttachmentUrl("testAttachmentUrl").build());

		Gcm gcm = settings.getGcmBuilder().setGcmStyleBuilder(BuilderFactory.getBuilder(GcmStyleBuilder.class))
				.setGcmLightsBuilder(BuilderFactory.getBuilder(GcmLightsBuilder.class)).build();

		GcmStyle style = gcm.getGcmStyleBuilder().setType(GcmStyleTypes.BIGTEXT_NOTIFICATION).setTitle("title").setText("text").setUrl("url").setLines(new String[] {"line1"}).build();

		GcmLights lights = gcm.getGcmLightsBuilder().setLedArgb(GcmLED.BLACK).setLedOffMs(1).setLedOnMs(1).build();

		builder.setGCMSettingsValues(settings.getGcmBuilder().setCollapseKey("testCollapseKey").setDelayWhileIdle(true)
				.setPayload(new JSONObject()).setPriority(GCMPriority.MIN).setSound("testSoundFile").setTimeToLive(42)
				.setIcon("testIcon").setVisibility(Visibility.PUBLIC).setSync(true)
				.setStyle(NotificationBuilder.generateJSON(style)).setLights(NotificationBuilder.generateJSON(lights))
				.build());

		builder.setChromeSettings(settings.getChromeWebBuilder().setTitle("testTitle").setIconUrl("testIconUrl")
				.setTimeToLive(42).setPayload(new JSONObject()).build());

		builder.setChromeAppExtSettings(settings.getChromeAppExtBuilder().setCollapseKey("testCollapseKey")
				.setDelayWhileIdle(true).setTitle("testTitle").setIconUrl("testIconUrl").setTimeToLive(42)
				.setPayload(new JSONObject()).build());

		builder.setFirefoxWebSettings(settings.getFirefoxWebBuilder().setTitle("testTitle").setIconUrl("testIconUrl")
				.setTimeToLive(42).setPayload(new JSONObject()).build());

		builder.setSafariWebSettings(settings.getSafariWebBuilder().setTitle("testTitle")
				.setUrlArgs(new String[] { "testUrlArgs1", "testUrlArgs2" }).setAction("testAction").build());

		JSONObject notification = builder.build();

		// Check APNS Settings

		checkAPNSSettings(notification);

		// Check GCM Settings

		checkGCMSettings(notification);

		// Check Chrome Settings

		checkChromeSettings(notification);

		// Check ChromExtension settings

		checkChromeExtensionSettings(notification);

		// Check Firefox Settings

		checkFirefoxSettings(notification);

		// Check Safari Settings

		checkSafariSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	private void checkSafariSettings(JSONObject notification) {
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
	}

	private void checkFirefoxSettings(JSONObject notification) {
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
	}

	private void checkChromeExtensionSettings(JSONObject notification) {
		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("chromeAppExt"));

		JSONObject chromeAppExt = notification.getJSONObject("settings").getJSONObject("chromeAppExt");

		assertTrue(chromeAppExt.has("collapseKey"));
		assertEquals("testCollapseKey", chromeAppExt.getString("collapseKey"));

		assertTrue(chromeAppExt.has("delayWhileIdle"));
		assertEquals(true, chromeAppExt.getBoolean("delayWhileIdle"));

		assertTrue(chromeAppExt.has("title"));
		assertEquals("testTitle", chromeAppExt.getString("title"));

		assertTrue(chromeAppExt.has("iconUrl"));
		assertEquals("testIconUrl", chromeAppExt.getString("iconUrl"));

		assertTrue(chromeAppExt.has("timeToLive"));
		assertEquals(42, chromeAppExt.getInt("timeToLive"));

		assertTrue(chromeAppExt.has("payload"));
		assertNotNull(chromeAppExt.getJSONObject("payload"));
		assertTrue(chromeAppExt.getJSONObject("payload").keySet().isEmpty());
	}

	private void checkChromeSettings(JSONObject notification) {
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
	}

	private void checkGCMSettings(JSONObject notification) {
		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("gcm"));

		JSONObject gcm = notification.getJSONObject("settings").getJSONObject("gcm");

		assertTrue(gcm.has("collapseKey"));
		assertEquals("testCollapseKey", gcm.getString("collapseKey"));

		assertTrue(gcm.has("delayWhileIdle"));
		assertEquals(true, gcm.getBoolean("delayWhileIdle"));

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
		assertEquals(true, gcm.getBoolean("sync"));

		assertTrue(gcm.has("style"));
		JSONObject gcmJson = gcm.getJSONObject("style");
		assertEquals(GcmStyleTypes.BIGTEXT_NOTIFICATION.name(), gcmJson.get("type"));
		assertEquals("text", gcmJson.get("text"));
		assertEquals("title", gcmJson.get("title"));
		assertEquals("url", gcmJson.get("url"));
		
		assertTrue(gcmJson.has("lines"));
		JSONArray jsonLocArgs = gcmJson.getJSONArray("lines");
		assertEquals("line1", jsonLocArgs.get(0));

		
		assertTrue(gcm.has("lights"));
		JSONObject gcmLightsJson = gcm.getJSONObject("lights");
		assertEquals(GcmLED.BLACK.name(), gcmLightsJson.get("ledArgb"));
		assertEquals(1, gcmLightsJson.get("ledOffMs"));
		assertEquals(1, gcmLightsJson.get("ledOnMs"));
	}

	private void checkAPNSSettings(JSONObject notification) {
		assertTrue(notification.has("settings"));

		JSONObject settings = notification.getJSONObject("settings");
		assertTrue(settings.has("apns"));

		JSONObject apnsSettings = settings.getJSONObject("apns");

		assertTrue(apnsSettings.has("badge"));
		assertEquals(1, apnsSettings.getInt("badge"));

		assertTrue(apnsSettings.has("interactiveCategory"));
		assertEquals("testInteractiveCategory", apnsSettings.getString("interactiveCategory"));

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
	}

	@Test
	public void shouldAllowToSetTarget() {

		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);
		PushNotificationsPlatform[] targetPlatforms = {};
		PushMessageModel model = new PushMessageModel.PushMessageModelBuilder()
				.setTargetBuilder(BuilderFactory.getBuilder(TargetBuilder.class)).build();
		builder.setTargetValues(model.getTargetBuilder().setDeviceIds(new String[] { "device1", "device2" })
				.setUserIds(new String[] { "userId1", "userId2" })
				.setPlatforms(new PushNotificationsPlatform[] { PushNotificationsPlatform.APPLE,
						PushNotificationsPlatform.GOOGLE, PushNotificationsPlatform.APPEXTCHROME,
						PushNotificationsPlatform.WEBCHROME, PushNotificationsPlatform.WEBFIREFOX, PushNotificationsPlatform.WEBSAFARI })
				.setTagNames(new String[] { "tag1", "tag2" }).build());

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
		assertEquals("G", platforms.get(1));
		assertEquals("APPEXT_CHROME", platforms.get(2));
		assertEquals("WEB_CHROME", platforms.get(3));
		assertEquals("WEB_FIREFOX", platforms.get(4));
		assertEquals("WEB_SAFARI", platforms.get(5));

		assertTrue(target.has("tagNames"));
		JSONArray tags = target.getJSONArray("tagNames");
		assertEquals("tag2", tags.get(1));
	}

	@Test
	public void shouldBuildWithJustAlertWhenAllOptionalParametersAreNull() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder(testAlert);

		Settings settings = new Settings.SettingsBuilder().setApnsBuilder(BuilderFactory.getBuilder(ApnsBuilder.class))
				.setGcmBuilder(BuilderFactory.getBuilder(GcmBuilder.class))
				.setChromeAppExtBuilder(BuilderFactory.getBuilder(ChromeAppExtBuilder.class))
				.setChromeWebBuilder(BuilderFactory.getBuilder(ChromeWebBuilder.class))
				.setFirefoxWebBuilder(BuilderFactory.getBuilder(FirefoxWebBuilder.class))
				.setSafariWebBuilder(BuilderFactory.getBuilder(SafariWebBuilder.class)).build();

		builder.setMessageURL(null)
				.setAPNSSettingsValues(settings.getApnsBuilder().setBadge(null).setAttachmentUrl(null)
						.setInteractiveCategory(null).setIosActionKey(null).setLaunchImage(null).setLocArgs(null)
						.setLocKey(null).setPayload(null).setSound(null).setSubtitle(null).setTitle(null)
						.setTitleLocArgs(null).setTitleLocKey(null).setType(null).build())

				.setAPNSSettingsValues(settings.getApnsBuilder().setBadge(null).setAttachmentUrl("")
						.setInteractiveCategory("").setIosActionKey("").setLaunchImage("").setLocArgs(null)
						.setLocKey("").setPayload(null).setSound("").setSubtitle("").setTitle("").setTitleLocArgs(null)
						.setTitleLocKey("").setType(null).build())

				.setGCMSettingsValues(settings.getGcmBuilder().setCollapseKey(null).setDelayWhileIdle(null).setIcon(null)
						.setInteractiveCategory(null).setLights(null).setPayload(null).setPriority(null).setSound(null)
						.setStyle(null).setSync(null).setTimeToLive(null).setVisibility(null).build())

				.setGCMSettingsValues(settings.getGcmBuilder().setCollapseKey("").setDelayWhileIdle(null).setIcon("")
						.setInteractiveCategory("").setLights(null).setPayload(null).setPriority(null).setSound("")
						.setStyle(null).setSync(null).setTimeToLive(null).setVisibility(null).build())

				.setChromeSettings(settings.getChromeWebBuilder().setIconUrl(null).setPayload(null).setTimeToLive(null)
						.setTitle(null).build())

				.setChromeSettings(settings.getChromeWebBuilder().setIconUrl("").setPayload(null).setTimeToLive(null)
						.setTitle("").build())

				.setFirefoxWebSettings(settings.getFirefoxWebBuilder().setIconUrl(null).setPayload(null)
						.setTimeToLive(null).setTitle(null).build())

				.setFirefoxWebSettings(settings.getFirefoxWebBuilder().setIconUrl("").setPayload(null)
						.setTimeToLive(null).setTitle("").build())

				.setSafariWebSettings(
						settings.getSafariWebBuilder().setAction(null).setTitle(null).setUrlArgs(null).build())

				.setSafariWebSettings(
						settings.getSafariWebBuilder().setAction("").setTitle("").setUrlArgs(null).build())

				.setTargetValues(null);

		JSONObject notification = builder.build();

		assertEquals(1, notification.keySet().size());
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
}
