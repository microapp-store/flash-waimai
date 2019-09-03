package cn.enilu.elm.api.entity.sub;

import java.util.List;

/**
 * Created  on 2018/1/7 0007.
 *
 * @author zt
 */
public class OrderTimelineNode {
    private List actions;
    private String description;
    private String sub_description;
    private String title;
    private Integer in_processing;

    public List getActions() {
        return actions;
    }

    public void setActions(List actions) {
        this.actions = actions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSub_description() {
        return sub_description;
    }

    public void setSub_description(String sub_description) {
        this.sub_description = sub_description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIn_processing() {
        return in_processing;
    }

    public void setIn_processing(Integer in_processing) {
        this.in_processing = in_processing;
    }
}
