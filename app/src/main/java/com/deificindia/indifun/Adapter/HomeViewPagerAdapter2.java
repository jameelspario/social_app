package com.deificindia.indifun.Adapter;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.deificindia.indifun.R;
import com.deificindia.indifun.Utility.ImagePickerActivity;
import com.deificindia.indifun.Utility.IndifunApplication;
import com.deificindia.indifun.Utility.Progress_Dialogue;
import com.deificindia.indifun.Utility.URLs;
import com.deificindia.indifun.pojo.getuserprofilegallerypojo.ResultItem;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeViewPagerAdapter2 extends PagerAdapter {

    private static final int REQUEST_IMAGE =100 ;
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ResultItem> images =new ArrayList<>();
    private String picturePath;
    private  ImageView imageView;
    private String photoid;

    public HomeViewPagerAdapter2(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.homecustom_layout, null);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        //  imageView.setImageResource(images[position]);
        Picasso.get().load(URLs.UserImageBaseUrl+images.get(position).getImage())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image).into(imageView);
       /* if(images.get(position).getImage()!=null && images.get(position).getImage().equals("1")){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    photoid=images.get(position).getId();
                    openMediaDialog();
                                    //  c.overridePendingTransition(0, 0);

                }
            });
        }*/
        /*if(images.get(position).getSliderType()!=null && images.get(position).getSliderType().equals("1")){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ABO5Application.getInstance().getlang(context).equals("ar")) {
                        context.startActivity(new Intent(context, InnerCatProductListActivity.class).putExtra(AppConstants.INNERCATID, images.get(position).getSliderTypeId()).putExtra(AppConstants.INNERCATNAME, images.get(position).getA_child_name()));
                        //  c.overridePendingTransition(0, 0);
                    }else if (ABO5Application.getInstance().getlang(context).equals("en")) {
                        context.startActivity(new Intent(context, InnerCatProductListActivity.class).putExtra(AppConstants.INNERCATID, images.get(position).getSliderTypeId()).putExtra(AppConstants.INNERCATNAME, images.get(position).getE_child_name()));
                        //  c.overridePendingTransition(0, 0);
                    }
                }
            });
        }else if(images.get(position).getSliderType()!=null && images.get(position).getSliderType().equals("2")){
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ABO5Application.getInstance().getlang(context).equals("ar")) {
                        context.startActivity(new Intent(context, ProductListActivity.class).putExtra(AppConstants.BRANDID, images.get(position).getSliderTypeId()).putExtra(AppConstants.BRANDNAME, images.get(position).getA_parent_name()));
                        //  c.overridePendingTransition(0, 0);
                    }else if (ABO5Application.getInstance().getlang(context).equals("en")) {
                        context.startActivity(new Intent(context, ProductListActivity.class).putExtra(AppConstants.BRANDID, images.get(position).getSliderTypeId()).putExtra(AppConstants.BRANDNAME, images.get(position).getE_parent_name()));
                        //  c.overridePendingTransition(0, 0);
                    }
                }
            });
        }*/

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }
    private void openMediaDialog() {
        Dexter.withActivity((Activity) context)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(context, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        ((Activity) context).startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(context, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);


        ((Activity) context).startActivityForResult(intent, REQUEST_IMAGE);
    }

    public static String encodeTobase64(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.givepermissions));
        builder.setMessage(context.getString(R.string.pleasegivepermissions));
        builder.setPositiveButton(context.getString(R.string.app_name), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(context.getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        ((Activity) context).startActivityForResult(intent, 101);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(((Activity) context).getContentResolver(), uri);
                    picturePath = encodeTobase64(bitmap);
                    updateaprofilegallery(photoid,picturePath);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }*/
    private void updateaprofilegallery(String photoid1,String picturePath1) {
        Dialog myDialog;
        Progress_Dialogue DialogUtils = null;
        myDialog = DialogUtils.showProgressDialog(context, "Loading Please Wait...");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.UPDATEUSEPROFILEGALLERY,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            if (obj.optInt("status") == 1) {
                                myDialog.dismiss();

                            } else {
                                myDialog.dismiss();
                                //    Toast.makeText(getActivity(), obj.optString("result"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            myDialog.dismiss();

                            Toast.makeText(context, context.getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myDialog.dismiss();
                        Toast.makeText(context, context.getString(R.string.netwrokerrorpleasetryaftersometime), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_id","12" /*IndifunApplication.getInstance().getCredential().getId()*/);
                params.put("image",picturePath1 /*IndifunApplication.getInstance().getCredential().getId()*/);
                params.put("profile_photos_id",photoid1 /*IndifunApplication.getInstance().getCredential().getId()*/);
                return params;
            }


        };
        IndifunApplication.getInstance().addToRequestQueue(stringRequest);
    }

    private void cameraIntent() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) context).startActivityForResult(intent, 0);
    }

    private String getEncodedString(Bitmap bitmap) {

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

      /* or use below if you want 32 bit images

       bitmap.compress(Bitmap.CompressFormat.PNG, (0–100 compression), os);*/
        byte[] imageArr = os.toByteArray();
        //imageArr1 = imageArr;
        return Base64.encodeToString(imageArr, Base64.DEFAULT);

    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

    public void addItem(ArrayList<ResultItem> sliderDataItems) {
        images.addAll(sliderDataItems);
        notifyDataSetChanged();
    }
    public void clearData() {
        images.clear(); // clear list
        notifyDataSetChanged(); // let your adapter know about the changes and reload view.
    }

}
