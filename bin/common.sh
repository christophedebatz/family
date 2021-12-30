#!/bin/bash

function get_env_file() {
     # get env file
  local env_file=".env"
  if [ -n "$1" ]; then
    env_file=".env.$1"
  fi

  echo "$env_file"
}

function export_env_variables() {
  export $(grep -v '^#' "$1" | xargs -0) || export $(grep -v '^#' "$1" | xargs -d '\n')
}

function success() {
  printf " \e[30;42m[ $1 ]\e[0m\n"
}

function warn() {
  printf " \e[0;30m\e[42m[ $1 ]\e[0m\n"
}

function error() {
  printf " \e[41m[ $1 ]\e[0m\n"
}

function start_redis()
{
    container_name="redis_test"
    logs_friendly_name="Redis"

    if docker top $container_name &>/dev/null
    then
        echo -n "$logs_friendly_name - Container $container_name is already running"

        # Clear keys
        docker exec -it $container_name redis-cli FLUSHALL > /dev/null

        return 0
    fi

    #Starting redis
    if docker container inspect ${container_name} 2> /dev/null > /dev/null
    then
        echo -n "$logs_friendly_name - Restarting container ($container_name)"
        docker start ${container_name}
    else
        echo -n  "$logs_friendly_name - Starting a dedicated Redis container ($container_name) for tests..."
        docker run --name=redis_test --publish=16379:6379 --hostname=redis --restart=on-failure --detach redis:latest
    fi

    echo -n "$logs_friendly_name - Redis is ready for connections."
}

