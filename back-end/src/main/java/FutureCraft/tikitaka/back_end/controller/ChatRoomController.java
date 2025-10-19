package FutureCraft.tikitaka.back_end.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomCreateRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomCreateResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetAddableListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetChatMembersReadInfoResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetChatMessageListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetChatRoomListResponseDto;
import FutureCraft.tikitaka.back_end.service.ChatRoomService;
import FutureCraft.tikitaka.back_end.service.implement.ChatRoomServiceImplement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping("/create")
    public ResponseEntity<? super ChatRoomCreateResponseDto> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ChatRoomCreateRequestDto requestDto) {
        return chatRoomService.create(userDetails.getUsername(), requestDto);
    }

    @GetMapping("/get/list")
    public ResponseEntity<? super GetChatRoomListResponseDto> getChatRoomList(@AuthenticationPrincipal UserDetails userDetails) {
        return chatRoomService.getList(userDetails.getUsername());
    }

    @GetMapping("/get/addable/list")
    public ResponseEntity<? super GetAddableListResponseDto> getAddableUserList(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("chatRoomId") Integer chatRoomId) {
        return chatRoomService.getAddableList(userDetails.getUsername(), chatRoomId);
    }

    @GetMapping("/get/addable/search")
    public ResponseEntity<? super GetAddableListResponseDto> searchAddableUserList(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("chatRoomId") Integer chatRoomId, @RequestParam("keyword") String keyword){
        return chatRoomService.getSearchAddableList(userDetails.getUsername(), chatRoomId, keyword);
    }

    @GetMapping("/get/history")
    public ResponseEntity<? super GetChatMessageListResponseDto> getHistory(@RequestParam("chatRoomId") Integer chatRoomId, @RequestParam(value = "messageId", required = false) Integer messageId, @AuthenticationPrincipal UserDetails userDetails) {
        return chatRoomService.getHistory(chatRoomId, messageId, userDetails.getUsername());
    }

    @GetMapping("/get/members/read_info")
    public ResponseEntity<? super GetChatMembersReadInfoResponseDto> getMembersReadInfo(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("chatRoomId") Integer chatRoomId) {
        return chatRoomService.getMembersReadInfo(userDetails.getUsername(), chatRoomId);
    }
}
