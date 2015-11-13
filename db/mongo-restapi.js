
/** merchants indexes **/
db.getCollection("merchants").ensureIndex({
  "_id": NumberInt(1)
},[
  
]);

/** users indexes **/
db.getCollection("users").ensureIndex({
  "_id": NumberInt(1)
},[
  
]);

/** merchants records **/
db.getCollection("merchants").insert({
  "_id": ObjectId("5645379015a09bc01a00002b"),
  "id": 3,
  "parentId": null,
  "name": "Dev-Merchant",
  "3dStatus": "ALL",
  "mcc": "6012",
  "ipnUrl": "",
  "apiKey": "b5c946997663e1356542fd966167bbae",
  "cpgKey": null,
  "type": "ECOM",
  "descriptor": "descriptor2",
  "secretKey": "1234",
  "comType": "API"
});
db.getCollection("merchants").insert({
  "_id": ObjectId("56453b8615a09bc01a00002c"),
  "transactionId": "1-1444392550-1",
  "id": 1,
  "parentId": null,
  "name": "Dev-Merchant",
  "3dStatus": "ALL",
  "mcc": "6012",
  "ipnUrl": null,
  "apiKey": "1234",
  "cpgKey": null,
  "type": "ECOM",
  "descriptor": "descriptor",
  "secretKey": "1234",
  "comType": "API"
});

/** users records **/
db.getCollection("users").insert({
  "_id": ObjectId("5645235115a09bf41700390c"),
  "email": "merchant@test.com",
  "password": "$2a$08$Svac34tvULNqNWlvOm2OF./oQlmShlwNkc38ObRr5I1luTytpKOnC"
});
