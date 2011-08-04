set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m

cmd /C %M2_HOME%\bin\mvn.bat clean install -f java2as-core/pom.xml -Dmaven.test.skip=true

cmd /C %M2_HOME%\bin\mvn.bat clean install -f java2as-ant-task/pom.xml -Dmaven.test.skip=true

set ANT_OPTS=-Xmx512m -XX:MaxPermSize=128m

cmd /C %ANT_HOME%\bin\ant.bat test -f java2as-ant-test/build.xml