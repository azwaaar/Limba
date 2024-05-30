package id.co.bitdata.b3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Deposit implements Parcelable {
    private int img;
    private String description;
    private String time;
    private String status;
    private String price;

    public Deposit() {

    }

    public Deposit(int img, String description, String time, String status, String price) {
        this.img = img;
        this.description = description;
        this.time = time;
        this.status = status;
        this.price = price;
    }

    protected Deposit(Parcel in) {
        img = in.readInt();
        description = in.readString();
        time = in.readString();
        status = in.readString();
        price = in.readString();
    }

    public static final Creator<Deposit> CREATOR = new Creator<Deposit>() {
        @Override
        public Deposit createFromParcel(Parcel in) {
            return new Deposit(in);
        }

        @Override
        public Deposit[] newArray(int size) {
            return new Deposit[size];
        }
    };

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(img);
        parcel.writeString(description);
        parcel.writeString(time);
        parcel.writeString(status);
        parcel.writeString(price);
    }
}
