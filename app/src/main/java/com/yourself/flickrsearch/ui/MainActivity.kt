package com.yourself.flickrsearch.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.yourself.flickrsearch.R

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private var imageListFragment = ImageListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
        toolbar.setNavigationIcon(R.mipmap.ic_launcher_foreground)


        if (savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(
                R.id.container,
                imageListFragment,
                ImageListFragment.TAG
            ).commitNow()


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
               imageListFragment.gotoListTop()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showHideActionBarWith(title: String?, showBackButton: Boolean) {

    }

}
