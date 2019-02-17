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

import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Stream;

public class ZodiacImageTest {

  private static final Logger LOG = LoggerFactory.getLogger(ZodiacImageTest.class);

  @ParameterizedTest()
  @MethodSource("zodiacProvider")
  void testImages(final Zodiac zodiac) throws IOException {
    final @Nullable InputStream file = zodiac.getImage();
    LOG.debug("Zodiac [{}]: [{}].", zodiac.name(), file);

    if (null != file) {
      file.close();
    }

    Assertions.assertAll(
        () -> Assertions.assertNotNull(file)
    );

  }

  static Stream<Zodiac> zodiacProvider() {
    return Arrays.stream(Zodiac.values());
  }
}
