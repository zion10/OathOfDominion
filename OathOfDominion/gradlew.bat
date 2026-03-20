@rem Gradle startup script for Windows
@rem Generated for Oath of Dominion - Grand Arena Companion

@if "%DEBUG%"=="" @echo off
@rem Set local scope
setlocal

set APP_NAME=Gradle
set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

if not exist "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" (
  echo.
  echo ERROR: gradle-wrapper.jar is missing!
  echo Run one of the following to fix this:
  echo   1. Open project in Android Studio - it will auto-fix
  echo   2. Run: gradle wrapper --gradle-version 8.2
  echo.
  exit /b 1
)

"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% ^
  "-classpath" "%CLASSPATH%" ^
  org.gradle.wrapper.GradleWrapperMain %*
