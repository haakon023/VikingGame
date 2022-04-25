# Viking Stop
A simple survival game where you defend yourself from angry vikings trying to steal all your gold. Created for desktop and android users with the libGDX game engine.

This game was developed by students at NTNU in Trondheim, while taking the course TDT4240 - Software Architecture.
![MainMenu](https://user-images.githubusercontent.com/38417354/165063858-41d38e53-7eff-4d25-b6ab-934641f52906.png)



## Table of Contents
1. [Game Description](#GameDescription)
2. [Engine, Frameworks and Technology](#Technology)
3. [How to Install and Run the Project](#InstallAndRun)
   1. [Requirements](#Requirements)
   2. [Obtaining the Code](#ObtainingCode)
   3. [Running the Game](#RunningGame)
     * [Using an Android Device](#UsingAndroidDevice)
     * [Using an Emulator](#UsingEmulator)
4. [How To Use the Project](#UseTheProject)
    1. [The Main Menu](#MainMenu)
    2. [Editing Profile](#EditingProfile)
    3. [Tutorial](#Tutorial)
    4. [Practice Mode](#PracticeMode)
    5. [Multiplayer](#Multiplayer)
    6. [Leaderboard](#Leaderboard)
5. [Developed by](#Credits) 
6. [Licence](#Licence)
7. [Structure](#Structure)

## 1 Game Description <a name="GameDescription"></a>
The game is a ”who can survive the longest” online multiplayer game. The game is based on the
battle of Lindisfarne, the first ever Viking raid in 793. The goal of the game is to defend your
monastery from the approaching Vikings who are trying to pillage your island and steal your gold.
You and your friend competes in real-time on who can hold off the Viking onslaught longest, in a
survival-style multiplayer duel. You and your friend will each play a fearsome monk who shoots
arrows to sink the approaching Viking ships. If you are attentive enough, you can try to catch the
moving power-ups and unlock short-term abilities for your monk. Once the Vikings ships reach the
shore, they begin their attack and deal damage to your monastery. Both you and your opponent’s
monastery’s health are indicated through a health bar. Each player will in addition to having its
own health bar shown at the screen, also have its opponent health bar showing. The player whose
health bar runs out first is looted by the Vikings. They steal your gold and you lose the game.

## 2 Engine, Frameworks and Technology <a name="Technology"></a>
Engine: [libGDX](https://libgdx.com/)  
ECS: [Ashley](https://github.com/libgdx/ashley)  
Physics Engine: [Box2D](https://box2d.org/)  
Development Environment: [Android Studio](https://developer.android.com/studio)  
Server Environment: [Firebase](https://firebase.google.com/)

## 3 How to Install and Run the Project <a name="InstallAndRun"></a>
### 3.1 Requirements <a name="Requirements"></a>
In order to run the application you will need an IDE (like [Android Studio](https://developer.android.com/studio) or [IntelliJ](https://www.jetbrains.com/idea/)). The minimum API level required to run the game is 19. You may also run the game through an emulator, such as the one that Android Studio includes.  

### 3.2 Obtaining the Code <a name="ObtainingCode"></a>
Once you have your IDE downloaded, you may now download the code. Firstly, make your way into your chosen folder through a terminal window. You can then clone into the code by using:  
```
git clone https://github.com/haakon023/VikingGame.git
```
Now open your IDE, select your chosen game folder in the IDE, and click open.

### 3.3 Running the Game <a name="RunningGame"></a>
Now you may run the code through an emulator, or through your Android device with API level 19 or higher. It might take some seconds running it for the first time. See below for a step-by-step guide on both methods.  

#### 3.3.1 Using an Android device <a name="UsingAndroidDevice"></a>
1. Use an USB-cable to connect your phone to the computer that will run the code.
2. Allow permissions needed on your device for the computer to run code on it.
3. On your computer, under ```Connected Devices``` select your chosen device.
4. Select the ```OK``` button and run the game with the play button (to the right, or in the bottom left corner).

#### 3.3.2 Using an Emulator <a name="UsingEmulator"></a>
1. Select ```AVD manager``` from the drop down menu at the top.
2. Press ```+ Create Virtual Device...```  
<img width="994" alt="new emulator" src="https://user-images.githubusercontent.com/38417354/165068262-47f46bb6-9ff9-4705-b2c6-23473b91e4cd.png">  

3. Pick a device (e.g. Pixel 2). Press the ```Next``` button.  
<img width="994" alt="select hardware" src="https://user-images.githubusercontent.com/38417354/165068424-10f7a7c0-11a2-44a8-83f0-7235bee393a4.png">  

4. Now select a x86 system image. ```R``` is a good choice, for example. Download it.  
<img width="994" alt="system image" src="https://user-images.githubusercontent.com/38417354/165068972-47910c7d-91fa-404d-a67a-35c3e7a7f728.png">  

5. Press ```Next``` after the download has finished. The final screen should look something like this:  
<img width="994" alt="avd" src="https://user-images.githubusercontent.com/38417354/165069289-78cd6aee-ec67-431c-9dad-d40d0037f3bb.png">  

6. Press ```Finish```.
7. Now make sure that your new emulated device is set at the top of your IDE (next to the run button). 
8. Press the run button, wait for your emulator to start, and the game should launch.  

## 4 How to Use the Project <a name="UseTheProject"></a>

### 4.1 The Main Menu <a name="MainMenu"></a>
The main menu will be the first screen you reach when launching the application. From here you can choose between the tutorial, practice and multiplayer mode.  
![MainMenu](https://user-images.githubusercontent.com/38417354/165059515-f115010f-7593-4fb5-8dfe-a0a573c3d465.png)


### 4.2 Editing profile <a name="EditingProfile"></a>
To edit your profile tap the avatar in the center of the main menu. Here you may change your avatar and name!  
![editProfile](https://user-images.githubusercontent.com/38417354/165059543-23d12415-02b2-4ed4-b760-344b56a6eb24.png)



### 4.3 Tutorial <a name="Tutorial"></a>
The tutorial will guide you through the basics on how the game works. It is recommended to go through it once.  
![Tutorial](https://user-images.githubusercontent.com/38417354/165059563-80b4399d-9c7a-483d-ae12-73865bb2fb27.png)


### 4.4 Practice mode <a name="PracticeMode"></a>
In practice mode you are able to fine tune your abilities. This also works as a single player mode.  
![PracticeMode](https://user-images.githubusercontent.com/38417354/165059612-800f65ad-1eff-4616-89f2-0ff24e784d79.png)


### 4.5 Multiplayer <a name="Multiplayer"></a>
In order to play multiplayer first select 'Host Game' on the main menu. You will then be put in to a lobby:  
![GameLobby](https://user-images.githubusercontent.com/38417354/165059647-c4c5b0a2-f136-456e-b972-8cd201d65772.png)


After creating a game lobby, have your opponent enter the game PIN displayed on the screen. Then start the game!    
![EnterPin](https://user-images.githubusercontent.com/38417354/165059903-4b904ae1-7027-448c-9f91-2197b535cd9c.png)


The first one to lose all their health loses the game!  
![MultiplayerIngame](https://user-images.githubusercontent.com/38417354/165059932-ce7f535b-b85d-4356-aa31-8d4a3e159dd4.png)


### 4.6 Leaderboard <a name="Leaderboard"></a>
The leaderboard can be displayed by clicking the 'crown' button on the main menu screen.  
![Highscore](https://user-images.githubusercontent.com/38417354/165059968-aa11911e-d213-4b86-af23-5c9ebc1f7553.png)


## 5 Developed by <a name="Credits"></a> 
Kristian Zunder Edvardsen  
Håkon Finstad  
Linnea Fossum Gustavsen  
Sascha Pascal Meyer  
Cornelius Emil Rebmann  
Signe B. Thrane-Nielsen  
Caio Wiebers


## 6 Licence <a name="Licence"></a>
[MIT Licence](MIT-LICENSE.txt)

## 7 Structure <a name="Structure"></a>
```
 |-- android
    |   |-- AndroidManifest.xml
    |   |-- build.gradle
    |   |-- google-services.json
    |   |-- ic_launcher-web.png
    |   |-- proguard-rules.pro
    |   |-- project.properties
    |   |-- src
    |       |-- group22
    |           |-- viking
    |               |-- game
    |                   |-- AndroidInterfaceClass.java
    |                   |-- AndroidLauncher.java
    |-- assets
    |   |-- fonts
    |   |   |-- Roboto.ttf
    |   |-- i18n
    |   |   |-- app.properties
    |   |   |-- app_de.properties
    |   |   |-- app_en.properties
    |   |   |-- app_no.properties
    |   |-- img
    |   |   |-- arrow-dummy.png
    |   |   |-- arrow.png
    |   |   |-- badlogic.jpg
    |   |   |-- bow.png
    |   |   |-- castle.png
    |   |   |-- energy_potion.png
    |   |   |-- GoatIcon.png
    |   |   |-- HealthBarFilling.png
    |   |   |-- healthBarFrame.png
    |   |   |-- health_potion.png
    |   |   |-- Island.png
    |   |   |-- KnightSprite.png
    |   |   |-- KnightSpriteHead.png
    |   |   |-- leaderboardButton.png
    |   |   |-- legolas.png
    |   |   |-- LegolasHead.png
    |   |   |-- medal.png
    |   |   |-- Monastery.png
    |   |   |-- mutedButton.png
    |   |   |-- OceanBack.png
    |   |   |-- OceanTop.png
    |   |   |-- Questionmark.png
    |   |   |-- RobinHoodSprite.png
    |   |   |-- RobinHoodSpriteHead.png
    |   |   |-- rubberDuck.png
    |   |   |-- stopHeader.png
    |   |   |-- unmutedButton.png
    |   |   |-- vikingHeader.png
    |   |   |-- vikingShip.png
    |   |   |-- VikingShipFull.png
    |   |   |-- VikingShipHull.png
    |   |   |-- VikingShipRuder.png
    |   |   |-- VikingShipSail.png
    |   |   |-- vikingShipSpecial.png
    |   |   |-- WarriorWomanSprite.png
    |   |   |-- WarriorWomanSpriteHead.png
    |   |   |-- WaveBottom.png
    |   |   |-- waveDark.png
    |   |   |-- waveLight.png
    |   |   |-- waveMedium.png
    |   |   |-- WaveTop.png
    |   |   |-- waveVeryLight.png
    |   |   |-- WizardSprite.png
    |   |   |-- WizardSpriteHead.png
    |   |   |-- WizardSpriteSurprised.png
    |   |-- sound
    |   |   |-- gameMusic.mp3
    |   |   |-- lobbyMusic.mp3
    |   |   |-- menuMusic.mp3
    |   |   |-- mumble.wav
    |   |   |-- soundBowFire.mp3
    |   |   |-- soundButton.mp3
    |   |   |-- soundError.mp3
    |   |   |-- soundGoat.mp3
    |   |   |-- soundSwish.wav
    |   |-- ui
    |       |-- uiskin.atlas
    |       |-- uiskin.json
    |       |-- uiskin.png
    |-- core
    |   |-- build.gradle
    |   |-- src
    |   |   |-- group22
    |   |       |-- viking
    |   |           |-- game
    |   |               |-- controller
    |   |               |   |-- GameStateManager.java
    |   |               |   |-- VikingGame.java
    |   |               |   |-- controllers
    |   |               |   |-- ECS
    |   |               |   |   |-- factory
    |   |               |   |   |   |-- AbstractFactory.java
    |   |               |   |   |   |-- PlayerFactory.java
    |   |               |   |   |   |-- PowerUpFactory.java
    |   |               |   |   |   |-- ProjectileFactory.java
    |   |               |   |   |   |-- TextureFactory.java
    |   |               |   |   |   |-- VikingFactory.java
    |   |               |   |   |-- input
    |   |               |   |   |   |-- InputController.java
    |   |               |   |   |-- systems
    |   |               |   |   |   |-- AnimationSystem.java
    |   |               |   |   |   |-- CollisionSystem.java
    |   |               |   |   |   |-- HomingProjectileSystem.java
    |   |               |   |   |   |-- LinearProjectileSystem.java
    |   |               |   |   |   |-- PhysicsDebugSystem.java
    |   |               |   |   |   |-- PhysicsSystem.java
    |   |               |   |   |   |-- PlayerControlSystem.java
    |   |               |   |   |   |-- RenderingSystem.java
    |   |               |   |   |   |-- TutorialVikingSystem.java
    |   |               |   |   |   |-- VikingSystem.java
    |   |               |   |   |-- utils
    |   |               |   |       |-- BodyFactory.java
    |   |               |   |       |-- ColliderListener.java
    |   |               |   |       |-- ZComparator.java
    |   |               |   |-- states
    |   |               |       |-- AbstractInformationOverlayState.java
    |   |               |       |-- AbstractPlayState.java
    |   |               |       |-- GameOverState.java
    |   |               |       |-- LeaderboardState.java
    |   |               |       |-- LoadingState.java
    |   |               |       |-- LobbyState.java
    |   |               |       |-- MenuState.java
    |   |               |       |-- OfflinePlayState.java
    |   |               |       |-- OnlinePlayState.java
    |   |               |       |-- ProfileSettingsState.java
    |   |               |       |-- SplashState.java
    |   |               |       |-- State.java
    |   |               |       |-- TutorialInterruptState.java
    |   |               |       |-- TutorialPlayState.java
    |   |               |-- firebase
    |   |               |   |-- FirebaseInterface.java
    |   |               |   |-- collections
    |   |               |   |   |-- FirebaseCollection.java
    |   |               |   |   |-- LobbyCollection.java
    |   |               |   |   |-- PlayerStatusCollection.java
    |   |               |   |   |-- ProfileCollection.java
    |   |               |   |-- exceptions
    |   |               |   |   |-- FieldKeyUnknownException.java
    |   |               |   |-- listeners
    |   |               |       |-- OnCollectionUpdatedListener.java
    |   |               |       |-- OnGetDataListener.java
    |   |               |       |-- OnPostDataListener.java
    |   |               |-- models
    |   |               |   |-- Assets.java
    |   |               |   |-- ECS
    |   |               |   |   |-- components
    |   |               |   |       |-- AnimationComponent.java
    |   |               |   |       |-- B2dBodyComponent.java
    |   |               |   |       |-- CollisionComponent.java
    |   |               |   |       |-- HomingProjectileComponent.java
    |   |               |   |       |-- LinearProjectileComponent.java
    |   |               |   |       |-- PlayerComponent.java
    |   |               |   |       |-- PowerUpComponent.java
    |   |               |   |       |-- StateComponent.java
    |   |               |   |       |-- TextureComponent.java
    |   |               |   |       |-- TransformComponent.java
    |   |               |   |       |-- TypeComponent.java
    |   |               |   |       |-- VikingComponent.java
    |   |               |   |-- firebase
    |   |               |   |   |-- FirebaseDocument.java
    |   |               |   |   |-- documents
    |   |               |   |       |-- Lobby.java
    |   |               |   |       |-- PlayerStatus.java
    |   |               |   |       |-- Profile.java
    |   |               |   |-- powerups
    |   |               |       |-- HealthPowerUp.java
    |   |               |       |-- IPowerUp.java
    |   |               |-- view
    |   |                   |-- ErrorDialog.java
    |   |                   |-- InformationOverlayView.java
    |   |                   |-- LeaderboardView.java
    |   |                   |-- LoadingView.java
    |   |                   |-- LobbyView.java
    |   |                   |-- MenuView.java
    |   |                   |-- PlayView.java
    |   |                   |-- ProfileSettingsView.java
    |   |                   |-- SoundManager.java
    |   |                   |-- SplashView.java
    |   |                   |-- View.java
    |   |                   |-- ViewComponentFactory.java
    |   |-- test
    |       |-- group22
    |           |-- viking
    |               |-- game
    |                   |-- ECS
    |                   |   |-- components
    |                   |       |-- PlayerComponentTest.java
    |                   |-- states
    |-- gradle
    |   |-- wrapper
    |       |-- gradle-wrapper.jar
    |       |-- gradle-wrapper.properties
