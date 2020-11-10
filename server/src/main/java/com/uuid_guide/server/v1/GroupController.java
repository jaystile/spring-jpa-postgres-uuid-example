package com.uuid_guide.server.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

/**
 * Note: This service is meant to be fuzzed so it intentionally does not protect
 * for sql injection.
 */
@RestController()
@RequestMapping("/group/v1")
public class GroupController {

    GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Test connectivity or preflight connections
     * 
     * @return "ACK"
     */
    @GetMapping("/acknowledge")
    String acknowledge() {
        return "ACK";
    }

    @GetMapping("/")
    List<Group> getAll() throws Exception {
        return groupService.getAll();
    }

    @GetMapping("/{id}")
    Group getById(@PathVariable String id) throws SQLException {
        return groupService.getById(id);
    }

}
