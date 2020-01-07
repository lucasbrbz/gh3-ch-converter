@echo off
TITLE Guitar Hero Chart Extractor - by Xebozone

REM First, let's set some paths.

REM remember our current directory
set BEGIN=%CD%

REM remeber the path for our tools
set TOOLS=%CD%\TOOLS

REM make and remember the path for our charts to go to
mkdir Charts
set CHARTS=%CD%\Charts

CLS

REM If the parameter is not blank, skip the message
IF NOT "%1" == "" GOTO ENDOFMESSAGE

echo This program will do all the hard work of extracting your chart files from your GH3/GHA disk.
echo.
echo This will also work for World Tour and Metallica, but the chart format is a bit different. Modification of these charts will be needed. Until the format is better understood, and this program is upgraded accordingly.
echo.
echo All you need to do is place your disk in the drive.
echo.
echo Please make sure to run this file on your desktop, or somewhere that has write access.
echo.
echo If you don't want to lose your DATAP.HED and DATAP.WAD files, do not run this file in the same directory.
echo.
echo If you do not have write access, please exit now, and copy this file to a location that does.
echo.
echo It is assumed that you have sufficient disk space. 50% the size of the game disk is recommended.
echo.
echo If you have a Guitar Hero disk, insert it now...
echo.
PAUSE

:ENDOFMESSAGE
REM First, let's find the correct path to the files
echo.
echo Scanning disk...
echo.

REM Here, we are going to apply parameters. The first parameter is the location of the files.
SET DISK=%1

REM Check if the first parameter is the location of the files

REM NOTE: "AND" boolean can be executed with two "IF" statements:

REM Program assumes that the path is correct if it is checked and specified
IF EXIST %DISK%\DATAP.HED IF EXIST %DISK%\DATAP.WAD GOTO BEGINEXTRACT

REM And if the parameter is not correct, set to default
SET DISK=D:\

REM and check again
IF EXIST %DISK%\DATAP.HED IF EXIST %DISK%\DATAP.WAD GOTO FOUNDDISK
GOTO SELECTPATH

:FOUNDDISK
echo Detected Guitar Hero files at %DISK%
REM CHOICE is buggy...go with a good ole' method
SET /P CHOICE=IS THIS CORRECT? [Y/N] 
IF %CHOICE% == Y GOTO BEGINEXTRACT
IF %CHOICE% == y GOTO BEGINEXTRACT
IF %CHOICE% == YES GOTO BEGINEXTRACT
IF %CHOICE% == YEs GOTO BEGINEXTRACT
IF %CHOICE% == YeS GOTO BEGINEXTRACT
IF %CHOICE% == yES GOTO BEGINEXTRACT
IF %CHOICE% == Yes GOTO BEGINEXTRACT
IF %CHOICE% == yeS GOTO BEGINEXTRACT
IF %CHOICE% == yEs GOTO BEGINEXTRACT
IF %CHOICE% == yes GOTO BEGINEXTRACT
IF %CHOICE% == N GOTO SELECTPATH
IF %CHOICE% == n GOTO SELECTPATH
IF %CHOICE% == NO GOTO SELECTPATH
IF %CHOICE% == No GOTO SELECTPATH
IF %CHOICE% == nO GOTO SELECTPATH
IF %CHOICE% == no GOTO SELECTPATH
echo.
REM ELSE
echo Please enter Y or N and press Enter
echo.
GOTO FOUNDDISK

:SELECTPATH
REM Ok, maybe they have more than one drive. Let the user specify the correct location
ECHO.
SET /P DISK=Please enter the correct path to the Guitar Hero Files : 
IF EXIST "%DISK%\DATAP.HED" IF EXIST "%DISK%\DATAP.WAD" GOTO BEGINEXTRACT

:PATHERROR
ECHO.
echo Sorry...that is not correct. DATAP.HED and DATAP.WAD not found
echo.
GOTO SELECTPATH

:BEGINEXTRACT
REM NOW, we need to extract the data
echo.
echo Data is now being extracted. Press enter when told to do so...
echo.
%TOOLS%\hedextract Extracted "%DISK%\datap.hed" "%DISK%\datap.wad"
echo.
echo You will now see lots of text flying past. Do not worry - all is well!

copy %DISK%/MUSIC ../.. @echo

REM Let's move on to our extracted data
cd Extracted\songs\

REM remove the non-chart related files - we don't need them
echo removing unneeded files, if they exist
del *gfx.pak.ps2
del *sfx.pak.ps2

REM extract every remaining pak file - these are our chart-related files
echo.
echo Extracting pak files...
echo.
for %%a IN (*.pak.ps2) DO %TOOLS%\PakExtractor %%a

REM lets move on to our extracted QB files
cd data\songs

REM remove extracted song scripts - we don't need them
echo.
echo removing more un-needed files, if they exist
echo.
del *scripts.qb.ps2
del *text.qb.ps2


REM rename mid.qb.ps2 files to remove extention - because of limitation of qb2chart
REM this is repeated 3 times, because I can't think of another way to do it
echo.
echo squashing down file names...because we have to...
echo.
ren *.* *.
ren *.* *.
ren *.* *.

REM Now, we can extract charts using qb2chart
echo.
echo extracting charts!
echo.
for %%b IN (*.*) DO %TOOLS%\qb2chart.exe %%b %%b star

REM We now have our charts! Let's move them to our charts folder
echo.
echo moving charts to charts folder
echo.
move *.chart %CHARTS%

REM Now, we can return to the original folder and kill everything to save space
cd %BEGIN%
echo. 
echo Almost done!
echo Let's clean up our mess!
echo.
rmdir /S /Q Extracted
del log*

REM Closing messages
cls
echo.
echo DONE! You will find the charts in the Charts folder in the same directory
PAUSE