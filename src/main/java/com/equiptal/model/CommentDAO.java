package com.equiptal.model;
import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.equiptal.DTO.CommentDTO;
import com.equiptal.otherServices.Mapper.CommentDTOMapper;

public interface CommentDAO {

    @SqlUpdate("INSERT INTO comment(user_id, tweet_id, tweet) VALUES ( :userId, :tweetId, :tweet) ")
    @GetGeneratedKeys
    Integer comment(@Bind("tweetId") Integer tweetId, @Bind("userId") Integer userId,
                    @Bind("tweet") String tweet);

    @SqlQuery("SELECT c.tweet, c.create_date, u.user_name FROM comment c " +
                    "JOIN \"User\" u ON u.id = c.user_id " +
                    "WHERE c.comment_id = :id ")
    @RegisterRowMapper(CommentDTOMapper.class)
    List<CommentDTO> getComment(@Bind("id") Integer id);

    @SqlQuery("SELECT c.tweet, c.create_date, u.user_name FROM comment c " +
                    "JOIN \"User\" u ON u.id = c.user_id " +
                    "WHERE c.tweet_id = :tweetId ")
    @RegisterRowMapper(CommentDTOMapper.class)
    List<CommentDTO> getComments(@Bind("tweetId") Integer tweetId);
    
}
