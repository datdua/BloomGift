// package com.example.bloomgift.controllers.Chatting;

// import java.util.Map;
// import java.util.concurrent.CompletableFuture;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.bloomgift.model.Message;
// import com.example.bloomgift.service.ChatService;

// @RestController
// @RequestMapping("/api/customer/chats")
// public class ChatControllerByCustomer {
    
//     private final ChatService chatService;

//     @Autowired
//     public ChatControllerByCustomer(ChatService chatService) {
//         this.chatService = chatService;
//     }

//     @PostMapping("/create")
//     public void createChat(@RequestParam int storeId, @RequestParam int accountId) {
//         chatService.createChat(storeId, accountId);
//     }

//     @PostMapping("/{chatId}/message")
//     public void sendMessage(@PathVariable String chatId, @RequestBody Message message) {
//         chatService.sendMessage(chatId, message);
//     }

//     @GetMapping("/{chatId}/messages")
//     public CompletableFuture<Map<String, Message>> getMessages(@PathVariable String chatId) {
//         return chatService.getChatMessages(chatId);
//     }

// }
