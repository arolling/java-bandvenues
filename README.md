# Concert Tracker

#### An app to track bands and venues, March 4/2016

#### By _**Abigail Rolling**_

## Description

A website to track bands and the venues at which they have played. It also allows the user to assign a genre to a band.


## Setup/Installation Requirements

Clone this repository:
```
$ cd ~/Desktop
$ git clone https://github.com/arolling/java-bandvenues
$ cd java-bandvenues
```

Open terminal and run Postgres:
```
$ postgres
```

Open a new tab in terminal and create the `hair_salon` database:
```
$ psql
$ CREATE DATABASE band_venues;
$ psql band_venues < band_venues.sql
```

Navigate back to the directory where this repository has been cloned and run gradle:
```
$ gradle run
```

## Known Bugs

An attribute for the size of a band's fanbase exists, but nothing is currently being done with it. Size of venue does not effect which bands can play there.


## Support and contact details

[E-mail me](mailto:arolling@gmail.com) with any comments or concerns.

## Technologies Used

* Java
* junit
* Gradle
* Spark
* fluentlenium
* PostgreSql
* Bootstrap


### License

Licensed under the GPL.

Copyright (c) 2016 **Abigail Rolling**
