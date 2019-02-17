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
import com.github.bmhm.twitter.tierkreiszeichenbot.zodiac.Zodiac;
import com.github.bmhm.twitter.tierkreiszeichenbot.zodiac.ZodiacUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.github.bmhm.twitter.tierkreiszeichenbot.bot.TweetUtil.createTweet;

@Component
public class ScheduleTweet {

  private static final Logger LOG = LoggerFactory.getLogger(ScheduleTweet.class);

  @Value("${user.timezone:Europe/Berlin}")
  private String timezone;

  @Autowired
  private Twitter twitter;

  @Autowired
  private ProgressbarUtil progressbarUtil;

  /**
   * Tweets the regular monday tweet.
   *
   * <p><pre>
   * second
   * |      minute (0-59),
   * |      |      hour (0-23),
   * |      |       |       day of the month (1-31),
   * |      |       |       |       month of the year (1-12),
   * |      |       |       |       |      day of the week (0-6 with 0=Sunday).
   * |      |       |       |       |      |
   * 0     31       5     1/1       *      ?</pre></p>
   */
  @Scheduled(cron = "${twitter.scheduler.daily.cron:0 31 5 1/1 * ?}")
  public void postDaily() {
    final ZonedDateTime now = ZonedDateTime.now(ZoneId.of(this.timezone));
    final ZodiacUtil zodiacUtil = new ZodiacUtil(now);
    final Zodiac current = zodiacUtil.getCurrent();

    if (current.startedToday(now)) {
      // das macht der andere cron.
      return;
    }

    final StatusUpdate tweet = createTweet(zodiacUtil, this.progressbarUtil);
    trySendTweet(tweet);
  }

  @Scheduled(cron = "${twitter.scheduler.firstDay.cron:0 31 5 * * ?}")
  public void postOnFirstDay() {
    final ZonedDateTime now = ZonedDateTime.now(ZoneId.of(this.timezone));
    final ZodiacUtil zodiacUtil = new ZodiacUtil(now);
    final Zodiac current = zodiacUtil.getCurrent();

    if (!current.startedToday(now)) {
      // das macht der andere cron.
      return;
    }

    final StatusUpdate tweet = createTweet(zodiacUtil, this.progressbarUtil);

    trySendTweet(tweet);
    // this will only happen on the first day of this zodiac.
    tryUploadImage(current, tweet);
  }

  private void tryUploadImage(final Zodiac current, final StatusUpdate tweet) {
    try (final InputStream imageStream = current.getImage()) {
      this.twitter.updateProfileImage(imageStream);
    } catch (final TwitterException tweetException) {
      LOG.error("unable to send tweet [{}].", tweet, tweetException);
    } catch (final IOException ioEx) {
      LOG.error("Unable to upload profile image: [{}].", current, ioEx);
    }
  }

  private void trySendTweet(final StatusUpdate tweet) {
    try {
      this.twitter.updateStatus(tweet);
    } catch (final TwitterException tweetException) {
      LOG.error("unable to send tweet [{}].", tweet, tweetException);
    }
  }

  @Scheduled(cron = "${twitter.scheduler.print.cron:0 0 * * * ?}")
  public void printCurrentState() {
    final ZonedDateTime now = ZonedDateTime.now(ZoneId.of(this.timezone));
    final ZodiacUtil zodiacUtil = new ZodiacUtil(now);

    final StatusUpdate tweet = createTweet(zodiacUtil, this.progressbarUtil);
    LOG.info("{}", tweet.toString().replace("\n", "\\n"));
  }


}
