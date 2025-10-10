package FutureCraft.tikitaka.back_end.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import FutureCraft.tikitaka.back_end.entity.MessageAttachmentEntity;

public interface MessageAttachmentRepository extends JpaRepository<MessageAttachmentEntity, Integer> {
    
}
