package com.twitterdan.facade.chat.response.chat;

import com.twitterdan.domain.chat.Chat;
import com.twitterdan.domain.user.User;
import com.twitterdan.dto.chat.response.chat.PrivateChatResponse;
import com.twitterdan.facade.GeneralFacade;
import com.twitterdan.facade.chat.ChatUserMapper;
import com.twitterdan.facade.chat.response.message.LastChatMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateChatResponseMapper extends GeneralFacade<Chat, PrivateChatResponse> {
  private final ChatUserMapper chatUserMapper;
  private final LastChatMessageMapper lastChatMessageMapper;

  public PrivateChatResponseMapper(ChatUserMapper chatUserMapper, LastChatMessageMapper lastChatMessageMapper) {
    super(Chat.class, PrivateChatResponse.class);
    this.chatUserMapper = chatUserMapper;
    this.lastChatMessageMapper = lastChatMessageMapper;
  }

  @Override
  protected void decorateDto(PrivateChatResponse dto, Chat entity, User user) {
    List<User> users = entity.getUsers();
    User guestUser;

    if (user.equals(users.get(0))) {
      guestUser = users.get(1);
      dto.setAuthUser(chatUserMapper.convertToDto(users.get(0)));
      dto.setGuestUser(chatUserMapper.convertToDto(guestUser));
      dto.setTitle(guestUser.getName());
      dto.setUserTag(guestUser.getUserTag());
      dto.setAvatarImgUrl(guestUser.getAvatarImgUrl());
    } else {
      guestUser = users.get(0);
      dto.setAuthUser(chatUserMapper.convertToDto(users.get(1)));
      dto.setGuestUser(chatUserMapper.convertToDto(guestUser));
      dto.setTitle(guestUser.getName());
      dto.setUserTag(guestUser.getUserTag());
      dto.setAvatarImgUrl(guestUser.getAvatarImgUrl());
    }

    if (entity.getLastMessage() != null) {
      dto.setLastMessage(lastChatMessageMapper.convertToDto(entity.getLastMessage(), guestUser));
    }
  }
}