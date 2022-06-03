package store.yaff.helper;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Sound {
    public static synchronized void playSound(String url, float volume, boolean stop) {
        (new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                InputStream audioSrc = Sound.class.getResourceAsStream("/assets/minecraft/yaff/sounds/" + url);
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
                clip.open(inputStream);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(volume);
                clip.start();
                if (stop) {
                    clip.stop();
                }
            } catch (Exception var8) {
                var8.printStackTrace();
            }
        })).start();
    }

}
