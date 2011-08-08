set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m

cmd /C %M2_HOME%\bin\mvn.bat clean install -f pom.xml -Dmaven.test.skip=true