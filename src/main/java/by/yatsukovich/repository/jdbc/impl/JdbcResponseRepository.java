package by.yatsukovich.repository.jdbc.impl;

import by.yatsukovich.domain.Response;
import by.yatsukovich.repository.ResponseRepository;
import by.yatsukovich.repository.jdbc.JdbcConnectionProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Primary
public class JdbcResponseRepository implements ResponseRepository {

    private final JdbcConnectionProvider jdbcConnectionProvider;

    @Override
    public Optional<Response> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public Response findById(Long id) {
        return null;
    }

    @Override
    public List<Response> findAll() {
        return null;
    }

    @Override
    public Response create(Response object) {
        return null;
    }

    @Override
    public Response update(Response object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Response> getResponsesBySurvey(Long surveyId) {
        return null;
    }
}
