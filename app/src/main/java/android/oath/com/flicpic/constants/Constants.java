package android.oath.com.flicpic.constants;

import android.oath.com.flicpic.ImagePojo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class contains all application level constants
 */
public class Constants {

    public static List<ImagePojo> IMAGE_POJO_LIST;
    public static AtomicInteger PAGINATION;
//Static block initializes the constants
    static {
        IMAGE_POJO_LIST = new ArrayList<>();
        PAGINATION = new AtomicInteger(0);
    }

    /**
     * private constructor
     */
    private Constants() {

    }
}
