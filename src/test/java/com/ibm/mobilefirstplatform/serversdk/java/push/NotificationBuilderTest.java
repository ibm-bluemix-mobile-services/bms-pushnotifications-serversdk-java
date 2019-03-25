package com.ibm.mobilefirstplatform.serversdk.java.push;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mobilefirstplatform.serversdk.java.push.APNs.Builder.APNSNotificationType;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.Builder.FCMPriority;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.Builder.Visibility;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMLights;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMLights.Builder.FCMLED;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMStyle;
import com.ibm.mobilefirstplatform.serversdk.java.push.FCM.FCMStyle.Builder.FCMStyleTypes;
import com.ibm.mobilefirstplatform.serversdk.java.push.Target.Builder.Platform;

public class NotificationBuilderTest {

private static JSONObject generateJSON(Object obj) {
		
		ObjectMapper mapper = new ObjectMapper();
		
		String jsonString = null;
		try {
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			jsonString = mapper.writeValueAsString(obj);

		} catch (JsonProcessingException exception) {
			exception.printStackTrace();
			
		}

		JSONObject json = jsonString != null ? new JSONObject(jsonString) : new JSONObject();

		return json;
	}

	@Test
	public void shouldBuildWithJustAlert() {
		String testAlert = "testMessage";

		Message message = new Message.Builder().alert(testAlert).build();
		
		Notification notificationObj = new Notification.Builder().message(message).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).build();
		
		JSONObject notification = generateJSON(model);
		
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldThrowExceptionWhenGivenANullMessage() {
		try {
			Message message = new Message.Builder().alert(null).build();
			new Notification.Builder().message(message).build();
		} catch (Throwable t) {
			// Return to mark as successful
			return;
		}
		fail("Exception not thrown.");
	}

	@Test
	public void shouldAllowAllOtherParametersToBeOptionalAndNotIncludeThemInTheNotificationIfNull() {

		String testAlert = "testMessage";
		

		Message message = new Message.Builder().alert("testMessage").url(null).build();

		APNs apns = new APNs.Builder().badge(null).attachmentUrl(null).interactiveCategory(null).iosActionKey(null)
				.launchImage(null).locArgs(null).locKey(null).payload(null).sound(null).subtitle(null).title(null)
				.titleLocArgs(null).titleLocKey(null).type(null).build();

		FCM fcm = new FCM.Builder().androidTitle(null).collapseKey(null).delayWhileIdle(null).icon(null).interactiveCategory(null)
				.lights(null).payload(null).priority(null).sound(null).style(null).sync(null).timeToLive(null)
				.visibility(null).build();

		ChromeAppExt chromeAppExt = new ChromeAppExt.Builder().collapseKey(null).delayWhileIdle(null)
				.iconUrl(null).payload(null).title(null).build();

		ChromeWeb chromeWeb = new ChromeWeb.Builder().iconUrl(null).payload(null).timeToLive(null).title(null)
				.build();

		FirefoxWeb firefoxWeb = new FirefoxWeb.Builder().iconUrl(null).payload(null).timeToLive(null)
				.title(null).build();

		SafariWeb safariWeb = new SafariWeb.Builder().action(null).title(null).urlArgs(null).build();

		Settings settings = new Settings.Builder().apns(apns).fcm(fcm).chromeAppExt(chromeAppExt)
				.chromeWeb(chromeWeb).firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();

		Target target = new Target.Builder().deviceIds(null).platforms(null).tagNames(null).userIds(null).build();

		Notification notificationObj = new Notification.Builder().message(message).target(target).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).settings(notificationObj.getSettings()).target(notificationObj.getTarget()).build();
		
		JSONObject notification = generateJSON(model);

		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureAPNSSettings() {

		String testAlert = "testMessage";

		Message message = new Message.Builder().alert(testAlert).build();

		APNs apns = new APNs.Builder().badge(1).interactiveCategory("testinteractiveCategory")
				.iosActionKey("testiOSactionKey").payload(new JSONObject()).sound("testsoundFile")
				.type(APNSNotificationType.DEFAULT).titleLocKey("testtitlelocKey").locKey("testlocKey")
				.launchImage("testlaunchImage").titleLocArgs(new String[] { "testtitlelocArgs1", "testtitlelocArgs2" })
				.locArgs(new String[] { "testlocArgs1", "testlocArgs" }).title("testtitle").subtitle("testSubtitle")
				.attachmentUrl("testattachmentUrl").build();

		Settings settings = new Settings.Builder().apns(apns).build();

		Notification notificationObj = new Notification.Builder().message(message).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).settings(notificationObj.getSettings()).build();
		
