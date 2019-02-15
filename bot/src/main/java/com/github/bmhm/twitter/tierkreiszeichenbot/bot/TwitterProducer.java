/*
 *  Copyright 2018 The twitter-tierkreiszeichenbot contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.bmhm.twitter.tierkreiszeichenbot.bot;

import com.github.bmhm.twitter.tierkreiszeichenbot.progressbar.ProgressType;
import com.github.bmhm.twitter.tierkreiszeichenbot.progressbar.ProgressbarUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Component
public class TwitterProducer {

  @Bean
  public Twitter getTwitter(@Autowired final TwitterConfig twitterAttributes) {
    final ConfigurationBuilder cb = new ConfigurationBuilder()
        .setDebugEnabled(twitterAttributes.isDebug())
        .setOAuthConsumerKey(twitterAttributes.getConsumerKey())
        .setOAuthConsumerSecret(twitterAttributes.getConsumerSecret())
        .setOAuthAccessToken(twitterAttributes.getAccessToken())
        .setOAuthAccessTokenSecret(twitterAttributes.getAccessTokenSecret());
    final TwitterFactory tf = new TwitterFactory(cb.build());

    return tf.getInstance();
  }

  @Bean
  @Scope("prototype")
  public ProgressbarUtil getProgressbarUtil(
      @Value("${twitter.progressbar.chars:}") final String progressbarChars,
      @Value("${twitter.progressbar.length:18}") final int progressbarLength) {
    return new ProgressbarUtil(ProgressType.BLOCK_SIMPLE, progressbarLength);
  }
}
