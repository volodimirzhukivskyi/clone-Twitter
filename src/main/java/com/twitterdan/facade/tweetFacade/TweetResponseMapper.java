package com.twitterdan.facade.tweetFacade;


import com.twitterdan.domain.dto.tweetDto.TweetResponse;
import com.twitterdan.domain.tweet.Tweet;
import com.twitterdan.facade.GeneralFacade;
import org.springframework.stereotype.Service;

@Service
public class TweetResponseMapper extends GeneralFacade<Tweet, TweetResponse> {
    public TweetResponseMapper() {
        super(Tweet.class, TweetResponse.class);
    }


    @Override
    protected void decorateEntity(Tweet entity, TweetResponse dto) {



    }
}
