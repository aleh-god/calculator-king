package by.godevelopment.kingcalculator.presentation.mainactivity

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.coroutineScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.ActivityMainBinding
import by.godevelopment.kingcalculator.domain.commons.models.ResultDataBase
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var mainActivityRepository: MainActivityRepository

    private val navController by lazy {
        Log.i(TAG, "navController by lazy: ")
        findNavController(R.id.nav_host_fragment)
    }

    private val listener by lazy {
        Log.i(TAG, "listener by lazy: ${binding.toolbar.menu} ")
        NavController.OnDestinationChangedListener { _, destination, _ ->
            Log.i(TAG, "listener by destination: $destination")
            binding.toolbar.menu.findItem(R.id.menu_players)?.isEnabled =
                destination.id == R.id.partiesListFragment
            binding.toolbar.menu.findItem(R.id.menu_delete_all)?.isEnabled =
                destination.id == R.id.partiesListFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        Log.i(TAG, "onCreate: ")
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        setupListeners()
    }

    private fun setupListeners() {
        navController.addOnDestinationChangedListener(listener)
        setupConfirmDialogListener()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_players -> {
            navController.navigate(R.id.action_partiesListFragment_to_playersListFragment)
            true
        }
        R.id.menu_delete_all -> {
            showDeleteConfirmDialog()
            true
        }
        R.id.menu_settings -> {
            Log.i(TAG, "onOptionsItemSelected: R.id.menu_settings")
            /*
             TODO("implement menu settings")
            val fragment = SettingsFragment.newInstance {
                Log.i(TAG, "SettingsFragment.newInstance: ")
            }
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
             */
            true
        }
        else -> { super.onOptionsItemSelected(item) }
    }

    private fun showDeleteConfirmDialog() {
        val dialogFragment = DeleteConfirmDialogFragment()
        dialogFragment.show(supportFragmentManager, DeleteConfirmDialogFragment.TAG)
    }

    private fun setupConfirmDialogListener() {
        supportFragmentManager.setFragmentResultListener(
            DeleteConfirmDialogFragment.REQUEST_KEY,
            this,
            FragmentResultListener { _, result ->
                when(result.getInt(DeleteConfirmDialogFragment.KEY_RESPONSE)) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        Log.i(TAG, "setupConfirmDialogListener: BUTTON_POSITIVE")
                        deleteAllPartyNotes()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {}
                }
            }
        )
    }

    private fun deleteAllPartyNotes() {
        lifecycle.coroutineScope.launch {
            val deleteResult = mainActivityRepository.deleteAllPartyNotes()
            when(deleteResult) {
                is ResultDataBase.Error -> showMessage(getString(deleteResult.message))
                is ResultDataBase.Success -> showMessage(
                    getString(R.string.message_delete_parties_result, deleteResult.value)
                )
            }
        }
    }

    private fun showMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(listener)
        _binding = null
        super.onDestroy()
    }
}
