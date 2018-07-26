package com.pklein.bookmemo.data;

import android.util.Log;

import com.pklein.bookmemo.SeeAllAdapter;

import java.util.ArrayList;

public class Book {

	public final static int FINISH = 1;
	public final static int PENDING = 0;
	public final static int BOUGHT = 1;
	public final static int NOTBOUGHT = 0;
		
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
	
	public Book(String title, String author, String desc, String type, int year, int bought, int finish, int tome, int chapter, int episode, int favorite) {
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
	
	public static ArrayList<Book> getAListOfBook() {
		ArrayList<Book> listBook = new ArrayList<Book>();
		
		listBook.add(new Book("title1", "author1", "desc", BookContract.TYPE_MANGA,1990,BOUGHT,PENDING,50,300,0,0));
		listBook.add(new Book("title2", "author2", "desc", BookContract.TYPE_LITERATURE,1991,NOTBOUGHT,FINISH,51,300,0,0));
		listBook.add(new Book("title3", "author3", "desc", BookContract.TYPE_LITERATURE,1992,NOTBOUGHT,FINISH,52,300,0,0));
		listBook.add(new Book("title4", "author4", "desc", BookContract.TYPE_MANGA,1993,BOUGHT,PENDING,53,300,0,0));
		return listBook;
	}
	
	/* ------------------------------------------  GETTER AND SETTER --------------------------------- */
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
