// Create an SQS service object
var sqs = new AWS.SQS({apiVersion: '2012-11-05'});
var queueURL_in = "https://sqs.us-east-1.amazonaws.com/593733335849/input";
var queueURL_out = "https://sqs.us-east-1.amazonaws.com/593733335849/output";

function requestRecipe(key){
    var params = {
    DelaySeconds: 10,
    MessageAttributes: {
        "Title": {
            DataType: "String",
            StringValue: "Recipe Request"
        }
    },
    MessageBody: "Recipe:"+key,
    QueueUrl: queueURL_in
    };

    sqs.sendMessage(params, function(err, data) {
    if (err) {
        console.log("Error", err);
    } else {
        console.log("Success", data.MessageId);
    }
    });
}

function receiveRecipe(){
    var params = {
        AttributeNames: [
            "SentTimestamp"
        ],
        MaxNumberOfMessages: 1,
        MessageAttributeNames: [
            "All"
        ],
        QueueUrl: queueURL_out,
        VisibilityTimeout: 0,
        WaitTimeSeconds: 0
    };

    sqs.receiveMessage(params, function(err, data) {
        if (err) {
            console.log("Receive Error", err);
        } else {
            console.log(data);
            /*var deleteParams = {
                QueueUrl: queueURL_out,
                ReceiptHandle: data.Messages[0].ReceiptHandle
            };
            sqs.deleteMessage(deleteParams, function(err, data) {
            if (err) {
                console.log("Delete Error", err);
            } else {
                console.log("Message Deleted", data);
            }
            });*/
        }
    });
}
