@echo off
TITLE MSVWavConverter - Splitting MSV files

FOR /L %%i IN (1,1,8) DO (
	FOR /L %%j IN (1,1,5) DO (
		IF %%i==8 (IF %%j==5 GOTO END_SPLIT)
		echo Splitting %%i-%%j.IMF
		audio-converter.exe v %%i %%j
		CLS
		echo Successfully split %%i-%%j.IMF
		echo.
		echo ------------------------------
		echo.
	)
)

:END_SPLIT

echo All MSV files have been split.
echo.
echo ------------------------------
echo.
echo Initiating conversion to .wav...
TIMEOUT 5
CLS

TITLE MSVWavConverter - Converting .vag files to .wav

FOR /L %%i IN (1,1,8) DO (
	FOR /L %%j IN (1,1,5) DO (
		FOR /L %%k IN (1,1,5) DO (
			IF %%i==8 (IF %%j==5 GOTO END_CONV)
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

:END_CONV

echo All .vag files have been converted.
echo.
echo Cleaning up the mess...

DEL *.vag
DEL *.IMF

echo Good to go!
echo.
echo END