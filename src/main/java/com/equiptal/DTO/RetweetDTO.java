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
public class RetweetDTO implements TweetBase {
    private Integer retweetId;
    private String reuserName;
    private String tweet;
    private Date recreateDate;
    private Array reusers;
    private Integer relikesCount;

    private Integer tweetId;
    private String userName;
    private Date createDate;
    private Array users;
    private Integer likesCount;

    @Override
    public Date getCreateDate() {
        return recreateDate;
    }

}
