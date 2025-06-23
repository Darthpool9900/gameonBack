package com.gameonDatabse.gameOn.routesapi;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gameonDatabse.gameOn.models.UserModel;
import com.gameonDatabse.gameOn.models.UserPosts;
import com.gameonDatabse.gameOn.models.PostComments;
import com.gameonDatabse.gameOn.models.FriendList;
import com.gameonDatabse.gameOn.repositories.UserRepository;
import com.gameonDatabse.gameOn.repositories.UserPostsRepository;
import com.gameonDatabse.gameOn.repositories.PCommentsRepository;
import com.gameonDatabse.gameOn.repositories.FriendListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class RouterApi {

    private static final Logger logger = LoggerFactory.getLogger(RouterApi.class);

    private final UserRepository userRepository;
    private final UserPostsRepository userPostsRepository;
    private final PCommentsRepository pCommentsRepository;
    private final FriendListRepository friendListRepository;

    @Autowired
    public RouterApi(UserRepository userRepository,
                     UserPostsRepository userPostsRepository,
                     PCommentsRepository pCommentsRepository,
                     FriendListRepository friendListRepository) {
        this.userRepository = userRepository;
        this.userPostsRepository = userPostsRepository;
        this.pCommentsRepository = pCommentsRepository;
        this.friendListRepository = friendListRepository;
    }

    @GetMapping("/hello")
    public String helloPage() {
        return "Hello world!";
    }

    @PostMapping("/user_create")
    public ResponseEntity<UserModel> createUser(@RequestBody UserModel userData) {
        UserModel savedUser = userRepository.save(userData);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @GetMapping("/get_user/{id}")
    public ResponseEntity<UserModel> getUser(@PathVariable long id) {
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/get_post/{id}")
    public ResponseEntity<UserPosts> getPostById(@PathVariable Long id) {
        Optional<UserPosts> postOptional = userPostsRepository.findById(id);
        if (postOptional.isPresent()) {
            return ResponseEntity.ok(postOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping(value = "/make_posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(
            @RequestPart("postData") String postDataJson,
            @RequestPart("file") MultipartFile file) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            UserPosts postData = objectMapper.readValue(postDataJson, UserPosts.class);

            String uploadDir = "Uploads/";
            Files.createDirectories(Paths.get(uploadDir));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + filename);
            Files.write(filePath, file.getBytes());

            postData.setMediaContent(filePath.toString());
            UserPosts savedPost = userPostsRepository.save(postData);

            return new ResponseEntity<>(savedPost, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();  // Garanta que exceção seja impressa
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar post: " + e.getMessage());
        }
    }


    @GetMapping("/get_posts")
    public ResponseEntity<List<UserPosts>> getAllPosts() {
        return ResponseEntity.ok(userPostsRepository.findAll());
    }

    @PostMapping("/post_comments")
    public ResponseEntity<PostComments> createComment(@RequestBody PostComments commentData) {
        logger.info("Recebido: id={}, uCommentId={}, postId={}, uComment={}",
                commentData.getId(), commentData.getUCommentId(), commentData.getPostId(), commentData.getUComment());

        PostComments savedComment = pCommentsRepository.save(commentData);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @GetMapping("/get_comments/{postId}")
    public ResponseEntity<List<PostComments>> getAllComments(@PathVariable long postId) {
        return ResponseEntity.ok(pCommentsRepository.findByPostId(postId));
    }

    @PostMapping("/add_friend")
    public ResponseEntity<?> addFriend(@Valid @RequestBody FriendList friendData) {
        logger.info("Recebido: userId={}, friendId={}, confirmation={}, send={}",
                friendData.getUserId(), friendData.getFriendId(), friendData.isConfirmation(), friendData.isSend());

        if (friendListRepository.existsByUserIdAndFriendId(friendData.getUserId(), friendData.getFriendId())) {
            return ResponseEntity.badRequest().body("Relação de amizade já existe");
        }

        friendData.setSend(true);
        friendData.setConfirmation(false);
        return ResponseEntity.status(HttpStatus.CREATED).body(friendListRepository.save(friendData));
    }

    @PatchMapping("/confirm_friend")
    public ResponseEntity<?> confirmFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        logger.info("Confirmando amizade: userId={}, friendId={}", userId, friendId);

        FriendList friendRequest = friendListRepository.findByUserIdAndFriendIdAndConfirmationFalse(userId,friendId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação de amizade não encontrada"));

        friendRequest.setConfirmation(true);
        friendListRepository.save(friendRequest);

        if (!friendListRepository.existsByUserIdAndFriendId(userId, friendId)) {
            FriendList reverseFriend = new FriendList();
            reverseFriend.setUserId(userId);
            reverseFriend.setFriendId(friendId);
            reverseFriend.setConfirmation(true);
            reverseFriend.setSend(false);
            friendListRepository.save(reverseFriend);
        }

        return ResponseEntity.ok("Amizade confirmada");
    }

    @DeleteMapping("/reject_friend")
    public ResponseEntity<?> rejectFriend(@RequestParam Long userId, @RequestParam Long friendId) {
        logger.info("Rejeitando amizade: userId={}, friendId={}", userId, friendId);

        FriendList friendRequest = friendListRepository.findByUserIdAndFriendIdAndConfirmationFalse(friendId, userId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitação de amizade não encontrada"));

        friendListRepository.delete(friendRequest);
        return ResponseEntity.ok("Solicitação de amizade rejeitada");
    }

    @PostMapping(value = "/upload_profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfileImage(
            @RequestParam("userId") Long userId,
            @RequestPart("file") MultipartFile file) {
        try {
            Optional<UserModel> userOptional = userRepository.findById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }

            String uploadDir = "ProfileImages/";
            Files.createDirectories(Paths.get(uploadDir));

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + filename);
            Files.write(filePath, file.getBytes());

            UserModel user = userOptional.get();
            user.setProfilePath(filePath.toString());
            userRepository.save(user);

            return ResponseEntity.ok().body("Imagem de perfil enviada com sucesso: " + filePath.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao fazer upload da imagem");
        }
    }

}