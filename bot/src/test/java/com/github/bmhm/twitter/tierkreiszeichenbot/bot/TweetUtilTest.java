package com.github.bmhm.twitter.tierkreiszeichenbot.bot;

import com.github.bmhm.twitter.tierkreiszeichenbot.progressbar.ProgressbarUtil;
import com.github.bmhm.twitter.tierkreiszeichenbot.zodiac.ZodiacUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.StatusUpdate;

import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

class TweetUtilTest {

  private static final Logger LOG = LoggerFactory.getLogger(TweetUtilTest.class);

  @ParameterizedTest
  @MethodSource("localeProvider")
  void createTweet(final Locale locale) {
    final ZodiacUtil zodiacUtil = new ZodiacUtil(ZonedDateTime.now());
    final ProgressbarUtil progressbarUtil = new ProgressbarUtil(new char[] {'_', '-'}, 4);
    final StatusUpdate tweet = TweetUtil.createTweet(zodiacUtil, progressbarUtil, locale);

    final String status = tweet.getStatus();
    LOG.debug("Generated tweet message: [{}].", status);

    Assertions.assertAll(
        () -> Assertions.assertFalse(status.isEmpty())
    );
  }

  static Stream<Locale> localeProvider() {
    final Set<String> langCodes = Set.of("de", "en", "gk", "gr", "nds", "fr", "it");
    return langCodes.stream()
        .map(Locale::new);
  }
}