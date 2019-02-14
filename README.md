[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/2759fda64e1941aa8009809c3557597e)](https://www.codacy.com/app/bmarwell/twitter-tierkreiszeichenbot?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=bmhm/twitter-tierkreiszeichenbot&amp;utm_campaign=Badge_Grade)

# twitter-tierkreiszeichenbot
A zodiac sign bot for twitter. Can post zodiac updates with a nice ASCII bar graph.

## Active bots

### German Bot
https://twitter.com/SternzeichenB

## Configuration

### Loading the configuration
You can either put a file `application.properties` next to your
jar file, or you can supply the path to the configuration
via command line argument:  `--spring.config.location=file:$HOME/.config/zodiacbot.properties`*[]:

### Property user.timezone (required)
* required
* Defaults to: Europe/Berlin
* Type: String
* Via: JVM-Arg (`-Duser.timezone=Europe/Berlin`)

This one should be given via jvm arg (`-Duser.timezone=Europe/Berlin`).
It will set the timezone for the bot. It has a massive impact.
First of all, it will be the source for your cron job (when the bot will post).
Furthermore, the timezone will be in effect when calculating days and seconds
between the current day and the next zodiac start, and between the current zodiac
start and the current day. Without the timezone information, the bot would not
be able to address leap days and leap seconds.
Keep your java installation up to date for this.

### Properties twitter.oauth.* (required)
* all 4 of them required
  * twitter.oauth.consumerKey
  * twitter.oauth.consumerSecret
  * twitter.oauth.accessToken
  * twitter.oauth.accessTokenSecret
* Defaults to: `null` (i.e. will throw exceptions).
* Type: Strings
* Via: config file.
  * You could use jvm args or program args, but this would expose your keys
    to all users on that system!

Go to https://developer.twitter.com/ to get your auth information.

### Property twitter.debug (optional)
* optional
* Defaults to: `false`
* Type: boolean
* Via: property file (preferred) or any other means.

Some more logging from the twitter library (twitter4j).

### Program arg `--doSingleTweet=true` (optional)
* optional
* Defauts to: `false`
* Type: boolean
* Via: preferred via program arg / command line.

If given The bot will only run until after 10 seconds and then tweet a single
status update.
Hint: Any crons may run in those time as well, resulting in more than one tweet.


## How to start

You can start this spring boot application via script or command line like this:

```bash
# Switch to any java 11, in this example I use jabba.
# Just make sure JAVA_HOME and PATH are set correctly.
jabba use "adopt-openj9@1.11.0-2"
# start like this
java -jar ~/bin/tierkreiszeichenbot-bot-0.1.0-SNAPSHOT.jar --spring.config.location=file:/home/bmarwell/.config/sternzeichenbot.properties
```
