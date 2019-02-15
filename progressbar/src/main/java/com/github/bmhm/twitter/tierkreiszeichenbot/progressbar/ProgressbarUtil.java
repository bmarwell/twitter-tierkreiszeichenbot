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

package com.github.bmhm.twitter.tierkreiszeichenbot.progressbar;

import java.util.StringJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgressbarUtil {

  private static final Logger LOG = LoggerFactory.getLogger(ProgressbarUtil.class);

  private final char[] blocks;
  private final int barLength;
  private final char emptyChar;
  private final char fullChar;

  public ProgressbarUtil(final ProgressType type, final int barLength) {
    this.blocks = type.getBlockChars();
    this.barLength = barLength;

    this.emptyChar = type.getEmptyChar();
    this.fullChar = type.getFullChar();
  }

  public ProgressbarUtil(final char[] chars, final int barLength) {
    this.blocks = chars;
    this.barLength = barLength;

    this.emptyChar = this.blocks[0];
    this.fullChar = this.blocks[blocks.length - 1];
  }

  public String getRepresentation(final double percent) {
    final double nonEmptyFieldFraction = this.barLength * percent;
    final int nonEmptyFieldCount = (int) Math.ceil(nonEmptyFieldFraction);
    final int fullFieldCount = getFullFieldCount(nonEmptyFieldFraction);
    final int halfStepCount = nonEmptyFieldCount - fullFieldCount;

    if (0 == halfStepCount && fullFieldCount == this.barLength) {
      // shortcut: All fields are full.
      return repeat(this.fullChar, nonEmptyFieldCount);
    }

    final double fractionField = nonEmptyFieldFraction - fullFieldCount;
    final int halfStepIndex = getHalfStepIndex(fractionField);
    final int emptyFieldCount = this.barLength - nonEmptyFieldCount;

    LOG.trace("BarLength: [{}]. FullChars: [{}]. HalfSteps: [{}]. EmptyFields: [{}].",
        this.barLength,
        fullFieldCount,
        halfStepCount,
        emptyFieldCount);

    return String.format("%s%s%s",
        repeat(this.fullChar, fullFieldCount),
        repeat(this.blocks[halfStepIndex], halfStepCount),
        repeat(this.emptyChar, emptyFieldCount)
    );
  }

  private int getHalfStepIndex(final double fractionField) {
    LOG.trace("Remainder: [{}].", fractionField);
    final int round = (int) Math.round(fractionField * (this.blocks.length - 1));
    if (round < 0) {
      return 0;
    }

    return round;
  }

  private int getFullFieldCount(final double v) {
    final int floor = (int) Math.floor(v);

    if (floor < 0) {
      return 0;
    }

    return floor;
  }

  /**
   * Repeat a char n times.
   *
   * <p>Taken from Apache StringUtils.<br>
   * Exact version: <a href="https://github.com/apache/commons-lang/blob/07f537120f6a9220c8cc8216b22f9f5744f619b1/src/main/java/org/apache/commons/lang3/StringUtils.java#L6322"
   * >commit 07f5371 on 19 Nov 2018 @ L6322</a>.<br></p>
   *
   * <p>Also licensed untdr Apache 2.</p>
   */
  public static String repeat(final char ch, final int repeat) {
    if (repeat <= 0) {
      return "";
    }
    final char[] buf = new char[repeat];
    for (int i = repeat - 1; i >= 0; i--) {
      buf[i] = ch;
    }
    return new String(buf);
  }

  public char[] getBlockChars() {
    return this.blocks;
  }

  public int getBarLength() {
    return this.barLength;
  }

  public char getEmptyChar() {
    return this.emptyChar;
  }

  public char getFullChar() {
    return this.fullChar;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "ProgressbarUtil{", "}")
        .add("blocks=" + this.blocks)
        .add("barLength=" + this.barLength)
        .add("emptyChar=" + this.emptyChar)
        .add("fullChar=" + this.fullChar)
        .toString();
  }
}
