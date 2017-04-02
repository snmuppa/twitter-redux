package com.fetherz.saim.twitterredux.images;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.fetherz.saim.twistertwit.R;
import com.fetherz.saim.twitterredux.utils.DynamicHeightImageView;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.load.resource.bitmap.BitmapResource.obtain;

;

/**
 * Created by sm032858 on 3/25/17.
 */
public class ImageLoaderImpl implements ImageLoader {
    @Override
    public void loadImage(String url, final DynamicHeightImageView imageView) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.ic_photo).error(R.drawable.ic_alert).transform(new BitmapTransformation(imageView.getContext()) {
            @Override
            protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
                BlurTransformation blurTransformation = new BlurTransformation(imageView.getContext());
                Resource<Bitmap> blurredBitmapResource = blurTransformation.transform(obtain(toTransform, pool), 10, 1);

                Bitmap combinedBitmap;
                Bitmap bottom = blurredBitmapResource.get();

                if ((combinedBitmap = pool.get(toTransform.getWidth(), bottom.getHeight() / 3 + toTransform.getHeight(), Bitmap.Config.ARGB_8888)) == null) {
                    combinedBitmap = Bitmap.createBitmap(toTransform.getWidth(), bottom.getHeight() / 3 + toTransform.getHeight(), toTransform.getConfig());
                }

                Canvas comboImage = new Canvas(combinedBitmap);
                comboImage.drawBitmap(toTransform, 0f, 0f, null);

                float radius = 85.0f; // angle of round corners
                Path clipPath = new Path();
                RectF rect = new RectF(0, 0, toTransform.getWidth(), toTransform.getHeight());
                clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);

                comboImage.clipPath(clipPath);

                Matrix matrix = new Matrix();
                matrix.postRotate(180);
                matrix.preScale(-1 , 1);
                matrix.postTranslate(0, toTransform.getHeight() * 2);

                comboImage.setMatrix(matrix);
                comboImage.drawBitmap(bottom, 0f, 0f, null);

                return obtain(combinedBitmap, pool).get();
            }

            @Override
            public String getId() {
                return ImageLoader.class.getName() + ".Transformation";
            }
        }).into(imageView);
    }
}