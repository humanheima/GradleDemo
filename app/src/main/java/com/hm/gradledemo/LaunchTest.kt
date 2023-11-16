package com.hm.gradledemo

import com.hm.gradlewdemo.SdkManager

class LaunchTest {

    fun test() {
        if (SdkManager.shouldLog) {
            SdkManager.haha()
        }
    }

}