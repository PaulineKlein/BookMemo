package com.pklein.bookmemo.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

	public final static int FINISH = 1;
	public final static int PENDING = 0;
	public final static int BOUGHT = 1;
	public final static int NOTBOUGHT = 0;

	private int mId;
	private String mtitle;
	private String mAuthor;
	private String mDesc;
	private String mType;
	private int mYear;
	private int mBought;
	private int mFinish;
	private int mTome;
	private int mChapter;
	private int mEpisode;
	private int mFavorite;

	public Book()
	{
		mId = 0;
		mtitle = "";
		mAuthor = "";
		mDesc = "";
		mType = BookContract.TYPE_LITERATURE;
		mYear = 0;
		mBought = 0;
		mFinish = PENDING;
		mTome = 0;
		mChapter = 0;
		mEpisode = 0;
		mFavorite = 0;
	}
	
	public Book(int id,String title, String author, String desc, String type, int year, int bought, int finish, int tome, int chapter, int episode, int favorite) {
		mId = id;
		mtitle = title;
		mAuthor = author;
		mDesc = desc;
		mType = type;
		mYear = year;
		mBought = bought;
		mFinish = finish;
		mTome = tome;
		mChapter = chapter;
		mEpisode = episode;
		mFavorite = favorite;
	}

	public Book(Parcel in) {
		mId = in.readInt();
        mtitle = in.readString();
        mAuthor = in.readString();
        mDesc = in.readString();
        mType = in.readString();
        mYear = in.readInt();
        mBought = in.readInt();
        mFinish = in.readInt();
        mTome = in.readInt();
        mChapter = in.readInt();
        mEpisode = in.readInt();
        mFavorite = in.readInt();
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mtitle);
        dest.writeString(mAuthor);
        dest.writeString(mDesc);
        dest.writeString(mType);
        dest.writeInt(mYear);
        dest.writeInt(mBought);
        dest.writeInt(mFinish);
        dest.writeInt(mTome);
        dest.writeInt(mChapter);
        dest.writeInt(mEpisode);
        dest.writeInt(mFavorite);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {

        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

	/* ------------------------------------------  GETTER AND SETTER --------------------------------- */
	public int getId() {
		return mId;
	}
	public void setId(int id) {
		this.mId = id;
	}

	public String getTitle() {
		return mtitle;
	}
	public void setTitle(String title) {
		this.mtitle = title;
	}

	public String getAuthor() {
		return mAuthor;
	}
	public void setAuthor(String Author) {
		this.mAuthor = Author;
	}

	public String getDesc() {
		return mDesc;
	}
	public void setDesc(String Desc) {
		this.mDesc = Desc;
	}

	public String getType() {
		return mType;
	}
	public void setType(String Type) {
		this.mType = Type;
	}

	public int getYear() {
		return mYear;
	}
	public void setYear(int Year) {
		this.mYear = Year;
	}

	public int getBought() {
		return mBought;
	}
	public void setBought(int Bought) {
		this.mBought = Bought;
	}

	public int getFinish() {
		return mFinish;
	}
	public void setFinish(int Finish) {
		this.mFinish = Finish;
	}

	public int getTome() {
		return mTome;
	}
	public void setTome(int Tome) {
		this.mTome = Tome;
	}

	public int getChapter() {
		return mChapter;
	}
	public void setChapter(int Chapter) {
		this.mChapter = Chapter;
	}
	
	public int getEpisode() { return mEpisode; }
	public void setEpisode(int Episode) {
		this.mEpisode = Episode;
	}

	public int getFavorite() {
		return mFavorite;
	}
	public void setFavorite(int Favorite) {
		this.mFavorite = Favorite;
	}
}
