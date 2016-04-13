Bookfast
=========

This application makes it easier to know when campsites, yurts, cabins, etc become available for reservations on 
[recreation.gov](http://recreation.gov). For those sites which are in high demand, this application can be useful to 
ensure you're the first one to know when new dates become available for reservations.

You can try a deployed instance of the app here: https://bookfast.herokuapp.com/

## How does this application work?
Periodically, this application will screen scrape the availability calendars for the desired sites on recreation.gov. 
It uses that information to keep track of when dates open up and when reservations have been made. 

This application provides a user interface to view availability dates, and subscribe to be notified by SMS instant message when dates open up.
  
Availabilities will also be announced on the [@mthoodlookouts](http://twitter.com/mthoodlookouts) Twitter account, so rather than subscribe to SMS 
notifications, users can simply subscribe to notifications to that Twitter account (those notifications would be
done within the Twitter app).

This application is based on the "play-heroku-seed" template provided by Lightspeed (aka Typesafe) as part of the 
Play Framework. This application uses the Jsoup library for screen scraping, and Akka for running Jsoup tasks. 
Twitter and Twilio APIs are used for notifications. 
Database manipulation is handled by Slick connected to PostgreSQL, which is used for keeping track of reserved dates. 
Heroku is used for hosting the application.  


### Subscription Form:

	Enter Phone Number: ___-___-___.
	
	You're subscribed to X notifications:  ___.
	
	Subscribe to be notified of availability for date MM/DD/YYYY.
	
	Unsubscribe to all notifications.

### Availability Page:

	5 mile butte is available for the following dates:
	
		List all rows in the availability database

### Back-end Logic:

	Set the DATE from which to begin searching.

	Query the URL
	
		if they're all N, then stop searching and reset the DATE mark to the previous search
		
		if there are any A, a or W, then
		
			if DATE is not in availbility database, then
			
				send AVAILABLE alert
				
				save the DATE in the DB
				
		for each R or X:
		
			if DATE exists in the availability database, then
			
				remove that element from the database
				
			send NOTAVAILABLE alert to subscribers of DATE.


### DB Schema:

#### TABLE SUBSCRIPTIONS:

	ID
	DATE
	phone number (STRING)
	URL

#### TABLE AVAILABILITY:

	ID
	Site Name
	Available Date

#### Database Setup
- Install PostgreSQL 9.3 Postgres.app is the easiest way: [postgresapp.com](http://postgresapp.com/)
- [PostgreSQL full documentation](http://www.postgresql.org/docs/9.3/interactive/)
- Run `psql`
- Set up a development database, note the underscores:
`CREATE DATABASE play_heroku_seed;`
- Confirm the database has been created by looking at the database list
`\l`


#### Run the application
- In the root folder of the repo:
`activator run`
- In a browser, open localhost:9000. If no folder for conf/evolutions/default exists, do not worry. On first request in browser, slick will automatically inspect the models, and generate a 1.sql file in conf/evolutions/default. This initial file is a complete schema of the application.

#### Development
##### Common database tasks
- If you need to connect to the database to inspect it or run sql:
`\c play_heroku_seed;`
and if you are currently logged into osx as "johnsmith" you should see
`You are now connected to database "play_heroku_seed" as user "johnsmith".`

- To view users created in the user table using psql:
1. Open psql, connect to the database:
`\c play_heroku_seed;`
2. View user table data:
`SELECT * FROM "user";`

Note: "user" is also a keyword in PostgreSQL, if you enter this command without quotation marks, it will not select from the play_heroku_seed user table, but instead will output from PostgreSQL's internal database users table and you will get something like this:

```
play_heroku_seed=# SELECT * FROM user;
 current_user
--------------
 Mashallah
(1 row)
```

- To reset your local database:
`DROP DATABASE play_heroku_seed;`
`CREATE DATABASE play_heroku_seed;`
and run the application


##### Introducing model changes to the database
If you modify the models, and you do not care about current production data (still before launch):

1. Stop the application
2. Delete conf/evolutions/default/1.sql
3. Open psql, reset the database by doing the following:
`DROP DATABASE play_heroku_seed;`
`CREATE DATABASE play_heroku_seed;`
4. Run the application, visit localhost:9000

A 1.sql file reflecting the current state of the application models will be auto-generated by slick, auto-applied by play, and now running. If a 1.sql file was not generated, you have likely introduced a change to the model that slick cannot interpret.

Slick is currently unable to generate incremental database evolution files to make those changes. It can only generate a complete snapshot of the application models at any point. If you want to introduce incremental changes to the models, you will need to manually write the SQL database evolutions.
### Procfile
The app will run without a Procfile, as the necessary settings have been put in application.conf, are read by Heroku's default Procfile settings in the scala buildpack and are applied.

The Procfile is included in this app for reference and best practices.Heroku reads the Procfile and attempts to initialize the app in build.sbt with the same name.

Procfile:
```
web: target/universal/stage/bin/play-heroku-seed
```
build.sbt:

```
name := """play-heroku-seed"""
```
If you modify the application name in the Procfile, make sure you update the application name in build.sbt. If the names do not match, the web process will fail to start on Heroku.


## License

Bookfast source code is licensed under the Apache License, Version 2.0.

See [Third Party Software](https://github.com/glowroot/glowroot/wiki/Third-Party-Software) for license detail of third party software included in the binary distribution.