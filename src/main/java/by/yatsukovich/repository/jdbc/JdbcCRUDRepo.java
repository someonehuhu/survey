package by.yatsukovich.repository.jdbc;


@Deprecated
public interface JdbcCRUDRepo {
    <T> T createOne(String createQuery, CRUDMapper<T> CRUDMapper, T domain);

}
