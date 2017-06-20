// Create an SQS service object
var sqs = new AWS.SQS({apiVersion: '2012-11-05'});
var queueURL_in;
var queueURL_out;

function requestRecipe(key){
    var params = {
    DelaySeconds: 10,
    MessageAttributes: {
        "Title": {
            DataType: "String",
            StringValue: "Recipe Request"
        }
    },
    MessageBody: '{"Recipe":'+key+'}',
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

function rendarRecipe(){
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
            var recipeList = $.parseJSON(data['Messages'][0]["Body"]);
            //console.log(recipe);
            //console.log(recipe[0]["Recipe"]);
            var recipePage = "";
            for(key in recipeList){
                var recipe = recipeList[key];
                var stepList = "";
                for(step in recipe["Steps"]){
                    if(step==0){
                        stepList += recipe["Steps"][step];
                    }
                    else{
                        stepList += '<br><span class="glyphicon glyphicon-arrow-down"></span><br>' + recipe["Steps"][step];
                    }                    
                }
                
                var ingList = "";
                for(ing in recipe["Ingredients"]){
                    ingList += '&nbsp;<code>' + recipe["Ingredients"][ing] + '</code>';
                }

                recipePage += getHtml([
                    '<div class="col-sm-4">',
                    '<div class="panel panel-info">',
                    '<div class="panel-heading">'+ recipe["Recipe"] + '</div>',
                    '<div class="panel-body" style="text-align:center;">'+ stepList + '</div>',
                    '<div class="panel-footer">Ingredients:&nbsp;'+ ingList + '</div>',
                    '</div>',
                    '</div>'
                ])
            }
            recipePage = '<div class="row">' + recipePage + '</div>'
            $('#main').html(recipePage);

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
