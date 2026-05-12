public interface GameLoopListener {
    void onUpdate();
    void onGameOver();
    SoundManager getSoundManager();
    EffectManager getEffectManager();
}