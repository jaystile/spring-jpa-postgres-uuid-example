package com.uuid_guide.server.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController()
@RequestMapping("/user_profile/v1")
public class UserController {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public UserController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    /**
     * Test connectivity or preflight connections
     * @return "ACK"
     */
    @GetMapping("/acknowledge")
    String acknowledge() {
      return "ACK";
    }

    @GetMapping("/")
    Page<UserProfile> getAllUserProfiles(Pageable pageable) {
        return userProfileRepository.findAll(pageable);
    }

    @GetMapping("/{id}")
    UserProfile getById(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        return userProfileRepository.getOne(uuid);
    }

}