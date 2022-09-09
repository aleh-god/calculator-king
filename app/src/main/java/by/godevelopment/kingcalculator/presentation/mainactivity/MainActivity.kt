package by.godevelopment.kingcalculator.presentation.mainactivity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.godevelopment.kingcalculator.R
import by.godevelopment.kingcalculator.commons.TAG
import by.godevelopment.kingcalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    private val listener by lazy {
        NavController.OnDestinationChangedListener { _, destination, _ ->
            binding.toolbar.menu.findItem(R.id.menu_players)?.isVisible =
                destination.id == R.id.partiesListFragment
            binding.toolbar.menu.findItem(R.id.menu_delete_all)?.isVisible =
                destination.id == R.id.partiesListFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        setupListeners()

    }

    private fun setupListeners() {
        navController.addOnDestinationChangedListener(listener)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }    // For Game Add Form Screen
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
            navController.navigate(R.id.action_partiesListFragment_to_settingsFragment)
            true
        }
        R.id.menu_help -> {
            showHelpText()
            true
        }
        else -> { super.onOptionsItemSelected(item) }
    }

    private fun showHelpText() {
        val helpText = when (navController.currentDestination?.id) {
            R.id.partiesListFragment -> R.string.help_text_parties_list
            R.id.playersListFragment -> R.string.help_text_players_list
            R.id.playerCardFragment -> R.string.help_text_player_card
            R.id.playerInfoFragment -> R.string.help_text_player_info
            R.id.playerAddFormFragment -> R.string.help_text_player_add_form
            R.id.partyCardFragment -> R.string.help_text_party_card
            R.id.partyInfoFragment -> R.string.help_text_party_info
            R.id.partyAddFormFragment -> R.string.help_text_player_add_form
            R.id.gameAddFormFragment -> R.string.help_text_game_add_form
            R.id.settingsFragment -> R.string.help_text_data_settings
            else -> R.string.message_error_data_null
        }

        Snackbar.make(
            binding.root,
            helpText,
            Snackbar.LENGTH_INDEFINITE
        )
            .setAction(R.string.snackbar_btn_neutral_ok) {}
            .show()
    }

    override fun onDestroy() {
        navController.removeOnDestinationChangedListener(listener)
        _binding = null
        super.onDestroy()
    }
}
