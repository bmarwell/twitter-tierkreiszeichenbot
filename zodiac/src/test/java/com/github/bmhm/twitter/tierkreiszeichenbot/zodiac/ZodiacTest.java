package com.github.bmhm.twitter.tierkreiszeichenbot.zodiac;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

class ZodiacTest {

  private static final Logger LOG = LoggerFactory.getLogger(ZodiacTest.class);

  public static final ZoneId BERLIN = ZoneId.of("Europe/Berlin");

  @Test
  void getNext_June27() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 6, 27,
        0, 0, 0, 0,
        BERLIN);
    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);
    final Zodiac next = zodiacUtil.getNext();

    assertEquals(Zodiac.LEO, next);
  }

  @Test
  void getNext_July22_end() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 7, 23,
        0, 0, 0, 0,
        BERLIN).minusNanos(1L);
    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);
    final Zodiac next = zodiacUtil.getNext();

    assertEquals(Zodiac.LEO, next);
  }

  @Test
  void getNext_July23_start() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 7, 23,
        0, 0, 0, 0,
        BERLIN);

    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);
    final Zodiac current = zodiacUtil.getCurrent();

    Assertions.assertAll(
        () -> assertEquals(Zodiac.LEO, current),
        () -> assertEquals(Zodiac.VIRGO, zodiacUtil.getNext())
    );
  }

  @Test
  void getNext_July23_daysOver() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 7, 23,
        0, 0, 0, 0,
        BERLIN);

    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);

    Assertions.assertAll(() -> assertEquals(0L, zodiacUtil.getWholeDaysElapsed()));
  }

  @Test
  void getNext_July22_daysLeft() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 7, 22,
        6, 31, 0, 0,
        BERLIN);

    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);

    Assertions.assertAll(() -> assertEquals(0L, zodiacUtil.getWholeDaysLeft()));
  }

  @Test
  void getOccurrencePercent_00() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 7, 23,
        0, 0, 0, 0,
        BERLIN);
    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);

    final double percentDone = zodiacUtil.getPercentDone();
    assertEquals(0.0f, percentDone, 0.01f);
  }

  @Test
  void getOccurrencePercent_halfDone() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 7, 23,
        0, 0, 0, 0,
        BERLIN).plusDays(15L).plusHours(12L);
    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);

    final double percentDone = zodiacUtil.getPercentDone();
    assertEquals(0.5f, percentDone, 0.02f);
  }

  @Test
  void getOccurrencePercent_almostDone() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 8, 24,
        0, 0, 0, 0,
        BERLIN).minusSeconds(1L);
    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);

    final double percentDone = zodiacUtil.getPercentDone();
    assertEquals(1.0f, percentDone, 0.01f);
  }

  @Test
  void testGetDaysSinceWassermann2019_Feb13() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 2, 13,
        22, 0, 0, 0,
        BERLIN).minusSeconds(1L);
    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);

    final long secondsOver = zodiacUtil.getWholeSecondsElapsed();
    final long minutesOver = secondsOver / 60;
    final long hoursOver = minutesOver / 60;
    final long daysOver = hoursOver / 24;
    final long hoursExtra = hoursOver - (daysOver * 24);
    final long wholeDaysOver = zodiacUtil.getWholeDaysElapsed();

    LOG.debug("{}d{}h ~= {}d.", daysOver, hoursExtra, wholeDaysOver);

    assertEquals(23, wholeDaysOver);
  }

  @Test
  void testGetDaysSinceWassermann2019_SameDay() {
    final ZonedDateTime dt = ZonedDateTime.of(
        2019, 1, 21,
        22, 0, 0, 0,
        BERLIN).minusSeconds(1L);
    final ZodiacUtil zodiacUtil = new ZodiacUtil(dt);

    final long secondsOver = zodiacUtil.getWholeSecondsElapsed();
    final long minutesOver = secondsOver / 60;
    final long hoursOver = minutesOver / 60;
    final long daysOver = hoursOver / 24;
    final long hoursExtra = hoursOver - (daysOver * 24);
    final long wholeDaysOver = zodiacUtil.getWholeDaysElapsed();

    LOG.debug("{}d{}h ~= {}d.", daysOver, hoursExtra, wholeDaysOver);

    assertEquals(0L, wholeDaysOver);
  }

}
