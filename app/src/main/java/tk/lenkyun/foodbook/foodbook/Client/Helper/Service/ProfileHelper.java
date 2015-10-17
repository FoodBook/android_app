package tk.lenkyun.foodbook.foodbook.Client.Helper.Service;

import android.graphics.Bitmap;

import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.ContentListener;
import tk.lenkyun.foodbook.foodbook.Client.Service.ContentService;
import tk.lenkyun.foodbook.foodbook.Data.Content;
import tk.lenkyun.foodbook.foodbook.Data.Photo.ContentPhoto;
import tk.lenkyun.foodbook.foodbook.Data.Photo.Photo;
import tk.lenkyun.foodbook.foodbook.Data.User.Profile;

/**
 * Created by lenkyun on 17/10/2558.
 */
public class ProfileHelper {
    private Profile profile;
    public ProfileHelper(Profile profile){
        this.profile = profile;
    }

    public void getProfilePictureContent(final ContentListener<Bitmap> bitmapContentListener){
        ContentService contentService = ContentService.getInstance();
        contentService.getBitmap(profile.getProfilePicture(), new ContentListener<Bitmap>() {
            @Override
            public void onContentLoaded(Content<Bitmap> content) {
                profile.setProfilePicture(ContentPhoto.fromContent(content));
                bitmapContentListener.onContentLoaded(content);
            }

            @Override
            public void onContentFailed(String errorDetail) {
                bitmapContentListener.onContentFailed(errorDetail);
            }
        });
    }

    public final static String NO_PHOTO = "NO_PHOTO";
    public void getCoverPictureContent(final ContentListener<Bitmap> bitmapContentListener){
        ContentService contentService = ContentService.getInstance();
        Photo coverPicture = profile.getCoverPicture();
        if(coverPicture == null){
            bitmapContentListener.onContentFailed(NO_PHOTO);
            return;
        }

        contentService.getBitmap(profile.getCoverPicture(), new ContentListener<Bitmap>() {
            @Override
            public void onContentLoaded(Content<Bitmap> content) {
                profile.setCoverPicture(ContentPhoto.fromContent(content));
                bitmapContentListener.onContentLoaded(content);
            }

            @Override
            public void onContentFailed(String errorDetail) {
                bitmapContentListener.onContentFailed(errorDetail);
            }
        });
    }
}
