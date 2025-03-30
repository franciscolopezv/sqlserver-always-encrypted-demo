#!/bin/bash

# Replace placeholders in template with environment variables
envsubst < /docker-entrypoint-initdb.d/setup.sql.template > /docker-entrypoint-initdb.d/setup.sql

# Run SQL Server and wait for it to be ready
/opt/mssql/bin/sqlservr &

# Wait for SQL Server to start (you can improve this with a proper health check loop)
sleep 10

# Run your dynamic SQL
/opt/mssql-tools/bin/sqlcmd -S localhost -U SA -P "$SA_PASSWORD" -d master -i /docker-entrypoint-initdb.d/setup.sql

# Keep the container running
tail -f /dev/null
