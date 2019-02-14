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

import com.github.bmhm.twitter.tierkreiszeichenbot.progressbar.ProgressbarUtil;
import com.github.bmhm.twitter.tierkreiszeichenbot.zodiac.ZodiacUtil;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@SpringBootApplication
@EnableScheduling
public class Application {

  private static final Logger LOG = LoggerFactory.getLogger(Application.class);

  @Value("${doSingleTweet:false}")
  private boolean doSingleTweet;

  @Value("${user.timezone:Europe/Berlin}")
  private String timezone;

  @Autowired
  public Twitter twitter;

  @Autowired
  private ProgressbarUtil progressbarUtil;


  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
    return this::run;
  }

  private void run(final String... args) {
    if (this.doSingleTweet) {
      LOG.info("Creating single tweet.");
      final ZonedDateTime now = ZonedDateTime.now(ZoneId.of(this.timezone));
      final ZodiacUtil zodiacUtil = new ZodiacUtil(now);
      final StatusUpdate tweet = TweetUtil.createTweet(zodiacUtil, this.progressbarUtil);

      try {
        LOG.info("Sending text: [{}].", tweet.getStatus().replace("\n", "\\n"));
        LOG.info("Press CTRL+C to cancel. You got 10 seconds.");
        TimeUnit.SECONDS.sleep(11);

        LOG.info("Sending tweet now…");
        final Status status = this.twitter.updateStatus(tweet);
        LOG.info("Tweet send, id = [{}], link = [{}].", status.getId(), "https://twitter.com/SternzeichenB/status/" + status.getId());
      } catch (final TwitterException tweetEx) {
        LOG.error("Unable to twitter tweet: [{}].", tweet, tweetEx);
      } catch (final InterruptedException interrupted) {
        Thread.currentThread().interrupt();
        LOG.error("Interrupted! Not sending tweet: [{}].", tweet, interrupted);
        System.exit(1);
      }

      System.exit(0);
    }
  }

}
