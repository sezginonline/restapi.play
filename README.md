# Play Framework REST API

An example of building stateless REST API, using JWT (JSON Web Token), unit tests and MongoDB connection with Morphia object-document mapper

## Running

- Create MongoDB database: `restapi` and import `db/mongo-restapi.js`
- Download activator at https://downloads.typesafe.com/typesafe-activator/1.3.6/typesafe-activator-1.3.6-minimal.zip
- You can run the app by using `cd /path/to/project & /path/to/activator run`
- Browse: `http://localhost:9000/api/v3/merchant`

## Testing

`cd /path/to/project & /path/to/activator test` will run all tests in the project.
