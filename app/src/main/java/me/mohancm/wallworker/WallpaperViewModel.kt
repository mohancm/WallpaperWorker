package me.mohancm.wallworker

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel

class WallpaperViewModel(application: Application) : AndroidViewModel(application){

    internal var imageUri: Uri? = null

    internal fun applyWall(){

    }

    private fun uriOrNull(uriString: String?): Uri? {
        return if (!uriString.isNullOrBlank()){
            Uri.parse(uriString)
        } else{
            null
        }
    }

    internal fun setImageUri(uri: String?){
        imageUri = uriOrNull(uri)
    }
}