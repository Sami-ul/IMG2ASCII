package model;

public class AsciiArt {
    private final String id;
    private final String content;
    private final String originalImagePath;
    private final long timestamp;

    public AsciiArt(String id, String content, String originalImagePath) {
        this.id = id;
        this.content = content;
        this.originalImagePath = originalImagePath;
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() { return id; }
    public String getContent() { return content; }
    public String getOriginalImagePath() { return originalImagePath; }
    public long getTimestamp() { return timestamp; }
}