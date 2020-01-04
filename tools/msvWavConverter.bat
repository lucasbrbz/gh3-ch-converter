@echo off

TITLE MSVWavConverter - Splitting MSV files
mode con:cols=60 lines=21

IF EXIST 8-4-6.vag GOTO BONUS_SPLIT

FOR /L %%i IN (1,1,8) DO (
	FOR /L %%j IN (1,1,5) DO (
		IF %%i==8 (IF %%j==5 GOTO BONUS_SPLIT)
		echo Splitting %%i-%%j.IMF
		audio-converter.exe v %%i %%j
		CLS
		echo Successfully split %%i-%%j.IMF
		echo.
		echo ------------------------------
		echo.
	)
)

:BONUS_SPLIT

IF EXIST b-25-6.vag GOTO CONVERT

SET LIST=a b c d e f g h i j k l m n o p q r s t u v w x y

echo Splitting bonus songs...
echo.

FOR %%i IN (%LIST%) DO (
	audio-converter.exe v b %%i
	CLS
	echo Splitting bonus songs...
	echo.
)

CLS
echo All MSV files have been split.
echo.
echo ------------------------------
echo.
echo Initiating conversion to .wav...
TIMEOUT 5
CLS

:CONVERT

TITLE MSVWavConverter - Converting .vag files to .wav

IF EXIST 8-4-6.wav GOTO BONUS_CONV

FOR /L %%i IN (1,1,8) DO (
	FOR /L %%j IN (1,1,5) DO (
		FOR /L %%k IN (1,1,6) DO (
			IF %%i==8 (IF %%j==5 GOTO BONUS_CONV)
			echo Converting %%i-%%j-%%k.vag
			audio-converter.exe w %%i %%j %%k
			CLS
			echo Converted %%i-%%j-%%k.vag to %%i-%%j-%%k.wav
			echo.
			echo ------------------------------
			echo.
		)
	)
)

:BONUS_CONV

IF EXIST b-25-6.wav GOTO END

echo Converting bonus songs...
echo.

SET LIST=a b c d e f g h i j k l m n o p q r s t u v w x y

FOR %%i IN (%LIST%) DO (
	FOR /L %%j IN (1,1,6) DO (
		bonus-converter.exe %%i %%j
		CLS
		echo Converting bonus songs...
		echo.
	)
)

CLS
echo All .vag files have been converted.
echo.
echo ------------------------------
echo.
echo Cleaning up the mess...

DEL *.vag
DEL *.IMF

:END

echo Good to go!
echo.
echo END