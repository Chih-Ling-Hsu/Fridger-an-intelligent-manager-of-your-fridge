var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {  

  res.render('index', { title: 'Fridger' });
  /*var Region = 'us-east-1';
  var DBEndPoint = 'https://dynamodb.us-east-1.amazonaws.com';
  var MyAlbumIdentityPoolId = 'us-east-1:9c06fc52-4277-475e-b2ea-09c4c3c208bd';
  var MyDBIdentityPoolId = 'us-east-1:00c34e6f-8132-4c6d-b4db-3e50b457323a';
  var DBIdentityPoolId = 'us-east-1:e8a03db6-5929-4652-ba1f-f7867da79c9e';

  var AWS = require("aws-sdk");

  AWS.config.update({
    region: Region,
    credentials: new AWS.CognitoIdentityCredentials({
      IdentityPoolId: DBIdentityPoolId
    })
  });

  console.log("Querying for images info.");

  var docClient = new AWS.DynamoDB.DocumentClient();

  var params = {
      TableName: "users",
      ProjectionExpression: "imagename, #bucketname, reko_result",
      FilterExpression: "#bucketname = :bkt",
      ExpressionAttributeNames: {
          "#bucketname": "bucketname",
      },
      ExpressionAttributeValues: {
          ":bkt": "rekognition-image-102062329"
      }
  };

  docClient.scan(params, onScan);

  function onScan(err, data) {
      if (err) {
          console.error("Unable to scan the table. Error JSON:", JSON.stringify(err, null, 2));
      } else {
          // print all the movies
          console.log("Scan succeeded.");
          data.Items.forEach(function(item) {
            console.log(
                  item.imagename + ": ",
                  item.bucketname, "- rating:", item.reko_result);
          });

          // continue scanning if we have more movies, because
          // scan can retrieve a maximum of 1MB of data
          if (typeof data.LastEvaluatedKey != "undefined") {
              console.log("Scanning for more...");
              params.ExclusiveStartKey = data.LastEvaluatedKey;
              docClient.scan(params, onScan);
          }
      }
    }*/
});

module.exports = router;



/*
//[Query and Scan the Data] http://docs.aws.amazon.com/amazondynamodb/latest/gettingstartedguide/GettingStarted.NodeJs.04.html

var AWS = require("aws-sdk");

AWS.config.update({
  region: Region,
  endpoint: DBEndPoint
});

var docClient = new AWS.DynamoDB.DocumentClient();

console.log("Querying for movies from 1985.");

var docClient = new AWS.DynamoDB.DocumentClient();

var params = {
    TableName: "users",
    ProjectionExpression: "#imagename, bucketname, rek_result",
    FilterExpression: "#imagename = :img",
    ExpressionAttributeNames: {
        "#imagename": "imagename",
    },
    ExpressionAttributeValues: {
        ":img": "aaa"
    }
};

console.log("Scanning Movies table.");
docClient.scan(params, onScan);

function onScan(err, data) {
    if (err) {
        console.error("Unable to scan the table. Error JSON:", JSON.stringify(err, null, 2));
    } else {
        // print all the movies
        console.log("Scan succeeded.");
        data.Items.forEach(function(item) {
          console.log(
                item.imagename + ": ",
                item.bucketname, "- rating:", item.rek_result);
        });

        // continue scanning if we have more movies, because
        // scan can retrieve a maximum of 1MB of data
        if (typeof data.LastEvaluatedKey != "undefined") {
            console.log("Scanning for more...");
            params.ExclusiveStartKey = data.LastEvaluatedKey;
            docClient.scan(params, onScan);
        }
    }
  }*/