#!/bin/bash

if [ ! -f "$HOME/.jabba/jabba.sh" ]; then 
  curl \
    -H "Authorization: token $GITHUB_OAUTH_TOKEN" \
    -H "User-Agent: bmhm/twitter-tierkreiszeichenbot" \
    --insecure \
    --silent \
    --show-error \
    --fail \
    --location \
    --max-time 20 \
    --retry 3 \
    --retry-delay 2 \
    "https://github.com/shyiko/jabba/raw/master/install.sh" | bash && . $HOME/.jabba/jabba.sh;
fi

. $HOME/.profile
export JAVA_HOME
