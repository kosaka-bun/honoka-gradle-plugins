#!/bin/sh

set -e

cd $(dirname "$0")/../..
PROJECT_PATH="$(pwd)"

# 发布
gradle-publish() {
  task_name=publish
  if [ -n "$1" ]; then
    task_name=":$1:$task_name"
  fi
  ./gradlew $task_name
}

gradle-publish honoka-gradle-utils
gradle-publish
