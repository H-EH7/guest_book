package eh7.guestbook.repository.jdbc;

import eh7.guestbook.domain.Post;
import eh7.guestbook.domain.Relationship;
import eh7.guestbook.domain.Side;
import eh7.guestbook.repository.PostRepository;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.repository.dto.PostSaveDto;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository
public class JdbcPostRepository implements PostRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcPostRepository(DataSource dataSource) {
        template = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("post")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Post save(Post post) {
        PostSaveDto postSaveDto = new PostSaveDto(post); // 저장을 위한 DTO 생성
        SqlParameterSource param = new BeanPropertySqlParameterSource(postSaveDto);
        Number key = jdbcInsert.executeAndReturnKey(param); // PK 값
        post.setId(key.longValue());
        return post;
    }

    @Override
    public void update(Long postId, PostUpdateDto updateDto) {

    }

    @Override
    public Optional<Post> findById(Long postId) {
        String sql = "select id, author, password, side, relationship, content from post where id = :id";
        try {
            Map<String, Object> param = Map.of("id", postId);
            Post post = template.queryForObject(sql, param, postRowMapper());
            return Optional.of(post);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Post> findAll(PostSearchCond cond) {
        return null;
    }

    @Override
    public Boolean delete(Long postId, String pwd) {
        return null;
    }

    private RowMapper<Post> postRowMapper() {
        // side와 relationship row 값을 enum으로 변경 후 맵핑
        return (rs, rowNum) -> {
            Post post = new Post();
            post.setId(rs.getLong("id"));
            post.setAuthor(rs.getString("author"));
            post.setPassword(rs.getString("password"));
            post.setSide(Side.valueOfLabel(rs.getString("side")));
            post.setRelationship(Relationship.valueOfLabel(rs.getString("relationship")));
            post.setContent(rs.getString("content"));
            return post;
        };
    }
}
