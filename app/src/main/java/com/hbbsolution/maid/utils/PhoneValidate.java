package com.hbbsolution.maid.utils;

/**
 * Created by buivu on 21/07/2017.
 */

public class PhoneValidate {
    public static boolean isPhoneNumberSuccess(String phoneNumber) {
        String phone = phoneNumber.replace(" ", "").replace(".", "").replace("-", "").replace("+84", "0").replace("(", "").replace(")", "");
        if ((phone.startsWith("09") || phone.startsWith("08")) && phone.length() == 10)
            return true;

        if (phone.startsWith("01") && phone.length() == 11)
            return true;
        return false;

    }
}