		JSONObject notification = generateJSON(model);

		checkAPNSSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureFCMSettings() {
		String testAlert = "testMessage";
		
		Message message = new Message.Builder().alert(testAlert).build();

		FCMStyle fcmstyle = new FCMStyle.Builder().type(FCMStyleTypes.BIGTEXT_NOTIFICATION).text("text")
				.title("title").url("url").lines(new String[] { "line1" }).build();

		FCMLights fcmlights = new FCMLights.Builder().ledArgb(FCMLED.BLACK).ledOffMs(1).ledOnMs(1).build();

		FCM fcm = new FCM.Builder().androidTitle("title").collapseKey("testcollapseKey").interactiveCategory("testinteractiveCategory")
				.delayWhileIdle(true).payload(new JSONObject()).priority(FCMPriority.MIN).sound("testsoundFile")
				.timeToLive(42).icon("testicon").visibility(Visibility.PUBLIC).sync(true).style(fcmstyle)
				.lights(fcmlights).build();

		Settings settings = new Settings.Builder().fcm(fcm).build();

		Notification notificationObj = new Notification.Builder().message(message).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).settings(notificationObj.getSettings()).build();
		
		JSONObject notification = generateJSON(model);

		checkFCMSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureChromeSettings() {
		String testAlert = "testMessage";
	
		Message message = new Message.Builder().alert(testAlert).build();

		ChromeWeb chromeWeb = new ChromeWeb.Builder().title("testtitle").iconUrl("testiconUrl").timeToLive(42)
				.payload(new JSONObject()).build();

		Settings settings = new Settings.Builder().chromeWeb(chromeWeb).build();

		Notification notificationObj = new Notification.Builder().message(message).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).settings(notificationObj.getSettings()).build();
		
		JSONObject notification = generateJSON(model);

		checkChromeSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureChromeExtSettings() {
		String testAlert = "testMessage";
	
		Message message = new Message.Builder().alert(testAlert).build();

		ChromeAppExt chromeAppExt = new ChromeAppExt.Builder().collapseKey("testcollapseKey")
				.delayWhileIdle(true).title("testtitle").iconUrl("testiconUrl").timeToLive(42).payload(new JSONObject())
				.build();

		Settings settings = new Settings.Builder().chromeAppExt(chromeAppExt).build();

		Notification notificationObj = new Notification.Builder().message(message).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).settings(notificationObj.getSettings()).build();
		
		JSONObject notification = generateJSON(model);


		checkChromeExtensionSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureFirefoxSettings() {
		String testAlert = "testMessage";
		
		Message message = new Message.Builder().alert(testAlert).build();

		FirefoxWeb firefoxWeb = new FirefoxWeb.Builder().title("testtitle").iconUrl("testiconUrl")
				.timeToLive(42).payload(new JSONObject()).build();

		Settings settings = new Settings.Builder().firefoxWeb(firefoxWeb).build();

		Notification notificationObj = new Notification.Builder().message(message).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).settings(notificationObj.getSettings()).build();
		
		JSONObject notification = generateJSON(model);

		checkFirefoxSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureSafariSettings() {
		String testAlert = "testMessage";

		Message message = new Message.Builder().alert(testAlert).build();

		SafariWeb safariWeb = new SafariWeb.Builder().title("testtitle")
				.urlArgs(new String[] { "testUrlArgs1", "testUrlArgs2" }).action("testaction").build();

		Settings settings = new Settings.Builder().safariWeb(safariWeb).build();

		Notification notificationObj = new Notification.Builder().message(message).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).settings(notificationObj.getSettings()).build();
		
		JSONObject notification = generateJSON(model);

		checkSafariSettings(notification);

