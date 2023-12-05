package com.example.myapplication

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.os.Vibrator
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableRow
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.allViews
import androidx.core.view.get
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentBlankBinding
import com.example.myapplication.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.time.LocalTime
import kotlin.properties.Delegates
import kotlin.random.Random


class BlankFragment : Fragment() {

    private lateinit var binding: FragmentBlankBinding
    lateinit var analyzeViewModel: BlankViewModel
    val list = mutableListOf<Int>()
    var listCoord = mutableListOf<MutableList<MutableList<List<Int>>>>()
    var coord2 = mutableListOf<MutableList<List<Int>>>()
    var size1 = 0
    var size2 = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlankBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val myAssetManager: AssetManager = requireContext().applicationContext.assets
        //val q1 = myAssetManager.list("Drone")?.size

        binding.switch2.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }


        analyzeViewModel = ViewModelProvider(this)[BlankViewModel::class.java]
        val _bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        _bitmap.eraseColor(Color.rgb(30, 30, 30))
        for (i in 0..99) {
            for (j in 0..99) {
                val qwe = Random.nextInt(140, 180)
                _bitmap.setPixel(
                    j,
                    i,
                    Color.rgb(qwe, qwe, qwe)
                )
            }
        }
        for (i in 0 until 2) {
            val tableRow = TableRow(requireContext())
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            for (j in 0 until 2) {
                if (j == 1) {
                    val imageView = ImageView(requireContext())
                    imageView.setImageResource(R.drawable.fone_graph)
                    imageView.setPadding(2)
                    imageView.id = j
                    tableRow.addView(imageView, j)
                } else {
                    val imageView = ImageView(requireContext())
                    imageView.setImageBitmap(_bitmap)
                    imageView.id = j
                    imageView.setPadding(2)
                    tableRow.addView(imageView, j)
                }
            }
            binding.TableLayout.addView(tableRow, i)
        }
        val tableRow = binding.TableLayout[0]
        binding.imageView3.setImageBitmap(_bitmap)
        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        binding.button.setOnClickListener {
            val imageView = ImageView(requireContext())
            imageView.setImageResource(R.drawable.fone_graph)
        }
        val q2 = resources.assets.list("Drone")?.size
        val q3 = resources.assets.list("texttoimages")
        val q1 = resources.assets.list("Drone")
        val model = Model.newInstance(requireContext())
        val list = mutableListOf<MutableList<String>>()
        val list1 = mutableListOf<MutableList<String>>()
        val s1 = "1.7.12"
        val s2 = "1.7.1"
        val serverReplaceList =s2.split(".")
        val appReplaceList = s1.split(".")
        var result = 0
        for (i in serverReplaceList!!.indices){
            if (serverReplaceList[i].toInt() == appReplaceList[i].toInt()) result =  0
            else {
                result = serverReplaceList[i].toInt() - appReplaceList[i].toInt()
                break
            }
        }
        when  {
            result == 0 -> {}
            result!! >= 1 ->{
                println(result)
            }
            else -> {}
        }


        println(s2.compareTo(s1))
        println(123)

        for (s in q3!!.indices) {
            val qwe = resources.assets.open("texttoimages/" + q3[s])
            //val asd = qwe.bufferedReader().readLines()
            list.add(qwe.bufferedReader().readLines() as MutableList<String>)
        }
        for (s in list[3].indices) {
            val qwe = list[3][s].split(";")
            list1.add(qwe as MutableList<String>)
        }
        val size = list1.size
        var k = 1
        while (k != 101) {
            val bitmap = Bitmap.createBitmap(100+1,100, Bitmap.Config.ARGB_8888)
            bitmap.eraseColor(Color.rgb(30, 30, 30))
            val listSeries = mutableListOf<Bitmap>()
            val list3 = mutableListOf<List<Bitmap>>()
            var x = 0
            for (i in 0 until k) {
                for (j in list1[i].indices) {
                    if (j == list1[i].lastIndex) break
                    val q = list1[i][j]
                    val color = 255 + list1[i][j].toDouble().toInt()
                    val colorForPixel = densityColorSA6(color)
                    bitmap.setPixel(
                        j,
                        x,
                        Color.rgb(colorForPixel, colorForPixel, colorForPixel)
                    )
                    listSeries.add(bitmap)
                }
                x++
                list3.add(0, listSeries)
            }
            for (i in list3.indices) {
                for (j in list3.indices) {
                    binding.imageView1.setImageBitmap(list3[i][j])
                }
            }
            val bitmap2 = binding.imageView1.drawable.toBitmap(340, 340, Bitmap.Config.ARGB_8888)
            saveMediaToStorage(bitmap2)
            k++
            println(k)
        }
        /*for (s in q1!!.indices) {
            val ims: InputStream = resources.assets.open("Drone/" + q1[s])
            val d = Drawable.createFromStream(ims, null)
            binding.imageView2.setImageDrawable(d)

            val bitmap = binding.imageView2.drawable.toBitmap(340, 340, Bitmap.Config.ARGB_8888)
            val image = Bitmap.createScaledBitmap(bitmap, 64, 64, true)
            //binding.button.setOnClickListener {
            //    val bm = (binding.imageView2.drawable as BitmapDrawable).bitmap
            //    val rty = bm.getColor(50, 50)
            //   val bitmaps = getScreenShotFromView(binding.linearLayout)
            //    if (bitmaps != null) {
                //saveMediaToStorage(bm)
            //    }
            //val boolean = checkPermission()
            //ActivityCompat.requestPermissions(requireActivity(), arrayOf(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE),1)
            //}

// Creates inputs for reference.
            val inputFeature0 =
                TensorBuffer.createFixedSize(intArrayOf(1, 64, 64, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * 64 * 64 * 3)
            byteBuffer.order(ByteOrder.nativeOrder())
            val intValues = IntArray(64 * 64)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            for (i in 0 until 64) {
                for (j in 0 until 64) {
                    val `val` = intValues[pixel] // RGB
                    byteBuffer.putFloat(((`val` shr 16 and 0xFF).toFloat()))
                    byteBuffer.putFloat(((`val` shr 8 and 0xFF).toFloat()))
                    byteBuffer.putFloat(((`val` and 0xFF).toFloat()))
                    pixel++
                }
            }

            inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val confidences: FloatArray = outputFeature0.floatArray
            // find the index of the class with the biggest confidence.
            // find the index of the class with the biggest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }
            val classes = arrayOf("Drone", "Non Drone")
            println(classes[maxPos])
            if (maxPos == 1) {
                println("не верный предикт ")
            }
            if (classes[maxPos] == "Drone") {
                /*vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        1000L,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )*/
                val mediaPlayer = MediaPlayer.create(context, R.raw.alarmbuzzer)
                mediaPlayer.start()
                /*val path = Uri.parse(resources.assets.open("alarmBuzzer.mp3").toString())
                val player: MediaPlayer = MediaPlayer.create(requireContext(),path)
                player.isLooping = true
                player.start()*/
            }

            var s = ""
            for (i in classes.indices) {
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100)
            }
            print(s)

