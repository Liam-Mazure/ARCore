package com.example.arcore

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import java.util.concurrent.CompletableFuture

class MainActivityViewModel : ViewModel() {

    var modelRenderables: List<ModelRenderable>? = null

    fun loadModels(context: Context){
        //Create a list of RenderableSource objects for each model
        val renderableSources = listOf(
            RenderableSource.builder()
                .setSource(context, Uri.parse("amongus.glb"), RenderableSource.SourceType.GLB)
                .setScale(0.5f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build(),

            RenderableSource.builder()
                .setSource(context, Uri.parse("charmander.glb"), RenderableSource.SourceType.GLB)
                .setScale(0.5f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build(),

            RenderableSource.builder()
                .setSource(context, Uri.parse("cubone.glb"), RenderableSource.SourceType.GLB)
                .setScale(0.5f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build(),

            RenderableSource.builder()
                .setSource(context, Uri.parse("mew.glb"), RenderableSource.SourceType.GLB)
                .setScale(0.5f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build(),

            RenderableSource.builder()
                .setSource(context, Uri.parse("pikachu.glb"), RenderableSource.SourceType.GLB)
                .setScale(0.5f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build(),

            RenderableSource.builder()
                .setSource(context, Uri.parse("snorlax.glb"), RenderableSource.SourceType.GLB)
                .setScale(0.5f)
                .setRecenterMode(RenderableSource.RecenterMode.ROOT)
                .build()
        )

        //Create a list of CompletableFuture objects for each model
        val futureRenderables = renderableSources.map { renderableSource ->
            ModelRenderable.builder()
                .setSource(context, renderableSource)
                .build()
        }

        //Wait for all CompletableFuture objects to complete
        CompletableFuture.allOf(*futureRenderables.toTypedArray()).thenAccept {
            modelRenderables = futureRenderables.mapNotNull { it.getNow(null) }
        }
    }

    fun placeModel(anchor: Anchor, context: Context, index: Int): Node?{
        //Check that model renderables have been loaded
        val modelRenderables = modelRenderables?: return null

        //Check that index is within the range of list
        if (index < 0 || index >= modelRenderables.size){
            return null
        }

        //Create a new Node to hold the model
        val node = Node()

        val anchorNode = AnchorNode(anchor)

        node.renderable = modelRenderables[index]

        //Add the node to the scene
        val arFragment = (context as MainActivity).supportFragmentManager.findFragmentById(R.id.ar_fragment) as ArFragment
        arFragment.arSceneView.scene.addChild(node)

        return node
    }
}