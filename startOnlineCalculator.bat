Setlocal EnableDelayedExpansion
set LIBS_JAR=./
for %%a in (*.jar) do set LIBS_JAR=.\%%a;!LIBS_JAR!
java  -cp .\bin;%LIBS_JAR%;%CLASS_PATH%  cbatest.CBAWebCalculator