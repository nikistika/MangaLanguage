package com.example.mangalanguage

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.FileOutputStream

class MainActivity2 : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var selectImageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        imageView = findViewById(R.id.imageView2)
        selectImageButton = findViewById(R.id.selectImageButton2)

        // Устанавливаем слушатель клика на кнопку "selectImageButton"
        selectImageButton.setOnClickListener {
            // Используем Picasso для загрузки изображения по URL
            val imageUrl = "https://www.mangaread.org/wp-content/uploads/WP-manga/data/manga_613a60983ceb0/a7efb9dbb3458d5be047f8f582815380/19.jpeg"
            Picasso.get().load(imageUrl).into(object : com.squareup.picasso.Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    // Отображаем загруженное изображение в ImageView
                    imageView.setImageBitmap(bitmap)

                    // Передаем изображение в CropImage для обрезки
                    val uri = getImageUriFromBitmap(bitmap)
                    startCropImageActivity(uri)
                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    // Обработка ошибки загрузки изображения
                    Toast.makeText(this@MainActivity2, "Error loading image", Toast.LENGTH_SHORT).show()
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

    // Остальной код остается без изменений


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Проверяем, было ли выполнено обрезание изображения и успешно ли оно завершилось
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Получаем результат обрезки изображения
            val result = CropImage.getActivityResult(data)
            val croppedImageUri = result.uri // Получаем Uri обрезанного изображения

            // Отображаем обрезанное изображение в ImageView
            imageView.setImageURI(croppedImageUri)

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
                val text = textBlock.value

                // Делаем что-то с распознанным текстом, например, выводим его в Toast
                Toast.makeText(this, "Recognized text: $text", Toast.LENGTH_SHORT).show()
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
