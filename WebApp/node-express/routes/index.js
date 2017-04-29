var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  
  var Region = 'us-east-1';
  var DBEndPoint = 'https://dynamodb.us-east-1.amazonaws.com';
  var S3IdentityPoolId = 'us-east-1:9c06fc52-4277-475e-b2ea-09c4c3c208bd';
  var DBIdentityPoolId = 'us-east-1:e8a03db6-5929-4652-ba1f-f7867da79c9e';

  //[Query and Scan the Data] http://docs.aws.amazon.com/amazondynamodb/latest/gettingstartedguide/GettingStarted.NodeJs.04.html
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
  }

  //[Sending and Receiving Messages in Amazon SQS] http://docs.aws.amazon.com/sdk-for-javascript/v2/developer-guide/sqs-examples-send-receive-messages.html
  /*var AWS = require("aws-sdk");

  AWS.config.update({
    region: Region,
    credentials: new AWS.CognitoIdentityCredentials({
      IdentityPoolId: S3IdentityPoolId
    })
  });

  // Create an SQS service object
  var sqs = new AWS.SQS({apiVersion: '2012-11-05'});

  var params = {
  DelaySeconds: 10,
  MessageAttributes: {
    "Title": {
      DataType: "String",
      StringValue: "The Whistler"
    },
    "Author": {
      DataType: "String",
      StringValue: "John Grisham"
    },
    "WeeksOn": {
      DataType: "Number",
      StringValue: "6"
    }
  },
  MessageBody: "Information about current NY Times fiction bestseller for week of 12/11/2016.",
  QueueUrl: "https://sqs.us-east-1.amazonaws.com/593733335849/input"
  };

  sqs.sendMessage(params, function(err, data) {
    if (err) {
      console.log("Error", err);
    } else {
      console.log("Success", data.MessageId);
    }
  });*/

  


  res.render('index', { title: 'Express' });
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