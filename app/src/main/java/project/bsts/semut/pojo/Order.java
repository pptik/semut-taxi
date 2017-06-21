package project.bsts.semut.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("biaya")
    @Expose
    private String biaya;
    @SerializedName("user_queue")
    @Expose
    private String userQueue;
    @SerializedName("source_lat")
    @Expose
    private Double sourceLat;
    @SerializedName("source_lon")
    @Expose
    private Double sourceLon;
    @SerializedName("destination_lat")
    @Expose
    private Double destinationLat;
    @SerializedName("destination_lon")
    @Expose
    private Double destinationLon;
    @SerializedName("source_address")
    @Expose
    private String sourceAddress;
    @SerializedName("destination_address")
    @Expose
    private String destinationAddress;
    @SerializedName("request_by")
    @Expose
    private RequestBy requestBy;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("_id")
    @Expose
    private String id;


    public String getBiaya(){return biaya;}

    public void setBiaya(String biaya){this.biaya = biaya;}

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserQueue() {
        return userQueue;
    }

    public void setUserQueue(String userQueue) {
        this.userQueue = userQueue;
    }

    public Double getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(Double sourceLat) {
        this.sourceLat = sourceLat;
    }

    public Double getSourceLon() {
        return sourceLon;
    }

    public void setSourceLon(Double sourceLon) {
        this.sourceLon = sourceLon;
    }

    public Double getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(Double destinationLat) {
        this.destinationLat = destinationLat;
    }

    public Double getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(Double destinationLon) {
        this.destinationLon = destinationLon;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public RequestBy getRequestBy() {
        return requestBy;
    }

    public void setRequestBy(RequestBy requestBy) {
        this.requestBy = requestBy;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}