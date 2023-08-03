package eh7.guestbook.repository.jdbc;

import eh7.guestbook.domain.Post;
import eh7.guestbook.repository.PostRepository;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.repository.dto.PostSaveDto;
import eh7.guestbook.repository.dto.PostUpdateDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        String sql = "update post " +
                "set author=:author, side=:side, relationship=:relationship, content=:content " +
                "where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("author", updateDto.getAuthor())
                .addValue("side", updateDto.getSide())
                .addValue("relationship", updateDto.getRelationship())
                .addValue("content", updateDto.getContent())
                .addValue("id", postId);

        template.update(sql, param);
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
        String author = cond.getAuthor();
        String side = cond.getSide();
        String relationship = cond.getRelationship();

        boolean authorFlag = StringUtils.hasText(author);
        boolean sideFlag = StringUtils.hasText(side);
        boolean relationshipFlag = StringUtils.hasText(relationship);

        String sql = "select id, author, password, side, relationship, content from post";

        // 동적 쿼리 =========================================
        // 하나라도 값이 있을 경우
        if (authorFlag || sideFlag || relationshipFlag) {
            sql += " where";
        }

        boolean andFlag = false;

        if (authorFlag) {
            sql += " author like concat('%', :author, '%')";
            andFlag = true;
        }

        if (sideFlag) {
            if (andFlag) {
                sql += " and";
            } else {
                andFlag = true;
            }
            sql += " side=:side";
        }

        if (relationshipFlag) {
            if (andFlag) {
                sql += " and";
            }
            sql += " relationship=:relationship";
        }
        // 동적 쿼리 끝 =====================================

        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);
        return template.query(sql, param, postRowMapper());
    }

    @Override
    public boolean delete(Long postId) {
        // 테이블에 없는 id 값인 경우
        if (findById(postId).isEmpty()) {
            return false;
        }

        String sql = "delete from post where id=:id";
        SqlParameterSource param = new MapSqlParameterSource("id", postId);
        template.update(sql, param);
        return true;
    }

    private RowMapper<Post> postRowMapper() {
        return BeanPropertyRowMapper.newInstance(Post.class);
    }
}
