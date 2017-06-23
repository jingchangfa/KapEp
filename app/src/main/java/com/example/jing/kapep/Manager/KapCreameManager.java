package com.example.jing.kapep.Manager;

import android.app.Activity;
import com.yalantis.ucrop.model.AspectRatio;

import java.io.File;
import java.util.List;

import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.RxGalleryFinalApi;
import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageMultipleResultEvent;

import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import cn.finalteam.rxgalleryfinal.ui.RxGalleryListener;
import cn.finalteam.rxgalleryfinal.ui.base.IRadioImageCheckedListener;



/**
 * Created by jing on 2017/6/19.
 */
// 相册，相机的管理类
// 引擎是第三方的-----RxGalleryFinal
// github https://github.com/FinalTeam/RxGalleryFinal
public class KapCreameManager {
    /**
     * 单选回调
     * 多选回调
     */
    public interface RadioFinishResultInKapCreameManager{
        void result(String imagePath);
    }
    public interface MultipleFinishResultInKapCreameManage{
        void result(List<MediaBean> cropBeans);
    }
    //--------------------- Dividing line -----------------------------
    /**
     * 依赖第三方
     * main.xml 注册activity 配置style
     * OpenLibByRadio 单选
     * OpenLibByMultiple 多选
     * OpenCompleteCamera 拍照（帖子的）
     * OpenCircleCamera  拍照（头像的）
     * */
    public static void OpenLibByRadio(Activity context, final RadioFinishResultInKapCreameManager result){
        RxGalleryFinal rx = RxGalleryFinal.with(context)
                .image()
                .radio()
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        // 图片选择的回调
                    }
                });
        //自定义裁剪
        RxGalleryFinalApi.setImgSaveRxCropSDCard("dujinyang/crop/cutImage.png");//手动设置裁剪的路径；
        rx.cropAspectRatioOptions(0, new AspectRatio("3:3", 30, 30))//方形裁剪
                .crop()
                .openGallery();
        // 设置裁剪的回调
        RxGalleryListener.getInstance().setRadioImageCheckedListener(new IRadioImageCheckedListener() {
            @Override
            public void cropAfter(Object t) {
                File file = (File)t;
                if (file == null) return;
                String path = file.getPath();
                result.result(file.getPath());
            }
            @Override
            public boolean isActivityFinish() {
                return true;
            }
        });
    }
    public static void OpenLibByMultiple(Activity context,final MultipleFinishResultInKapCreameManage result){
        // 多选还是得自己裁剪啊～
//        .cropAspectRatioOptions(0, new AspectRatio("3:3", 375, 667))//制定宽高裁剪
//        .crop()//裁剪
        RxGalleryFinal.with(context)
                .image()
                .multiple()
                .maxSize(8)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageMultipleResultEvent>() {
                    @Override
                    protected void onEvent(ImageMultipleResultEvent imageMultipleResultEvent) throws Exception {
                        // 1.回调更新 2.关闭图片选择
                        result.result(imageMultipleResultEvent.getResult());
                    }
                })
                .openGallery();
    }
}
//
//    /**临时存放图片的地址，如需修改，请记得创建该路径下的文件夹*/
//    private static final String lsimg = "file:///sdcard/temp.jpg";
//    private static final int GET_BY_ALBUM = 801;//如果有冲突，记得修改
//    private static final int GET_BY_CAMERA = 802;//如果有冲突，记得修改
//    private static final int CROP = 803;//如果有冲突，记得修改
//
//    /**
//     * 相机(最大尺寸的按比例裁剪，而后上传)
//     * 单选相册
//     * 多选相册
//     * */
//    public static void OpenCreame(Activity act){
//        String state = Environment.getExternalStorageState();
//        if (state.equals(Environment.MEDIA_MOUNTED)) {
//            Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(lsimg));
//            getImageByCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
//            act.startActivityForResult(getImageByCamera, GET_BY_CAMERA);
//        } else {
//            Log.e("e","请确认已经插入SD卡");
//        }
//    }
//    public static void OpenPhotoLib(Activity act){
//        Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
//        getAlbum.setType("image/*");
//        act.startActivityForResult(getAlbum, GET_BY_ALBUM);
//    }
//    /**
//     * 有裁剪功能
//     * 在 onActivityResult 方法进行处理
//     * */



//GalleryFinal
//import cn.finalteam.galleryfinal.CoreConfig;
//import cn.finalteam.galleryfinal.FunctionConfig;
//import cn.finalteam.galleryfinal.GalleryFinal;
//import cn.finalteam.galleryfinal.ThemeConfig;
//import cn.finalteam.galleryfinal.widget.GFImageView;

// Glide 配置类
//class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader {
//    @Override
//    public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
//        Glide.with(activity)
//                .load("file://" + path)
//                .placeholder(defaultDrawable)
//                .error(defaultDrawable)
//                .override(width, height)
//                .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
//                .skipMemoryCache(true)
//                //.centerCrop()
//                .into(new ImageViewTarget<GlideDrawable>(imageView) {
//                    @Override
//                    protected void setResource(GlideDrawable resource) {
//                        imageView.setImageDrawable(resource);
//                    }
//
//                    @Override
//                    public void setRequest(Request request) {
//                        imageView.setTag(R.id.adapter_item_tag_key,request);
//                    }
//
//                    @Override
//                    public Request getRequest() {
//                        return (Request) imageView.getTag(R.id.adapter_item_tag_key);
//                    }
//                });
//    }
//
//    @Override
//    public void clearMemoryCache() {
//    }
//}

