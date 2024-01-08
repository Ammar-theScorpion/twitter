package com.equiptal.model;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.equiptal.DTO.CommentDTO;
import com.equiptal.DTO.RetweetDTO;
import com.equiptal.DTO.TweetDTO;
import com.equiptal.otherServices.Mapper.CommentDTOMapper;
import com.equiptal.otherServices.Mapper.RetweetDTOMapper;
import com.equiptal.otherServices.Mapper.TweetDTOMapper;

public interface TweetDAO {

        @SqlUpdate("Insert INTO Tweet(tweet, user_id) VALUES ( :tweet, :userId)")
        @GetGeneratedKeys
        Integer insert(@Bind("tweet") String tweet, @Bind("userId") Integer userId);

        @SqlUpdate("Insert INTO Retweet(tweet_id, retweet_user_id) VALUES ( :tweetId, :retweetUserId)")
        @GetGeneratedKeys
        Integer insertRetweet(@Bind("tweetId") Integer tweetId, @Bind("retweetUserId") Integer retweetUserId);

        // LIKES
        @SqlUpdate("INSERT INTO \"like\"(user_id, tweet_id) VALUES ( :userId, :tweetId) ")
        void giveLike(@Bind("tweetId") Integer tweetId, @Bind("userId") Integer userId);

        @SqlUpdate("DELETE FROM \"like\" WHERE user_id = :userId AND tweet_id=:tweetId ")
        void deleteLike(@Bind("tweetId") Integer tweetId, @Bind("userId") Integer userId);

        @SqlQuery("SELECT COUNT(l.like_id) FROM \"like\" l WHERE tweet_id=:tweetId")
        Integer likesCount(@Bind("tweetId") Integer tweetId);
        // LIKES

        @SqlUpdate("INSERT INTO comment(user_id, tweet_id, tweet) VALUES ( :userId, :tweetId, :tweet) ")
        void comment(@Bind("tweetId") Integer tweetId, @Bind("userId") Integer userId,
                        @Bind("tweet") String tweet);

        @SqlQuery("SELECT c.tweet, c.create_date, u.user_name FROM comment c " +
                        "JOIN \"User\" u ON u.id = c.user_id " +
                        "WHERE c.tweet_id = :tweetId ")
        @RegisterRowMapper(CommentDTOMapper.class)
        List<CommentDTO> getComments(@Bind("tweetId") Integer tweetId);

        @SqlQuery(" SELECT EXISTS (SELECT l.like_id FROM \"like\" l " +
                        "WHERE l.user_id = :userId AND l.tweet_id = :tweetId ) ")
        Boolean noLikeForThisTweet(@Bind("tweetId") Integer tweetId, @Bind("userId") Integer userId);

        @SqlQuery("SELECT t.id, u.user_name, t.tweet, t.create_date, l.user_id as users, COUNT(l.like_id) as count "
                        +
                        "FROM Tweet t JOIN \"User\" u ON u.id = t.user_id LEFT JOIN \"like\" l ON l.tweet_id = t.id " +
                        "WHERE (:id != 0 AND  u.id=:id) OR (:id = 0) " +
                        "GROUP BY t.id, u.user_name, t.tweet, t.create_date, l.user_id ORDER BY create_date DESC ")
        @RegisterRowMapper(TweetDTOMapper.class)
        List<TweetDTO> getTweets(@Bind("id") Integer id);

        @SqlQuery("SELECT r.id, u.user_name, t.tweet, r.create_date, l.user_id as users, COUNT(l.like_id) as count" +
                        "t.id as oid, uo.user_name as ouser_name, t.create_date as ocreateDate, ol.user_id as ousers, COUNT(ol.like_id) as ocount"
                        +
                        "FROM Retweet r " +
                        "JOIN Tweet t ON r.tweet_id = t.id " +
                        "JOIN \"User\" u ON u.id = r.retweet_user_id " +
                        "JOIN \"User\" uo ON uo.id = t.user_id " +
                        "LEFT JOIN \"like\" l ON l.tweet_id = r.id " +
                        "LEFT JOIN \"like\" ol ON ol.tweet_id = t.id " +
                        "WHERE (:id != 0 AND u.id = :id) OR (:id = 0) " +
                        "GROUP BY r.id, u.user_name, t.tweet, r.create_date, l.user_id " +
                        "ORDER BY r.create_date DESC ")
        @RegisterRowMapper(RetweetDTOMapper.class)
        List<RetweetDTO> getRetweets(@Bind("id") Integer id);

        @SqlQuery("SELECT t.id, u.user_name, t.tweet, t.create_date " +
                        "FROM Tweet t JOIN \"User\" u ON u.id = t.user_id WHERE t.id = :tweetId")
        @RegisterRowMapper(TweetDTOMapper.class)
        TweetDTO findById(@Bind("tweetId") Integer tweetId);

        @SqlQuery("SELECT r.id, u.user_name, t.tweet, r.create_date, null " +
                        "FROM Retweet r JOIN Tweet t ON r.tweet_id = t.id JOIN \"User\" u ON u.id = t.user_id WHERE t.id = :tweetId")
        @RegisterRowMapper(RetweetDTOMapper.class)
        RetweetDTO findByIdRT(@Bind("tweetId") Integer tweetId);

        @SqlQuery("SELECT u.user_name " +
                        "FROM Tweet t JOIN \"User\" u ON u.id = t.user_id WHERE t.id = :tweetId AND u.id = t.user_id")
        String getTweetOwner(@Bind("tweetId") Integer tweetId);

}
