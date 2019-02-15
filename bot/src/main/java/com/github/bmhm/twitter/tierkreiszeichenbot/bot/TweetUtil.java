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
import twitter4j.StatusUpdate;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public final class TweetUtil {

  private static final String CLASS_NAME = TweetUtil.class.getCanonicalName();

  private TweetUtil() {
    // util class.
  }

  public static StatusUpdate createTweet(
      final ZodiacUtil zodiacUtil,
      final ProgressbarUtil progressbarUtil,
      final Locale locale) {
    final Zodiac current = zodiacUtil.getCurrent();

    final Zodiac nextZodiac = zodiacUtil.getNext();
    final double percentDone = zodiacUtil.getPercentDone();

    final ResourceBundle messages = ResourceBundle.getBundle(CLASS_NAME, locale);
    final String tweetTemplate = messages.getString("tweet");

    final MessageFormat form = new MessageFormat(tweetTemplate, locale);
    final Object[] formatArgs = {
        zodiacUtil.getWholeDaysElapsed() + 1,
        current.getDisplayName(),
        progressbarUtil.getRepresentation(percentDone),
        percentDone * 100,
        zodiacUtil.getWholeDaysLeft() + 1,
        nextZodiac.getDisplayName()
    };
    final String tweet = form.format(formatArgs);
    final StatusUpdate statusUpdate = new StatusUpdate(tweet);
    statusUpdate.setPossiblySensitive(false);

    return statusUpdate;
  }

  public static StatusUpdate createTweet(
      final ZodiacUtil zodiacUtil,
      final ProgressbarUtil progressbarUtil) {
    return createTweet(zodiacUtil, progressbarUtil, Locale.getDefault());
  }

}
