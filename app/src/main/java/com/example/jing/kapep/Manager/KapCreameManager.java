package com.example.jing.kapep.Manager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

/**
 * Created by jing on 2017/6/19.
 */
// 相册，相机的管理类
public class KapCreameManager {
    /**临时存放图片的地址，如需修改，请记得创建该路径下的文件夹*/
    private static final String lsimg = "file:///sdcard/temp.jpg";

    private static final int GET_BY_ALBUM = 801;//如果有冲突，记得修改
    private static final int GET_BY_CAMERA = 802;//如果有冲突，记得修改
    private static final int CROP = 803;//如果有冲突，记得修改

    /**
     * 相机(最大尺寸的按比例裁剪，而后上传)
     * 单选相册
     * 多选相册
     * */
    public static void OpenCreame(Activity act){
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(lsimg));
            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
            act.startActivityForResult(getImageByCamera, GET_BY_CAMERA);
        } else {
            Log.e("e","请确认已经插入SD卡");
        }
    }
    public static void OpenPhotoLib(Activity act){
        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
        getAlbum.setType("image/*");
        act.startActivityForResult(getAlbum, GET_BY_ALBUM);
    }
    /**
     * 有裁剪功能
     * 在 onActivityResult 方法进行处理
     * */

}
