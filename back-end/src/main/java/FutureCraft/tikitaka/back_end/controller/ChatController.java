package FutureCraft.tikitaka.back_end.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import FutureCraft.tikitaka.back_end.common.MessageType;
import FutureCraft.tikitaka.back_end.dto.object.chat.ChatMessageDto;
import FutureCraft.tikitaka.back_end.dto.object.chat.UpdateReadMessageDto;
import FutureCraft.tikitaka.back_end.service.ChatService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/send")
    public void sendMessage(@Payload ChatMessageDto requestDto, Principal principal) {
        requestDto.setType(MessageType.TEXT);
        requestDto.setSenderId(principal.getName());
        chatService.sendMessage(requestDto);
    }

    @PostMapping("/app/send/file")
    public void sendFile(@RequestParam("files") List<MultipartFile> files, @RequestParam("chatRoomId") Integer chatRoomId, @AuthenticationPrincipal UserDetails userDetails) {
        chatService.sendFile(files, chatRoomId, userDetails.getUsername());
    }

    @MessageMapping("/update/read")
    public void read(@Payload UpdateReadMessageDto updateReadMessageDto, Principal principal) {
        chatService.updateLastRead(principal.getName(), updateReadMessageDto);
    }
}
