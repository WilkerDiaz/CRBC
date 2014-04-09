svn update --username trinitaria --password beco10
@set CLASSPATH=.
@rem FOR %%x IN (%JAVA_HOME%\jre\lib\ext\*.jar) do @call agregaCp.bat %%x
@FOR %%x IN (jars\*.jar) do @call agregaCp.bat %%x
@START %JAVA_HOME%\bin\javaw -Xmx128M -cp %CLASSPATH% com.becoblohm.cr.CR
@rem %JAVA_HOME%\bin\java -Xmx128M -cp %CLASSPATH% com.becoblohm.cr.CR
@exit
