package com.github.bmhm.twitter.tierkreiszeichenbot.progressbar;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.IntStream;
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

class ProgressbarUtilTestFancy {

  private static final Logger LOG = LoggerFactory.getLogger(ProgressbarUtilTestFancy.class);

  @Test
  void getRepresentation_empty() {
    final int barLength = 16;
    final ProgressType blockSimple = ProgressType.BLOCK_FANCY;
    final ProgressbarUtil progressbarUtil = new ProgressbarUtil(blockSimple, barLength);
    final String representation = progressbarUtil.getRepresentation(0.0d);

    LOG.debug("Progress: [{}].", representation);

    final char firstChar = blockSimple.getEmptyChar();
    Assertions.assertAll(
        () -> assertEquals(barLength, representation.length()),
        () -> assertEquals(ProgressbarUtil.repeat(firstChar, barLength), representation)
    );
  }

  @Test
  void getRepresentation_half_16() {
    final int barLength = 16;
    final ProgressType blockSimple = ProgressType.BLOCK_FANCY;
    final ProgressbarUtil progressbarUtil = new ProgressbarUtil(blockSimple, barLength);
    final String representation = progressbarUtil.getRepresentation(0.5d);

    LOG.debug("Progress: [{}].", representation);

    final char firstChar = blockSimple.getEmptyChar();
    final char lastChar = blockSimple.getFullChar();
    final int halfBarSize = barLength / 2;
    final String expectedGraph = ProgressbarUtil.repeat(lastChar, halfBarSize) + ProgressbarUtil.repeat(firstChar, halfBarSize);
    Assertions.assertAll(
        () -> assertEquals(barLength, representation.length()),
        () -> assertEquals(expectedGraph, representation)
    );
  }

  @Test
  void getRepresentation_half_2() {
    final int barLength = 2;
    final ProgressType blockSimple = ProgressType.BLOCK_FANCY;
    final ProgressbarUtil progressbarUtil = new ProgressbarUtil(blockSimple, barLength);
    final String representation = progressbarUtil.getRepresentation(0.5d);

    LOG.debug("Progress: [{}].", representation);

    final char firstChar = blockSimple.getEmptyChar();
    final char lastChar = blockSimple.getFullChar();
    final int halfBarSize = barLength / 2;
    final String expectedGraph = ProgressbarUtil.repeat(lastChar, halfBarSize) + ProgressbarUtil.repeat(firstChar, halfBarSize);
    Assertions.assertAll(
        () -> assertEquals(barLength, representation.length()),
        () -> assertEquals(expectedGraph, representation)
    );
  }

  @Test
  void getRepresentation_threeQuarters() {
    final int barLength = 16;
    final ProgressType blockSimple = ProgressType.BLOCK_FANCY;
    final ProgressbarUtil progressbarUtil = new ProgressbarUtil(blockSimple, barLength);
    final String representation = progressbarUtil.getRepresentation(0.75d);

    LOG.debug("Progress: [{}].", representation);

    final char firstChar = blockSimple.getEmptyChar();
    final char lastChar = blockSimple.getFullChar();
    final String expectedGraph = ProgressbarUtil.repeat(lastChar, (int) (barLength * 0.75))
        + ProgressbarUtil.repeat(firstChar, (int) (barLength * 0.25));
    Assertions.assertAll(
        () -> assertEquals(barLength, representation.length()),
        () -> assertEquals(expectedGraph, representation)
    );
  }

  @Test
  void getRepresentation_doteight() {
    final int barLength = 16;
    final ProgressType blockSimple = ProgressType.BLOCK_FANCY;
    final ProgressbarUtil progressbarUtil = new ProgressbarUtil(blockSimple, barLength);
    final String representation = progressbarUtil.getRepresentation(0.8d);

    LOG.debug("Progress: [{}].", representation);

    Assertions.assertAll(
        () -> assertEquals(barLength, representation.length())
    );
  }

  @Test
  void getRepresentation_full() {
    final int barLength = 16;
    final ProgressType blockSimple = ProgressType.BLOCK_FANCY;
    final ProgressbarUtil progressbarUtil = new ProgressbarUtil(blockSimple, barLength);
    final String representation = progressbarUtil.getRepresentation(1.0d);

    LOG.debug("Progress: [{}].", representation);

    final char lastChar = blockSimple.getFullChar();
    Assertions.assertAll(
        () -> assertEquals(barLength, representation.length()),
        () -> assertEquals(ProgressbarUtil.repeat(lastChar, barLength), representation)
    );
  }

  @Test
  void getRepresentation_anyLength_half() {
    final ProgressType blockSimple = ProgressType.BLOCK_FANCY;

    final List<ProgressbarUtil> progressbarUtils = IntStream.range(0, 40)
        .sorted()
        .sequential()
        .mapToObj(length -> new ProgressbarUtil(blockSimple, length))
        .collect(toList());

    progressbarUtils.stream()
        .sequential()
        .forEach(bar -> {
          final String representation = bar.getRepresentation(0.5);
          LOG.debug("Progress: [{}].", representation);
          assertEquals(bar.getBarLength(), representation.length());
        });
  }

  @Test
  void getRepresentation_20_anyPercent() {
    final ProgressType blockSimple = ProgressType.BLOCK_FANCY;
    final ProgressbarUtil progressbarUtil = new ProgressbarUtil(blockSimple, 20);

    final List<Double> collect = IntStream.range(100, 1000)
        .sorted()
        .sequential()
        .mapToDouble(anInt -> anInt / 1000.0d)
        .mapToObj(Double::valueOf)
        .collect(toList());

    collect.stream()
        .forEach(d -> LOG.debug("Double: [{}].", d));

    collect.stream()
        .sequential()
        .forEach(bar -> {
          final String representation = progressbarUtil.getRepresentation(bar);
          LOG.debug("Progress: [{}].", representation);
          assertEquals(progressbarUtil.getBarLength(), representation.length());
        });
  }

}
