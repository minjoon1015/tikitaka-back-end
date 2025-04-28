package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomAddFriendListRequestDto;
import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomCreateRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomAddFriendListResponseDto;
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
    public ResponseEntity<? super ChatRoomCreateResponseDto> create(@RequestBody ChatRoomCreateRequestDto requestDto) {
        return chatRoomService.create(requestDto);
    }

    @PostMapping("add/friend/list")
    public ResponseEntity<? super ChatRoomAddFriendListResponseDto> addUserList(@RequestBody ChatRoomAddFriendListRequestDto requestDto) {
        return userChatService.addList(requestDto);
    }
}
