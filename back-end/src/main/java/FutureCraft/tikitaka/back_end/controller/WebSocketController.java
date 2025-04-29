// package FutureCraft.tikitaka.back_end.controller;

// import org.springframework.messaging.handler.annotation.MessageMapping;
// import org.springframework.messaging.handler.annotation.Payload;
// import org.springframework.messaging.simp.SimpMessagingTemplate;
// import org.springframework.stereotype.Controller;

// import FutureCraft.tikitaka.back_end.dto.test.TestDto;
// import lombok.RequiredArgsConstructor;

// @Controller
// @RequiredArgsConstructor
// public class ChatController {
//     private final SimpMessagingTemplate messagingTemplate;

//     @MessageMapping("/send")
//     public void sendMessage(@Payload TestDto testDto) {
//         System.out.println(testDto.getTopic());
//         System.out.println(testDto.getMessage());
//         messagingTemplate.convertAndSend(testDto.getTopic(), testDto);
//     }
// }
