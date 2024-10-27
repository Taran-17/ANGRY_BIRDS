# AngryBirdGame

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/libgdx/gdx-liftoff).

This project was generated with a template including simple application launchers and a main class extending `Game` that sets the first screen.

This Project contains the following Classes:-
->MyAngryBirds
->HomeScreen
->LevelsScreen
->GameScreen
->PauseScreen
->SettingsScreen
->WinScreen
->LoseScreen

This Project contains the following working Buttons:-
->Play
->Pause
->Settings
->Back
->Exit
->Quit
->Resume
->Levels

Working of the Project:-

When you run the program through Gradle->Tasks->applications->run, the HomeScreen appears which has the Button options to Play, Settings, Levels, Exit.
-> The Play button navigates you to the current LevelScreen (here taken as level 1 only).
    This LevelScreen has the button options to Pause the game (which navigates it to the pause      screen having the buttons to Resume or Quit the game, Resume takes it back to the               LevelScreen and Quit takes it to the HomeScreen), Settings (which navigates you to the          SettingsScreen, which when pressed Back navigates you back to the GameScreen),                  Win/LoseScreen (navigating you to the respective screens).  
-> The Settings Button navigates you to the SettingsScreen have the Music:On, Sound:On, Back        Buttons(leading you back to the HomeScreen).
-> The Levels Button Navigates you to the LevelScreen where the user can select he/she wants to    play if they unlocked till respective and the previous levels. The Back Button on the           LevelScreen navigates you back to the HomeScreen.
-> The Exit Button terminates the program. 


## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
