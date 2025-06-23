package com.gameonDatabse.gameOn.repositories;

import com.gameonDatabse.gameOn.models.UserPosts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPostsRepository extends JpaRepository<UserPosts, Long> {
    // Métodos personalizados podem ser adicionados aqui também
}
