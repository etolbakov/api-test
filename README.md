# api-test

TODO-mvc app to get familiar with 
 - compojure
 - ring
 - atom

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

##Endpoints
 - Get all employees
 
 ```curl -X GET http://localhost:3000/employees```

- Get employee by id

 ```curl -X GET http://localhost:3000/employees/1```

- Get employee by name

 ```curl -X GET http://localhost:3000/employees/bill```

- Add new employee

```
curl -X POST http://localhost:3000/employees \
        --data '{"name":"John", "age" : 29, "salary":"4455", "company": "MLtd"}' --header "Content-type:application/json"
```

- Remove employee

```
curl -X DELETE http://localhost:3000/employees/3        
```

## To read
http://weavejester.github.io/compojure/compojure.route.html




## License

Copyright © 2018 Eugene T.
