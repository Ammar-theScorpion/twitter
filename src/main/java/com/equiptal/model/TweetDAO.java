package com.equiptal.model;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.equiptal.DTO.RetweetDTO;
import com.equiptal.DTO.TweetDTO;
import com.equiptal.otherServices.Mapper.RetweetDTOMapper;
import com.equiptal.otherServices.Mapper.TweetDTOMapper;

public interface TweetDAO {

        @SqlUpdate("Insert INTO Tweet(tweet, user_id) VALUES ( :tweet, :userId)")
        @GetGeneratedKeys
        Integer insert(@Bind("tweet") String tweet, @Bind("userId") Integer userId);

        @SqlUpdate("Insert INTO Retweet(tweet_id, retweet_user_id) VALUES ( :tweetId, :retweetUserId)")
        @GetGeneratedKeys
        Integer insertRetweet(@Bind("tweetId") Integer tweetId, @Bind("retweetUserId") Integer retweetUserId);


        @SqlQuery("SELECT t.id, u.user_name, t.tweet, t.create_date, l.user_id as users, COUNT(l.like_id) as count "
                        + "FROM Tweet t JOIN \"User\" u ON u.id = t.user_id LEFT JOIN \"like\" l ON l.tweet_id = t.id "
                        + "WHERE (:id != 0 AND  u.id=:id) OR (:id = 0) "
                        + "GROUP BY t.id, u.user_name, t.tweet, t.create_date, l.user_id ORDER BY create_date DESC "
                        + "LIMIT :limit OFFSET :offset")
        @RegisterRowMapper(TweetDTOMapper.class)
        List<TweetDTO> getTweets(@Bind("id") Integer id, @Bind("limit") Integer limit, @Bind("offset") Integer offset);

        @SqlQuery("SELECT t.id, u.user_name, t.tweet, t.create_date, l.user_id as users, COUNT(l.like_id) as count "
                        + "FROM Tweet t JOIN \"User\" u ON u.id = t.user_id LEFT JOIN \"like\" l ON l.tweet_id = t.id "
                        + "WHERE t.id=:id "
                        + "GROUP BY t.id, u.user_name, t.tweet, t.create_date, l.user_id ORDER BY create_date DESC "
                        + "")
        @RegisterRowMapper(TweetDTOMapper.class)
        TweetDTO getTweet(@Bind("id") Integer id);

        @SqlQuery("SELECT r.id, u.user_name, t.tweet, r.create_date, l.user_id as users, COUNT(l.like_id) as count, " +
                        "t.id as oid, uo.user_name as ouser_name, t.create_date as ocreateDate, ol.user_id as ousers, COUNT(ol.like_id) as ocount "
                        +
                        "FROM Retweet r " +
                        "JOIN Tweet t ON r.tweet_id = t.id " +
                        "JOIN \"User\" u ON u.id = r.retweet_user_id " +
                        "JOIN \"User\" uo ON uo.id = t.user_id " +
                        "LEFT JOIN \"like\" l ON l.tweet_id = r.id " +
                        "LEFT JOIN \"like\" ol ON ol.tweet_id = t.id " +
                        "WHERE (:id != 0 AND u.id = :id) OR (:id = 0) " +
                        "GROUP BY r.id, u.user_name, t.tweet, r.create_date, l.user_id, t.id, uo.user_name, t.create_date, ol.user_id "
                        +
                        "ORDER BY r.create_date DESC " +
                        "LIMIT :limit OFFSET :offset ")
        @RegisterRowMapper(RetweetDTOMapper.class)
        List<RetweetDTO> getRetweets(@Bind("id") Integer id, @Bind("limit") Integer limit,
                        @Bind("offset") Integer offset);

        @SqlQuery("SELECT t.id, u.user_name, t.tweet, t.create_date, null as users, 0 as count " +
                        "FROM Tweet t JOIN \"User\" u ON u.id = t.user_id WHERE t.id = :tweetId")
        @RegisterRowMapper(TweetDTOMapper.class)
        TweetDTO findById(@Bind("tweetId") Integer tweetId);

        @SqlQuery("SELECT r.id, u.user_name, t.tweet, r.create_date, l.user_id as users, COUNT(l.like_id) as count, " +
                        "t.id as oid, uo.user_name as ouser_name, t.create_date as ocreateDate, ol.user_id as ousers, COUNT(ol.like_id) as ocount "
                        +
                        "FROM Retweet r " +
                        "JOIN Tweet t ON r.tweet_id = t.id " +
                        "JOIN \"User\" u ON u.id = r.retweet_user_id " +
                        "JOIN \"User\" uo ON uo.id = t.user_id " +
                        "LEFT JOIN \"like\" l ON l.tweet_id = r.id " +
                        "LEFT JOIN \"like\" ol ON ol.tweet_id = t.id " +
                        "WHERE r.id = :tweetId " +
                        "GROUP BY r.id, u.user_name, t.tweet, r.create_date, l.user_id, t.id, uo.user_name, t.create_date, ol.user_id "
                        +
                        "ORDER BY r.create_date DESC")
        @RegisterRowMapper(RetweetDTOMapper.class)
        RetweetDTO findByIdRT(@Bind("tweetId") Integer tweetId);

        @SqlQuery("SELECT u.user_name " +
                        "FROM Tweet t JOIN \"User\" u ON u.id = t.user_id WHERE t.id = :tweetId AND u.id = t.user_id")
        String getTweetOwner(@Bind("tweetId") Integer tweetId);

}
