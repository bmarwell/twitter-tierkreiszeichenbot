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
import java.util.StringJoiner;

public enum Zodiac {
  ARIES("Widder", 3, 21),
  TAURUS("Stier", 4, 21),
  GEMINI("Zwillinge", 5, 21),
  CANCER("Krebs", 6, 22),
  LEO("Löwe", 7, 23),
  VIRGO("Jungfrau", 8, 24),
  LIBRA("Waage", 9, 24),
  SCORPIO("Skorpion", 10, 24),
  SAGITTARIUS("Schütze", 11, 23),
  CAPRICORNUS("Steinbock", 12, 22),
  AQUARIUS("Wassermann", 1, 21),
  /**
   * Fische. Pisces.
   */
  PISCES("Fische", 2, 20);


  private final String description;
  private final int monthStart;
  private final int dayStart;

  Zodiac(final String germanName, final int monthStart, final int dayStart) {
    this.description = germanName;
    this.monthStart = monthStart;
    this.dayStart = dayStart;
  }

  public String getDescription() {
    return this.description;
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
