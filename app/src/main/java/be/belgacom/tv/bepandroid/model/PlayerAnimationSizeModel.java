package be.belgacom.tv.bepandroid.model;

/**
 * Created by akash.sharma on 5/23/2017.
 */

public class PlayerAnimationSizeModel
{
    /**
     * src_pos_x : 1250
     * src_pos_y : 30
     * dst_pos_x : 1050
     * dst_pos_y : 30
     * width : 200
     * height : 114
     * time_in_milli_secs : 625
     * zorder : 3
     * animation_type : 0
     */

    private int src_pos_x;
    private int src_pos_y;
    private int dst_pos_x;
    private int dst_pos_y;
    private int width;
    private int height;
    private int time_in_milli_secs;
    private int zorder;
    private int animation_type;

    public int getSrc_pos_x() {
        return src_pos_x;
    }

    public void setSrc_pos_x(int src_pos_x) {
        this.src_pos_x = src_pos_x;
    }

    public int getSrc_pos_y() {
        return src_pos_y;
    }

    public void setSrc_pos_y(int src_pos_y) {
        this.src_pos_y = src_pos_y;
    }

    public int getDst_pos_x() {
        return dst_pos_x;
    }

    public void setDst_pos_x(int dst_pos_x) {
        this.dst_pos_x = dst_pos_x;
    }

    public int getDst_pos_y() {
        return dst_pos_y;
    }

    public void setDst_pos_y(int dst_pos_y) {
        this.dst_pos_y = dst_pos_y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTime_in_milli_secs() {
        return time_in_milli_secs;
    }

    public void setTime_in_milli_secs(int time_in_milli_secs) {
        this.time_in_milli_secs = time_in_milli_secs;
    }

    public int getZorder() {
        return zorder;
    }

    public void setZorder(int zorder) {
        this.zorder = zorder;
    }

    public int getAnimation_type() {
        return animation_type;
    }

    public void setAnimation_type(int animation_type) {
        this.animation_type = animation_type;
    }
}
