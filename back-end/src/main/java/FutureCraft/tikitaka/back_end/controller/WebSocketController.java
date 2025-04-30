package FutureCraft.tikitaka.back_end.controller;

import java.time.LocalDateTime;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatMessageRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatMessageResponseDto;
import FutureCraft.tikitaka.back_end.provider.JwtProvider;
import FutureCraft.tikitaka.back_end.service.MessageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final JwtProvider jwtProvider;
    private final MessageService messageService;

    @MessageMapping("/send")
    public void send(@Payload ChatMessageRequestDto requestDto, @Header("token") String token) {
        String senderId = jwtProvider.validate(token);
        requestDto.setSenderId(senderId);
        requestDto.setWriteDateTime(LocalDateTime.now());
        StringBuffer topic = new StringBuffer("topic/");
        topic.append(requestDto.getChatId());
        ChatMessageResponseDto responseDto = messageService.save(requestDto);

        if (responseDto == null) {
            messagingTemplate.convertAndSend(topic.toString(), "메시지 전송 실패");
        }
        else {
            messagingTemplate.convertAndSend(topic.toString(), responseDto);
        }
    }
}
