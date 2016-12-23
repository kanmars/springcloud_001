http://X.X.X.X/diamond-server
abc/123

配置新数据源的步骤：

入口配置
//----------------------------------------------------------------------------------------
1.matrx
dataid:			com.taobao.tddl.v1_tddl_cut_dbgroups
groupid:		DEFAULT_GROUP
content:		group_cut_0,group_cut_1,group_cut_2,group_cut_3

注意dataId拼接字符串com.taobao.tddl.v1_{appName}_dbgroups
content:    {dbIndexPrefix}+{dbIndexCount}（从0开始）  逗号分隔
//----------------------------------------------------------------------------------------
2.group
注意dataID 规则  com.taobao.tddl.jdbc.group_V2.4.1_{matrx.content[i]}
         content 	: 具体数据库个数，配置读写权重。
下面是三个数据库：atom_cut_0写库  atom_cut_0_read_0读库atom_cut_0_read_1读库
com.taobao.tddl.jdbc.group_V2.4.1_group_cut_0
DEFAULT_GROUP
atom_cut_0:r0w10,atom_cut_0_read_0:r10w0,atom_cut_0_read_1:r10w0

com.taobao.tddl.jdbc.group_V2.4.1_group_cut_1
DEFAULT_GROUP
atom_cut_1:r0w10,atom_cut_1_read_0:r10w0,atom_cut_1_read_1:r10w0

com.taobao.tddl.jdbc.group_V2.4.1_group_cut_2
DEFAULT_GROUP
atom_cut_2:r0w10,atom_cut_2_read_0:r10w0,atom_cut_2_read_1:r10w0

com.taobao.tddl.jdbc.group_V2.4.1_group_cut_3
DEFAULT_GROUP
atom_cut_3:r0w10,atom_cut_3_read_0:r10w0,atom_cut_3_read_1:r10w0
//----------------------------------------------------------------------------------------
3.atom连接->写库
com.taobao.tddl.atom.global.{group.content[i]}
写库
每个ATOM的数据库连接配置
每天atom数据库连接参数配置

atom w 写库0
com.taobao.tddl.atom.global.atom_cut_0  DEFAULT_GROUP
ip=10.126.53.197
port=3306
dbName=atom_cut_0
dbType=mysql
dbStatus=RW

atom w 写库1
com.taobao.tddl.atom.global.atom_cut_1    DEFAULT_GROUP
ip=10.126.53.197
port=3306
dbName=atom_cut_1
dbType=mysql
dbStatus=RW

atom w 写库2
com.taobao.tddl.atom.global.atom_cut_2   DEFAULT_GROUP
ip=10.126.53.197
port=3306
dbName=atom_cut_2
dbType=mysql
dbStatus=RW


atom w 写库3
com.taobao.tddl.atom.global.atom_cut_3   DEFAULT_GROUP
ip=10.126.53.197
port=3306
dbName=atom_cut_3
dbType=mysql
dbStatus=RW
3.atom连接->读库
com.taobao.tddl.atom.global.{group.content[i]}

atom r0 读库0
com.taobao.tddl.atom.global.atom_cut_0_read_1  DEFAULT_GROUP
ip=10.126.56.104
port=3306
dbName=atom_cut_0_read_1
dbType=mysql
dbStatus=RW

atom r0 读库1
com.taobao.tddl.atom.global.atom_cut_1_read_1    DEFAULT_GROUP
ip=10.126.56.104
port=3306
dbName=atom_cut_1_read_1
dbType=mysql
dbStatus=RW

atom r0 读库2
com.taobao.tddl.atom.global.atom_cut_2_read_1   DEFAULT_GROUP
ip=10.126.56.104
port=3306
dbName=atom_cut_2_read_1
dbType=mysql
dbStatus=RW

atom r0 读库3
com.taobao.tddl.atom.global.atom_cut_3_read_1   DEFAULT_GROUP
ip=10.126.56.104
port=3306
dbName=atom_cut_3_read_1
dbType=mysql
dbStatus=RW
//----------------------------------------------------------------------------------------
4.Atom配置属性
com.taobao.tddl.atom.app.tddl_cut.atom_cut_0    DEFAULT_GROUP
userName=zerobuyuat
minPoolSize=1
maxPoolSize=2
idleTimeout=10
blockingTimeout=5
preparedStatementCacheSize=15
connectionProperties=characterEncoding=utf-8


com.taobao.tddl.atom.app.tddl_cut.atom_cut_1   DEFAULT_GROUP
userName=zerobuyuat
minPoolSize=1
maxPoolSize=2
idleTimeout=10
blockingTimeout=5
preparedStatementCacheSize=15
connectionProperties=characterEncoding=utf-8

com.taobao.tddl.atom.app.tddl_cut.atom_cut_2  DEFAULT_GROUP
userName=zerobuyuat
minPoolSize=1
maxPoolSize=2
idleTimeout=10
blockingTimeout=5
preparedStatementCacheSize=15
connectionProperties=characterEncoding=utf-8

com.taobao.tddl.atom.app.tddl_cut.atom_cut_3   DEFAULT_GROUP
userName=zerobuyuat
minPoolSize=1
maxPoolSize=2
idleTimeout=10
blockingTimeout=5
preparedStatementCacheSize=15
connectionProperties=characterEncoding=utf-8

//----------------------------------------------------------------------------------------
5.Atom密码
atom 每个数据库密码单独配置  password   加密程序 在SecureIdentityLoginModule
123456=29ec2159f152a824fccd68a871b83415
root=-64d29910cc13d220ea2e89c490b1e4bf
//com.taobao.tddl.atom.passwd.数据库名称.mysql.用户名称
com.taobao.tddl.atom.passwd.{dbName}.{dbType}.{userName}

写库
com.taobao.tddl.atom.passwd.atom_cut_0.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_1.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_2.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_3.mysql.zerobuyuat

DEFAULT_GROUP
encPasswd=29ec2159f152a824fccd68a871b83415



读库

com.taobao.tddl.atom.passwd.atom_cut_0_read_0.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_1_read_0.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_2_read_0.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_3_read_0.mysql.zerobuyuat

DEFAULT_GROUP
encPasswd=29ec2159f152a824fccd68a871b83415



读库


com.taobao.tddl.atom.passwd.atom_cut_0_read_1.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_1_read_1.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_2_read_1.mysql.zerobuyuat
com.taobao.tddl.atom.passwd.atom_cut_3_read_1.mysql.zerobuyuat

DEFAULT_GROUP
encPasswd=29ec2159f152a824fccd68a871b83415


