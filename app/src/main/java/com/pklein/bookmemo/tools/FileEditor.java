package com.pklein.bookmemo.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;


public class FileEditor {

    private static final String TAG = FileEditor.class.getSimpleName();
    public static final String DIRECTORY = "BookMemo";
    private String mydate;
    private File file;
    private String Info ="";


    public FileEditor()
    {
        mydate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        file = new File(Environment.getExternalStorageDirectory() + File.separator + DIRECTORY, DIRECTORY +mydate+".csv");

        File myDir = new File(Environment.getExternalStorageDirectory()+File.separator + DIRECTORY);
        if (!myDir.exists()) {
            myDir.mkdir(); //On crée le répertoire (s'il n'existe pas!!)
        }

        Info =   " TEST " + System.getProperty("line.separator" )+" TEST2 ";
    }


    public void exportData(File myFile,ContentResolver contentResolver) throws Exception
    {
        try
        {
            // create file at root directory
            if(!myFile.exists())
            {
                myFile.createNewFile();
            }
            FileWriter filewriter = new FileWriter(myFile,false);

            BookDbTool bookDbTool = new BookDbTool();
            Info = bookDbTool.getAllforFile(contentResolver);

            filewriter.write(Info);
            filewriter.close();
        }
        catch (Exception e){
            Log.e(TAG,e.getMessage());
            throw new Exception("Exportdata Error");
        }

    }

    public void importData(ContentResolver contentResolver, Uri uri) throws Exception
    {
        Log.i(TAG,uri.getPath() );
        if(!uri.getPath().toLowerCase().contains("csv"))
        {
            throw new Exception("csv");
        }

        InputStream inputStream = contentResolver.openInputStream(uri);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

       // file = new File(Environment.getExternalStorageDirectory() + File.separator + DIRECTORY, DIRECTORY + ".csv");
       /* file = new File(uri.getPath());
       // file = new File(new URI(uri.getPath()));
        if(!file.exists())
        {
            throw new Exception("Absent");
        }
        FileReader filereader = new FileReader(file);
        BufferedReader reader = new BufferedReader(filereader);*/

        try
        {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] RowData = csvLine.split(";");

                //we check if there is still 11 columns and that we don't try to import the header line :
                if(RowData.length==11 && csvLine.indexOf("Author")==-1)
                {
                    String title = RowData[0];
                    String author = RowData[1];
                    String desc = RowData[2];
                    String type = RowData[3];
                    int year = Integer.parseInt(RowData[4]);
                    int bought = Integer.parseInt(RowData[5]);
                    int finish = Integer.parseInt(RowData[6]);
                    int tome = Integer.parseInt(RowData[7]);
                    int chapter = Integer.parseInt(RowData[8]);
                    int episode = Integer.parseInt(RowData[9]);
                    int favorite = Integer.parseInt(RowData[10]);

                    BookDbTool bookDbTool = new BookDbTool();
                    try {
                        bookDbTool.insertBook(contentResolver, title, author, desc, type, year, finish, bought, chapter, tome, episode, favorite);
                    }
                    catch(Exception e){
                        Log.e(TAG,e.getMessage());
                    }
                }
            }

            reader.close();
            inputStream.close();
        }
        catch (Exception e){
            Log.e(TAG,e.getMessage());
            throw new Exception("importData Error");
        }

    }

    /* ------------------------------------------  GETTER AND SETTER --------------------------------- */
    public String getMydate() { return mydate; }
    public void setMydate(String mydate) { this.mydate = mydate;}

    public File getFile() {	return file;}
    public void setFile(File file) {	this.file = file;}

    public String getInfo() {return Info;}
    public void setInfo(String info) {	Info = info;}

}
