本文件夹为PKI(Public Key Infrastructure)公钥认证体系测试数据

server.key是私钥文件，pem格式，采用openssl生成
	openssl genrsa -out server.key -des 1024
server.cert是自认证证书文件，pem格式，采用openssl生成
	openssl req -new -x509 -nodes -sha1 -days 365 -key server.key > server.cert
server.key.pkcs8是pkcs8格式的私钥文件，JAVA无法处理标准pem格式的私钥，需要把pem私钥转化为pkcs8格式或者pkcs12等格式才可以使用
	openssl pkcs8 -topk8 -nocrypt -in server.key -out server.key.pkcs8
server.cert.der是自认证证书文件,der格式，采用openssl转换.java自带工具keyTool从JavaKeyStore导出的证书都是der证书
	使用openssl转换的方法为
	将DER格式证书转换成PEM格式
	openssl x509 -in cert.cer -inform der -outform PEM -out cert.crt

--------------
附常用命令：
1、申请私钥pem,1024长度
	openssl genrsa -out server.key -des 1024
2、生成自签名证书
	openssl req -new -x509 -nodes -sha1 -days 365 -key server.key > server.cert
3、私钥格式转换为pkcs8
	openssl pkcs8 -topk8 -nocrypt -in server.key -out server.key.pkcs8
4、从证书中导出公钥（x509证书，pem格式）
	openssl x509 -in cert.crt -pubkey -out public.key