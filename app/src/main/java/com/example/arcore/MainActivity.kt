package com.example.arcore

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.media.Image
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable


class MainActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 0

    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as CustomArFragment

        checkCameraPermission()

        //Load the models
        viewModel.loadModels(this)

        //ImageViews
        val amongus = findViewById<ImageView>(R.id.amongus)
        amongus.setOnClickListener {
            println("Amongus")
            index = 0
        }

        val charmander = findViewById<ImageView>(R.id.charmander)
        charmander.setOnClickListener {
            println("Charmander")
            index = 1
        }

        val cubone = findViewById<ImageView>(R.id.cubone)
        cubone.setOnClickListener {
            println("Cubone")
            index = 2
        }

        val mew = findViewById<ImageView>(R.id.mew)
        mew.setOnClickListener {
            println("Mew")
            index = 3
        }

        val pikachu = findViewById<ImageView>(R.id.pikachu)
        pikachu.setOnClickListener {
            println("Pikachu")
            index = 4
        }

        val snorlax = findViewById<ImageView>(R.id.snorlax)
        snorlax.setOnClickListener {
            println("Snorlax")
            index = 5
        }

        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            viewModel.placeModel(index, anchorNode)

        }
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            }
            else{
                //Permission denied
                Toast.makeText(this, "Camera permission required for AR", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun checkCameraPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        }
        else{
            println("\nPermission already granted")
        }
    }


}