FROM  ubuntu:18.04

LABEL MAINTAINER="Sachini"

# add sql scripts
ADD ./scripts /etc/mysql/sachini

# Tell debconf to use the noninteractive frontend
ENV DEBIAN_FRONTEND noninteractive

# Tell debconf that the default MySQL root password should be "admin"
RUN echo 'mysql-server mysql-server/root_password password admin' | debconf-set-selections
RUN echo 'mysql-server mysql-server/root_password_again password admin' | debconf-set-selections

# Install MySQL
RUN apt-get update && apt-get -y install mysql-server

# Start MySQL and run our scripts
WORKDIR /etc/mysql/sachini

RUN service mysql restart \
  && mysql --user=root --password=admin < init-root.sql \
  && mysql --user=root --password=admin < schema.sql

EXPOSE 3306

ENTRYPOINT mysqld_safe --bind-address=0.0.0.0