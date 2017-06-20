var express = require('express');
var router = express.Router();

// handle "./space" post request
router.post('/', function(req, res, next){
	var Region = 'us-east-1';
    var DBEndPoint = 'https://dynamodb.us-east-1.amazonaws.com';
    var MyAlbumIdentityPoolId;
    var MyDBIdentityPoolId;
    var DBIdentityPoolId;

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

    var result = [];
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

                    result.push({imagename: item.imagename, bucketname: item.bucketname, reko_result: item.reko_result});
                }
            );

            // continue scanning if we have more movies, because
            // scan can retrieve a maximum of 1MB of data
            if (typeof data.LastEvaluatedKey != "undefined") {
                console.log("Scanning for more...");
                params.ExclusiveStartKey = data.LastEvaluatedKey;
                docClient.scan(params, onScan);
            }

            console.log('-----------') 
            console.log(result)
            res.send(result);
        }
    }
	//var obj = {};
	//console.log('body: ' + JSON.stringify(req.body));
	//res.send(req.body);
});



module.exports = router;