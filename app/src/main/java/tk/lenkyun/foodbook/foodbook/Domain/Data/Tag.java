package tk.lenkyun.foodbook.foodbook.Domain.Data;

/**
 * Created by lenkyun on 16/10/2558.
 */
public class Tag {
    private String tagReferal, tagName;
    public Tag(String tagName){
        this(tagName, null);
    }

    public Tag(String tagName, String referalID){
        this.tagName = tagName;
        this.tagReferal = referalID;
    }

    public String getTagName() {
        return tagName;
    }

    public String getTagReferal() {
        return tagReferal;
    }
}
