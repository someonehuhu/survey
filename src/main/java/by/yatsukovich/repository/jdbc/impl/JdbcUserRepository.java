package by.yatsukovich.repository.jdbc.impl;

import by.yatsukovich.domain.User;
import by.yatsukovich.exception.dao.FailedCreateEntityException;
import by.yatsukovich.repository.UserRepository;
import by.yatsukovich.repository.jdbc.JdbcConnectionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Primary
public class JdbcUserRepository implements UserRepository {

    private final JdbcConnectionProvider jdbcConnectionProvider;

    private static final String[] USER_GENERATED_KEY_NAMES = new String[]{"user_id"};

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    /**
     * @param user created.No need to insert data in ref tables.
     * @return user created with generated id;
     */
    @Override
    public User create(User user) {
        final String createQuery = "INSERT INTO \"user\" (email) VALUES (?);";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcConnectionProvider.getConnection();
            statement = connection.prepareStatement(createQuery, USER_GENERATED_KEY_NAMES);
            statement.setString(1, user.getMail());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new FailedCreateEntityException("Failed to insert user");
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                return user;
            } else {
                throw new FailedCreateEntityException("Failed to retrieve generated user id");
            }
        } catch (SQLException e) {
            throw new FailedCreateEntityException(e);
        } finally {
            jdbcConnectionProvider.close(statement);
            jdbcConnectionProvider.close(connection);
        }
    }

    @Override
    public User update(User object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
