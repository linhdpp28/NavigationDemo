package linhdo.inface.features.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header.*
import linhdo.inface.ConfigKey
import linhdo.inface.InApplication
import linhdo.inface.R
import linhdo.inface.ServiceLocator
import linhdo.inface.extensions.getFBAuth
import linhdo.inface.extensions.getSavedConfigPrefs
import linhdo.inface.extensions.startActivity
import linhdo.inface.features.auth.MainActivity


class HomeActivity : AppCompatActivity() {
    val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if (savedInstanceState != null && !savedInstanceState.isEmpty) {
        }

        setSupportActionBar(toolbar ?: return)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(toolbar ?: return, navController)
        NavigationUI.setupWithNavController(navigationSideMenu ?: return, navController)

        viewModel.setUserRepo(ServiceLocator.instance(this).getUserRepo())
        viewModel.getUserDetail()?.observe(this, Observer {
            tvUsername?.text = it?.username ?: ""
            tvEmail?.text = it?.email ?: ""
        })

        navigationSideMenu?.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mnuLogout -> {
                    InApplication.instance.getFBAuth().apply {
                        signOut()
                        startActivity<MainActivity>()
                    }
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        InApplication.instance.getSavedConfigPrefs().edit {
            this.putString(ConfigKey.LANGUAGE_KEY, InApplication.instance.config.language)
        }
    }
}