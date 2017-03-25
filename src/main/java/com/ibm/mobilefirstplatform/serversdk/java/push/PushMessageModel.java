/*
 *     Copyright 2017 IBM Corp.
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.ibm.mobilefirstplatform.serversdk.java.push.NotificationBuilder.PushNotificationsPlatform;


@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class PushMessageModel {

    private Message message;
    private Target target;
    private Settings settings;


    public void setMessage(final Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setTarget(final Target target) {
        this.target = target;
    }

    public Target getTarget() {
        return target;
    }

    public void setSettings(final Settings settings) {
        this.settings = settings;
    }

    public Settings getSettings() {
        return settings;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static final class Message {

        private String alert;
        private String url;

        public void setAlert(final String alert) {
            this.alert = alert;
        }

        public String getAlert() {
            return alert;
        }

        public void setUrl(final String url) {
            this.url = url;
        }

        public String getUrl() {
            return url;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static final class Target {

        private String [] deviceIds = null;
        private String [] userIds = null;
        private String []  platforms = null;
        private String [] tagNames = null;

        public void setDeviceIds(final String [] deviceIds) {
            this.deviceIds = deviceIds;
        }

        public String [] getDeviceIds() {
            return deviceIds;
        }

        public void setUserIds(final String [] userIds) {
            this.userIds = userIds;
        }

        public String [] getUserIds() {
            return userIds;
        }

         public void setTagNames(final String [] tagNames) {
            this.tagNames = tagNames;
        }

        public String [] getTagNames() {
            return tagNames;
        }

        public void setPlatforms(final PushNotificationsPlatform [] platforms) {
        	
        	String [] platformArray = new String[platforms.length]; 
        	
        	for (int i=0;i<platforms.length;i++ ) {
    			platformArray[i]=platforms[i].getValue(); 
       	}
        
        	this.platforms = platformArray;
        }

        public String [] getPlatforms() {
            return  platforms;
        }
    }
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static final class Settings {

        private Apns apns;
        private Android gcm;
        private FirefoxWeb firefoxWeb;
        private ChromeWeb chromeWeb;
        private SafariWeb safariWeb;
        private ChromeAppExt chromeAppExt;
       

        public void setApns(final Apns apns) {
            this.apns = apns;
        }

        public Apns getApns() {
            return apns;
        }

        public void setGcm(final Android gcm) {
            this.gcm = gcm;
        }

        public Android getGcm() {
            return gcm;
        }

        public FirefoxWeb getFirefoxWeb() {
            return firefoxWeb;
        }

        public void setFirefoxWeb(final FirefoxWeb firefoxWeb) {
            this.firefoxWeb = firefoxWeb;
        }

        public ChromeWeb getChromeWeb() {
            return chromeWeb;
        }

        public void setSafariWeb(final SafariWeb safariWeb) {
            this.safariWeb = safariWeb;
        }

        public SafariWeb getSafariWeb() {
            return safariWeb;
        }

        public void setChromeWeb(final ChromeWeb chromeWeb) {
            this.chromeWeb = chromeWeb;
        }

        public ChromeAppExt getChromeAppExt() {
            return chromeAppExt;
        }

        public void setChromeAppExt(final ChromeAppExt chromeAppExt) {
            this.chromeAppExt = chromeAppExt;
        }
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Apns {

            private Integer badge;
            private String sound;
            private String iosActionKey;
            private String payload;
            private String category;
            private String interactiveCategory;
            private Type type;
            private String titleLocKey;
            private String locKey;
            private String launchImage;
            private String [] titleLocArgs;
            private String [] locArgs;
            private String subtitle;
            private String title;
            private String body;
            private String attachmentUrl;

            public final void setTitle(final String title) {
                this.title = title;
            }
            public final String getTitle() {
            	
                return title;
            }

            public final void setAttachmentUrl(final String url) {
                this.attachmentUrl = url;
            }

            public final String getAttachmentUrl() {
                return attachmentUrl;
            }

            public final void setSubtitle(final String subtitle) {
                this.subtitle = subtitle;
            }

            public final String getSubtitle() {
                return subtitle;
            }

            public final void setBody(final String body) {
                this.body = body;
            }

            public final String getBody() {
                return body;
            }

            public final void setBadge(final Integer badge) {
                this.badge = badge;
            }

            public final Integer getBadge() {
                return badge;
            }

            public final void setTitleLocKey(final String titleLocKey) {
                this.titleLocKey = titleLocKey;
            }

            public final String getTitleLocKey() {
                return titleLocKey;
            }

            public final void setLocKey(final String locKey) {
                this.locKey = locKey;
            }

            public final String getLocKey() {
                return locKey;
            }

            public final void setLaunchImage(final String launchImage) {
                this.launchImage = launchImage;
            }

            public final String getLaunchImage() {
                return launchImage;
            }

            public final void setTitleLocArgs(final String [] titleLocArgs) {
                this.titleLocArgs = titleLocArgs;
            }

            public final String [] getTitleLocArgs() {
                return titleLocArgs;
            }

            public final void setLocArgs(final String [] locArgs) {
                this.locArgs = locArgs;
            }

            public final String [] getLocArgs() {
                return locArgs;
            }

            public final void setSound(final String sound) {
                this.sound = sound;
            }

            public final String getSound() {
                return sound;
            }

            public final void setIosActionKey(final String iosActionKey) {
                this.iosActionKey = iosActionKey;
            }

            public final String getIosActionKey() {
                return iosActionKey;
            }

            public final void setPayload(final JsonNode payload) {
            	
            	this.payload = payload.toString();
            }

            @JsonRawValue
            public final String getPayload() {
                return this.payload;
            }

            public final void setCategory(final String category) {
                this.category = category;
            }

            public final String getCategory() {
                return category;
            }

            public final void setInteractiveCategory(final String interactiveCategory) {
                this.interactiveCategory = interactiveCategory;
            }

            public final String getInteractiveCategory() {
                return interactiveCategory;
            }

            public final void setType(final String type) {
                this.type = Enum.valueOf(Type.class, type.toUpperCase());
            }

            public final Type getType() {
                return type;
            }

            public enum Type {
                DEFAULT, SILENT, MIXED;
            }
        }
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Gcm {
            private String delayWhileIdle;
            private Integer timeToLive;
            private String collapseKey;
            private String payload;

            public final void setDelayWhileIdle(final String delayWhileIdle) {
                this.delayWhileIdle = delayWhileIdle;
            }

            public final String getDelayWhileIdle() {
                return delayWhileIdle;
            }

            public final void setTimeToLive(final Integer timeToLive) {
                this.timeToLive = timeToLive;
            }

            public final Integer getTimeToLive() {
                return timeToLive;
            }

            public final void setCollapseKey(final String collapseKey) {
                this.collapseKey = collapseKey;
            }

            public final String getCollapseKey() {
                return collapseKey;
            }

            public final void setPayload(final JsonNode payload) {
                this.payload = payload.toString();
            }

            @JsonRawValue
            public final String getPayload() {
                return this.payload;
            }
        }

        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static final class Android extends Gcm {
            private String sync;
            private String sound;
            private String category;
            private String interactiveCategory;
            private Priority priority;
            private Style style;
            private Visibility visibility;
            private String icon;

            
            public void setSound(final String sound) {
                this.sound = sound;
            }

            public String getSound() {
                return sound;
            }

            public void setPriority(final String priority) {
                this.priority = Enum.valueOf(Priority.class, priority.toUpperCase());
            }

            public Priority getPriority() {
                return priority;
            }

            public void setVisibility(final String visibility) {
                this.visibility = Enum.valueOf(Visibility.class, visibility.toUpperCase());
            }

            public Visibility getVisibility() {
                return visibility;
            }

            public void setIcon(final String icon) {
                this.icon = icon;
            }

            public String getIcon() {
                return icon;
            }

            public void setCategory(final String category) {
                this.category = category;
            }

            public String getCategory() {
                return category;
            }

            public void setInteractiveCategory(final String interactiveCategory) {
                this.interactiveCategory = interactiveCategory;
            }

            public String getInteractiveCategory() {
                return interactiveCategory;
            }

         

            public void setSync(final String sync) {
                this.sync = sync;
            }

            public String getSync() {
                return sync;
            }

            public void setStyle(final Style style)  {
                this.style = style;
            }

            public Style getStyle() {
                return style;
            }

          
            public enum Priority {
                MAX(2), HIGH(1), DEFAULT(0), LOW(-1), MIN(-2);

                private final int value;

                Priority(final int value) {
                    this.value = value;
                }

                public int getValue() {
                    return value;
                }
            }

            public enum Visibility {
                PUBLIC(1), PRIVATE(0), SECRET(-1);

                private final int value;

                Visibility(final int value) {
                    this.value = value;
                }

                public int getValue() {
                    return value;
                }
            }

            
        }
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static final class Style {
            private String type;
            private String url;
            private String title;
            private String text;
            private String [] lines;

            public void setType(final String type) {
                this.type = type;
            }

            public String getType() {
                return type;
            }

            public void setUrl(final String url) {
                this.url = url;
            }

            public String getUrl() {
                return url;
            }

            public void setTitle(final String title) {
                this.title = title;
            }

            public String getTitle() {
                return title;
            }

            public void setText(final String text) {
                this.text = text;
            }

            public String getText() {
                return text;
            }

            public void setLines(final String [] lines) {
                this.lines = lines;
            }

            public String [] getLines() {
                return lines;
            }
        }


        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class Web  {

            private String title;
            private String iconUrl;
            private Integer timeToLive;
            private String payload;

            public final void setTitle(final String title) {
                this.title = title;
            }

            public final String getTitle() {
                return title;
            }

            public final void setIconUrl(final String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public final String getIconUrl() {
                return iconUrl;
            }

            public final Integer getTimeToLive() {
                return timeToLive;
            }

            public final void setTimeToLive(final Integer timeToLive) {
                this.timeToLive = timeToLive;
            }

            public final void setPayload(final JsonNode payload) {
                this.payload = payload.toString();
            }

            @JsonRawValue
            public final String getPayload() {
                return this.payload;
            }
        }
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static class ChromeWeb extends Web {

        }
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static final class SafariWeb extends Apns {

            private String action;
            private String[] urlArgs;

            public String getAction() {
                return action;
            }

            public void setAction(final String action) {
                this.action = action;
            }

            public String[] getUrlArgs() {
                return urlArgs;
            }

            public void setUrlArgs(final String[] urlArgs) {
                this.urlArgs = urlArgs;
            }
        }

        public static class FirefoxWeb extends Web {

        }
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        public static final class ChromeAppExt extends Gcm {
            private String title;
            private String iconUrl;

            public void setTitle(final String title) {
                this.title = title;
            }

            public String getTitle() {
                return title;
            }

            public void setIconUrl(final String iconUrl) {
                this.iconUrl = iconUrl;
            }

            public String getIconUrl() {
                return iconUrl;
            }
        }


    }
}
