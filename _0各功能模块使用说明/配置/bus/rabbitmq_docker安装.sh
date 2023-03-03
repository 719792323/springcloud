docker pull rabbitmq

docker run -d --name rabbit -p 15672:15672 -p 5672:5672 rabbitmq

docker exec -it rabbit /bin/sh

rabbitmq-plugins enable rabbitmq_management

# 访问15672端口查看WebUi，账户密码都是guest


# 在docker中启动可能会导致webui出现Management API returned status code 500
# 进而导致各种webui上的功能不可用
# 解决方案如下
docker exec -it rabbitmq /bin/sh

cd /etc/rabbitmq/conf.d/

echo management_agent.disable_metrics_collector = false > management_agent.disable_metrics_collector.conf

# 退出容器后，重启rabbitmq
docker restart rabbit

