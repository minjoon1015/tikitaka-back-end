package FutureCraft.tikitaka.back_end.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomAddFriendListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomAddRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomCreateRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomAddFriendListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomAddResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomCreateResponseDto;
import FutureCraft.tikitaka.back_end.service.ChatRoomService;
import FutureCraft.tikitaka.back_end.service.UserChatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat/")
@RequiredArgsConstructor
public class ChatController {
    private final ChatRoomService chatRoomService;
    private final UserChatService userChatService;

    @PostMapping("create")
    public ResponseEntity<? super ChatRoomCreateResponseDto> create(@RequestBody ChatRoomCreateRequestDto requestDto, @AuthenticationPrincipal String id) {
        requestDto.setManagerUserId(id);
        List<String> participants = requestDto.getParticipants();
        participants.add(id);
        requestDto.setParticipants(participants);
        return chatRoomService.create(requestDto);
    }

    @GetMapping("add/list/{chatId}")
    public ResponseEntity<? super ChatRoomAddFriendListResponseDto> addUserList(@PathVariable("chatId") Integer chatId, @AuthenticationPrincipal String id) {
        ChatRoomAddFriendListRequestDto requestDto = new ChatRoomAddFriendListRequestDto(id, chatId);
        return userChatService.addList(requestDto);
    }

    @PostMapping("add")
    public ResponseEntity<? super ChatRoomAddResponseDto> add(@RequestBody ChatRoomAddRequestDto requestDto) {
        return userChatService.add(requestDto);
    }
}
