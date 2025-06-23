package com.gameonDatabse.gameOn.repositories;

import com.gameonDatabse.gameOn.models.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendListRepository extends JpaRepository<FriendList, Long> {

    // Encontra todos os amigos de um usuário
    List<FriendList> findByUserId(Long userId);

    // Encontra todas as solicitações pendentes (não confirmadas) para um usuário
    List<FriendList> findByUserIdAndConfirmationFalse(Long userId);

    // Encontra uma solicitação pendente específica
    Optional<FriendList> findByUserIdAndFriendIdAndConfirmationFalse(Long userId, Long friendId);

    // Encontra todas as solicitações enviadas por um usuário
    List<FriendList> findByUserIdAndSendTrue(Long userId);

    // Verifica se uma relação de amizade existe
    boolean existsByUserIdAndFriendId(Long userId, Long friendId);

    // Verifica se uma solicitação de amizade foi enviada
    boolean existsByUserIdAndFriendIdAndSendTrue(Long userId, Long friendId);

    // Remove uma relação de amizade
    void deleteByUserIdAndFriendId(Long userId, Long friendId);
}