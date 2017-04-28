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

![](https://i.imgur.com/6WZuoMP.png =500x)


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

## Purchase Plan

Budget plan must include equipment name, estimated price, and the use. ([Purchase Link](https://www.raspberrypi.com.tw/purchase/))

產品連結
- [開發板&擴充板](https://www.raspberrypi.com.tw/shop/board/)
- [Camera](https://www.raspberrypi.com.tw/tag/camera/)
- [Raspberry Pi 3 Model B with Camera Module v2](https://www.element14.com/community/docs/DOC-83171/l/raspberry-pi-3-model-b-with-camera-module-v2)
- [Raspberry Pi 3 Model B 開箱文](http://blog.itist.tw/2016/03/clean-installation-and-setup-on-raspbian-jessie.html)
- [Raspberry Pi 3 打造隨身無線基地台](http://blog.itist.tw/2016/03/using-raspberry-pi-3-as-wifi-ap-with-raspbian-jessie.html)

| Equipment name | Usage | Estimated Price | Note |
| -------------- | ----- | --------------- | ---- 
| Raspberry Pi Camera(V2) | Capture food object image. | $ 1100 | 8百萬像素 |
|PL2303HXD USB轉TTL序列傳輸線|For debugging| $200| |
| HC-SR04 超音波距離感測器 | Detect if a food object is put in or take out from the fridge. | $ 100 | [Product Link](https://www.raspberrypi.com.tw/12830/1231/) |

In Total: $ 1400.

## Work Plan
### 1. Work Arrangement

#### 賴宗裕
- Raspberry pi
    - Image Capture
    - Connect AWS(MQTT)
- AWS
    - Rekognition(Food Recognition) 

#### 張耿豪
- AWS
    - EMR( Recipe Recommendation )
    - Elastisearch( Search Recipe )
- Database
    - Build and Maintain RDB/DynamoDB

#### 徐芷翎
- Smart Phone (MobileHub)
    - Notification of food expiration
    - Food object list of your fridge
    - Show Recommended Recipe
- Database
    - Retrieve Data from RDB/DynamoDB

### 2. Gantt Chart of Work Milestones

![](https://i.imgur.com/ibVg2qm.png)