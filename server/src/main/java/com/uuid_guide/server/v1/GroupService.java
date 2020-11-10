package com.uuid_guide.server.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    public static final Logger LOG = LoggerFactory.getLogger(GroupService.class);

    private DataSource dataSource;

    @Autowired
    public GroupService(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    List<Group> getAll() throws SQLException {
        String query = "select id, display_name from groups";
        LOG.info("Query: {}", query);
        Connection conn = dataSource.getConnection();
        List<Group> groups = new ArrayList<Group>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
              Group g = new Group();
              g.setId(UUID.fromString(rs.getString("id")));
              g.setDisplayName(rs.getString("display_name"));
              groups.add(g);
            }
          } catch (SQLException e) {
            LOG.error("An error occurred.", e);
          }
          return groups;
    }


    Group getById(String id) throws SQLException {
        String query = "select id, display_name from groups where id=?";
        LOG.info("Query: {}", query);
        try (PreparedStatement stmt = dataSource.getConnection().prepareStatement(query)) {
            stmt.setObject(1, UUID.fromString(id));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
              Group g = new Group();
              g.setId(UUID.fromString(rs.getString("id")));
              g.setDisplayName(rs.getString("display_name"));
              return g;
            }
        } catch (SQLException e) {
            LOG.error("An error occurred.", e);
        }
        return null;

    }    

}
