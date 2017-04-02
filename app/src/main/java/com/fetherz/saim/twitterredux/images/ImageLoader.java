package com.fetherz.saim.twitterredux.images;

import com.fetherz.saim.twitterredux.utils.DynamicHeightImageView;

/**
 * Created by sm032858 on 3/25/17.
 */

public interface ImageLoader {
    void loadImage(String url, DynamicHeightImageView imageView);
}
