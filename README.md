
#### The task

A retailer offers a rewards program to its customers, awarding points based on each recorded purchase.

A customer receives 2 points for every dollar spent over $100 in each transaction, plus 1 point for every dollar spent over $50 in each transaction

(e.g. a $120 purchase = 2x$20 + 1x$50 = 90 points).


Given a record of every transaction during a three month period, calculate the reward points earned for each customer per month and total.

###### as I understand it (which somehow took me a moment to understand) 
* x <= 50 no points
* x > 50 && x <= 100 = 1points per dollar (max 50)
* x > 100 = 2 points per each dollar (excluding 50-100 pointing range, which was not entirely clear) 

#### Requirements 
In order to test build and run application you must have

* JDK 8+
* Maven (LTS)
* NodeJS (LTS)

#### How to build app

Ideally all you have to do is `mvn package` in the root of the project. If it does not work, I have messed something up and nothing can bo done about it (let me know)

What could go wrong is the installation of frontend dependencies.You can try to go to `fronterd/charter` and run `npm install`. If that runs fine, maven build should pass now.

#### How run the app

Once `mvn package` completes, do:
```
cd target
java -Dspring.profiles.active=demo -jar 4Charter-1.0-SNAPSHOT.jar 
```

App should be accessible via `http://localhost:8080`. In the `demo` profile, application is using inmemory H2 database with some data in it. Running application without `demo` profile will use persistent (file backed), but empty storage
