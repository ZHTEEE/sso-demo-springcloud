# sso-demo-springcloud
1.未进行登陆操作时访问 localhost:8003/demo/ ，访问被拒绝

![测试](img/1.png)

2.访问 localhost:8002/admin/login ，进行登陆操作返回token

![测试](img/2.png)

3.将token放在http请求头部访问 localhost:8003/demo/ ，访问通过返回 Hello World !

![测试](img/3.png)

4.将token放在http请求头部访问 localhost:8002/admin/logout ，进行注销

![测试](img/4.png)

5.将token放在http请求头部访问 localhost:8003/demo/ ，由于已经注销，访问被拒绝

![测试](img/5.png)
