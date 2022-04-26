package com.example.kanjimemory

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KanjiApplication: Application() {
    // Application = top level class that an application can hold
    // add android:name=".KanjiApplication" to AndroidManifest.xml

}