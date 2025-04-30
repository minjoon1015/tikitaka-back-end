package FutureCraft.tikitaka.back_end.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import FutureCraft.tikitaka.back_end.entity.document.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {
    
}
