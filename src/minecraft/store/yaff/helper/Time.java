package store.yaff.helper;

public class Time {
    protected long lastMS = 0;

    public void reset() {
        this.lastMS = getNanoTime();
    }

    public boolean hasReached(double milliseconds) {
        return ((getNanoTime() - this.lastMS) >= milliseconds);
    }

    public long getNanoTime() {
        return System.nanoTime() / 1000000L;
    }

    public long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public int convert(float perSecond) {
        return (int) (1000 / perSecond);
    }

    public int getSecond(int seconds) {
        return seconds * 1000;
    }

    public int getMinute(int minutes) {
        return minutes * 1000 * 60;
    }

    public int getHour(int hours) {
        return hours * 1000 * 60 * 60;
    }

}
