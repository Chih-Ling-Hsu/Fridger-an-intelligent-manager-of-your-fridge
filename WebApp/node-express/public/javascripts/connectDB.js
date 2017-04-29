var ImgTableName = 'users';



/*var dynamodb = new AWS.DynamoDB();

function createMovies() {
    var params = {
        TableName : ImgTableName,
        KeySchema: [
            { AttributeName: "year", KeyType: "HASH"},
            { AttributeName: "title", KeyType: "RANGE" }
        ],
        AttributeDefinitions: [
            { AttributeName: "year", AttributeType: "N" },
            { AttributeName: "title", AttributeType: "S" }
        ],
        ProvisionedThroughput: {
            ReadCapacityUnits: 5,
            WriteCapacityUnits: 5
        }
    };

    dynamodb.createTable(params, function(err, data) {
        if (err) {
            document.getElementById('textarea').innerHTML = "Unable to create table: " + "\n" + JSON.stringify(err, undefined, 2);
        } else {
            document.getElementById('textarea').innerHTML = "Created table: " + "\n" + JSON.stringify(data, undefined, 2);
        }
    });
}*/



var params_scan = {
    TableName: ImgTableName
};
var docClient = new AWS.DynamoDB.DocumentClient();

//[JavaScript and DynamoDB] http://docs.aws.amazon.com/amazondynamodb/latest/gettingstartedguide/GettingStarted.JavaScript.html
//[Class: AWS.DynamoDB.DocumentClient] http://docs.aws.amazon.com/AWSJavaScriptSDK/latest/AWS/DynamoDB/DocumentClient.html#scan-property
function scanData() {
    document.getElementById('textarea').innerHTML += "Scanning images table." + "\n";

    docClient.scan(params_scan, onScan);

    function onScan(err, data) {
        if (err) {
            document.getElementById('textarea').innerHTML += "Unable to scan the table: " + "\n" + JSON.stringify(err, undefined, 2);
        } else {
            // Print all the images
            document.getElementById('textarea').innerHTML += "Scan succeeded. " + "\n";
            data.Items.forEach(function(image) {
                document.getElementById('textarea').innerHTML += image.imagename + ": " + image.bucketname + " - rating: " + image.reko_result + "\n";
            });

            // Continue scanning if we have more images (per scan 1MB limitation)
            document.getElementById('textarea').innerHTML += "Scanning for more..." + "\n";
            params.ExclusiveStartKey = data.LastEvaluatedKey;
            docClient.scan(params, onScan);            
        }
    }
}

