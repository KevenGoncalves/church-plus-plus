package com.example.churchplusplus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.churchplusplus.databinding.ActivityMainBinding
import com.example.churchplusplus.screens.contribuicoes.ContribuicoesFragment
import com.example.churchplusplus.screens.dashboard.DashboardFragment
import com.example.churchplusplus.screens.definicoes.DefinicoesFragment
import com.example.churchplusplus.screens.despesas.DespesasFragment
import com.example.churchplusplus.screens.login.LoginFragment
import com.example.churchplusplus.screens.membros.MembrosFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {

   private lateinit var binding: ActivityMainBinding
   private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        navView= binding.bottomNavigationView
        setupNavigation()

        val dashboardFragment = DashboardFragment()
        val despesasFragment = DespesasFragment()
        val membrosFragment = MembrosFragment()
        val contribuicoesFragment = ContribuicoesFragment()
        val definicoesFragment = DefinicoesFragment()

        navView.setOnItemSelectedListener {
            when(it.itemId){
                    R.id.dashboard -> setCurrentFragment(dashboardFragment)
                    R.id.despesas -> setCurrentFragment(despesasFragment)
                    R.id.membros1 -> setCurrentFragment(membrosFragment)
                    R.id.contribuicos -> setCurrentFragment(contribuicoesFragment)
                    R.id.definicoes -> setCurrentFragment(definicoesFragment)
                else -> setCurrentFragment(dashboardFragment)
            }
        }
    }

    private fun setupNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.myNavHost) as NavHostFragment
        val navController = navHost.navController

        navController.addOnDestinationChangedListener { _, destination, _->
            when (destination.id) {
                R.id.loginFragment-> navView.visibility = View.GONE
                R.id.registerFragment-> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }
        }
    }

    private fun setCurrentFragment(fragment:Fragment): Boolean {
        supportFragmentManager.beginTransaction().
        replace(R.id.myNavHost,fragment)
            .commit()

        return false
    }

}