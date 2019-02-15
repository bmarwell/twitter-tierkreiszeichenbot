package com.github.bmhm.twitter.tierkreiszeichenbot.zodiac;/*
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

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public enum Zodiac {
  ARIES(3, 21),
  TAURUS(4, 21),
  GEMINI(5, 21),
  CANCER(6, 22),
  LEO(7, 23),
  VIRGO(8, 24),
  LIBRA(9, 24),
  SCORPIO(10, 24),
  SAGITTARIUS(11, 23),
  CAPRICORNUS(12, 22),
  AQUARIUS(1, 21),
  /**
   * Fische. Pisces.
   */
  PISCES(2, 20);


  private final String description;
  private final int monthStart;
  private final int dayStart;

  Zodiac(final int monthStart, final int dayStart) {
    final ResourceBundle messages = ResourceBundle.getBundle(this.getClass().getCanonicalName());
    this.description = messages.getString(this.name());
    this.monthStart = monthStart;
    this.dayStart = dayStart;
  }

  public String getDisplayName() {
    return this.description;
  }

  public String getDisplayName(final Locale locale) {
    final ResourceBundle messages = ResourceBundle.getBundle(this.getClass().getCanonicalName(), locale);
    return messages.getString(this.name());
  }

  public int getMonthStart() {
    return this.monthStart;
  }

  public int getDayStart() {
    return this.dayStart;
  }

  /**
   * Needed for comparator.
   */
  public long daysToOccurrence(final ZonedDateTime dt) {
    final ZonedDateTime nextOccurrence = getNextOccurrence(dt);

    return ChronoUnit.DAYS.between(dt, nextOccurrence);
  }


  public ZonedDateTime getCurrentOccurrence(final ZonedDateTime dt) {
    final ZonedDateTime thisOccurrence = ZonedDateTime.of(
        dt.getYear(),
        this.monthStart,
        this.dayStart,
        0,
        0,
        0,
        0,
        dt.getZone());

    if (!thisOccurrence.isAfter(dt)) {
      return thisOccurrence;
    }

    return thisOccurrence.withYear(thisOccurrence.getYear() - 1);
  }

  public ZonedDateTime getNextOccurrence(final ZonedDateTime dt) {
    final ZonedDateTime thisOccurrence = ZonedDateTime.of(
        dt.getYear(),
        this.monthStart,
        this.dayStart,
        0,
        0,
        0,
        0,
        dt.getZone());

    if (!thisOccurrence.isAfter(dt)) {
      return thisOccurrence.withYear(thisOccurrence.getYear() + 1);
    }

    return thisOccurrence;
  }

  public boolean startedToday(final ZonedDateTime now) {
    final ZonedDateTime currentOccurrence = getCurrentOccurrence(now);

    return currentOccurrence.getDayOfMonth() == getDayStart()
        && currentOccurrence.getMonthValue() == getMonthStart()
        && currentOccurrence.getYear() == now.getYear();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "Zodiac{", "}")
        .add("description='" + this.description + "'")
        .add("monthStart=" + this.monthStart)
        .add("dayStart=" + this.dayStart)
        .toString();
  }

}
