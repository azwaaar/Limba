package id.co.bitdata.b3.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Menu implements Parcelable {
    private int imgMenu;
    private String titleMenu;

    public Menu() {

    }

    public Menu(int imgMenu, String titleMenu) {
        this.imgMenu = imgMenu;
        this.titleMenu = titleMenu;
    }

    protected Menu(Parcel in) {
        imgMenu = in.readInt();
        titleMenu = in.readString();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

    public int getImgMenu() {
        return imgMenu;
    }

    public void setImgMenu(int imgMenu) {
        this.imgMenu = imgMenu;
    }

    public String getTitleMenu() {
        return titleMenu;
    }

    public void setTitleMenu(String titleMenu) {
        this.titleMenu = titleMenu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(imgMenu);
        parcel.writeString(titleMenu);
    }
}
