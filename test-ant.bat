cmd /C %M2_HOME%\bin\mvn.bat install -f java2as-core/pom.xml -Dmaven.test.skip=true

cmd /C %ANT_HOME%\bin\ant.bat -v test -f java2as-ant-test/build.xml