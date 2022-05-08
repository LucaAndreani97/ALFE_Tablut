# ALFE_TABLUT

## Run the Local Server

The easiest way is to utilize the ANT configuration script from console.
Go into the project folder (the folder with the `build.xml` file):
```
cd TablutCompetition/Tablut
```

Compile the project:

```
ant clean
ant compile
```

The compiled project is in  the `build` folder.
Run the server with:

```
ant server
```

Check the behaviour using the random players in two different console windows.

## Run players

To start the player, cd into AlfeTablut/TablutCompetition/Tablut/jar and run

```
./runmyplayer.sh COLOR TIMEOUT SERVER_IP
```

For example, if the server was started on `localhost`, run

```
./runmyplayer.sh white 55 localhost
```
to run the white player, and run
```
./runmyplayer.sh black 55 localhost
```
to run the black player.

