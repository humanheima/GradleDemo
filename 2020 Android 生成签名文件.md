### 2023 Android 生成签名文件

keytool -genkey -alias mykey -keyalg RSA -keystore mykeystore.jks -keysize 2048

生成密钥后，会有这样的警告。
Warning:
JKS 密钥库使用专用格式。建议使用 "keytool -importkeystore -srckeystore mykeystore.jks -destkeystore mykeystore.jks -deststoretype pkcs12" 迁移到行业标准格式 PKCS12。

我们使用下面的命令将密钥库迁移到 PKCS12 格式。

keytool -importkeystore -srckeystore mykeystore.jks -destkeystore mykeystore.p12 -deststoretype PKCS12

### 查看密钥库信息

keytool -list -v -keystore <keystore文件路径> 


keytool -list -v -keystore mykeystore.jks