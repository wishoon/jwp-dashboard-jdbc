package com.techcourse.dao;

import com.techcourse.domain.User;
import java.util.List;
import nextstep.jdbc.JdbcTemplate;
import nextstep.jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDao {

    private static final Logger log = LoggerFactory.getLogger(UserDao.class);

    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> getUserRowMapper = (resultSet, rm) -> {
        final Long id = resultSet.getLong("id");
        final String account = resultSet.getString("account");
        final String password = resultSet.getString("password");
        final String email = resultSet.getString("email");
        return new User(id, account, password, email);
    };

    public UserDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(final User user) {
        final var sql = "insert into users (account, password, email) values (?, ?, ?)";

        jdbcTemplate.update(sql, user.getAccount(), user.getPassword(), user.getEmail());
    }

    public void update(final User user) {
        final var sql = "update users set password = ? where id = ?";

        jdbcTemplate.update(sql, user.getPassword(), user.getId());
    }

    public List<User> findAll() {
        final var sql = "select * from users";

        return jdbcTemplate.queryForList(sql, getUserRowMapper);
    }

    public User findById(final Long id) {
        final var sql = "select id, account, password, email from users where id = ?";

        return jdbcTemplate.queryForObject(sql, getUserRowMapper, id);
    }

    public User findByAccount(final String account) {
        final var sql = "select * from users where account = ?";

        return jdbcTemplate.queryForObject(sql, getUserRowMapper, account);
    }
}
