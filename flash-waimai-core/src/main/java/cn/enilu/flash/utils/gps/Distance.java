package cn.enilu.flash.utils.gps;

/**
 * 根据gps坐标计算距离
 * @author ：enilu
 * @date ：Created in 2019/9/16 19:38
 */

public class Distance {
    private Double[] start;
    private Double[] end;
    public Distance(Double srcLng,Double srcLat,Double destLng,Double destLat){
        this.start = new Double[]{srcLat,srcLng};
        this.end = new Double[]{destLat,destLng};
    }
    public Distance(Double[] start, Double[] end) {
        this.start = start;
        this.end = end;
    }

    public Double[] getStart() {
        return start;
    }

    public void setStart(Double[] start) {
        this.start = start;
    }

    public Double[] getEnd() {
        return end;
    }

    public void setEnd(Double[] end) {
        this.end = end;
    }

    /**
     * 返回两点间的距离，单位：米
     * @return
     */
    public Double getDistance() {
        if (start == null || end == null) {
            return null;
        }
        double lon1 = (Math.PI / 180) * start[0];
        double lon2 = (Math.PI / 180) * end[0];
        double lat1 = (Math.PI / 180) * start[1];
        double lat2 = (Math.PI / 180) * end[1];

        // 地球半径
        double R = 6371;
        // 两点间距离 km，如果想要米的话，结果*1000就可以了
        double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;
        return d*1000;

    }

    /*
     * 计算两点之间距离
     *
     * @param start
     *
     * @param end
     *
     * @return 米
     */
    public Double getDistance(Double[] start, Double[] end) {
        this.start = start;
        this.end = end;
        return getDistance();
    }
}