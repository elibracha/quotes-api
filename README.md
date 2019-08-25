# quotes-api

This is an implementation for a rest CRUD api for quote managment. The two main entities are Quote and Item with have a
many to many relationship. same item can be present in different quote and so on. the api expose 3 controllers
with 3 main endpoints:

* /user - only avaliable to test easily token generation (instead of working against another auth server). create new user 
          in db.
          
* /authenticate - accept json with username and password and return a valid jwt.
* /quotes/** - all CRUD api endpoints.
