package eh7.guestbook.repository.jdbc;

import eh7.guestbook.domain.Like;
import eh7.guestbook.repository.LikeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class JdbcLikeRepository implements LikeRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcLikeRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("likes")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Like save(Like like) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(like);
        Number key = jdbcInsert.executeAndReturnKey(param);
        like.setId(key.longValue());
        return like;
    }

    @Override
    public Like findById(Long likeId) {
        String sql = "select * from likes where id=:id";
        try {
            SqlParameterSource param = new MapSqlParameterSource()
                    .addValue("id", likeId);
            return template.queryForObject(sql, param, likeRowMapper());
        } catch (EmptyResultDataAccessException e) {
            // TODO: 예외 변환 필요
            throw e;
        }
    }

    @Override
    public List<Like> findByPostId(Long postId) {
        String sql = "select * from likes where post_id=:id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", postId);
        return template.query(sql, param, likeRowMapper());
    }

    @Override
    public void delete(Long likeId) {
        String sql = "delete from likes where id=:id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", likeId);
        template.update(sql, param);
    }

    private RowMapper<Like> likeRowMapper() {
        return BeanPropertyRowMapper.newInstance(Like.class);
    }
}