		// Should also still have the message in the notification:
		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}

	@Test
	public void shouldAllowToConfigureAllSettingsSimultaneouslyWithoutOverridingEachOther() {
		String testAlert = "testMessage";

		Message message = new Message.Builder().alert(testAlert).build();

		APNs apns = new APNs.Builder().badge(1).interactiveCategory("testinteractiveCategory")
				.iosActionKey("testiOSactionKey").payload(new JSONObject()).sound("testsoundFile")
				.type(APNSNotificationType.DEFAULT).titleLocKey("testtitlelocKey").locKey("testlocKey")
				.launchImage("testlaunchImage").titleLocArgs(new String[] { "testtitlelocArgs1", "testtitlelocArgs2" })
				.locArgs(new String[] { "testlocArgs1", "testlocArgs" }).title("testtitle").subtitle("testSubtitle")
				.attachmentUrl("testattachmentUrl").build();

		FCMStyle fcmstyle = new FCMStyle.Builder().type(FCMStyleTypes.BIGTEXT_NOTIFICATION).text("text")
				.title("title").url("url").lines(new String[] { "line1" }).build();

		FCMLights fcmlights = new FCMLights.Builder().ledArgb(FCMLED.BLACK).ledOffMs(1).ledOnMs(1).build();

		FCM fcm = new FCM.Builder().androidTitle("androidTitle").collapseKey("testcollapseKey").interactiveCategory("testinteractiveCategory")
				.delayWhileIdle(true).payload(new JSONObject()).priority(FCMPriority.MIN).sound("testsoundFile")
				.timeToLive(42).icon("testicon").visibility(Visibility.PUBLIC).sync(true).style(fcmstyle)
				.lights(fcmlights).build();

		ChromeWeb chromeWeb = new ChromeWeb.Builder().title("testtitle").iconUrl("testiconUrl").timeToLive(42)
				.payload(new JSONObject()).build();

		ChromeAppExt chromeAppExt = new ChromeAppExt.Builder().collapseKey("testcollapseKey")
				.delayWhileIdle(true).title("testtitle").iconUrl("testiconUrl").timeToLive(42).payload(new JSONObject())
				.build();
		FirefoxWeb firefoxWeb = new FirefoxWeb.Builder().title("testtitle").iconUrl("testiconUrl")
				.timeToLive(42).payload(new JSONObject()).build();

		SafariWeb safariWeb = new SafariWeb.Builder().title("testtitle")
				.urlArgs(new String[] { "testUrlArgs1", "testUrlArgs2" }).action("testaction").build();

		Settings settings = new Settings.Builder().apns(apns).fcm(fcm).chromeWeb(chromeWeb)
				.chromeAppExt(chromeAppExt).firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();
		
		Notification notificationObj = new Notification.Builder().message(message).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).settings(notificationObj.getSettings()).build();
		
		JSONObject notification = generateJSON(model);


		// Check APNS Settings

		checkAPNSSettings(notification);

		// Check FCM Settings

		checkFCMSettings(notification);

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

	private void checkFCMSettings(JSONObject notification) {
		assertTrue(notification.has("settings"));
		assertTrue(notification.getJSONObject("settings").has("fcm"));

		JSONObject fcm = notification.getJSONObject("settings").getJSONObject("fcm");

		assertTrue(fcm.has("collapseKey"));
		assertEquals("testcollapseKey", fcm.getString("collapseKey"));

		assertTrue(fcm.has("interactiveCategory"));
		assertEquals("testinteractiveCategory", fcm.getString("interactiveCategory"));

		assertTrue(fcm.has("delayWhileIdle"));
		assertEquals(true, fcm.getBoolean("delayWhileIdle"));

		assertTrue(fcm.has("payload"));
		assertNotNull(fcm.getJSONObject("payload"));
		assertTrue(fcm.getJSONObject("payload").keySet().isEmpty());

		assertTrue(fcm.has("priority"));
		assertEquals(FCMPriority.MIN.name(), fcm.getString("priority"));

		assertTrue(fcm.has("sound"));
		assertEquals("testsoundFile", fcm.getString("sound"));

		assertTrue(fcm.has("timeToLive"));
		assertEquals(42, fcm.getInt("timeToLive"));

		assertTrue(fcm.has("icon"));
		assertEquals("testicon", fcm.getString("icon"));

		assertTrue(fcm.has("visibility"));
		assertEquals(Visibility.PUBLIC.name(), fcm.getString("visibility"));

		assertTrue(fcm.has("sync"));
		assertEquals(true, fcm.getBoolean("sync"));

		assertTrue(fcm.has("style"));
		JSONObject fcmJson = fcm.getJSONObject("style");
		assertEquals(FCMStyleTypes.BIGTEXT_NOTIFICATION.name(), fcmJson.get("type"));
		assertEquals("text", fcmJson.get("text"));
		assertEquals("title", fcmJson.get("title"));
		assertEquals("url", fcmJson.get("url"));

		assertTrue(fcmJson.has("lines"));
		JSONArray jsonlocArgs = fcmJson.getJSONArray("lines");
		assertEquals("line1", jsonlocArgs.get(0));

		assertTrue(fcm.has("lights"));
		JSONObject fcmlightsJson = fcm.getJSONObject("lights");
		assertEquals(FCMLED.BLACK.name(), fcmlightsJson.get("ledArgb"));
		assertEquals(1, fcmlightsJson.get("ledOffMs"));
		assertEquals(1, fcmlightsJson.get("ledOnMs"));
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
		assertTrue(apnsSettings.getJSONObject("payload").keySet().isEmpty());

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
		
		Message message = new Message.Builder().alert(testAlert).build();

		Target targetValue = new Target.Builder().deviceIds(new String[] { "device1", "device2" })
				.userIds(new String[] { "userId1", "userId2" })
				.platforms(new Platform[] { Platform.APPLE,
						Platform.GOOGLE, Platform.APPEXTCHROME,
						Platform.WEBCHROME, Platform.WEBFIREFOX,
						Platform.WEBSAFARI })
				.tagNames(new String[] { "tag1", "tag2" }).build();

		Notification notificationObj = new Notification.Builder().message(message).target(targetValue).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).target(notificationObj.getTarget()).build();
		
		JSONObject notification = generateJSON(model);


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
		
		Message message = new Message.Builder().alert(testAlert).url(null).build();

		APNs apns = new APNs.Builder().badge(null).attachmentUrl(null).interactiveCategory(null).iosActionKey(null)
				.launchImage(null).locArgs(null).locKey(null).payload(null).sound(null).subtitle(null).title(null)
				.titleLocArgs(null).titleLocKey(null).type(null)

				.badge(null).attachmentUrl("").interactiveCategory("").iosActionKey("").launchImage("").locArgs(null)
				.locKey("").payload(null).sound("").subtitle("").title("").titleLocArgs(null).titleLocKey("").type(null)
				.build();

		FCM fcm = new FCM.Builder().androidTitle(null).collapseKey(null).delayWhileIdle(null).icon(null).interactiveCategory(null)
				.lights(null).payload(null).priority(null).sound(null).style(null).sync(null).timeToLive(null)
				.visibility(null)

				.collapseKey("").delayWhileIdle(null).icon("").interactiveCategory("").lights(null).payload(null)
				.priority(null).sound("").style(null).sync(null).timeToLive(null).visibility(null).build();

		ChromeWeb chromeWeb = new ChromeWeb.Builder().iconUrl(null).payload(null).timeToLive(null).title(null)
				.iconUrl("").payload(null).timeToLive(null).title("").build();

		FirefoxWeb firefoxWeb = new FirefoxWeb.Builder().iconUrl(null).payload(null).timeToLive(null)
				.title(null)

				.iconUrl("").payload(null).timeToLive(null).title("").build();

		SafariWeb safariWeb = new SafariWeb.Builder().action(null).title(null).urlArgs(null).action("")
				.title("").urlArgs(null).build();

		ChromeAppExt chromeAppExt = new ChromeAppExt.Builder().collapseKey(null).delayWhileIdle(null)
				.iconUrl(null).payload(null).timeToLive(null).title(null).collapseKey("").delayWhileIdle(null)
				.iconUrl("").payload(null).timeToLive(null).title("").build();

		Settings settings = new Settings.Builder().apns(apns).fcm(fcm).chromeWeb(chromeWeb)
				.chromeAppExt(chromeAppExt).firefoxWeb(firefoxWeb).safariWeb(safariWeb).build();

		Target target = new Target.Builder().deviceIds(null).platforms(null).tagNames(null).userIds(null).build();
		
		Notification notificationObj = new Notification.Builder().message(message).target(target).settings(settings).build();
		
		PushMessageModel model = new PushMessageModel.Builder().message(notificationObj.getMessage()).target(notificationObj.getTarget()).settings(notificationObj.getSettings()).build();
		
		JSONObject notification = generateJSON(model);

		assertTrue(notification.has("message"));
		assertTrue(notification.getJSONObject("message").has("alert"));
		assertEquals(testAlert, notification.getJSONObject("message").getString("alert"));
	}
}
