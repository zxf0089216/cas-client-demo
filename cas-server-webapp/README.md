产生keystore
keytool -genkeypair -alias cas-server -keyalg RSA -keysize 2048 -storepass changeit -keystore server.keystore

导出证书
keytool -exportcert -alias cas-server -file cas-server.crt -storepass changeit  -keystore server.keystore

导入证书
keytool -importcert -alias cas-server -file cas-server.crt -keystore "%JAVA_HOME%\jre\lib\security\cacerts" -storepass changeit -noprompt

keytool -importcert -alias cas-server -file cas-server.crt -keystore "C:\Program Files\Java\jdk1.7.0_51\jre\lib\security\cacerts" -storepass changeit -noprompt

查看 keypair：
keytool -list -storepass changeit -keystore server.keystore

keytool -list -storepass changeit -keystore "%JAVA_HOME%\jre\lib\security\cacerts"

删除 keypair：
keytool -delete -alias cas-server -storepass changeit

keytool -delete -trustcacerts -alias cas-server -keystore "C:\Program Files\Java\jdk1.7.0_51\jre\lib\security\cacerts" -storepass changeit