@echo off
title Moun Builder
:top
cls
echo Press any key to start build.
pause > nul
cls
call gradlew moun
pause