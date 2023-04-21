package com.example.arcore

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.ar.sceneform.SceneView
import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.ar.core.Session
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import java.util.concurrent.CompletableFuture


class MainActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 0
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as CustomArFragment

        checkCameraPermission()

        //Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)

//        val objList = listOf(
//            "android.resource://com.example.arcore/${R.raw.cubone}",
//            "android.resource://com.example.arcore/${R.raw.snorlax}",
//        )

        //Create the ObjAdapter
//        val objAdapter = ObjAdapter(this, objList)

        //Set the adapter for the RecyclerView
//        recyclerView.adapter = objAdapter

        println("\n\n\n\nModel Builder\n\n\n\n")
        val renderableSource = RenderableSource.builder()
            .setSource(this, Uri.parse("cubone.glb"), RenderableSource.SourceType.GLB)
            .setScale(0.5f)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()

        val futureRenderable = ModelRenderable.builder()
            .setSource(this, renderableSource)
            .build()

        arFragment.setOnTapArPlaneListener { hitResult, plane, motionEvent ->
            val anchor = hitResult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)

            println("Planed tapped\n\n")

            futureRenderable.thenAccept { modelRenderable ->
                val modelNode = Node()
                modelNode.renderable = modelRenderable
                anchorNode.addChild(modelNode)
            }
        }
    }

    fun loadModel(){

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

        }
    }


}