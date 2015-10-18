package tk.lenkyun.foodbook.foodbook.Client.Helper.Service;

import android.graphics.Bitmap;

import tk.lenkyun.foodbook.foodbook.Client.Service.Listener.ContentListener;
import tk.lenkyun.foodbook.foodbook.Client.Service.PhotoContentService;
import tk.lenkyun.foodbook.foodbook.Data.Content;
import tk.lenkyun.foodbook.foodbook.Data.Photo.PhotoContent;
import tk.lenkyun.foodbook.foodbook.Data.Photo.PhotoItem;
import tk.lenkyun.foodbook.foodbook.Data.User.Profile;

/**
 * Created by lenkyun on 17/10/2558.
 */
public class ProfileHelper {
    public final static String NO_PHOTO = "NO_PHOTO";
    private Profile profile;

    public ProfileHelper(Profile profile){
        this.profile = profile;
    }

    public void getProfilePictureContent(final ContentListener<Bitmap> bitmapContentListener){
        PhotoContentService contentService = PhotoContentService.getInstance();
        contentService.getPhotoContent(profile.getProfilePicture(), new ContentListener<Bitmap>() {
            @Override
            public void onContentLoaded(Content<Bitmap> content) {
                profile.setProfilePicture(PhotoContent.fromContent(content));
                bitmapContentListener.onContentLoaded(content);
            }

            @Override
            public void onContentFailed(String errorDetail) {
                bitmapContentListener.onContentFailed(errorDetail);
            }
        });
    }

    public void getCoverPictureContent(final ContentListener<Bitmap> bitmapContentListener){
        PhotoContentService contentService = PhotoContentService.getInstance();
        PhotoItem coverPicture = profile.getCoverPicture();
        if(coverPicture == null){
            bitmapContentListener.onContentFailed(NO_PHOTO);
            return;
        }

        contentService.getPhotoContent(profile.getCoverPicture(), new ContentListener<Bitmap>() {
            @Override
            public void onContentLoaded(Content<Bitmap> content) {
                profile.setCoverPicture(PhotoContent.fromContent(content));
                bitmapContentListener.onContentLoaded(content);
            }

            @Override
            public void onContentFailed(String errorDetail) {
                bitmapContentListener.onContentFailed(errorDetail);
            }
        });
    }
}
