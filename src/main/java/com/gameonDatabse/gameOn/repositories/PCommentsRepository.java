package com.gameonDatabse.gameOn.repositories;
import com.gameonDatabse.gameOn.models.PostComments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PCommentsRepository extends JpaRepository<PostComments, Long> {
    List<PostComments> findByPostId(Long postId);
}
