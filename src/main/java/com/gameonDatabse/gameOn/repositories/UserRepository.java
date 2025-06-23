package com.gameonDatabse.gameOn.repositories;

import com.gameonDatabse.gameOn.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    // Você pode adicionar métodos personalizados aqui, se necessário
}
