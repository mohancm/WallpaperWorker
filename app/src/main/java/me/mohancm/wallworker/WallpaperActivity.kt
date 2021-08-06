package me.mohancm.wallworker

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import me.mohancm.wallworker.databinding.ActivityWallpaperBinding
import java.io.IOException

open class WallpaperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWallpaperBinding

    private var setWall: Button? = null
    private var wallImage: ImageView? = null
    private var imageList = arrayOf<Int>(
        R.drawable.pixel_six_wall,
        R.drawable.wallpaper1,
        R.drawable.wallpaper2,
        R.drawable.wallpaper3,
        R.drawable.wallpaper4,
        R.drawable.wallpaper5,
        R.drawable.wallpaper6,
        R.drawable.wallpaper7,
        R.drawable.wallpaper8,
        R.drawable.wallpaper9,
        R.drawable.wallpaper10,
        R.drawable.wallpaper11,
        R.drawable.wallpaper12,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWallpaperBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWall = findViewById(R.id.set_wall_button)
        wallImage = findViewById(R.id.wallpaper_image)
        var viewModel: WallpaperViewModel = ViewModelProvider(this).get(WallpaperViewModel::class.java)

        // Image uri should be stored in the ViewModel; put it there then display
        val imageUriExtra = intent.getStringExtra(KEY_IMAGE_URI)
        viewModel.setImageUri(imageUriExtra)
        viewModel.imageUri?.let { imageUri -> binding.wallpaperImage
//            Glide.with(this).load(imageUri).into(binding.wallpaperImage)
        }

        setWall?.setOnClickListener {
//            applyWallpaper()
        }
    }

    @SuppressLint("ResourceType")
    fun applyWallpaper(context: Context) {
        // creating the instance of the WallpaperManager
        var wallpaperManager = WallpaperManager.getInstance(this)
        try {
            var image = imageList.random()
            wallpaperManager.setResource(image)
            wallImage?.setImageResource(image)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

//    private fun applyWallPeriodically(){
//        var imageWorker = PeriodicWorkRequestBuilder<>()
//
//    }


}