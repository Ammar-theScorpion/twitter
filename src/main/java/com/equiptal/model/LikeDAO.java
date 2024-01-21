package com.equiptal.model;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface LikeDAO {

    // LIKES
    @SqlUpdate("INSERT INTO \"like\"(user_id, tweet_id) VALUES ( :userId, :tweetId) ")
    void giveLike(@Bind("tweetId") Integer tweetId, @Bind("userId") Integer userId);

    @SqlUpdate("DELETE FROM \"like\" WHERE user_id = :userId AND tweet_id=:tweetId ")
    void deleteLike(@Bind("tweetId") Integer tweetId, @Bind("userId") Integer userId);

    @SqlQuery("SELECT COUNT(l.like_id) FROM \"like\" l WHERE tweet_id=:tweetId")
    Integer likesCount(@Bind("tweetId") Integer tweetId);
    
    @SqlQuery(" SELECT EXISTS (SELECT l.like_id FROM \"like\" l " +
    "WHERE l.user_id = :userId AND l.tweet_id = :tweetId ) ")
    Boolean noLikeForThisTweet(@Bind("tweetId") Integer tweetId, @Bind("userId") Integer userId);

    // LIKES

}
