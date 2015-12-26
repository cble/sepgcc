package com.sepgcc.site.utils;

import com.sepgcc.site.dao.entity.UploadDO;
import com.sepgcc.site.dto.Upload;
import net.sf.cglib.beans.BeanCopier;

public class UploadUtils {

    private static final BeanCopier toUploadCopier = BeanCopier.create(UploadDO.class, Upload.class, false);

    public static Upload toUpload(UploadDO uploadDO) {
        Upload result = null;
        if (uploadDO != null) {
            result = new Upload();
            toUploadCopier.copy(uploadDO, result, null);
        }
        return result;
    }
}
