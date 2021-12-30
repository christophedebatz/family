#!/bin/bash -e

. ./bin/common.sh

function connect_db() {
  # creating pgpass Postgres credentials file
  touch ~/.pgpass
  chmod 600 ~/.pgpass
  echo "$POSTGRES_HOST:$POSTGRES_PORT:$POSTGRES_DB:$POSTGRES_USER:$POSTGRES_PASSWORD" > ~/.pgpass

  # connecting to pg database
  echo "Trying to connect to Postgres $POSTGRES_DB"
  until PGPASSFILE=~/.pgpass psql -h "$POSTGRES_HOST" -U "$POSTGRES_USER" -d "$POSTGRES_DB" -c '\l'; do
    echo >&2 "$(date +%Y%m%dt%H%M%S) Postgres is unavailable - sleeping"
    sleep 2
  done
  echo >&2 "$(date +%Y%m%dt%H%M%S) Postgres is up - executing command"
}

function create_db_user() {
  # creating non-root pg user
  echo "Trying to create user for president's database administration "
  sqlFile="$(pwd)/bin/$1.sql"
  psql PGPASSFILE=~/.pgpass psql -h "$POSTGRES_HOST" -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f "$sqlFile"
}

env_file=$(get_env_file "dev")

# load "dev" env variables
export_env_variables "$env_file"

# connecting to dev db
connect_db

# creating new db user for dev (play dev.sql)
create_db_user "dev"

mvn clean install
cp -R target app/

# launch app with pm2
./mvnw spring-boot:run
