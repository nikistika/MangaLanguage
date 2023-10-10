package com.example.mangalanguage

    import android.content.ClipData
    import android.content.ClipboardManager
    import android.content.Context
    import android.content.Intent
    import android.content.pm.PackageManager
    import android.graphics.Bitmap
    import android.graphics.ImageDecoder
    import android.net.Uri
    import android.os.Bundle
    import android.util.SparseArray
    import android.view.View
    import android.widget.Button
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.app.ActivityCompat
    import androidx.core.content.ContextCompat
    import androidx.lifecycle.lifecycleScope
    import com.example.mangalanguage.databinding.ActivityMainBinding
    import com.google.android.gms.vision.Frame
    import com.google.android.gms.vision.text.TextBlock
    import com.google.android.gms.vision.text.TextRecognizer
    import com.google.android.material.appbar.MaterialToolbar
    import com.theartofdev.edmodo.cropper.CropImage
    import com.theartofdev.edmodo.cropper.CropImageView
    import kotlinx.coroutines.launch
    import java.lang.StringBuilder


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var topAppBar: MaterialToolbar
    private lateinit var button_capture: Button
    private lateinit var button_copy: Button
    private lateinit var textView_data: TextView
    private val REQUEST_CAMERA_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()

        button_capture = binding.buttonCapture
        button_copy = binding.buttonCopy
        textView_data = binding.textData

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_CODE)
        }

        button_capture.setOnClickListener { view ->
            try {
                lifecycleScope.launch {
                    CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this@MainActivity)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

        button_copy.setOnClickListener { view ->
            val scanned_text = textView_data.text.toString()
            copyToClipBoard(scanned_text)
        }
        otherActivities()
    }



    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri

                val source: ImageDecoder.Source = ImageDecoder.createSource(this.contentResolver, resultUri)
                val bitmap: Bitmap = ImageDecoder.decodeBitmap(source)

                getTextFromImage(bitmap)
            }
        }
    }

    fun initialization(){
        topAppBar = binding.mainTopAppbar
    }

    fun otherActivities (){
        topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.reader_goMainActivity -> {
                    val intent = Intent(this, ReaderActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    fun getTextFromImage(bitmap: Bitmap){
        val recognizer: TextRecognizer = TextRecognizer.Builder(this).build()
        if(!recognizer.isOperational){
            Toast.makeText(this, "Error Occurred!!!", Toast.LENGTH_SHORT).show()
        }
        else {
            val frame: Frame = Frame.Builder().setBitmap(bitmap).build()
            val textBlockSparseArray: SparseArray<TextBlock> = recognizer.detect(frame)
            val stringBilder: StringBuilder = StringBuilder()

            for (i in 0 until textBlockSparseArray.size()) {
                val textBlock: TextBlock = textBlockSparseArray.valueAt(i)
                stringBilder.append(textBlock.value)
                stringBilder.append("\n")
            }
            textView_data.text = stringBilder.toString()
            button_capture.text = "Retake"
            button_copy.visibility = View.VISIBLE
        }
    }

    fun copyToClipBoard(text: String){
        val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip: ClipData = ClipData.newPlainText("Copied data", text)
        clipBoard.setPrimaryClip(clip)
        Toast.makeText(this,"Copied to clipboard!", Toast.LENGTH_SHORT).show()
    }
}