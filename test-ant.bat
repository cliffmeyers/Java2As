set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m

cmd /C %M2_HOME%\bin\mvn.bat install -f java2as-core/pom.xml -Dmaven.test.skip=true

cmd /C %M2_HOME%\bin\mvn.bat install -f java2as-ant-task/pom.xml -Dmaven.test.skip=true

REM set ANT_OPTS=-Xmx512m -XX:MaxPermSize=128m

set ANT_OPTS=-Xmx512m -XX:MaxPermSize=128m -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y

cmd /C %ANT_HOME%\bin\ant.bat test -f java2as-ant-test/build.xml