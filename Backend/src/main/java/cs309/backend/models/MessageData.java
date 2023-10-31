package cs309.backend.models;

import cs309.backend.jpa.entity.MessageEntity;
import cs309.backend.jpa.entity.user.UserEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record MessageData(
        @NotNull int id,
        @NotNull UserEntity sender,
        @NotNull UserEntity receiver,
        @NotNull @NotBlank String content,
        @NotNull Date sentDate,
        @NotNull MessageEntity.MessageType messageType
) {
    public static MessageData fromEntity(MessageEntity ent) {
        return new MessageData(
                ent.getId(),
                ent.getSender(),
                ent.getReceiver(),
                ent.getContent(),
                ent.getSentDate(),
                ent.getMessageType()
        );
    }

    public MessageEntity toEntity() {
        // Creating a new MessageEntity and setting the values from MessageData.
        MessageEntity entity = new MessageEntity();
        entity.setId(this.id);
        entity.setSender(this.sender);
        entity.setReceiver(this.receiver);
        entity.setContent(this.content);
        entity.setSentDate(this.sentDate);
        entity.setMessageType(this.messageType);

        return entity;
    }

}
