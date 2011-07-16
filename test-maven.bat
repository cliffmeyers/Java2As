set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m

cmd /C %M2_HOME%\bin\mvn.bat install -f java2as-core/pom.xml -Dmaven.test.skip=true

cmd /C %M2_HOME%\bin\mvn.bat install -f java2as-maven-plugin/pom.xml -Dmaven.test.skip=true

set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m -Xdebug -Xrunjdwp:transport=dt_socket,address=8787,server=y,suspend=y

cmd /C %M2_HOME%\bin\mvn.bat -f java2as-maven-test/pom.xml process-classes