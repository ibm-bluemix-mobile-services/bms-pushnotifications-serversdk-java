package com.ibm.mobilefirstplatform.serversdk.java.push;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Message;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Apns.ApnsBuilder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeAppExt;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.ChromeWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.FirefoxWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.GCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.GcmLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.GcmLights;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.GcmStyle;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.GcmStyleTypes;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.Gcm.GcmBuilder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Settings.SafariWeb;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target.TargetBuilder;
import com.ibm.mobilefirstplatform.serversdk.java.push.PushMessageModel.Target.TargetBuilder.PushNotificationsPlatform;

public class NotificationBuilderTest {

	@Test
	public void shouldBuildWithJustAlert() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();
		builder.message(message);
		JSONObject notification = builder.build();

		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldThrowExceptionWhenGivenANullMessage() {
		try {
			Message message = new Message.MessageBuilder().alert(null).build();
			new NotificationBuilder().message(message).build();
		} catch (Throwable t) {
			// Return to mark as successful
			return;
		}
		fail("Exception not thrown.");
	}

	@Test
	public void shouldAllowAllOtherParametersToBeOptionalAndNotIncludeThemInTheNotificationIfNull() {

		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert("testMessage").url(null).build();

		Apns apns = new Apns.ApnsBuilder().badge(null).attachmentUrl(null).interactiveCategory(null).iosActionKey(null)
				.launchImage(null).locArgs(null).locKey(null).payload(null).sound(null).subtitle(null).title(null)
				.titleLocArgs(null).titleLocKey(null).type(null).build();

		Gcm gcm = new Gcm.GcmBuilder().collapseKey(null).delayWhileIdle(null).icon(null).interactiveCategory(null)
				.lights(null).payload(null).priority(null).sound(null).style(null).sync(null).timeToLive(null)
				.visibility(null).build();

		ChromeAppExt chromeAppExt = new ChromeAppExt.ChromeAppExtBuilder().collapseKey(null).delayWhileIdle(null)
				.iconUrl(null).payload(null).title(null).build();

		ChromeWeb chromeWeb = new ChromeWeb.ChromeWebBuilder().iconUrl(null).payload(null).timeToLive(null).title(null)
				.build();

		FirefoxWeb firefoxWeb = new FirefoxWeb.FirefoxWebBuilder().iconUrl(null).payload(null).timeToLive(null)
				.title(null).build();

		SafariWeb safariWeb = new SafariWeb.SafariWebBuilder().action(null).title(null).urlArgs(null).build();

		Settings settings = new Settings.SettingsBuilder().apns(apns).gcm(gcm).chromeAppExt(chromeAppExt)
				.chromeWeb(chromeWeb).firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();

		Target target = new Target.TargetBuilder().deviceIds(null).platforms(null).tagNames(null).userIds(null).build();

		builder.message(message).target(target).settings(settings);

		JSONObject notification = builder.build();

		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureAPNSSettings() {

		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();

		Apns apns = new Apns.ApnsBuilder().badge(1).interactiveCategory("testinteractiveCategory")
				.iosActionKey("testiOSactionKey").payload(new JSONObject().put("key", "value")).sound("testsoundFile")
				.type(APNSNotificationType.DEFAULT).titleLocKey("testtitlelocKey").locKey("testlocKey")
				.launchImage("testlaunchImage").titleLocArgs(new String[] { "testtitlelocArgs1", "testtitlelocArgs2" })
				.locArgs(new String[] { "testlocArgs1", "testlocArgs" }).title("testtitle").subtitle("testSubtitle")
				.attachmentUrl("testattachmentUrl").build();

		Settings settings = new Settings.SettingsBuilder().apns(apns).build();

		JSONObject notification = builder.message(message).settings(settings).build();

		checkAPNSSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureGCMSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();

		GcmStyle gcmstyle = new GcmStyle.GcmStyleBuilder().type(GcmStyleTypes.BIGTEXT_NOTIFICATION).text("text")
				.title("title").url("url").lines(new String[] { "line1" }).build();

		GcmLights gcmlights = new GcmLights.GcmLightsBuilder().ledArgb(GcmLED.BLACK).ledOffMs(1).ledOnMs(1).build();

		Gcm gcm = new Gcm.GcmBuilder().collapseKey("testcollapseKey").delayWhileIdle(true)
				.payload(new JSONObject().put("key", "value")).priority(GCMPriority.MIN).sound("testsoundFile")
				.timeToLive(42).icon("testicon").visibility(Visibility.PUBLIC).sync(true).style(gcmstyle)
				.lights(gcmlights).build();

		Settings settings = new Settings.SettingsBuilder().gcm(gcm).build();

		JSONObject notification = builder.message(message).settings(settings).build();

		checkGCMSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureChromeSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();

		ChromeWeb chromeWeb = new ChromeWeb.ChromeWebBuilder().title("testtitle").iconUrl("testiconUrl").timeToLive(42)
				.payload(new JSONObject()).build();

		Settings settings = new Settings.SettingsBuilder().chromeWeb(chromeWeb).build();

		JSONObject notification = builder.message(message).settings(settings).build();

		checkChromeSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureChromeExtSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();

		ChromeAppExt chromeAppExt = new ChromeAppExt.ChromeAppExtBuilder().collapseKey("testcollapseKey")
				.delayWhileIdle(true).title("testtitle").iconUrl("testiconUrl").timeToLive(42).payload(new JSONObject())
				.build();

		Settings settings = new Settings.SettingsBuilder().chromeAppExt(chromeAppExt).build();

		JSONObject notification = builder.message(message).settings(settings).build();

		checkChromeExtensionSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureFirefoxSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();

		FirefoxWeb firefoxWeb = new FirefoxWeb.FirefoxWebBuilder().title("testtitle").iconUrl("testiconUrl")
				.timeToLive(42).payload(new JSONObject()).build();

		Settings settings = new Settings.SettingsBuilder().firefoxWeb(firefoxWeb).build();

		JSONObject notification = builder.message(message).settings(settings).build();

		checkFirefoxSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureSafariSettings() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();

		SafariWeb safariWeb = new SafariWeb.SafariWebBuilder().title("testtitle")
				.urlArgs(new String[] { "testUrlArgs1", "testUrlArgs2" }).action("testaction").build();

		Settings settings = new Settings.SettingsBuilder().safariWeb(safariWeb).build();

		JSONObject notification = builder.message(message).settings(settings).build();

		checkSafariSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureAllSettingsSimultaneouslyWithoutOverridingEachOther() {
		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();

		Apns apns = new Apns.ApnsBuilder().badge(1).interactiveCategory("testinteractiveCategory")
				.iosActionKey("testiOSactionKey").payload(new JSONObject().put("key", "value")).sound("testsoundFile")
				.type(APNSNotificationType.DEFAULT).titleLocKey("testtitlelocKey").locKey("testlocKey")
				.launchImage("testlaunchImage").titleLocArgs(new String[] { "testtitlelocArgs1", "testtitlelocArgs2" })
				.locArgs(new String[] { "testlocArgs1", "testlocArgs" }).title("testtitle").subtitle("testSubtitle")
				.attachmentUrl("testattachmentUrl").build();

		GcmStyle gcmstyle = new GcmStyle.GcmStyleBuilder().type(GcmStyleTypes.BIGTEXT_NOTIFICATION).text("text")
				.title("title").url("url").lines(new String[] { "line1" }).build();

		GcmLights gcmlights = new GcmLights.GcmLightsBuilder().ledArgb(GcmLED.BLACK).ledOffMs(1).ledOnMs(1).build();

		Gcm gcm = new Gcm.GcmBuilder().collapseKey("testcollapseKey").delayWhileIdle(true)
				.payload(new JSONObject().put("key", "value")).priority(GCMPriority.MIN).sound("testsoundFile")
				.timeToLive(42).icon("testicon").visibility(Visibility.PUBLIC).sync(true).style(gcmstyle)
				.lights(gcmlights).build();

		ChromeWeb chromeWeb = new ChromeWeb.ChromeWebBuilder().title("testtitle").iconUrl("testiconUrl").timeToLive(42)
				.payload(new JSONObject()).build();

		ChromeAppExt chromeAppExt = new ChromeAppExt.ChromeAppExtBuilder().collapseKey("testcollapseKey")
				.delayWhileIdle(true).title("testtitle").iconUrl("testiconUrl").timeToLive(42).payload(new JSONObject())
				.build();
		FirefoxWeb firefoxWeb = new FirefoxWeb.FirefoxWebBuilder().title("testtitle").iconUrl("testiconUrl")
				.timeToLive(42).payload(new JSONObject()).build();

		SafariWeb safariWeb = new SafariWeb.SafariWebBuilder().title("testtitle")
				.urlArgs(new String[] { "testUrlArgs1", "testUrlArgs2" }).action("testaction").build();

		Settings settings = new Settings.SettingsBuilder().apns(apns).gcm(gcm).chromeWeb(chromeWeb)
				.chromeAppExt(chromeAppExt).firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();
		JSONObject notification = builder.message(message).settings(settings).build();

		// Check APNS Settings

		checkAPNSSettings(notification);

		// Check GCM Settings

		checkGCMSettings(notification);

		// Check Chrome Settings

		checkChromeSettings(notification);

		// Check ChromExtension Settings

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
		assertEquals("testtitle", safariWeb.getString("title"));

		assertTrue(safariWeb.has("urlArgs"));
		JSONArray jsonlocArgs = safariWeb.getJSONArray("urlArgs");
		assertEquals("testUrlArgs1", jsonlocArgs.get(0));

		assertTrue(safariWeb.has("action"));
		assertEquals("testaction", safariWeb.getString("action"));
	}

	private void checkFirefoxSettings(JSONObject notification) {
		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("firefoxWeb"));

		JSONObject firefoxWeb = notification.getJSONObject("settings").getJSONObject("firefoxWeb");

		assertTrue(firefoxWeb.has("title"));
		assertEquals("testtitle", firefoxWeb.getString("title"));

		assertTrue(firefoxWeb.has("iconUrl"));
		assertEquals("testiconUrl", firefoxWeb.getString("iconUrl"));

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
		assertEquals("testcollapseKey", chromeAppExt.getString("collapseKey"));

		assertTrue(chromeAppExt.has("delayWhileIdle"));
		assertEquals(true, chromeAppExt.getBoolean("delayWhileIdle"));

		assertTrue(chromeAppExt.has("title"));
		assertEquals("testtitle", chromeAppExt.getString("title"));

		assertTrue(chromeAppExt.has("iconUrl"));
		assertEquals("testiconUrl", chromeAppExt.getString("iconUrl"));

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
		assertEquals("testtitle", chromeWeb.getString("title"));

		assertTrue(chromeWeb.has("iconUrl"));
		assertEquals("testiconUrl", chromeWeb.getString("iconUrl"));

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
		assertEquals("testcollapseKey", gcm.getString("collapseKey"));

		assertTrue(gcm.has("delayWhileIdle"));
		assertEquals(true, gcm.getBoolean("delayWhileIdle"));

		assertTrue(gcm.has("payload"));
		assertNotNull(gcm.getJSONObject("payload"));
		assertTrue(gcm.getJSONObject("payload").has("key"));

		assertTrue(gcm.has("priority"));
		assertEquals(GcmBuilder.GCMPriority.MIN.name(), gcm.getString("priority"));

		assertTrue(gcm.has("sound"));
		assertEquals("testsoundFile", gcm.getString("sound"));

		assertTrue(gcm.has("timeToLive"));
		assertEquals(42, gcm.getInt("timeToLive"));

		assertTrue(gcm.has("icon"));
		assertEquals("testicon", gcm.getString("icon"));

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
		JSONArray jsonlocArgs = gcmJson.getJSONArray("lines");
		assertEquals("line1", jsonlocArgs.get(0));

		assertTrue(gcm.has("lights"));
		JSONObject gcmlightsJson = gcm.getJSONObject("lights");
		assertEquals(GcmBuilder.GcmLED.BLACK.name(), gcmlightsJson.get("ledArgb"));
		assertEquals(1, gcmlightsJson.get("ledOffMs"));
		assertEquals(1, gcmlightsJson.get("ledOnMs"));
	}

	private void checkAPNSSettings(JSONObject notification) {
		assertTrue(notification.has("settings"));

		JSONObject settings = notification.getJSONObject("settings");
		assertTrue(settings.has("apns"));

		JSONObject apnsSettings = settings.getJSONObject("apns");

		assertTrue(apnsSettings.has("badge"));
		assertEquals(1, apnsSettings.getInt("badge"));

		assertTrue(apnsSettings.has("interactiveCategory"));
		assertEquals("testinteractiveCategory", apnsSettings.getString("interactiveCategory"));

		assertTrue(apnsSettings.has("iosActionKey"));
		assertEquals("testiOSactionKey", apnsSettings.getString("iosActionKey"));

		assertTrue(apnsSettings.has("payload"));
		assertNotNull(apnsSettings.getJSONObject("payload"));
		assertTrue(apnsSettings.getJSONObject("payload").has("key"));

		assertTrue(apnsSettings.has("type"));
		assertEquals(APNSNotificationType.DEFAULT.name(), apnsSettings.getString("type"));

		assertTrue(apnsSettings.has("sound"));
		assertEquals("testsoundFile", apnsSettings.getString("sound"));

		assertTrue(apnsSettings.has("titleLocKey"));
		assertEquals("testtitlelocKey", apnsSettings.getString("titleLocKey"));

		assertTrue(apnsSettings.has("locKey"));
		assertEquals("testlocKey", apnsSettings.getString("locKey"));

		assertTrue(apnsSettings.has("launchImage"));
		assertEquals("testlaunchImage", apnsSettings.getString("launchImage"));

		assertTrue(apnsSettings.has("titleLocArgs"));
		JSONArray jsontitlelocArgs = apnsSettings.getJSONArray("titleLocArgs");
		assertEquals("testtitlelocArgs1", jsontitlelocArgs.get(0));

		assertTrue(apnsSettings.has("locArgs"));
		JSONArray jsonlocArgsApns = apnsSettings.getJSONArray("locArgs");
		assertEquals("testlocArgs1", jsonlocArgsApns.get(0));

		assertTrue(apnsSettings.has("title"));
		assertEquals("testtitle", apnsSettings.getString("title"));

		assertTrue(apnsSettings.has("subtitle"));
		assertEquals("testSubtitle", apnsSettings.getString("subtitle"));

		assertTrue(apnsSettings.has("attachmentUrl"));
		assertEquals("testattachmentUrl", apnsSettings.getString("attachmentUrl"));
	}

	@Test
	public void shouldAllowToSetTarget() {

		String testAlert = "testMessage";
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).build();

		Target targetValue = new Target.TargetBuilder().deviceIds(new String[] { "device1", "device2" })
				.userIds(new String[] { "userId1", "userId2" })
				.platforms(new TargetBuilder.PushNotificationsPlatform[] { PushNotificationsPlatform.APPLE,
						PushNotificationsPlatform.GOOGLE, PushNotificationsPlatform.APPEXTCHROME,
						PushNotificationsPlatform.WEBCHROME, PushNotificationsPlatform.WEBFIREFOX,
						PushNotificationsPlatform.WEBSAFARI })
				.tagNames(new String[] { "tag1", "tag2" }).build();

		JSONObject notification = builder.message(message).target(targetValue).build();

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
		NotificationBuilder builder = new NotificationBuilder();

		Message message = new Message.MessageBuilder().alert(testAlert).url(null).build();

		Apns apns = new Apns.ApnsBuilder().badge(null).attachmentUrl(null).interactiveCategory(null).iosActionKey(null)
				.launchImage(null).locArgs(null).locKey(null).payload(null).sound(null).subtitle(null).title(null)
				.titleLocArgs(null).titleLocKey(null).type(null)

				.badge(null).attachmentUrl("").interactiveCategory("").iosActionKey("").launchImage("").locArgs(null)
				.locKey("").payload(null).sound("").subtitle("").title("").titleLocArgs(null).titleLocKey("").type(null)
				.build();

		Gcm gcm = new Gcm.GcmBuilder().collapseKey(null).delayWhileIdle(null).icon(null).interactiveCategory(null)
				.lights(null).payload(null).priority(null).sound(null).style(null).sync(null).timeToLive(null)
				.visibility(null)

				.collapseKey("").delayWhileIdle(null).icon("").interactiveCategory("").lights(null).payload(null)
				.priority(null).sound("").style(null).sync(null).timeToLive(null).visibility(null).build();

		ChromeWeb chromeWeb = new ChromeWeb.ChromeWebBuilder().iconUrl(null).payload(null).timeToLive(null).title(null)
				.iconUrl("").payload(null).timeToLive(null).title("").build();

		FirefoxWeb firefoxWeb = new FirefoxWeb.FirefoxWebBuilder().iconUrl(null).payload(null).timeToLive(null)
				.title(null)

				.iconUrl("").payload(null).timeToLive(null).title("").build();

		SafariWeb safariWeb = new SafariWeb.SafariWebBuilder().action(null).title(null).urlArgs(null).action("")
				.title("").urlArgs(null).build();

		ChromeAppExt chromeAppExt = new ChromeAppExt.ChromeAppExtBuilder().collapseKey(null).delayWhileIdle(null)
				.iconUrl(null).payload(null).timeToLive(null).title(null).collapseKey("").delayWhileIdle(null)
				.iconUrl("").payload(null).timeToLive(null).title("").build();

		Settings settings = new Settings.SettingsBuilder().apns(apns).gcm(gcm).chromeWeb(chromeWeb)
				.chromeAppExt(chromeAppExt).firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();

		Target target = new Target.TargetBuilder().deviceIds(null).platforms(null).tagNames(null).userIds(null).build();

		JSONObject notification = builder.message(message).settings(settings).target(target).build();

		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
}
