package com.github.bmhm.twitter.tierkreiszeichenbot.bot;/*
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

import java.util.StringJoiner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TwitterConfig {

  @Value("${twitter.debug:false}")
  private boolean debug;

  @Value("${twitter.oauth.consumerKey}")
  private String consumerKey;

  @Value("${twitter.oauth.consumerSecret}")
  private String consumerSecret;

  @Value("${twitter.oauth.accessToken}")
  private String accessToken;

  @Value("${twitter.oauth.accessTokenSecret}")
  private String accessTokenSecret;

  TwitterConfig() {
    // nothing
  }

  public boolean isDebug() {
    return this.debug;
  }

  public void setDebug(final boolean debug) {
    this.debug = debug;
  }

  public String getConsumerKey() {
    return this.consumerKey;
  }

  public void setConsumerKey(final String consumerKey) {
    this.consumerKey = consumerKey;
  }

  public String getConsumerSecret() {
    return this.consumerSecret;
  }

  public void setConsumerSecret(final String consumerSecret) {
    this.consumerSecret = consumerSecret;
  }

  public String getAccessToken() {
    return this.accessToken;
  }

  public void setAccessToken(final String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessTokenSecret() {
    return this.accessTokenSecret;
  }

  public void setAccessTokenSecret(final String accessTokenSecret) {
    this.accessTokenSecret = accessTokenSecret;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "TwitterConfig{", "}")
        .add("debug=" + this.debug)
        .add("consumerKey='" + this.consumerKey + "'")
        .add("consumerSecret='" + this.consumerSecret + "'")
        .add("accessToken='" + this.accessToken + "'")
        .add("accessTokenSecret='" + this.accessTokenSecret + "'")
        .toString();
  }


}