// Releases model resources if no longer used.
        }*/
        model.close()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getScreenShotFromView(v: View): Bitmap? {
        // create a bitmap object
        var screenshot: Bitmap? = null
        try {
            // inflate screenshot object
            // with Bitmap.createBitmap it
            // requires three parameters
            // width and height of the view and
            // the background color
            screenshot =
                Bitmap.createBitmap(v.measuredWidth, v.measuredHeight, Bitmap.Config.ARGB_8888)
            screenshot.eraseColor(Color.argb((255 - 99).toFloat(), 0F, 0F, 0F))
            // Now draw this bitmap on a canvas

        } catch (e: Exception) {
            Log.e("GFG", "Failed to capture screenshot because:" + e.message)
        }
        // return the bitmap
        return screenshot
    }

    private fun saveMediaToStorage(bitmap: Bitmap) {
        // Generating a file name
        val filename = "${System.currentTimeMillis()}.png"

        // Output stream
        var fos: OutputStream? = null

        // For devices running android >= Q
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // getting the contentResolver
            requireContext().contentResolver?.also { resolver ->

                // Content resolver will process the contentvalues
                val contentValues = ContentValues().apply {

                    // putting file information in content values
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
                }

                // Inserting the contentValues to
                // contentResolver and getting the Uri
                val imageUri: Uri? =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

                // Opening an outputstream with the Uri that we got
                fos = imageUri?.let { resolver.openOutputStream(it) }
            }
        } else {
            // These for devices running on android < Q
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val image = File(imagesDir, filename)
            fos = FileOutputStream(image)
        }

        fos?.use {
            // Finally writing the bitmap to the output stream that we opened
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
    }
    private fun densityColorSA6(color: Int): Int {
        val k = 255 / (100 - 40)
        val x = color - (255 - 85)
        val _color = x * k
        return if (_color < 30) 30//Color.parseColor("#0065a8")
            else if (_color >= 255) 255//Color.parseColor("#FF0000")
            else _color//getColorByValue(_color)
    }

    private fun checkPermission(): Boolean {
        return if (SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val result =
                ContextCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE)
            val result1 =
                ContextCompat.checkSelfPermission(requireContext(), WRITE_EXTERNAL_STORAGE)
            result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        @Nullable data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    // perform action when allow permission success
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Allow permission for storage access!",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }


    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                println(123)
            } else {
                //user denied the permission
            }
        }
    }
}