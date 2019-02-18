[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![Build Status](https://travis-ci.org/bmhm/twitter-tierkreiszeichenbot.svg?branch=master)](https://travis-ci.org/bmhm/twitter-tierkreiszeichenbot) [![Maintainability](https://api.codeclimate.com/v1/badges/4465f0c11214c29f822f/maintainability)](https://codeclimate.com/github/bmhm/twitter-tierkreiszeichenbot/maintainability) [![codecov](https://codecov.io/gh/bmhm/twitter-tierkreiszeichenbot/branch/master/graph/badge.svg)](https://codecov.io/gh/bmhm/twitter-tierkreiszeichenbot)


# twitter-tierkreiszeichenbot
A zodiac sign bot for twitter. Can post zodiac updates with a nice ASCII bar graph.

## Active bots

  * 🇩🇪 [@SternzeichenB](https://twitter.com/SternzeichenB) (German, tz=Europe/Berlin)
  * 🇬🇧 [@BotZodiac](https://twitter.com/BotZodiac) (British English, tz=Europe/London) 
  
## Features

  * You can enable or disable the progressbar.
  * You can set a custom progressbar length.
  * Translated to German and English, more languages are easy to add.
  * TimeZone support.
  * Will change avatar (twitter profile picture) according to zodiac sign.
  * (Coming) will react to simple questions.
  * (Coming) custom ASCII art bar characters.

## Configuration

See [Wiki:Configuration](https://github.com/bmhm/twitter-tierkreiszeichenbot/wiki/Configuration).

## How to start

You can start this spring boot application via script or command line like this:

```bash
# Switch to any java 11, in this example I use jabba.
# Just make sure JAVA_HOME and PATH are set correctly.
jabba use "adopt-openj9@1.11.0-2"
# start like this
java \
  -Duser.language=de \
  -jar ~/bin/tierkreiszeichenbot-bot-0.1.0-SNAPSHOT.jar \
  --spring.config.location=file:$HOME/.config/sternzeichenbot.properties \
  --user.timezone=Europe/Sofia
```
