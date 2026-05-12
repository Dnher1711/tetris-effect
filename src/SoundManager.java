import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundManager {
    private Clip moveClip, musicClip, rotateClip, breakClip, landClip;

    public SoundManager() {
        // Nạp file từ Resources Root (dấu / ở đầu là bắt buộc)
        moveClip = loadClip("/move.wav");
        musicClip = loadClip("/music.wav");
        breakClip = loadClip ("/break (1).wav");

    }

    private Clip loadClip(String fileName) {
        try {
            InputStream is = SoundManager.class.getResourceAsStream(fileName);
            if (is == null) return null;
            InputStream bufferedIn = new BufferedInputStream(is);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            return clip;
        } catch (Exception e) {
            System.err.println("Không tìm thấy: " + fileName);
            return null;
        }
    }

    public void playMove() {
        play(moveClip, -10.0f);
    }

    // Phát tiếng nổ hàng - mức âm lượng to rõ (-5.0)
    public void playBreak() {
        play(breakClip, 0.0f);
    }
    public void playMusic() {
        if (musicClip != null) {
            setVolume(musicClip, -5.0f);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY); // Lặp lại liên tục
            musicClip.start();
        }
    }

    // Hàm bổ trợ để phát nhạc ngay lập tức và chỉnh âm lượng
    private void play(Clip clip, float volume) {
        if (clip != null) {
            setVolume(clip, volume);
            clip.setFramePosition(0); // Luôn đưa về đầu file để phát lại ngay khi bấm phím
            clip.start();
        }
    }

    // Hàm điều chỉnh âm lượng (Decibel)
    private void setVolume(Clip clip, float volume) {
        try {
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                // Giới hạn giá trị trong khoảng cho phép của phần cứng
                float min = gainControl.getMinimum();
                float max = gainControl.getMaximum();
                if (volume < min) volume = min;
                if (volume > max) volume = max;

                gainControl.setValue(volume);
            }
        } catch (Exception e) {
            // Nếu máy không hỗ trợ chỉnh âm lượng thì bỏ qua để tránh lỗi game
        }
    }
}