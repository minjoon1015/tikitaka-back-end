package FutureCraft.tikitaka.back_end.service;

import org.springframework.http.ResponseEntity;

import FutureCraft.tikitaka.back_end.dto.request.chat.ChatRoomCreateRequestDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.ChatRoomCreateResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetAddableListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetChatMembersReadInfoResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetChatMessageListResponseDto;
import FutureCraft.tikitaka.back_end.dto.response.chat.GetChatRoomListResponseDto;

public interface ChatRoomService {
    ResponseEntity<? super ChatRoomCreateResponseDto> create(String id, ChatRoomCreateRequestDto requestDto);
    ResponseEntity<? super GetChatRoomListResponseDto> getList(String id);
    ResponseEntity<? super GetAddableListResponseDto> getAddableList(String id, Integer chatRoomId);
    ResponseEntity<? super GetAddableListResponseDto> getSearchAddableList(String id, Integer chatRoomId, String keyword);
    ResponseEntity<? super GetChatMessageListResponseDto> getHistory(Integer chatRoomId, Integer messageId, String id);
    ResponseEntity<? super GetChatMembersReadInfoResponseDto> getMembersReadInfo(String id, Integer chatRoomId);
}
