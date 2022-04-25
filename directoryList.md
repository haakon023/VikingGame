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
