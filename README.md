# Kanji Memory

This is my first app, fully written in Kotlin using Jetpack Compose for the UI and Room for the database. I followed the recommended architecture using MVVM and learned a lot creating this app.

It was designed to support learning 101 Japanese characters, i.e., Kanjis, in a fun and accessible way. 
Upon running the app, the home screen will show 5 buttons, which lead to the following (from top to bottom): 
- A screen of several buttons that vibrate. This was just added to try out different vibration patterns.
- A list of all 101 kanjis and their translations (only one translation was chosen for each kanji, although there may be more).
- A memory exercise, for which the task is to find the matching pairs between kanji and translation.
- A repetition exercise, where the correct translation needs to be typed into the textfield via the keyboard. It is designed as a spaced repetition system, though not fully formed yet. When entering a wrong translation, a longer vibration will occur than when the correct translation is entered (hardest exercise).
- A drag and drop exercise, where the correct kanji needs to be dragged into the translation (easiest exercise).

The following shows screenshots of the homescreen and the destinations of the buttons in chronological order: 

<div align="center">
  <h3>Home Screen</h3>
  <img src="HomeScreen.png" alt="homescreen" width="400"/><br>
  
  <h3>Vibration Screen</h3>
  <img src="VibrationScreen.png" alt="vibrationscreen" width="400"/><br>

  <h3>Kanji List Screen</h3>
  <img src="KanjiList.png" alt="kanjilist" width="400"/><br>

  <h3>Memory Exercise Screen</h3>
  <img src="ExerciseScreen.png" alt="exercisescreen" width="400"/><br>

  <h3>Repetition Exercise Screen</h3>
  <img src="RepetitionExercise.png" alt="repetitionscreen" width="400"/><br>

  <h3>Drag & Drop Exercise Screen</h3>
  <img src="DragDropExercise.png" alt="dragdropscreen" width="400"/>
</div>
