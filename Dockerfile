FROM harbor.baocloud.cn/devops/tomcat:main-latest

#不同应用需要调整下方war包目录
COPY  ./target/*.war /usr/local/tomcat/webapps/demo-dm.war