# consul下载
[官方地址](https://developer.hashicorp.com/consul/downloads)
1. Centos
```text
   sudo yum install -y yum-utils
   sudo yum-config-manager --add-repo https://rpm.releases.hashicorp.com/RHEL/hashicorp.repo
   sudo yum -y install consul
```
注意：Centos下启动consul，后再windows端无法访问到8500端口，在虚拟机中的服务可以正常访问（目前还没有解决这个问题）
2. windows
windows直接在官网下载对应版本软件即可

3. docker
docker run -d -p 8500:8500 --name consul consul agent -server -bootstrap-expect=1 -client 0.0.0.0 -ui

# 启动consul
在windows和linux下运行如下命令（linux下有上述的bug问题）
consul agent -dev
启动后可以通过访问8500端口查看consul的webUI

