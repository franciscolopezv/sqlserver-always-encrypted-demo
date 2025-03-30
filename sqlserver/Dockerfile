FROM mcr.microsoft.com/mssql/server:latest

USER root

RUN ACCEPT_EULA=Y apt-get update && \
    ACCEPT_EULA=Y apt-get install -y mssql-tools unixodbc-dev gettext && \
    echo 'export PATH="$PATH:/opt/mssql-tools/bin"' >> ~/.bashrc

ENV PATH="$PATH:/opt/mssql-tools/bin"

USER mssql

# Copy the SQL template and entry script
COPY init-sql /docker-entrypoint-initdb.d/

# Switch to root to grant execute permission
USER root
RUN chmod +x /docker-entrypoint-initdb.d/entrypoint.sh

# Switch back to mssql user
USER mssql

ENTRYPOINT ["/bin/bash", "/docker-entrypoint-initdb.d/entrypoint.sh"]