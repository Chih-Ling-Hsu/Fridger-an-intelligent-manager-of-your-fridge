var Region = 'us-east-1';
var DBEndPoint = 'https://dynamodb.us-east-1.amazonaws.com';
var S3IdentityPoolId = 'us-east-1:9c06fc52-4277-475e-b2ea-09c4c3c208bd';
var DBIdentityPoolId = 'us-east-1:e8a03db6-5929-4652-ba1f-f7867da79c9e';
var testID = 'us-east-1:ac0afb2d-5f46-4b42-ab4b-7a6dcef43f06'

AWS.config.update({
  region: Region,
  credentials: new AWS.CognitoIdentityCredentials({
    IdentityPoolId: S3IdentityPoolId
  })//,
  //endpoint: DBEndPoint

  // accessKeyId default can be used while using the downloadable version of DynamoDB. 
  // For security reasons, do not store AWS Credentials in your files. Use Amazon Cognito instead.
  //accessKeyId: "fakeMyKeyId",
  // secretAccessKey default can be used while using the downloadable version of DynamoDB. 
  // For security reasons, do not store AWS Credentials in your files. Use Amazon Cognito instead.
  //secretAccessKey: "fakeSecretAccessKey"
});

AWS.config.apiVersions = {
  dynamodb: '2012-08-10'
  // other service API versions
};