package com.example.katota.demo.repository

import com.example.katota.demo.model.Message
import org.springframework.stereotype.Service

@Service
class MessageRepository {
    val messages = mutableMapOf<String, Message>()
}