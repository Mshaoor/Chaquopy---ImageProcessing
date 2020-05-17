package com.myproject.imageprocessing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Button takeimage, saveimage;
    ImageView img;
    OutputStream outputStream;
    private Bitmap bitmap;
    EditText name;
    String name1 = "";
    String a = "";
    String fi="";
    File dir;
    String imageset="No";
    String Picklepath="";
    private final int IMG_REQUEST = 1;
    byte[] b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }


        takeimage = (Button) findViewById(R.id.takepic);
        saveimage = (Button) findViewById(R.id.save);
        img = (ImageView) findViewById(R.id.img);
        name = (EditText) findViewById(R.id.name);

        takeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setType("image/*");
                it.setAction(it.ACTION_GET_CONTENT);
                startActivityForResult(it, IMG_REQUEST);
            }
        });

        saveimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    if (name.getText().toString().isEmpty() || imageset.equals("NO")) {
                        if (name.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Please Enter user name", Toast.LENGTH_LONG).show();
                        } else if (imageset.equals("NO")) {
                            Toast.makeText(getApplicationContext(), "Please choose an image", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        name1 = name.getText().toString().trim();
                        File filepath = Environment.getExternalStorageDirectory();
                        dir = new File(filepath.getAbsolutePath() + "/ImageProcessing/" + name1);


                        if (dir.isDirectory()) {

                            String[] children = dir.list();
                            for (int i = 0; i < children.length; i++) {
                                new File(dir, children[i]).delete();
                            }

                            dir.mkdir();
                            File file = new File(dir, System.currentTimeMillis() + ".jpg");
                            try {
                                outputStream = new FileOutputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            try {
                                outputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Picklepath = dir.toString();
                           // Toast.makeText(getApplicationContext(), Picklepath, Toast.LENGTH_LONG).show();
                            a = BitMapToString(bitmap);
                            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
                        } else {
                            dir.mkdir();
                            File file = new File(dir, System.currentTimeMillis() + ".jpg");
                            try {
                                outputStream = new FileOutputStream(file);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            try {
                                outputStream.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Picklepath = dir.toString();
                            //Toast.makeText(getApplicationContext(), Picklepath, Toast.LENGTH_LONG).show();

                            a = BitMapToString(bitmap);
                            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_LONG).show();
                        }
                    }
                }
                catch(Exception e)
                {
                    String err=e.getMessage().toString();
                    Toast.makeText(getApplicationContext(), "Something is missing", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    Uri uri;
    // get image from gallrry
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        try{
            if(requestCode==IMG_REQUEST && resultCode == RESULT_OK && data!=null) {
                uri = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                img.setImageBitmap(bitmap);
                img.setVisibility(View.VISIBLE);
                imageset="YES";
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }
    String temp="";


    public String BitMapToString(Bitmap bitmap) {
       try {
           Bitmap converetdImage = getResizedBitmap(bitmap, 400);
           ByteArrayOutputStream baos = new ByteArrayOutputStream();
           converetdImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
           b = baos.toByteArray();
           temp = Base64.encodeToString(b, Base64.DEFAULT);
           //Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
           try {
               Python py = Python.getInstance();
               PyObject pyf = py.getModule("activity1");//filename
               PyObject obj = pyf.callAttr("face_embeddings", temp, name1, Picklepath);//defination  and args
               String word = obj.toString();
               Toast.makeText(getApplicationContext(), word, Toast.LENGTH_LONG).show();
           } catch (Exception e) {
               Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
           }


       }catch(Exception e)
       {
           Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
       }
        return "send image";
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

}
