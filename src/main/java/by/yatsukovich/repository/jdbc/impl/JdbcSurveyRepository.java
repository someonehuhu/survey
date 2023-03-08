package by.yatsukovich.repository.jdbc.impl;

import by.yatsukovich.domain.Mailing;
import by.yatsukovich.domain.Survey;
import by.yatsukovich.domain.User;
import by.yatsukovich.exception.dao.EntityNotFoundException;
import by.yatsukovich.exception.dao.FailedCreateEntityException;
import by.yatsukovich.repository.SurveyRepository;
import by.yatsukovich.repository.jdbc.JdbcConnectionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Primary
public class JdbcSurveyRepository implements SurveyRepository {
    private final JdbcConnectionProvider jdbcConnectionProvider;

    private static final String[] SURVEY_GENERATED_KEY_NAMES = new String[]{"survey_id"};

    @Override
    public Optional<Survey> findOne(Long id) {
        try {
            return Optional.ofNullable(findById(id));
        } catch (EntityNotFoundException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }

    }

    @Override
    public Survey findById(Long id) {
        //this check is redundant?
        if (id == null) {
            throw new EntityNotFoundException(new IllegalArgumentException("Method {findById} id = " + id));
        }
        //
        final String findQuery =
                "select u.user_id, u.email, " +
                        "s.survey_id, s.share_link , s.access_codeword, s.responders_limit, " +
                        "s.validity_date, s.time_limit " +
                        "from survey s inner join \"user\" u on s.user_id  = u.user_id " +
                        "where s.survey_id = ? and s.is_deleted = false ;";
        //
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcConnectionProvider.getConnection();
            statement = connection.prepareStatement(findQuery);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            //
            if (resultSet.next()) {
                return parseNonParentColumns(resultSet);
            } else {
                throw new EntityNotFoundException("Survey not found with id = {" + id + "}");
            }
        } catch (SQLException e) {
            throw new EntityNotFoundException(e.getMessage(), e);
        } finally {
            jdbcConnectionProvider.close(statement);
            jdbcConnectionProvider.close(connection);
        }
    }

    @Override
    public List<Survey> findAll() {
        final String findAllQuery =
                "select u.user_id, u.email, " +
                        "s.survey_id, s.share_link , s.access_codeword, s.responders_limit, " +
                        "s.validity_date, s.time_limit " +
                        "from survey s inner join \"user\" u on s.user_id  = u.user_id" +
                        "where s.is_deleted = false ;";
        //
        Connection connection = null;
        Statement statement = null;
        try {
            connection = jdbcConnectionProvider.getConnection();
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(findAllQuery);
            //
            List<Survey> surveys = new ArrayList<>();
            while (resultSet.next()) {
                surveys.add(parseNonParentColumns(resultSet));
            }
            return surveys;
        } catch (SQLException e) {
            throw new EntityNotFoundException(e.getMessage(), e);
        } finally {
            jdbcConnectionProvider.close(statement);
            jdbcConnectionProvider.close(connection);
        }
    }

    @Override
    public Survey create(Survey survey) {
        final String createQuery = "INSERT INTO survey " +
                "(user_id, share_link, access_codeword, responders_limit, validity_date, time_limit) " +
                "VALUES (?, ?, ?, ?, ?, ?);";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcConnectionProvider.getConnection();
            statement = connection.prepareStatement(createQuery, SURVEY_GENERATED_KEY_NAMES);
            statement.setLong(1, survey.getUser().getId());
            statement.setString(2, survey.getShareLink());
            statement.setString(3, survey.getAccessCodeword());
            if (survey.getRespondersLimit() == null) {
                statement.setNull(4, Types.INTEGER);
            } else {
                statement.setInt(4, survey.getRespondersLimit());
            }
            statement.setTimestamp(5, survey.getValidityDate());
            if (survey.getTimeLimit() == null) {
                statement.setNull(6, Types.BIGINT);
            } else {
                statement.setLong(6, survey.getRespondersLimit());
            }
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new FailedCreateEntityException("Failed to insert survey :" + survey);
            }
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                survey.setId(resultSet.getLong(1));
                return survey;
            } else {
                throw new FailedCreateEntityException("Failed to retrieve generated survey id.");
            }
        } catch (SQLException e) {
            throw new FailedCreateEntityException(e.getMessage(), e);
        } finally {
            jdbcConnectionProvider.close(statement);
            jdbcConnectionProvider.close(connection);
        }
    }


    /**
     * Need to get updated valued from findById() / select in same connection using same statement,
     * or it's invocation is redundant?
     *
     * @param survey object to update.
     * @return updated value.
     */
    @Override
    public Survey update(Survey survey) {
        final String createQuery = "UPDATE survey " +
                "SET share_link = ?, access_codeword = ?, responders_limit = ?, validity_date = ?, time_limit = ?, " +
                "changed_on = now() " +
                "WHERE survey_id = ?;";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcConnectionProvider.getConnection();
            statement = connection.prepareStatement(createQuery);
            statement.setString(1, survey.getShareLink());
            statement.setString(2, survey.getAccessCodeword());
            statement.setInt(3, survey.getRespondersLimit());
            statement.setTimestamp(4, survey.getValidityDate());
            statement.setLong(5, survey.getTimeLimit());
            statement.setLong(6, survey.getId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new FailedCreateEntityException("Failed to update survey : " + survey);
            }
            //return survey;?????
            return findById(survey.getId());
        } catch (SQLException e) {
            throw new FailedCreateEntityException(e.getMessage(), e);
        } finally {
            jdbcConnectionProvider.close(statement);
            jdbcConnectionProvider.close(connection);
        }
    }

    @Override
    public void delete(Long id) {
        final String createQuery = "UPDATE survey " +
                "SET is_deleted = true " +
                "WHERE survey_id = ?;";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcConnectionProvider.getConnection();
            statement = connection.prepareStatement(createQuery);
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows != 1) {
                throw new FailedCreateEntityException("Failed to mark survey as deleted with id : {" + id + "}");
            }
        } catch (SQLException e) {
            throw new FailedCreateEntityException(e.getMessage(), e);
        } finally {
            jdbcConnectionProvider.close(statement);
            jdbcConnectionProvider.close(connection);
        }
    }

    //TODO How correctly implement one to many for list of parent entities??
    @Override
    public List<Survey> getSurveysWithExpiringMailings(Timestamp currentDate) {
        final String findExpiringSurveysMailingsQuery =
                "with survey_owner as (" +
                        "s.survey_id, s.share_link , s.access_codeword, s.responders_limit, " +
                        "s.validity_date, s.time_limit, u.user_id user_id, u.email email " +
                        "from survey s inner join \"user\" u on s.user_id = u.user_id " +
                        "where s.validity_date = ?" +
                        ")" +
                        "select survey_owner.user_id, survey_owner.email, survey_owner.survey_id, survey_owner.share_link, " +
                        "survey_owner.access_codeword, survey_owner.responders_limit, survey_owner.validity_date, " +
                        "survey_owner.time_limit, m.mailing_id, m.mail " +
                        "from mailing m inner join profile_mail on m.survey_id = profile_mail.survey_id" +
                        "order by survey_owner.survey_id asc ;";
        //
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = jdbcConnectionProvider.getConnection();
            statement = connection.prepareStatement(findExpiringSurveysMailingsQuery);
            ResultSet resultSet = statement.executeQuery();
            //

            return null;
        } catch (SQLException e) {
            throw new EntityNotFoundException(e.getMessage(), e);
        } finally {
            jdbcConnectionProvider.close(statement);
            jdbcConnectionProvider.close(connection);
        }

    }

    private Mailing parseMailing(ResultSet resultSet) throws SQLException {
        return Mailing.builder()
                .id(resultSet.getLong("mailing_id"))
                .mail(resultSet.getString("mail"))
                .build();
    }

    private Survey parseNonParentColumns(ResultSet resultSet) throws SQLException {
        User userCreated = User.builder()
                .id(resultSet.getLong("user_id"))
                .mail(resultSet.getString("email"))
                .build();

        return Survey.builder()
                .id(resultSet.getLong("survey_id"))
                .shareLink(resultSet.getString("share_link"))
                .accessCodeword(resultSet.getString("access_codeword"))
                .respondersLimit(resultSet.getInt("responders_limit"))
                .validityDate(resultSet.getTimestamp("validity_date"))
                .timeLimit(resultSet.getLong("time_limit"))
                .user(userCreated)
                .build();
    }

}
