# STYLiSH
## WebPage
* http://52.196.104.13
* http://ec2-52-196-104-13.ap-northeast-1.compute.amazonaws.com

## Brian

* conclusion
    * realize what is the upstream
    * realize what is Pull Request

## Work Flow and Run Web Server
1. build the .jar file in my computer
    1. mvn package
2. scp the file to instance
    1. scp -i my-key.pem src dst
3. run the jar file
    1. nohup java -jar stylish.jar &
    2. nohup means no hang up, and & is running java in background

* why use 80 port
    * because 80 port is default for http
* how I run server program in background
    * use & to run program in background

## 
* install java
    * sudo apt install openjdk-17-jdk
* nginx
    * setting listen port 80
    * setting proxy_pass to http://localhost:8080
 


