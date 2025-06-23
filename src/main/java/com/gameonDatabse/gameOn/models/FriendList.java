package com.gameonDatabse.gameOn.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "friend_list")
public class FriendList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "userId não pode ser nulo")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "friendId não pode ser nulo")
    @Column(name = "friend_id", nullable = false)
    private Long friendId;

    @Column(name = "confirmation", nullable = false)
    private boolean confirmation = false;

    @Column(name = "send", nullable = false)
    private boolean send = false;

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public boolean isConfirmation() {
        return confirmation;
    }

    public boolean isSend() {
        return send;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public void setConfirmation(boolean confirmation) {
        this.confirmation = confirmation;
    }

    public void setSend(boolean send) {
        this.send = send;
    }
}