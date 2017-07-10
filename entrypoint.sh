#! /bin/sh

POSTGRES_HOST=${POSTGRES_HOST:-postgres}
POSTGRES_PORT=5432
POSTGRES_DB=${POSTGRES_DB:-${POSTGRES_ENV_POSTGRES_DB}}
POSTGRES_USER=${POSTGRES_USER:-${POSTGRES_ENV_POSTGRES_USER}}
POSTGRES_PASSWORD=${POSTGRES_PASSWORD:-${POSTGRES_ENV_POSTGRES_PASSWORD}}
POSTGRES_URL="jdbc:postgresql://${POSTGRES_HOST}:${POSTGRES_PORT}/${POSTGRES_DB}"

# Set up database
echo "Waiting to set up database..."
nc="nc -z ${POSTGRES_HOST} ${POSTGRES_PORT}"
${nc}
while [ $? -ne 0 ]; do
  echo ...
  sleep 5
  ${nc}
done
cd flyway/
./flyway migrate -url="${POSTGRES_URL}" -user="${POSTGRES_USER}" -password="${POSTGRES_PASSWORD}"
cd

# Run application
java -jar ./app.jar
