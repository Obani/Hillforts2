package ie.app.william.hillforts.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import ie.app.william.hillforts.activities.HillfortAdapter
import ie.app.william.hillforts.activities.MainActivity
import kotlinx.android.synthetic.main.activity_hillforts_list.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import ie.app.william.hillforts.R
import ie.app.william.hillforts.main.MainApp
import ie.app.william.hillforts.models.HillfortModel
import org.jetbrains.anko.startActivity

class HillfortListActivity : AppCompatActivity(), HillfortListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillforts_list)
        app = application as MainApp

        toolbarMain.title = title
        setSupportActionBar(toolbarMain)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadHillforts()
    }

    private fun loadHillforts() {
        async(UI) {
            showHillforts(app.hillforts.findAll())
        }
    }

    fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<MainActivity>(200)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onHillfortClick(hillforts: HillfortModel) {
        startActivityForResult(intentFor<MainActivity>().putExtra("hillfort_edit", hillforts), 201)
    }
}