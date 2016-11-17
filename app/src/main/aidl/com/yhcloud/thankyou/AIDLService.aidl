// AIDLService.aidl
package com.yhcloud.thankyou;

// Declare any non-default types here with import statements

interface AIDLService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
    void success();
    void login(String username, String password);
}
