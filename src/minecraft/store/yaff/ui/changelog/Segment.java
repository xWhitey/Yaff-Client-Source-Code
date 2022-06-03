package store.yaff.ui.changelog;

public class Segment {

    private String content;

    private SegmentType type;

    public Segment(String content) {
        this.content = content;
        this.type = SegmentType.NONE;
    }

    public Segment(String content, SegmentType type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SegmentType getType() {
        return type;
    }

    public void setType(SegmentType type) {
        this.type = type;
    }

}
