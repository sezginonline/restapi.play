package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value = "merchants", noClassnameStored = true)
public class MongoMerchant {

    @Id
    private ObjectId _id;

    private String transactionId;

    private Integer id;

    private ObjectId parentId;

    private String name;

    @Property("3dStatus")
    private String status3d;

    private String mcc;

    private String ipnUrl;

    private String apiKey;

    private String cpgKey;

    private String type;

    private String descriptor;

    private String secretKey;

    private String comType;

    @JsonIgnore
    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    @JsonIgnore
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ObjectId getParentId() {
        return parentId;
    }

    public void setParentId(ObjectId parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus3d() {
        return status3d;
    }

    public void setStatus3d(String status3d) {
        this.status3d = status3d;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getIpnUrl() {
        return ipnUrl;
    }

    public void setIpnUrl(String ipnUrl) {
        this.ipnUrl = ipnUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCpgKey() {
        return cpgKey;
    }

    public void setCpgKey(String cpgKey) {
        this.cpgKey = cpgKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getComType() {
        return comType;
    }

    public void setComType(String comType) {
        this.comType = comType;
    }
}
