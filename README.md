## Use Scenario
- 冰箱空間管理 Fridge Management
    - Raspberry Pi - Recognize the foods that are going to be put in the fridge.
    - Raspberry Pi - Recognize the foods that are taken out from the fridge.
    - Manage the content in the fridge by the two       results that are mentioned above, and show the lists and images to the users when they are making inquiry by the phone.
- 食物過期提醒 Expiration Alarm
    - Estimate the expiration date of the food according to what category it's in.
    - When a specific food is placed in the fridge too long, notify the user by sending a message to the user's phone. 
- 食譜小助手 Recipe assistant
    - Check whether the required foods that are list in the recipe are sufficient or not.
    - Recipe recommendation.

## System Architecture Plot

![](https://i.imgur.com/tKNtdAo.png)


## Services to Be Used

- **Compute**
    - Elastic Beanstalk : To deploy and scaling web applications and services.
    - Cloudwatch : Monitor resource and application
- **Storage**: S3, RDB/DynamoDB
- **MobileHub**
    - Sign-in : Use account to connect to the corresponding fridge database.
    - Notification : Notify user if any food is placed in the fridge too long.
    - Lambda cloud logic : To build backend services that you can call from your mobile app.
    - Cognito Sync : To synchronize user profile data across mobile devices and the web without requiring your own backend.

![](https://i.imgur.com/d5vjMfN.png)

- **AI** - Rekognition
    - Recognize food object through image
    - Compute similarity between food object images
- **Iot Platfrom** - MQTT
    - Connect AWS with MQTT methods(Using AWS Iot Platform)
- **BigData**
    - CloudSearch : Search recipe in DB
    - EMR : Calculate the preference of user according type of food in the refrigerator
