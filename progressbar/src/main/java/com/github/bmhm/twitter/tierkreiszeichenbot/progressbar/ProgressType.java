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

public enum ProgressType {
  /**
   * Simple block type:
   * {@code [████████░░░░░░░] 50 %}.
   */
  BLOCK_SIMPLE(new char[]{'░', '█'}),
  /**
   * More fancy version with 8ths.
   */
  BLOCK_FANCY(new char[]{'░', '▏', '▎', '▍', '▌', '▋', '▊', '▉', '█'});

  private final char[] blockChars;
  private final int charCount;

  ProgressType(final char[] blockChars) {
    this.blockChars = blockChars;
    this.charCount = blockChars.length;
  }

  public char[] getBlockChars() {
    return this.blockChars;
  }

  public int getCharCount() {
    return this.charCount;
  }

  public char getFullChar() {
    return this.blockChars[this.charCount - 1];
  }

  public char getEmptyChar() {
    return this.blockChars[0];
  }
}
