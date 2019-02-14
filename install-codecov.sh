#!/bin/bash

LATEST_ARTIFACT=$(curl \
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
  "https://api.github.com/repos/codacy/codacy-coverage-reporter/releases/latest" \
  | jq -r '.assets[0].browser_download_url')

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
  -o "$HOME/codacy-coverage-reporter-assembly-latest.jar" \
  "$LATEST_ARTIFACT"
