package com.saleor.saleor_api.storage;
import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties("storage")
@Getter
@Setter
public class StorageProperties {
    /**
     * Folder location for storing files
     */
//    private String shopImageLocation = "product_images";

    private String shopLogoLocation = "shop_logo";

    private String avatarLocation = "member_avatars";

    private String avatarReviewer = "reviewer_image";

    private String productImageLocation = "product_image";

}
