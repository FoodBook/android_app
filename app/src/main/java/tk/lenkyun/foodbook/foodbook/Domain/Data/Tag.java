package tk.lenkyun.foodbook.foodbook.Domain.Data;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Tag {
    private String tagReferal, tagName;
    public Tag(String tagName){
        this(tagName, null);
    }

    public Tag(){}

    public Tag(String tagName, String referalID){
        this.tagName = tagName;
        this.tagReferal = referalID;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTagReferal() {
        return tagReferal;
    }

    public void setTagReferal(String tagReferal) {
        this.tagReferal = tagReferal;
    }
}
