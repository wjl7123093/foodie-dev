##虚拟机搭建 CentOS 7 服务器
###1. 软件准备
名称|用途|版本
:--:|:--:|:--:
Vmware|虚拟机|[WorkStationPro 15](https://www.52pojie.cn/thread-801613-1-1.html)
CentOS|Linux系统|[Alibaba CentOS 7](http://mirrors.aliyun.com/centos/7/isos/x86_64/CentOS-7-x86_64-DVD-2003.iso)
SecureCRT|SSH客户端|[SecureCRT 8.x](https://pan.baidu.com/s/1wOmn1VAEqbYgE-2c-1KnoQ  密码: 3w7f)
FileZilla|FTP客户端|[FileZilla 3.50](https://filezilla-project.org)
###2. 环境准备
名称|用途|版本
:--:|:--:|:--:
jdk|java环境|[jdk-8u261-linux-x64.tar.gz](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html#license-lightbox)
tomcat|服务器|[apache-tomcat-9.0.38.tar.gz](https://mirror.bit.edu.cn/apache/tomcat/tomcat-9/v9.0.38/bin/apache-tomcat-9.0.38.tar.gz)
###3. 基础服务器搭建
####3.1 安装虚拟机
- 密码：admin123
####3.2 安装 CentOS 镜像
- 安装完成后，查看 CentOS 系统ip，用宿主机与虚拟机进行互相ping。检查是否能ping通。

####3.3 安装 SecureCRT 客户端
- 在 3.2 完成后，通过虚拟机中 CentOS 的 ip 去创建一个 Session，用于访问 CentOS。

####3.4 安装 FileZilla 客户端
1. 安装完成后，在 CentOS `/home` 目录下，创建一个 `software` 文件夹，然后将 jdk 与 tomcat 两个包(.tar.gz)上传到该目录下。

####3.5 解压 tomcat & java
1. 通过 `tar -zxvf` 命令解压两个压缩包。
2. 再执行如下命令，将文件移动至 `/usr/java` 目录下

```linux
mv tomcat文件夹名称 tomcat-frontend		// 重命名文件夹
cd 
mkdir /usr/java
mv jdk文件夹名称 /usr/java		// 移动文件至目标文件夹
mv tomcat /usr/local
```

####3.6 配置 java 环境变量
- 修改 profile 文件

```
vim /etc/profile
```

- 配置 jdk 环境变量

```
export JAVA_HOME=/usr/java/jdk1.8.0_261
export CLASSPATH=.:%JAVA_HOME%/lib/dt.jar:%JAVA_HOME%/lib/tools.jar
export PATH=$PATH:$JAVA_HOME/bin
```

- 刷新 profile，使其生效

```
source /etc/profile
```


####3.7 启动 tomcat
- 通过执行如下命令启动 tomcat

```linux
cd /usr/local/tomcat/bin
./startup.sh
```

- 检查 tomcat 是否启动成功

```
ps -ef |grep tomcat
```

- 通过宿主机访问 tomcat（http://192.168.197.128:8080）
>如果访问失败，则检查以下两项：
>
>1. [解决 Centos7 启动tomcat 但是外部不能访问的问题](https://blog.csdn.net/weixin_39477597/article/details/82464731)
>2. [【关键】虚拟机 centos7 宿主机打不开tomcat项目](https://blog.csdn.net/u013208953/article/details/51516614)
>3. 主要执行第 2. 步，通过如下命令停止 filewalld 服务即可。
>`sudo systemctl stop firewalld.service && sudo systemctl disable firewalld.service`

- 再次访问 tomcat，此时一般都能够访问成功。

####3.8 启动第二台 tomcat
1. 重新执行 3.5 步，只是将 tomcat 重命名为 `tomcat-api`。
2. 打开 `tomcat-api/conf/server.xml`，修改如下节点端口号（目的是为了避免和第一台 tomcat 端口重复）：

```
<Connector port="8088" protocol="HTTP/1.1" ...>
<Server port="8008"  shutdown="SHUTDOWN">
<Connector port="8049" protocol="AJP/1.3" ...> 
```

3. 重新执行 3.7 步，启动 tomcat。
4. 至此，一台 tomcat 的访问地址为：`http://192.168.197.128:8080`，另一台 tomcat 的访问地址为：`http://192.168.197.128:8088`。


###4. 数据库安装
####4.1 下载安装包
1. 直接访问： [MariaDB 10.4下载](http://yum.mariadb.org/10.4/centos7-amd64/rpms)
2. 在下载列表中，选择下载如下包：
	- [galera](galera-4-26.4.5-1.el7.centos.x86_64.rpm)
	- [jemalloc](jemalloc-3.6.0-1.el7.x86_64.rpm)
	- [jemalloc-devel](jemalloc-devel-3.6.0-1.el7.x86_64.rpm)
	- [MariaDB-client](MariaDB-client-10.4.14-1.el7.centos.x86_64.rpm)
	- [MariaDB-common](MariaDB-common-10.4.14-1.el7.centos.x86_64.rpm)
	- [MariaDB-compat](MariaDB-compat-10.4.14-1.el7.centos.x86_64.rpm)
	- [MariaDB-server](MariaDB-server-10.4.14-1.el7.centos.x86_64.rpm)
	
	>版本号可以更新为当前列表中的最新版
3. 将以上7个包合并到一个文件夹中，名称自定义（建议 MariaDB-10.4.14.stable-CentOS7.x）
4. 通过 FileZilla 上传到 `/home/software/` 中。

####4.2 离线安装
1. 根据 [MariaDB 安装指引](https://mariadb.com/kb/en/mariadb-installation-version-10121-via-rpms-on-centos-7/) 按步骤 1) 安装。
2. 步骤 1) 执行完毕后，步骤 2) 3) 4) 可以合并执行。即：`rpm -ivh 按2) 3) 4)中的顺序写入包名`
3. 如果安装的是 10.4.8 以后的版本，在执行步骤 2. 会报错，还需要安装 `libaio` 依赖包。

```
wget http://mirror.centos.org/centos/6/os/x86_64/Packages/libaio-0.3.107-10.el6.x86_64.rpm
rpm -ivh libaio-0.3.107-10.el6.x86_64.rpm
```

4. 如果安装的是 10.4.14 以后的版本，在步骤 3. 完成后，再次执行步骤 2. 还会报错，还需要安装 [socat](http://www.rpmfind.net/linux/centos/7.8.2003/os/x86_64/Packages/socat-1.7.3.2-2.el7.x86_64.rpm) 依赖包。然后将依赖包通过 FileZilla 拷贝至 `/home/software/MariaDB-10.4.14.stable-CentOS7.x/` 文件夹下。通过 `rpm -ivh socat-1.7.3.2-2.el7.x86_64.rpm` 进行安装。
5. 重新执行步骤 2. 此时应该会顺利安装成功。
6. 执行 `systemctl start mariadb.service` 启动数据库。如果启动失败，则先执行 `yum install mariadb-server -y`。参考 [linux 安装 mysql 失败，提示 unit...not found 解决办法](https://blog.csdn.net/weixin_39533992/article/details/78195383)

####4.3 安全配置
1. 执行 `mysql_secure_installation`
2. 输入 root用户 的密码：`按 Enter（回车）键`
3. 根据提示建议输入 `n` 即可。
4. 是否修改密码：输入 `y`
5. 输入新密码：`admin123`
6. 再次输入新密码：`admin123`
7. 是否移除匿名用户：输入 `y`
8. 是否不允许root用户远程登录：输入 `n` （即允许）
9. 是否删除 test 数据库：输入 `y`
10. 是否重新加载权限表：输入 `y`
11. 设置完毕。

####4.4 Navicat 连接 MariaDB
1. 关闭宿主主机防火墙。
2. 打开 Navicat，新建 MariaDB，输入主机 ip（即 192.168.197.128），端口 3306，用户名 root，密码即上一步设置的密码。
3. 测试连接，会提示连接失败。通过如下操作解决：
	- `mysql -u root -p`
	- `grant all privileges on *.* to 'root'@'%' identified by 'root密码';`（将 root密码 换成自己的密码）
	- `flush privileges;`
	- 再重新点击测试连接，此时会提示连接成功。
4. 执行 sql 文件，生成 tables。（或者拷贝本地数据库数据至远程数据库）

###5. 多环境部署
####5.1 profile 多环境支持
####5.2 SpringBoot 打包 war
####5.3 上传并发布 war
####5.4 发布前端项目
####5.5 解决 Cookie 异常（虚拟机不需要，云服务器需要）
















