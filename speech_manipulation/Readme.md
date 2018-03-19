These are [Praat](http://www.fon.hum.uva.nl/praat/) scripts and folder structure for the manipulation of speech files: pitch, pitch range, and formants. To run one script, simply open it with Praat and click on "Run".

Written by Elvira Ibragimova.

This Readme has been written by Laura Fernández Gallardo.




### Preparation

* 4 male files (must start with 'm') and 4 female files (must start with 'w')
  * 2 of the males and 2 of the females should have high pitch and low pitch range (negatively perceived speaker traits)
  * the other  2 males and 2 females should have low pitch and high pitch range (positively perceived speaker traits)


* folder structure for output ('manipulated' folder and subfolders)

  ​

### "Analyse"

* input parameters: needed to properly estimate pitch: 60-300 Hz (males) and 100-500 Hz (females) - these are the general pitch ranges - not the ranges of the population of our speech files.

* Takes the speech files and computes F0 mean, median, min, max, range, std and writes 'results.txt'. All values are in Hz.

* In 'settings.txt' and in a Praat popup window, it shows the mean parameters for this female and male population (the given speech files)

  ​

### "Manipulation"

1. Select speaker gender

2. Input general pitch range of the corresponding speaker gender

3. Select what aspects to manipulate (pitch / pitch range / pitch and pitch range)

4. Input a value to indicate the extent of the change

    * amount of change in pitch in Hz
    * rate of change in pitch range, from 0 to 1. Example 0.2 would indicate a change of 20% 
    * ignored if the maniputation of this aspect has not been selected. Example, if pitch only selected, then the value of pitch range is ignored.

5. Input pitch range of the population of our speech files, of the corresponding speaker gender

6. Output: manipulated speech files in the corresponding subfolder of 'manipulated'. The pitch of the two positively perceived speakers has been increased, and their pitch range lowered. For the negatively perceived speakers, their pitch has been lowered and their pitch range increased.

    ​

### "Formant"

1. Select speaker gender
2. Input general pitch range of the corresponding speaker gender
3. Input formant change factor (e.g. 0.05 indicates a change of 5% up and down).
4. Output: manipulated speech files in the 'FormantShitf' subfolder of 'manipulated'.  The formant frequencies have been increased for the two positively perceived speakers, and lowered for the two negative speakers.