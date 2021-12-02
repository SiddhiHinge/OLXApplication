package com.olx.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.olx.entity.BlacklistTokenDocument;

public interface BlacklistTokenRepository extends MongoRepository<BlacklistTokenDocument, ObjectId>{

	BlacklistTokenDocument findByTokenEquals(String token);
}
