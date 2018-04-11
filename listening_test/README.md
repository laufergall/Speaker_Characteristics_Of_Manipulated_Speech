# Listening test 

### Goal

To assess the effects of manipulated speech (pitch, pitch range and formant shifts) on speakers' attributed warmth.



### Speech stimuli

Speakers (from the [NSC corpus](http://www.qu.tu-berlin.de/?id=nsc-corpus)) chosen according to their pitch and pitch range values, and to their [previously attributed warmth](https://github.com/laufergall/Subjective_Speaker_Characteristics/tree/master/doc/listening_tests):

* Females, positive warmth: 
  * w102_aden
  * w257_nasinu
* Females, negative warmth: 
  * w161_vaitele
  * w266_turkmenabat
* Males, positive warmth:
  * m041_bucharest
  * m097_basseterre
* Males, negative warmth:
  * m090_riyadh
  * m294_portauprince



Manipulation conditions:

* Original speech: no manipulation.
* Pitch: shifting mean pitch frequency
  * if speakers are originally positively perceived: by 40Hz up 
  * if speakers are originally negatively perceived: by 40Hz down 
* Pitch range: extending or narrowing pitch range
  * if speakers are originally positively perceived: pitch range 40% down
  * if speakers are originally negatively perceived: pitch range 40% up
* Pitch and pitch range (combined): shifting mean pitch frequency and extending or narrowing pitch range
  * if speakers are originally positively perceived: pitch 40Hz up and pitch range 40% down
  * if speakers are originally negatively perceived: pitch 40Hz down and pitch range 40% up
* Pitch and formant shift (combined): shifting mean pitch frequency and formant frequencies
  * if speakers are originally positively perceived: pitch 40Hz up and formant frequencies shift by 10% up
  * if speakers are originally negatively perceived: pitch 40Hz down and formant frequencies shift by 10% down

Total: 8 speakers x 5 conditions = 40 speech stimuli.

Content: "Ich hab in meinem Vertrag viele Frei-SMS, die benutze ich aber kaum. Ich würde auf die SMS gern verzichten und meine Frei-Minuten dafür erhöhen" (Turn 2b of scripted dialogs of the NSC corpus).

Mean duration: 6.9 s (stdev = 0.6 s), min = 5.7 s, max = 7.9 s.



### Participants

[Listening tests](https://github.com/laufergall/Speaker_Characteristics_Of_Manipulated_Speech/tree/master/listening_test) have been conducted with 7 participants (5m, 2f) who rated perceived warmth (on the scale "cold----hearty") of manipulated speech. They were aged 27 years on average (stdev = 2.3, range: 23-30). Their mother tongue was different: Croatian, Bangla, Polish, Nepalese, Spanish, German, Dutch.



### GUI

Programmed in java, adapting [this](https://github.com/laufergall/GUI_SpeakerCharacteristics) other GUI to the current listening test.

A screenshot of the GUI can be seen in screenshot.png.



„Inwieweit treffen die folgenden Attribute auf den Sprecher zu?“

gefuehlskalt --------- (continuous slider) ----------- herzlich



The task for the listeners was to listen to the speech and indicate a value from "cold" to "hearty" in a continuous scale, according to their perception of the speaker.


