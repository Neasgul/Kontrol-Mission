#!/usr/bin/env bash

# git tag
export GRADLE_PATH=./version   # path to the gradle file

export VERSION=$(<$GRADLE_PATH)
export GIT_TAG=$VERSION.$TRAVIS_BUILD_NUMBER

echo version: $VERSION
echo release tag: $GIT_TAG

git config --global user.email "builds@travis-ci.com"
git config --global user.name "Travis CI"
git tag $GIT_TAG -a -m "Generated tag from TravisCI for build $TRAVIS_BUILD_NUMBER"
git push -q https://$GIT_KEY@github.com/Neasgul/Mission-Kontrol --tags
