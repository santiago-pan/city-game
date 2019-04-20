package com.nadisam.citybombing.levels.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import com.nadisam.citybombing.log.Logger;
import com.nadisam.citybombing.pro.R;

public class Share
{
    public static void share(Bitmap bmp, String text, Context context)
    {
        // Default chooser
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.share_subject));
        intent.putExtra(Intent.EXTRA_STREAM, getImageUri(context, bmp));
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("image/*");
        context.startActivity(Intent.createChooser(intent, context.getResources().getString(R.string.share_title)));

    }

    private static Uri getImageUri(Context context, Bitmap inImage)
    {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), context.getResources().getString(R.string.app_name));
        
        if(false == mediaStorageDir.exists())
        {
            mediaStorageDir.mkdir();
        }
        String bmpPath = mediaStorageDir.getPath() + File.separator + "cicybombing_stats.jpg";

        try
        {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;

            FileOutputStream fileOutputStream = new FileOutputStream(bmpPath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            inImage.compress(CompressFormat.JPEG, 100, bos);

            bos.flush();
            bos.close();

            File file = new File(bmpPath);

            Uri uri = Uri.fromFile(file);

            return uri;
        }
        catch (Exception e)
        {
            Logger.error("Share ERROR", e);
            return null;
        }
    }
}
