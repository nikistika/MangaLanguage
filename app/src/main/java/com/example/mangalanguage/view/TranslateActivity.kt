package com.example.mangalanguage.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseArray
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.mangalanguage.databinding.ActivityTranslateBinding
import com.example.mangalanguage.models.GoogleTranslate.TranslationRequest
import com.example.mangalanguage.network.TranslateApiClient
import com.example.mangalanguage.network.TranslateApiService
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class TranslateActivity : AppCompatActivity() {

    lateinit var binding: ActivityTranslateBinding
    lateinit var textEng: TextView
    lateinit var textRu: TextView
    lateinit var topAppBar: MaterialToolbar

    lateinit var text: String


    val translate: MutableLiveData<String> = MutableLiveData()
    val translateResult: LiveData<String> = translate

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        topAppBar = binding.topAppbar
        textEng = binding.textEng
        textRu = binding.textRu

        val apiService = TranslateApiClient.create()

        translateResult.observe(this, Observer {
        //Переводчик
        val translationRequest = TranslationRequest(
            query = it,
            sourceLanguage = "en",
            targetLanguage = "ru",
            apiKey = "AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw"
        )

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response  = apiService.translate(
                    query = translationRequest.query,
                    sourceLanguage = translationRequest.sourceLanguage,
                    targetLanguage = translationRequest.targetLanguage,
                    apiKey = translationRequest.apiKey
                )

                val translatedText = response.data.translations.first().translatedText
// Используйте полученный перевод (translatedText)
                textRu.text = translatedText
            } catch (e: Exception) {
// Обработка ошибки
            }
        }
        })

        topAppBar.setNavigationOnClickListener {
            finish()
            Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
        }

        val imageUrl = intent.getStringExtra("message_key")
        if (imageUrl != null) {
        Picasso.get().load(imageUrl).into(object : com.squareup.picasso.Target {
            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                // Отображаем загруженное изображение в ImageView
                // imageView.setImageBitmap(bitmap)

                // Передаем изображение в CropImage для обрезки
                val uri = getImageUriFromBitmap(bitmap)
                startCropImageActivity(uri)
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                // Обработка ошибки загрузки изображения
                Toast.makeText(this@TranslateActivity, "Error loading image", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                // Подготовка к загрузке изображения
            }
        })
    }
    }
    private fun startCropImageActivity(imageUri: Uri) {
        CropImage.activity(imageUri)
            .setGuidelines(CropImageView.Guidelines.ON)
            .start(this)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Проверяем, было ли выполнено обрезание изображения и успешно ли оно завершилось
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Получаем результат обрезки изображения
            val result = CropImage.getActivityResult(data)
            val croppedImageUri = result.uri // Получаем Uri обрезанного изображения

            // Отображаем обрезанное изображение в ImageView
//            binding.imageView.setImageURI(croppedImageUri)

            // Запускаем метод для распознавания текста на обрезанном изображении
            recognizeTextFromImage(croppedImageUri)
        } else {
            // В случае ошибки при обрезке изображения выводим Toast
            Toast.makeText(this, "Error cropping image", Toast.LENGTH_SHORT).show()
        }

    }

    private fun recognizeTextFromImage(imageUri: Uri) {
        // Создаем объект TextRecognizer для распознавания текста
        val textRecognizer = TextRecognizer.Builder(this).build()

        // Проверяем доступность распознавателя текста
        if (!textRecognizer.isOperational) {
            // Если распознаватель недоступен, выводим сообщение
            Toast.makeText(this, "Text recognizer not available", Toast.LENGTH_SHORT).show()
        } else {
            // Загружаем изображение из URI в виде Bitmap
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))

            // Создаем Frame из Bitmap для обработки с помощью Google Vision API
            val frame = Frame.Builder().setBitmap(bitmap).build()

            // Создаем SparseArray для хранения текстовых блоков
            val textBlocks = SparseArray<TextBlock>()

            // Выполняем обнаружение текстовых блоков на изображении
            val items = textRecognizer.detect(frame)
            for (i in 0 until items.size()) {
                val item = items.valueAt(i)
                textBlocks.put(i, item)
            }

            // Обрабатываем
            // обнаруженные текстовые блоки
            for (i in 0 until textBlocks.size()) {
                val textBlock = textBlocks.get(i)
                 text = textBlock.value.replace("\n", " ")

                // Делаем что-то с распознанным текстом, например, выводим его в TextView
                textEng.text = text

                translate.postValue(text)
            }
        }
    }

    private fun getImageUriFromBitmap(bitmap: Bitmap?): Uri {
        val imagesFolder = File(cacheDir, "images")
        if (!imagesFolder.exists()) {
            imagesFolder.mkdirs()
        }

        val imageFile = File(imagesFolder, "image.jpg")
        val outputStream = FileOutputStream(imageFile)
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()

        return FileProvider.getUriForFile(this, "$packageName.fileprovider", imageFile)
    }
}