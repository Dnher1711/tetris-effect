public interface GameLoopListener {
    void onUpdate();
    void onGameOver();
    // THÊM DÒNG NÀY:
    EffectManager getEffectManager();
}