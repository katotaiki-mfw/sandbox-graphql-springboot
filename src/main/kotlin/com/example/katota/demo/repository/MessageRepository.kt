import org.springframework.stereotype.Service

@Service
class MessageRepository {
    val messages = mutableMapOf<String, Message>()
}