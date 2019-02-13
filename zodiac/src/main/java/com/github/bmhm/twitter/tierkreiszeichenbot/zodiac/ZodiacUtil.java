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

package com.github.bmhm.twitter.tierkreiszeichenbot.zodiac;

import static java.util.stream.Collectors.toList;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ZodiacUtil {

  private static final Logger LOG = LoggerFactory.getLogger(ZodiacUtil.class);

  private static final Function<ZonedDateTime, Comparator<Zodiac>> DAYS_NEXT_COMPARATOR = (dt) -> Comparator
      .comparingLong(z -> z.daysToOccurrence(dt));

  private final ZonedDateTime basis;
  private final Zodiac current;
  private final Zodiac next;
  private final ZonedDateTime currentStart;
  private final ZonedDateTime nextStart;

  public ZodiacUtil(final ZonedDateTime basis) {
    this.basis = basis;
    final List<Zodiac> zodiacList = getSortedByNextOccurrence(basis).collect(toList());
    this.current = zodiacList.get(zodiacList.size() - 1);
    this.next = zodiacList.get(0);
    this.currentStart = this.current.getCurrentOccurrence(basis);
    this.nextStart = this.next.getNextOccurrence(basis);
  }

  public Zodiac getCurrent() {
    return this.current;
  }

  public Zodiac getNext() {
    return this.next;
  }

  /**
   * How many whole days have passed since the beginning of this zodiac sign?
   * Only whole days will count.
   *
   * @return the number of days passed since beginning of this zodiac.
   */
  public long getWholeDaysLeft() {
    return ChronoUnit.DAYS.between(this.basis, this.nextStart);
  }

  /**
   * How many whole seconds have passed since start?
   *
   * <p>Hint: 2.990s will output 2.</p>
   */
  public long getSecondsOver() {
    LOG.debug("Start: [{}]. End: [{}].", this.currentStart, this.basis);
    return this.currentStart.until(this.basis, ChronoUnit.SECONDS);
  }

  public long getWholeDaysOver() {
    return this.currentStart.until(this.basis, ChronoUnit.DAYS);
  }

  public double getPercentDone() {
    final long seconds = ChronoUnit.SECONDS.between(this.currentStart, this.nextStart);
    final long over = getSecondsOver();

    return ((1.0d * over) / seconds);
  }

  private static Stream<Zodiac> getSortedByNextOccurrence(final ZonedDateTime basis) {
    return Arrays.stream(Zodiac.values())
        .sorted(DAYS_NEXT_COMPARATOR.apply(basis));
  }

}
