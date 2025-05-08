package com.randyn1080.socialmediapepspringproject.repository;

import com.randyn1080.socialmediapepspringproject.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

}