//配置功能 (按照需求自己配置)
//    // 单选相册
//    private static FunctionConfig RedioFunctionConfigCreating(){
//        FunctionConfig functionConfig = new FunctionConfig.Builder()
//                .setMutiSelectMaxSize(9)//配置多选数量
//                .setEnableCamera(true)//开启相机功能
//                .setEnableCrop(true)//开启裁剪功能
//                .setCropSquare(true)//裁剪正方形
//                .build();
//        return functionConfig;
//    }
//    // 多选相册
//    private static FunctionConfig MultipleFunctionConfigCreating(){
//        FunctionConfig functionConfig = new FunctionConfig.Builder()
//                .setMutiSelectMaxSize(9)//配置多选数量
//                .setEnableCamera(true)//开启相机功能
//                .setEnableCrop(true)//开启裁剪功能
//                .setCropSquare(true)//裁剪正方形
//                .build();
//        return functionConfig;
//    }
//    // 拍照（全屏）
//    private static FunctionConfig PhotoCompleteFunctionConfigCreating(){
//        FunctionConfig functionConfig = new FunctionConfig.Builder()
//                .setMutiSelectMaxSize(9)//配置多选数量
//                .setEnableCrop(true)//开启裁剪功能
//                .setCropSquare(true)//裁剪正方形
//                .build();
//        return functionConfig;
//    }
//    // 拍照（可裁剪+圆形）
//    private static FunctionConfig PhotoCircleFunctionConfigCreating(){
//        FunctionConfig functionConfig = new FunctionConfig.Builder()
//                .setMutiSelectMaxSize(9)//配置多选数量
//                .setEnableCrop(true)//开启裁剪功能
//                .setCropSquare(true)//裁剪正方形
//                .build();
//        return functionConfig;
//    }

////--------------------- Dividing line -----------------------------
//    /**
//     * 依赖第三方
//     * ConfigSetinging 配置 **在你的Application中调用，以添加配置**
//     * OpenLibByRadio 单选
//     * OpenLibByMultiple 多选
//     * OpenCompleteCamera 拍照（帖子的）
//     * OpenCircleCamera  拍照（头像的）
//     * */
//    public static void ConfigSeting(Context context){
//        // 主题配置
//        ThemeConfig theme = ThemeConfigCreating();
//        // 引擎配置
//        GlideImageLoader imageloader = ImageLoaderConfigCreating();
//        // 核心配置
//        CoreConfig coreConfig = new CoreConfig.Builder(context, imageloader, theme)
//                .build();
//        // 创建
//        GalleryFinal.init(coreConfig);
//    }
//    private static final int REQUEST_CODE_GALLERY = 801;
//    private static final int REQUEST_CODE_CAMERA = 802;
//
//    public static void OpenLibByRadio(GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback){
//        FunctionConfig functionConfig = RedioFunctionConfigCreating();
//        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig,mOnHanlderResultCallback);
//    }
//    public static void OpenLibByMultiple(GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback){
//        FunctionConfig functionConfig = MultipleFunctionConfigCreating();
//        GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
//    }
//    public static void OpenCompleteCamera(GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback){
//        FunctionConfig functionConfig = PhotoCompleteFunctionConfigCreating();
//        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
//    }
//    public static void OpenCircleCamera(GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback){
//        FunctionConfig functionConfig = PhotoCircleFunctionConfigCreating();
//        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);
//    }
//    // 辅助方法
//    /**
//     * ThemeConfigCreating 生成主题
//     * ImageLoaderConfigCreating 生成图片加载引擎
//     * FunctionConfigCreating 生成配置
//     * */
//    private static ThemeConfig ThemeConfigCreating(){
//        ThemeConfig theme = new ThemeConfig.Builder()
//                .setTitleBarTextColor(R.color.Bank_White_ffffff)
//                .setTitleBarBgColor(R.color.Bank_Black_313131)
//                .setIconBack(R.mipmap.nav_back)
//                .build();
//        return theme;
//    }
//    private static GlideImageLoader ImageLoaderConfigCreating(){
//        GlideImageLoader imageloader = new GlideImageLoader();
//        return imageloader;
//    }
//    public static void OpenCompleteCamera(Context context,RxBusResultSubscriber<ImageRadioResultEvent> result){
//        RxGalleryFinal
//                .with(context)
//                .image()
//                .radio()
//                .crop()//裁剪
//                .imageLoader(ImageLoaderType.GLIDE)
//                .subscribe(result)//图片选择结果
//                .openGallery();
//    }
//    public static void OpenCircleCamera(Context context,RxBusResultSubscriber<ImageRadioResultEvent> result){
//        RxGalleryFinal
//                .with(context)
//                .image()
//                .radio()
//                .crop()//裁剪
//                .imageLoader(ImageLoaderType.GLIDE)
//                .subscribe(result)//图片选择结果
//                .openGallery();
//    }