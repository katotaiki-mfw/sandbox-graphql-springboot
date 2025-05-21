package com.example.katota.demo.resolver

import java.util.UUID
import com.example.katota.demo.model.Message
import com.example.katota.demo.model.MessageInput
import com.example.katota.demo.repository.MessageRepository
import org.springframework.stereotype.Controller
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.Argument

@Controller
class MessageResolver(private val messageRepository: MessageRepository) {
    @QueryMapping
    fun getMessage(@Argument id: String): Message? {
        return messageRepository.messages[id]
    }

    @QueryMapping
    fun getMessages(): List<Message> {
        return messageRepository.messages.values.toList()
    }

    @QueryMapping
    fun searchMessages(
        @Argument author: String?, 
        @Argument content: String?
    ): List<Message> {
        return messageRepository.messages.values.filter { message ->
            (author.isNullOrBlank() || message.author.contains(author, ignoreCase = true)) &&
            (content.isNullOrBlank() || message.content.contains(content, ignoreCase = true))
        }
    }

    @MutationMapping
    fun createMessage(@Argument input: MessageInput): Message {
        val id = UUID.randomUUID().toString()

        val message = Message(id, input.content, input.author)
        messageRepository.messages[id] = message
        return message
    }

    @MutationMapping
    fun updateMessage(@Argument id: String, @Argument input: MessageInput): Message {
        val message = messageRepository.messages[id]
        if (message == null) {
            throw IllegalArgumentException("Message not found")
        }
        val newMessage = Message(id, input.content, input.author)
        messageRepository.messages[id] = newMessage
        return newMessage
    }

    @MutationMapping
    fun deleteMessage(@Argument id: String): Message {
        val message = messageRepository.messages[id]
        if (message == null) {
            throw IllegalArgumentException("Message not found")
        }
        messageRepository.messages.remove(id)
        return message
    }
}