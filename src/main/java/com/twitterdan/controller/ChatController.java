package com.twitterdan.controller;

import com.twitterdan.dao.MessageRepository;
import com.twitterdan.domain.chat.Chat;
import com.twitterdan.domain.chat.ChatType;
import com.twitterdan.domain.chat.Message;
import com.twitterdan.domain.user.User;
import com.twitterdan.dto.chat.request.GroupChatRequest;
import com.twitterdan.dto.chat.request.PrivateChatRequest;
import com.twitterdan.dto.chat.response.ChatResponseAbstract;
import com.twitterdan.dto.chat.request.MessageRequest;
import com.twitterdan.dto.chat.response.MessageResponse;
import com.twitterdan.dto.chat.response.MessageResponseAbstract;
import com.twitterdan.facade.chat.MessageRequestMapper;
import com.twitterdan.facade.chat.request.GroupChatRequestMapper;
import com.twitterdan.facade.chat.request.PrivateChatRequestMapper;
import com.twitterdan.facade.chat.response.*;
import com.twitterdan.service.ChatService;
import com.twitterdan.service.MessageService;
import com.twitterdan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/chats")
public class ChatController {

  private final ChatService chatService;
  private final UserService userService;
  private final MessageService messageService;
  private final MessageResponseMapper messageResponseMapper;
  private final MessageRequestMapper messageRequestMapper;
  private final PrivateChatResponseMapper privateChatResponseMapper;
  private final GroupChatResponseMapper groupChatResponseMapper;
  private final PrivateChatRequestMapper privateChatRequestMapper;
  private final GroupChatRequestMapper groupChatRequestMapper;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final MessageRepository messageRepository;

  private final PrivateMessageResponseMapper privateMessageResponseMapper;
  private final GroupMessageResponseMapper groupMessageResponseMapper;

  @GetMapping
  public ResponseEntity<List<ChatResponseAbstract>> getChats(
    @RequestParam Long userId,
    @RequestParam int pageNumber,
    @RequestParam int pageSize
  ) {
    List<ChatResponseAbstract> chats = chatService.findAlLByUserId(userId, pageNumber, pageSize)
      .stream()
      .map(ch -> {
        if (ch.getType().equals(ChatType.PRIVATE)) {
          return privateChatResponseMapper.convertToDto(ch, userId);
        }
        return groupChatResponseMapper.convertToDto(ch);
      })
      .toList();
    return ResponseEntity.ok(chats);
  }

  private void saveFirstChatMessage(Long id, Chat chat, String text) {
    User user = userService.findById(id);
    Message message = messageService.saveFirstNewChatMessage(chat, user, text);
    chat.setLastMessage(message);
  }

  @GetMapping("/private")
  public ResponseEntity<ChatResponseAbstract> findPrivateChat(@RequestParam Long authUserId, @RequestParam Long guestUserId) {
    Chat chat = chatService.findPrivateChatByUsersIds(authUserId, guestUserId);

    return ResponseEntity.ok(privateChatResponseMapper.convertToDto(chat));
  }

  @PostMapping("/private")
  public ResponseEntity<ChatResponseAbstract> addPrivateChat(@RequestBody PrivateChatRequest privateChatRequest) {
    Chat chat = privateChatRequestMapper.convertToEntity(privateChatRequest);
    Chat savedChat = chatService.savePrivateChat(chat);
    Long authUserId = privateChatRequest.getAuthUserId();
    String text = privateChatRequest.getMessage();
    saveFirstChatMessage(authUserId, savedChat, text);

    return ResponseEntity.ok(privateChatResponseMapper.convertToDto(savedChat));
  }

  @PostMapping("/group")
  public ResponseEntity<ChatResponseAbstract> addGroupChat(@RequestBody GroupChatRequest groupChatRequest) {
    Chat chat = groupChatRequestMapper.convertToEntity(groupChatRequest);
    Chat savedChat = chatService.saveGroupChat(chat);
    Long authUserId = groupChatRequest.getAuthUserId();
    String text = groupChatRequest.getMessage();
    saveFirstChatMessage(authUserId, savedChat, text);

    return ResponseEntity.ok(groupChatResponseMapper.convertToDto(savedChat));
  }

  @GetMapping("/messages")
  public ResponseEntity<List<MessageResponseAbstract>> getMessages(@RequestParam Long chatId) {
    List<Message> messages = messageService.findByChatId(chatId);
    List<MessageResponseAbstract> messageResponses = messages.stream()
      .map(m -> {
        ChatType type = m.getChat().getType();

        if (type.equals(ChatType.PRIVATE)) {
          return privateMessageResponseMapper.convertToDto(m);
        }
        return groupMessageResponseMapper.convertToDto(m);

      })
      .toList();

    return ResponseEntity.ok(messageResponses);
  }

  @PostMapping("/messages")
  public ResponseEntity<MessageResponse> saveMessage(@RequestBody MessageRequest messageRequest) {
    Message message = messageRequestMapper.convertToEntity(messageRequest);
//    rabbitTemplate.convertAndSend("messages", message);
    Message savedMessage = messageService.save(message);
    return ResponseEntity.ok(messageResponseMapper.convertToDto(savedMessage));
  }

  @MessageMapping("/chat")
  public void saveGroupMessage(@RequestBody MessageRequest messageRequest) {
    System.out.println(messageRequest);
    Long authUserId = messageRequest.getUserId();
    Message message = messageRequestMapper.convertToEntity(messageRequest);
    Message savedMessage = messageService.save(message);
    ChatType type = savedMessage.getChat().getType();
    MessageResponseAbstract responseAbstract;

    if (type.equals(ChatType.PRIVATE)) {
      responseAbstract = privateMessageResponseMapper.convertToDto(savedMessage, authUserId);
    } else {
      responseAbstract = groupMessageResponseMapper.convertToDto(savedMessage, authUserId);
    }

    simpMessagingTemplate.convertAndSend("/topic/chat." + messageRequest.getChatId(), ResponseEntity.ok(responseAbstract));
  }

//  @GetMapping("/test")
//  public List<Chat> test(@RequestParam Long userId){
//    return chatService.test(userId);
//  }
}
