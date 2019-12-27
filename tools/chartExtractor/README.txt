Guitar Hero 3, Guitar Hero Aerosmith, Guitar Hero World Tour, Guitar Hero Metallica Chart Extractor

I am new here, and I found it took a few goes to learn now to extract the charts from my Guitar Hero games. Even though the process isn't that difficult, it is tedious. Most of the programs do not support wildcards in the command line, and so, each chart has to be done manually.

Not for long:

This program will do all the hard work of extracting your chart files from your GH3/A disk

This will also work for GHWT/M, but the chart format is a bit different. Modification of these charts will be needed. Until the format is better understood, and this program is upgraded accordingly.

All you have to do is insert your disk. The program will try to auto-detect the disk, extract the charts, and clean up it's mess.

You will be left with a Charts folder.

I tested it on my GH3 and GHWT PS2 disks on Windows Vista. It should work on XP too.

Please note that extracting charts is illegal unless you own the game. Do not go asking me or others for charts, we do not support piracy here.

Usage:

chartExtractor [file location]

[file location] is the location of your Guitar Hero data files. Usually, this is your CD\DVD Drive

The program can be executed without parameters, and it will try find the files itself

Version History:

1.3
Fix a bug in the checking of user choices
Modified PakEditor2 into PakExtractor - source included

1.2
User can specify a path to the files as a parameter. Extraction begins immediately if a valid path is entered
Disk detection code clean-up

1.1
Replaced some functions with more compatible code
Better clean-up
Better disk checking

1.0
Initial Release

Known Issues:
The conversion process does not like the "boss battles" on the GH3 disk, but you can get them to convert by using Leff's Song Importer which seems to follow a different conversion note conversion process. I found Leff's tool corrupts the other charts bass tracks though. I hope he gets this sorted some time:
http://www.scorehero.com/forum/viewtopic.php?t=57361
