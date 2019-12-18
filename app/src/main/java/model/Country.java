package model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Country implements Parcelable {
    String name;
    String nativeName,alpha3Code;

    public String getAlpha3Code() {
        return alpha3Code;
    }

    public void setAlpha3Code(String alpha3Code) {
        this.alpha3Code = alpha3Code;
    }

    public String[] getBorders() {
        return borders;
    }

    public void setBorders(String[] borders) {
        this.borders = borders;
    }

    String[]borders;
    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    String population;
//    long population;

  /*  public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(nativeName);
        dest.writeString(alpha3Code);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Country createFromParcel(Parcel in) {
            return new Country(in);
        }

        public Country[] newArray(int size) {
            return new Country[size];
        }
    };

    // "De-parcel object
    public Country(Parcel in) {
        name = in.readString();
        nativeName = in.readString();
        alpha3Code = in.readString();
//        in.readStringArray(borders);
    }
}
