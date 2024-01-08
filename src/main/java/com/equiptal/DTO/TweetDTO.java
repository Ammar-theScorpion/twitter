package com.equiptal.DTO;

import java.sql.Array;
import java.util.Date;

import com.equiptal.otherServices.Tweet.TweetBase;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetDTO implements TweetBase {
    private Integer tweetId; // from User table
    private String userName; // from User table
    private String tweet;
    private Date createDate;
    private Array users;
    private Integer likesCount;

    @Override
    public Date getCreateDate() {
        return createDate;
    }
}
