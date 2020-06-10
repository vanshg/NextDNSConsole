package sh.van.nextdnsconsole

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.compose.setValue
import androidx.compose.state
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.layout.padding
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import com.google.android.material.navigation.NavigationView
import sh.van.nextdnsconsole.databinding.ActivityMainBinding
import timber.log.Timber
import kotlin.reflect.KProperty
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.ui.foundation.Text
import androidx.ui.layout.Column
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview

data class TabItem(val title: Int, val icon: Int, val ui: () -> Unit)

@Preview
@Composable
fun BottomNav() {
    val items = listOf(
        R.string.menu_setup to R.drawable.ic_menu_setup,
        R.string.menu_security to R.drawable.ic_menu_security,
        R.string.menu_privacy to R.drawable.ic_menu_privacy,
        R.string.menu_parental_control to R.drawable.ic_menu_parental_control,
        R.string.menu_blacklist to R.drawable.ic_menu_blacklist,
        R.string.menu_whitelist to R.drawable.ic_menu_whitelist,
        R.string.menu_analytics to R.drawable.ic_menu_analytics,
        R.string.menu_logs to R.drawable.ic_menu_logs,
        R.string.menu_settings to R.drawable.ic_menu_settings
    )
    var selectedIndex by state { 0 }
    Column {
        TabRow(
            selectedIndex = selectedIndex,
            scrollable = true,
            items = items
        ) { index, item ->
            Tab(
                text = { Text(text = stringResource(id = item.first)) },
                icon = { Icon(asset = vectorResource(id = item.second)) },
                selected = selectedIndex == index,
                onSelected = { selectedIndex = index }
            )
        }
        when (items[selectedIndex].first) {
            R.string.menu_setup -> Text("This is setup") // SetupScreen()
            R.string.menu_security -> Text("This is security") // SecurityScreen()
            R.string.menu_privacy -> Text("This is privacy") // PrivacyScreen()
            R.string.menu_parental_control -> Text("This is parental_control") // ParentalControlScreen()
            R.string.menu_blacklist -> Text("This is blacklist") // BlacklistScreen()
            R.string.menu_whitelist -> Text("This is whitelist") // WhitelistScreen()
            R.string.menu_analytics -> Text("This is analytics") // AnalyticsScreen()
            R.string.menu_logs -> Text("This is logs") // LogsScreen()
            R.string.menu_settings -> Text("This is settings") // SettingsScreen()
        }
    }
}


class MainActivity : AppCompatActivity() {

//    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { BottomNav() }
//        val binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        setSupportActionBar(binding.appBar.toolbar)
//        appBarConfiguration = AppBarConfiguration(binding.navView.menu, binding.drawerLayout)
//        val navController = findNavController(R.id.nav_host_fragment)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        binding.navView.setupWithNavController(navController)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment)
//        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
//    }
}
