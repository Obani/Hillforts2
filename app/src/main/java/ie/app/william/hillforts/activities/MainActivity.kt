package ie.app.william.hillforts.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import ie.app.william.hillforts.R
import ie.app.william.hillforts.activities.MapsActivity
import ie.app.william.hillforts.helpers.readImage
import ie.app.william.hillforts.helpers.readImageFromPath
import ie.app.william.hillforts.helpers.showImagePicker
import ie.app.william.hillforts.main.MainApp
import ie.app.william.hillforts.models.Location
import ie.app.william.hillforts.models.HillfortModel

class MainActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2
    lateinit var map: GoogleMap

    fun configureMap() {
        map.uiSettings.setZoomControlsEnabled(true)
        val loc = LatLng(hillfort.lat, hillfort.lng)
        val options = MarkerOptions().title(hillfort.title).position(loc)
        map.addMarker(options)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, hillfort.zoom))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        app = application as MainApp
        mapView2.onCreate(savedInstanceState);

        mapView2.getMapAsync {
            map = it
            configureMap()
        }

        val defaultLocation = Location(52.245696, -7.139102, 15f)

        if (intent.hasExtra("placemark_edit")) {
            edit = true
            hillfort = intent.extras.getParcelable<HillfortModel>("placemark_edit")
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            if (hillfort.image != null) {
                chooseImage.setText(R.string.change_hillfort_image)
            }
        } else {
            hillfort.lat = defaultLocation.lat
            hillfort.lng = defaultLocation.lng
            hillfort.zoom = defaultLocation.zoom
        }

        hillfortLocation.setOnClickListener {
            if (hillfort.zoom != 0f) {
                defaultLocation.lat = hillfort.lat
                defaultLocation.lng = hillfort.lng
                defaultLocation.zoom = hillfort.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", defaultLocation), LOCATION_REQUEST)
        }

        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            //btnAdd.setText(R.string.save_hillfort)

            hillfort = intent.extras.getParcelable<HillfortModel>("hillfort_edit")
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            if (hillfort.image != null) {
                chooseImage.setText(R.string.change_hillfort_image)
            }
        }

        /*btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()

            if (edit) {
                app.hillforts.update(hillfort.copy())
                setResult(201)
                finish()
            }
            else {
                if (hillfort.title.isNotEmpty()) {
                    app.hillforts.create(hillfort.copy())
                    setResult(200)
                    finish()
                }
                else {
                    toast(R.string.enter_hillfort_title)
                }
            }
        }*/

        fun save() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()

            if (edit) {
                app.hillforts.update(hillfort.copy())
                setResult(201)
                finish()
            } else {
                if (hillfort.title.isNotEmpty()) {
                    app.hillforts.create(hillfort.copy())
                    setResult(200)
                    finish()
                } else {
                    toast(R.string.enter_hillfort_title)
                }
            }
        }

        fun onOptionsItemSelected(item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.item_save -> {
                    save()
                }
                R.id.item_cancel -> {
                    setResult(RESULT_CANCELED)
                    finish()
                }
            }
            return super.onOptionsItemSelected(item)
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        hillfortLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (hillfort.zoom != 0f) {
                location.lat = hillfort.lat
                location.lng = hillfort.lng
                location.zoom = hillfort.zoom
            }
            startActivityForResult(intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    hillfort.image = data.getData().toString()
                    hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_hillfort_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras.getParcelable<Location>("location")
                    hillfort.lat = location.lat
                    hillfort.lng = location.lng
                    hillfort.zoom = location.zoom
                    configureMap()
                }
            }
        }

        fun onDestroy() {
            super.onDestroy()
            mapView2.onDestroy()
        }

        fun onLowMemory() {
            super.onLowMemory()
            mapView2.onLowMemory()
        }

        fun onPause() {
            super.onPause()
            mapView2.onPause()
        }

        fun onResume() {
            super.onResume()
            mapView2.onResume()
        }

        fun onSaveInstanceState(outState: Bundle?) {
            super.onSaveInstanceState(outState)
            mapView2.onSaveInstanceState(outState)
        }

        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_hillfort, menu)
            return super.onCreateOptionsMenu(menu)
        }

        fun onOptionsItemSelected(item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.item_cancel -> {
                    setResult(RESULT_CANCELED)
                    finish()
                }
            }
            return super.onOptionsItemSelected(item)
        }

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            when (requestCode) {
                IMAGE_REQUEST -> {
                    if (data != null) {
                        hillfort.image = data.getData().toString()
                        hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                        chooseImage.setText(R.string.change_hillfort_image)
                    }
                }
                LOCATION_REQUEST -> {
                    if (data != null) {
                        val location = data.extras.getParcelable<Location>("location")
                        hillfort.lat = location.lat
                        hillfort.lng = location.lng
                        hillfort.zoom = location.zoom
                    }
                }
            }
        }
    }
}
