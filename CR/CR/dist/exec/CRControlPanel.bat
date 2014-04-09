@set CLASSPATH=.
@FOR %%x IN (jars\*.jar) do @call agregaCp.bat %%x
@start %JAVA_HOME%\bin\javaw -cp %CLASSPATH% com.becoblohm.cr.CRControlPanel
@exit