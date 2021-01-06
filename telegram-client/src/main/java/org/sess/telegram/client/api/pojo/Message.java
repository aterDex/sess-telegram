package org.sess.telegram.client.api.pojo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.apache.tomcat.jni.Poll;
import org.sess.telegram.client.api.helper.UnixDataToLocalDateTimeDeserializer;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class Message {

    /**
     * Unique message identifier inside this chat
     */
    private long message_id;

    /**
     * Optional. Sender, empty for messages sent to channels
     */
    private User from;

    /**
     * Optional. Sender of the message, sent on behalf of a chat. The channel itself for channel messages. The supergroup itself for messages from anonymous group administrators. The linked channel for messages automatically forwarded to the discussion group
     */
    private Chat sender_chat;
    @JsonDeserialize(using = UnixDataToLocalDateTimeDeserializer.class)
    private ZonedDateTime date;
    private Chat chat;
    private User forward_from;
    private Chat forward_from_chat;
    private Integer forward_from_message_id;
    private Integer forward_signature;
    private String forward_sender_name;
    @JsonDeserialize(using = UnixDataToLocalDateTimeDeserializer.class)
    private ZonedDateTime forward_date;
    private Message message;
    private User via_bot;
    @JsonDeserialize(using = UnixDataToLocalDateTimeDeserializer.class)
    private ZonedDateTime edit_date;
    private String media_group_id;
    private String author_signature;
    private String text;
    private List<MessageEntity> entities;
    private Animation animation;
    private Audio audio;
    private Document document;
    private List<PhotoSize> photo;
    private Sticker sticker;
    private Video video;
    private VideoNote video_note;
    private Voice voice;
    private String caption;
    private List<MessageEntity> caption_entities;
    private Contact contact;
    private Dice dice;
    private Game game;
    private Poll poll;
    private Venue venue;
    private Location location;
    private List<User> new_chat_members;
    private User left_chat_member;
    private String new_chat_title;
    private List<PhotoSize> new_chat_photo;
    private Boolean delete_chat_photo;
    private Boolean group_chat_created;
    private Boolean supergroup_chat_created;
    private Boolean channel_chat_created;
    private Long migrate_to_chat_id;
    private Long migrate_from_chat_id;
    private Message pinned_message;
    private Invoice invoice;
    private SuccessfulPayment successful_payment;
    private String connected_website;
    private PassportData passport_data;
    private ProximityAlertTriggered proximity_alert_triggered;
    private InlineKeyboardMarkup reply_markup;
}
