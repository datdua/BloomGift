package com.example.bloomgift.service;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bloomgift.model.Chat;
import com.example.bloomgift.model.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
@Service
public class ChatService {
    
    private final FirebaseDatabase firebaseDatabase;

    @Autowired
    public ChatService(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public void createChat(int storeId, int accountId) {
        String chatId = firebaseDatabase.getReference("chats").push().getKey();
        Chat chat = new Chat(storeId, accountId);
        firebaseDatabase.getReference("chats").child(chatId).setValueAsync(chat);
    }

    public void sendMessage(String chatId, Message message) {
        firebaseDatabase.getReference("chats").child(chatId).child("messages").push().setValueAsync(message);
    }

    public CompletableFuture<Map<String, Message>> getChatMessages(String chatId) {
        CompletableFuture<Map<String, Message>> future = new CompletableFuture<>();
        firebaseDatabase.getReference("chats").child(chatId).child("messages")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String, Message> messages = new HashMap<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Message message = snapshot.getValue(Message.class);
                            messages.put(snapshot.getKey(), message);
                        }
                        future.complete(messages);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        future.completeExceptionally(databaseError.toException());
                    }
                });
        return future;
    }
}