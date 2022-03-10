package tanks.io.AudioEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.concurrent.ConcurrentHashMap;

import tanks.io.Screens.GamePlayScreen;

public class AudioEngine {
    GamePlayScreen mainGaming;
    ConcurrentHashMap<Integer, Float> stepCounter;
    Sound sound;
    Sound track;
    Sound tower;

    private Long idTrack;
    private Long idTower;
    private float timer_towr_ratation;
    private boolean timerTower;

    public AudioEngine(GamePlayScreen mainGaming) {
        timer_towr_ratation = 0;
        this.mainGaming = mainGaming;
        timerTower = false;

//        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sound/BSB.ogg"));
//        Sound track = Gdx.audio.newSound(Gdx.files.internal("sound/00708.ogg"));
//        Sound tower = Gdx.audio.newSound(Gdx.files.internal("sound/bash.ogg"));

        sound = mainGaming.getAssetsManagerGame().get("sound/BSB.ogg", Sound.class);
        track = mainGaming.getAssetsManagerGame().get("sound/00708.ogg", Sound.class);
        tower = mainGaming.getAssetsManagerGame().get("sound/bash.ogg", Sound.class);
    }

    public void pleySoundKickStick() {
        float distanc = 1;
        long id = sound.play();
        sound.setPitch(id, MathUtils.random(.95f, 1.1f));
        sound.setVolume(id, distanc);
    }

    public void pleySoundKickStick( float vol) {
        if(vol < 0) return;
        float distanc = 1;
        long id = sound.play();
        sound.setPitch(id, MathUtils.random(.95f, 1.1f));
        sound.setVolume(id, distanc);
    }

    public void pleySoundOfTracks() {
        //  System.out.println(idTrack);

        if (idTrack == null) {
            idTrack = track.play();
            sound.setLooping(idTrack, true);
        } else sound.resume(idTrack);


    }

    public void stopSoundOfTracks() {
        if (idTrack != null)
            sound.pause(idTrack);
    }


    public void pleySoundOfTower() {
        //   System.out.println(idTrack);
//        timerTower = true;
        timer_towr_ratation = 0;
        if (idTower == null) {
            idTower = tower.play();
            sound.setLooping(idTower, true);
        } else sound.resume(idTower);
        timer_towr_ratation += Gdx.graphics.getDeltaTime();
    }

    public void stopSoundOfTower() {
        // System.out.println(timer_towr_ratation);
        try {
            timer_towr_ratation += Gdx.graphics.getDeltaTime();
            if (timer_towr_ratation > .25f) {
                sound.pause(idTower);
                timer_towr_ratation = 0;
                if (idTower != null) {
                    sound.pause(idTower);
                }
            }
        } catch (NullPointerException e) {
         //   System.out.println("11");
        }
    }

}
