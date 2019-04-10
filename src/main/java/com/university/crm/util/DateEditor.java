package com.university.crm.util;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * date: 2019/3/24
 * description :
 *
 * @author : zhencai.cheng
 */
public class DateEditor extends PropertyEditorSupport {

    public String getAsText() {
        Date value = (Date) getValue();
        if (null == value || "null".equals(value)) {
            return "";
        } else {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            return df.format(value);
        }
    }

    public void setAsText(String text) throws IllegalArgumentException {
        Date value = null;
        if (null != text && !text.equals("")) {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                value = df.parse(text);
            } catch (Exception e) {
                df = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    value = df.parse(text);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        }
        setValue(value);
    }
}
